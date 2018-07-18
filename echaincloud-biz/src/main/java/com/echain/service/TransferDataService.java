package com.echain.service;

import com.echain.dao.EcTransactionDao;
import com.echain.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class TransferDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferDataService.class);

    @Autowired
    EcTransactionDao transactionDao;

    @Autowired
    UserService userService;

    @Autowired
    PointService pointService;

    /**
     * 订单上传
     */
    public EcTransaction saveTransferData(EcTransaction transaction) {
        try {
            transactionDao.insertSelective(transaction);
            return transaction;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }

        return null;
    }

    public EcTransaction selectById(Long id) {
        return transactionDao.selectByPrimaryKey(id);
    }

    public Long selectMinIdNokeyAndChecked() {
        return transactionDao.selectMinIdNokeyAndChecked();
    }

    public boolean saveTransferData(Long userId, MultipartFile file, String realPathStar, String realPathEnd)
            throws IllegalStateException, IOException {
        EcTransaction ect = new EcTransaction();
        ect.setCreateTime(new Date());
        ect.setUserId(userId);
        ect.setDappId(0L);
        ect.setUserDappId(0L);
        ect.setTransactionPicture(realPathEnd);
        ect.setStatus("0");
        ect.setDescribeText("");
        ect.setDescribeMd5("");
        ect.setTransactionKey("0");
        Integer result = transactionDao.selectCount(ect);

        if (result <= 0) {
            result = transactionDao.insert(ect);
        }

        if (result > 0) {
            // 添加积分 TODO

            // 将图片存放起来
            File img = new File(realPathStar + realPathEnd);
            if (!img.getParentFile().exists()) {
                img.getParentFile().mkdirs();
            }
            file.transferTo(img);

            return true;
        }
        return false;
    }

    /**
     * 根据用户id查询在echain上传订单图片
     */

    public List<EcTransaction> selectEchainListByUserId(Long userId) {
        return transactionDao.selectEchainListByUserId(userId);
    }

    /**
     * 跟新订单物流信息
     */
    public boolean updateEcTransaction(EcTransaction ecTransaction) {
        Integer result = transactionDao.updateByPrimaryKeySelective(ecTransaction);
        if (result > 0) {
            // 给用户增加积分 30
            String code = changePointsFormEchain(ecTransaction.getId(), 30L);
            if ("200".equals(code)) {
                return true;
            }
            return true;
        }
        return false;
    }

    /**
     * 获取echain上传的待审核订单图片列表
     *
     * @return
     */
    public List<EcTransaction> selectListForCheck() {
        return transactionDao.selectListForCheck();
    }

    public List<EcTransaction> selectListForCheckWithLimit(Integer index) {
        Integer m = (index - 1) * 10;
        return transactionDao.selectListForCheckWithLimit(m, 10);
    }

    /**
     * 审核订单
     *
     * @return
     */
    public boolean updateEcTransaction(Long id, boolean check) {
        String status = "0";
        status = check ? "1" : "2";
        EcTransaction tr = new EcTransaction();
        tr.setId(id);
        tr.setStatus(status);
        Integer result = transactionDao.updateByPrimaryKeySelective(tr);
        if (check == true && result > 0) {
            // 添加给用户添加积分 20
            String code = changePointsFormEchain(id, 20L);
            if ("200".equals(code)) {
                return true;
            }
        }
        return false;
    }

    public String changePointsFormEchain(Long transactionId, Long points) {
        Long userId = transactionDao.selectByPrimaryKey(transactionId).getUserId();
        EcUser user = userService.selectUserByPrimaryKey(userId);
        EcUserPoints userSendPoint = null;
        EcUserPoints userReceivePoint = null;

        EcUser userSend = userService.getUserByPhoneNumber("0");
        if (userSend != null) {
            userSendPoint = userService.getUserPointByUserId(userSend.getId());
        }

        EcUser userReceive = userService.getUserByPhoneNumber(user.getPhoneNumber());
        if (userReceive != null) {
            userReceivePoint = userService.getUserPointByUserId(userReceive.getId());
        }

        EcDapp dapp = userService.getDappByDappname("echain");
        EcUserDapp userSendDapp = userService.getUserDappByUserIdAndDappId(userSend.getId(), dapp.getId(), null);
        EcUserDapp userRecevieDapp = userService.getUserDappByUserIdAndDappId(userReceive.getId(), dapp.getId(),
                null);

        return pointService.changePoints(userSendPoint.getUserId(), userReceivePoint.getUserId(), "", points, transactionId, dapp,
                userSendDapp, userRecevieDapp);
    }

    public EcTransaction selectTransactionById(Long transactionId) {
        return transactionDao.selectByPrimaryKey(transactionId);
    }

    public EcTransaction getTransactionsByPlatform(String dappName, String transactionPlatform, String transactionNo) {
        EcDapp dapp = userService.getDappByDappname(dappName);

        EcTransaction result = transactionDao.getTransactionsByPlatform(dapp.getId(), transactionPlatform, transactionNo);

        return result == null ? new EcTransaction() : result;
    }
}
