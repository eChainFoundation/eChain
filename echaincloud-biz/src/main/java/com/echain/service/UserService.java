package com.echain.service;

import com.echain.constant.RedisConstant;
import com.echain.dao.EcUserDao;
import com.echain.dao.EcUserPointsDao;
import com.echain.dao.EcUserWalletDao;
import com.echain.entity.*;
import com.echain.manager.RedisKeyMag;
import com.echain.redis.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    EcUserDao userDao;

    @Autowired
    EcUserPointsDao userPointsDao;

    @Autowired
    UserDappService userDappService;

    @Autowired
    DappService dappService;

    @Autowired
    EcUserWalletDao userWalletDao;

    @Autowired
    LogisticsCompanyService logisticsCompanyService;

    @Autowired
    RedisKeyMag redisKeyMag;

    private EcUser selectByPhoneNumber(String phoneNumber) {
        String key = Redis.mergeKey(RedisConstant.USER, "phoneNumber", phoneNumber);
        EcUser o = redisKeyMag.getValue(key, EcUser.class);
        if (o != null) {
            return o;
        }

        EcUser result = userDao.selectByPhoneNumber(phoneNumber);
        redisKeyMag.setValue(key, result);

        return result;
    }

    public EcUser insertSelective(EcUser user) {
        userDao.insertSelective(user);
        return user;
    }

    public EcUser selectByPrimaryKey(Long userId) {
        String key = Redis.mergeKey(RedisConstant.USER, "userId", Long.toString(userId));
        EcUser o = redisKeyMag.getValue(key, EcUser.class);
        if (o != null) {
            return o;
        }

        EcUser result = userDao.selectByPrimaryKey(userId);
        redisKeyMag.setValue(key, result);

        return result;
    }

    /********************************缓存分割线********************************/

    public EcUser getUserByPhoneNumber(String phoneNumber,String country) {
        EcUser user = selectByPhoneNumber(phoneNumber);
        if (user == null) {
            user = new EcUser();
            user.setPhoneNumber(phoneNumber);
            user.setCreateTime(new Date());
            user.setCountry(country);
            insertSelective(user);
        }
        return user;
    }

    public EcUser getUserByPhoneNumber(String phoneNumber) {
        EcUser user = selectByPhoneNumber(phoneNumber);
        if (user == null) {
            user = new EcUser();
            user.setPhoneNumber(phoneNumber);
            user.setCreateTime(new Date());
            insertSelective(user);
        }
        return user;
    }

    public EcDapp getDappByDappname(String dappName) {
        EcDapp dapp = dappService.selectByDappName(dappName);
        if (dapp == null) {
            dapp = new EcDapp();
            dapp.setCreateTime(new Date());
            dapp.setDappName(dappName);
            dapp.setDappLogo("logo");
            dapp.setDappLogo("/static/img/earth.png");
            dapp.setDappUrl("url");
            dappService.insertSelective(dapp);
        }
        return dapp;
    }

    public EcUserDapp getUserDappByUserIdAndDappId(Long userId, Long dappId, String isUploadSingle) {
        EcUserDapp userDapp = userDappService.selectByUserIdAndDappId(userId, dappId);
        if (userDapp == null) {
            userDapp = new EcUserDapp();
            userDapp.setCreateTime(new Date());
            userDapp.setDappId(dappId);
            userDapp.setUserId(userId);
            if (isUploadSingle != null && !"".equals(isUploadSingle))
                userDapp.setIsUploadSingle(isUploadSingle);
            userDapp.setConsumePoints(0l);
            userDapp.setGetPoints(0l);
            userDappService.insertSelective(userDapp);
        }
        return userDapp;
    }

    public EcUserDapp getUserDappByUserIdAndDappId(Long userId, Long dappId, String isUploadSingle, String upEchainFrequency) {
        EcUserDapp userDapp = userDappService.selectByUserIdAndDappId(userId, dappId);
        if (userDapp == null) {
            userDapp = new EcUserDapp();
            userDapp.setCreateTime(new Date());
            userDapp.setDappId(dappId);
            userDapp.setUserId(userId);
            if (isUploadSingle != null && !"".equals(isUploadSingle))
                userDapp.setIsUploadSingle(isUploadSingle);
            userDapp.setConsumePoints(0l);
            userDapp.setGetPoints(0l);
            userDapp.setUpEchainFrequency(upEchainFrequency);
            userDappService.insertSelective(userDapp);
        }
        return userDapp;
    }

    /**
     * 抓取用户在每一个app获取总数和消费总数 <br>
     */
    public List<EcUserDapp> getUserDappPointsByUserId(Long userId) {
        return userDappService.selectByUserId(userId);
    }

    public EcLogisticsCompany getLogisticsCompanyByCompanyname(String logisticsCompanyName) {
        EcLogisticsCompany logisticsCompany = logisticsCompanyService.selectByCompanyName(logisticsCompanyName);
        if (logisticsCompany == null) {
            logisticsCompany = new EcLogisticsCompany();
            logisticsCompany.setCompanyName(logisticsCompanyName);
            logisticsCompany.setCreateTime(new Date());
            logisticsCompanyService.insertSelective(logisticsCompany);
        }
        return logisticsCompany;
    }

    public EcUserPoints getUserPointByUserId(Long userId) {
        EcUserPoints userPoints = userPointsDao.selectByUserId(userId);
        if (userPoints == null) {
            userPoints = new EcUserPoints();
            userPoints.setUserId(userId);
            userPoints.setAllPoints(0l);
            userPoints.setConsumePoints(0l);
            userPoints.setFreezePoints(0l);
            userPoints.setNowPoints(0l);
            userPoints.setCreateTime(new Date());
            userPointsDao.insertSelective(userPoints);
        }
        return userPoints;
    }

    /**
     * 获取用户剩余积分
     */
    public EcUserPoints getUserPointByUserIdNoInsert(Long userId) {
        return userPointsDao.selectByUserId(userId);
    }

    public EcUser getUserByPhoneNumberNoInsert(String phoneNumber) {
        return selectByPhoneNumber(phoneNumber);
    }

    /**
     * 查询dapp信息
     */
    public EcDapp getDappByDappId(Long dappId) {
        return dappService.selectByPrimaryKey(dappId);
    }

    /**
     * 查询用户
     */
    public EcUser selectUserByPrimaryKey(Long userId) {
        return selectByPrimaryKey(userId);
    }

    public EcUserWallet saveOrUpdateUserWallet(EcUser user, String wallet) {
        EcUserWallet userWallet = userWalletDao.selectByUserId(user.getId());
        if (userWallet == null) {
            userWallet = new EcUserWallet();
            userWallet.setUserId(user.getId());
            userWallet.setCreateTime(new Date());
            userWallet.setWallet(wallet);
            userWallet.setType("1");
            userWalletDao.insertSelective(userWallet);
        } else {
            userWallet.setWallet(wallet);
            userWalletDao.updateByPrimaryKeySelective(userWallet);
        }
        return userWallet;
    }

    public List<EcUser> selectUserBySingleUpload() {
        return userDao.selectSingleUploadUsers();
    }

    public EcUserWallet getUserWalletByUserId(Long userId) {
        EcUserWallet userWallet = userWalletDao.selectByUserId(userId);
        if (userWallet == null) {
            userWallet = new EcUserWallet();
            userWallet.setUserId(userId);
            userWallet.setCreateTime(new Date());
            userWallet.setType("1");
            userWalletDao.insertSelective(userWallet);
        }
        return userWallet;
    }

}
