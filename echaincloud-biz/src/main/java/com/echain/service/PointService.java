package com.echain.service;

import com.echain.dao.EcPointsPoolDao;
import com.echain.dao.EcTakePointsDao;
import com.echain.dao.EcTransactionDao;
import com.echain.dao.EcUserPointsDao;
import com.echain.entity.*;
import com.echain.helper.TranscationExecutor;
import com.echain.manager.PointMag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PointService {

    @Autowired
    EcUserPointsDao userPointsDao;

    @Autowired
    PointMag pointMag;

    @Autowired
    UserDappService userDappService;

    @Autowired
    EcTransactionDao transactionDao;

    @Autowired
    EcPointsPoolDao pointsPoolDao;

    @Autowired
    EcTakePointsDao takePointsDao;

    private void insertEcPoints(Long points, Long transactionId, EcUserDapp userSendDapp, EcUserDapp userReceiveDapp) {
        EcPoints epSend = new EcPoints();
        epSend.setCreateTime(new Date());
        epSend.setDappId(userSendDapp.getDappId());
        epSend.setPoints(points);
        epSend.setTransactionId(transactionId);
        epSend.setUserDappId(userSendDapp.getId());
        epSend.setUserId(userSendDapp.getUserId());
        epSend.setType("0");
        pointMag.insertSelective(epSend);

        EcPoints epReceive = new EcPoints();
        epReceive.setCreateTime(new Date());
        epReceive.setDappId(userReceiveDapp.getDappId());
        epReceive.setPoints(points);
        epReceive.setTransactionId(transactionId);
        epReceive.setUserDappId(userReceiveDapp.getId());
        epReceive.setUserId(userReceiveDapp.getUserId());
        epReceive.setType("1");
        pointMag.insertSelective(epReceive);
    }

    private void updateUserPoints(Long points, EcUserPoints userSendPoint, EcUserPoints userReceivePoint) {
        userSendPoint.setAllPoints(userSendPoint.getAllPoints() - points);
        userSendPoint.setNowPoints(userSendPoint.getNowPoints() - points);
        userSendPoint.setConsumePoints(userSendPoint.getConsumePoints() + points);
        userPointsDao.updateByPrimaryKeySelective(userSendPoint);

        userReceivePoint.setAllPoints(userReceivePoint.getAllPoints() + points);
        userReceivePoint.setNowPoints(userReceivePoint.getNowPoints() + points);
        userPointsDao.updateByPrimaryKeySelective(userReceivePoint);
    }

    private void updateUserDappPoints(Long points, EcUserDapp userSendDapp, EcUserDapp userRecevieDapp) {
        userSendDapp.setConsumePoints(userSendDapp.getConsumePoints() + points);
        userDappService.updateByPrimaryKeySelective(userSendDapp);

        userRecevieDapp.setGetPoints(userRecevieDapp.getGetPoints() + points);
        userDappService.updateByPrimaryKeySelective(userRecevieDapp);
    }

    public String changePoints(final EcUserPoints userSendPoint, final EcUserPoints userReceivePoint,
                               final String descText, final Long points, final Long transactionId, final EcDapp dapp,
                               final EcUserDapp userSendDapp, final EcUserDapp userReceiveDapp) {
        TranscationExecutor<String> executor = new TranscationExecutor<String>() {
            public String execute() {
                try {
                    EcPoints epSend = new EcPoints();
                    epSend.setCreateTime(new Date());
                    epSend.setDappId(userSendDapp.getDappId());
                    epSend.setPoints(points);
                    epSend.setTransactionId(transactionId);
                    epSend.setUserDappId(userSendDapp.getId());
                    epSend.setUserId(userSendDapp.getUserId());
                    epSend.setDescribeText(descText);
                    epSend.setType("0");
                    pointMag.insertSelective(epSend);

                    EcPoints epReceive = new EcPoints();
                    epReceive.setCreateTime(new Date());
                    epReceive.setDappId(userReceiveDapp.getDappId());
                    epReceive.setPoints(points);
                    epReceive.setTransactionId(transactionId);
                    epReceive.setUserDappId(userReceiveDapp.getId());
                    epReceive.setUserId(userReceiveDapp.getUserId());
                    epReceive.setDescribeText(descText);
                    epReceive.setType("1");
                    pointMag.insertSelective(epReceive);

                    userSendPoint.setAllPoints(userSendPoint.getAllPoints() - points);
                    userSendPoint.setNowPoints(userSendPoint.getNowPoints() - points);
                    userSendPoint.setConsumePoints(userSendPoint.getConsumePoints() + points);
                    userPointsDao.updateByPrimaryKeySelective(userSendPoint);

                    userReceivePoint.setAllPoints(userReceivePoint.getAllPoints() + points);
                    userReceivePoint.setNowPoints(userReceivePoint.getNowPoints() + points);
                    userPointsDao.updateByPrimaryKeySelective(userReceivePoint);

                    userSendDapp.setConsumePoints(userSendDapp.getConsumePoints() + points);
                    userDappService.updateByPrimaryKeySelective(userSendDapp);

                    userReceiveDapp.setGetPoints(userReceiveDapp.getGetPoints() + points);
                    userDappService.updateByPrimaryKeySelective(userReceiveDapp);

                    return "200";
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return "500";
            }
        };

        return executor.run();
    }

    public String changeTransferPoints(final EcTransaction transaction, final EcUserPoints userSendPoint,
                                       final EcUserPoints userReceivePoint, final Long points, final EcDapp dapp, final EcUserDapp userSendDapp,
                                       final EcUserDapp userReceiveDapp) {
        TranscationExecutor<String> executor = new TranscationExecutor<String>() {
            public String execute() {
                try {

                    transactionDao.insertSelective(transaction);

                    EcPoints epSend = new EcPoints();
                    epSend.setCreateTime(new Date());
                    epSend.setDappId(userSendDapp.getDappId());
                    epSend.setPoints(points);
                    epSend.setTransactionId(transaction.getId());
                    epSend.setUserDappId(userSendDapp.getId());
                    epSend.setUserId(userSendDapp.getUserId());
                    epSend.setType("0");
                    pointMag.insertSelective(epSend);

                    EcPoints epReceive = new EcPoints();
                    epReceive.setCreateTime(new Date());
                    epReceive.setDappId(userReceiveDapp.getDappId());
                    epReceive.setPoints(points);
                    epReceive.setTransactionId(transaction.getId());
                    epReceive.setUserDappId(userReceiveDapp.getId());
                    epReceive.setUserId(userReceiveDapp.getUserId());
                    epReceive.setType("1");
                    pointMag.insertSelective(epReceive);

                    userSendPoint.setAllPoints(userSendPoint.getAllPoints() - points);
                    userSendPoint.setNowPoints(userSendPoint.getNowPoints() - points);
                    userSendPoint.setConsumePoints(userSendPoint.getConsumePoints() + points);
                    userPointsDao.updateByPrimaryKeySelective(userSendPoint);

                    userReceivePoint.setAllPoints(userReceivePoint.getAllPoints() + points);
                    userReceivePoint.setNowPoints(userReceivePoint.getNowPoints() + points);
                    userPointsDao.updateByPrimaryKeySelective(userReceivePoint);

                    userSendDapp.setConsumePoints(userSendDapp.getConsumePoints() + points);
                    userDappService.updateByPrimaryKeySelective(userSendDapp);

                    userReceiveDapp.setGetPoints(userReceiveDapp.getGetPoints() + points);
                    userDappService.updateByPrimaryKeySelective(userReceiveDapp);

                    return transaction.getId().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return transaction.getId().toString();
            }
        };

        return executor.run();
    }

    /**
     * 查询指定用户指定app下得积分 交易记录
     */
    public List<EcPoints> selectPointsByUserIdAndAppId(Long userId, Long appId) {
        return pointMag.selectByUserIdAndAppId(userId, appId);
    }

    public String takePoints(EcUser user, EcUserPoints userPoints, Long allPoints, EcUserPoints adminPoints, Long free,
                             EcUserWallet userWallet) {
        TranscationExecutor<String> executor = new TranscationExecutor<String>() {
            public String execute() {
                try {
                    userPoints.setAllPoints(userPoints.getAllPoints() - allPoints);
                    userPoints.setNowPoints(userPoints.getNowPoints() - allPoints);
                    userPointsDao.updateByPrimaryKeySelective(userPoints);

                    adminPoints.setAllPoints(adminPoints.getAllPoints() + free);
                    adminPoints.setNowPoints(adminPoints.getNowPoints() + free);
                    userPointsDao.updateByPrimaryKeySelective(adminPoints);

                    EcPoints ecPoints = new EcPoints();
                    ecPoints.setCreateTime(new Date());
                    ecPoints.setDappId(0l);
                    ecPoints.setPoints(allPoints);
                    ecPoints.setTransactionId(0l);
                    ecPoints.setUserDappId(0l);
                    ecPoints.setUserId(user.getId());
                    ecPoints.setType("0");
                    ecPoints.setDescribeText("积分取现");
                    pointMag.insertSelective(ecPoints);

                    EcPoints ecAdminPoints = new EcPoints();
                    ecAdminPoints.setCreateTime(new Date());
                    ecAdminPoints.setDappId(0l);
                    ecAdminPoints.setPoints(free);
                    ecAdminPoints.setTransactionId(0l);
                    ecAdminPoints.setUserDappId(0l);
                    ecAdminPoints.setUserId(adminPoints.getUserId());
                    ecAdminPoints.setType("2");
                    ecAdminPoints.setDescribeText("取现手续费");
                    pointMag.insertSelective(ecAdminPoints);

                    EcTakePoints takePoints = new EcTakePoints();
                    takePoints.setCreateTime(new Date());
                    takePoints.setStatus("0");
                    takePoints.setTakePoints(allPoints - free);
                    takePoints.setUserWalletId(userWallet.getId());
                    takePoints.setType("1");
                    takePointsDao.insertSelective(takePoints);

                    EcPointsPool pool = pointsPoolDao.selectByPrimaryKey(1l);
                    if (pool != null) {
                        pool.setAllPoints(pool.getAllPoints() - allPoints + free);
                        pool.setNowPoints(pool.getNowPoints() - allPoints + free);
                        pointsPoolDao.updateByPrimaryKeySelective(pool);
                    }
                    return "2";
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return "0";
            }
        };

        return executor.run();
    }
}
