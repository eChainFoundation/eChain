package com.echain.service;

import com.echain.common.util.IdWorker;
import com.echain.dao.EcPointsPoolDao;
import com.echain.dao.EcTakePointsDao;
import com.echain.dao.EcTransactionDao;
import com.echain.dao.EcUserPointsDao;
import com.echain.entity.*;
import com.echain.enumaration.WalletType;
import com.echain.helper.TranscationExecutor;
import com.echain.manager.PointMag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

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

    /**
     * ECP充值 积分划转
     * flag=0:充值，flag=1:提现
     */
    @Transactional
    public String changePoints2(Long receiveUserId, Long points, String desc, char flag) {
        Long sendUserId = 1L;

        if ('1' == flag) {
            points = -1 * points;
        }

        EcUserDapp ecSendUserDapp = userDappService.selectByUserIdAndDappId(sendUserId, 1L);
        EcUserDapp ecUserDapp = userDappService.selectByUserIdAndDappId(receiveUserId, 1L);

        EcPoints epSend = new EcPoints(sendUserId, 1L, ecSendUserDapp.getId(), 0L, points,
                "0", desc, new Date());
        pointMag.insertSelective(epSend);

        EcPoints epReceive = new EcPoints(receiveUserId, 1L, ecUserDapp.getId(), 0L, points,
                "1", desc, new Date());
        pointMag.insertSelective(epReceive);

        int sendFlag = 0;
        int receiveFlag = 0;

        //乐观锁更新
        while (sendFlag == 0) {
            EcUserPoints userSendPoint = userService.getUserPointByUserId(sendUserId);
            sendFlag = userPointsDao.updatePointByUserId(sendUserId, userSendPoint.getAllPoints() - points,
                    userSendPoint.getNowPoints() - points, userSendPoint.getConsumePoints() + points,
                    userSendPoint.getNowPoints());
        }

        while (receiveFlag == 0) {
            EcUserPoints userReceivePoint = userService.getUserPointByUserId(receiveUserId);
            receiveFlag = userPointsDao.updatePointByUserId(receiveUserId, userReceivePoint.getAllPoints() + points,
                    userReceivePoint.getNowPoints() + points, null,
                    userReceivePoint.getNowPoints());
        }

        int sendDappFlag = 0;
        int receiveDappFlag = 0;

        while (sendDappFlag == 0) {
            ecSendUserDapp = userDappService.selectByUserIdAndDappId(sendUserId, 1L);
            sendDappFlag = userDappService.updatePoint(ecSendUserDapp.getUserId(), ecSendUserDapp.getDappId(),
                    ecSendUserDapp.getConsumePoints() + points, ecSendUserDapp.getConsumePoints(),
                    null, null);
        }

        while (receiveDappFlag == 0) {
            ecSendUserDapp = userDappService.selectByUserIdAndDappId(receiveUserId, 1L);
            receiveDappFlag = userDappService.updatePoint(ecSendUserDapp.getUserId(), ecSendUserDapp.getDappId(),
                    null, null,
                    ecUserDapp.getGetPoints() + points, ecUserDapp.getGetPoints());
        }

        return "200";
    }

    public String changePoints(Long sendUserId, Long receiveUserId,
                               final String descText, final Long points, final Long transactionId, final EcDapp dapp,
                               final EcUserDapp userSendDapp, final EcUserDapp userReceiveDapp) {
        TranscationExecutor<String> executor = new TranscationExecutor<String>() {
            public String execute() {
                try {
                    EcPoints epSend = new EcPoints(userSendDapp.getUserId(), userSendDapp.getDappId(), userSendDapp.getId(),
                            transactionId, points, "0", descText, new Date());
                    pointMag.insertSelective(epSend);

                    EcPoints epReceive = new EcPoints(userReceiveDapp.getUserId(), userReceiveDapp.getDappId(),
                            userReceiveDapp.getId(), transactionId, points, "1", descText, new Date());
                    pointMag.insertSelective(epReceive);

                    int sendFlag = 0;
                    int receiveFlag = 0;

                    //乐观锁更新
                    while (sendFlag == 0) {
                        EcUserPoints userSendPoint = userService.getUserPointByUserId(sendUserId);
                        sendFlag = userPointsDao.updatePointByUserId(sendUserId, userSendPoint.getAllPoints() - points,
                                userSendPoint.getNowPoints() - points, userSendPoint.getConsumePoints() + points,
                                userSendPoint.getNowPoints());
                    }

                    while (receiveFlag == 0) {
                        EcUserPoints userReceivePoint = userService.getUserPointByUserId(receiveUserId);
                        receiveFlag = userPointsDao.updatePointByUserId(receiveUserId, userReceivePoint.getAllPoints() + points,
                                userReceivePoint.getNowPoints() + points, null,
                                userReceivePoint.getNowPoints());
                    }

                    int sendDappFlag = 0;
                    int receiveDappFlag = 0;

                    while (sendDappFlag == 0) {
                        EcUserDapp userSendDapp2 = userDappService.selectByUserIdAndDappId(userSendDapp.getUserId(), userSendDapp.getDappId());
                        sendDappFlag = userDappService.updatePoint(userSendDapp2.getUserId(), userSendDapp2.getDappId(),
                                userSendDapp2.getConsumePoints() + points, userSendDapp2.getConsumePoints(),
                                null, null);
                    }

                    while (receiveDappFlag == 0) {
                        EcUserDapp userReceiveDapp2 = userDappService.selectByUserIdAndDappId(userReceiveDapp.getUserId(), userReceiveDapp.getDappId());
                        receiveDappFlag = userDappService.updatePoint(userReceiveDapp2.getUserId(), userReceiveDapp2.getDappId(),
                                null, null,
                                userReceiveDapp2.getGetPoints() + points, userReceiveDapp2.getGetPoints());
                    }

                    changeWalletValue(sendUserId, new BigDecimal(-points), descText);
                    changeWalletValue(receiveUserId, new BigDecimal(points), descText);

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
                                       final EcUserPoints userReceivePoint, final Long points, final EcDapp dapp,
                                       final EcUserDapp userSendDapp, final EcUserDapp userReceiveDapp) {
        TranscationExecutor<String> executor = new TranscationExecutor<String>() {
            public String execute() {
                try {
                    transactionDao.insertSelective(transaction);

                    EcPoints epSend = new EcPoints(userSendDapp.getUserId(), userSendDapp.getDappId(), userSendDapp.getId(),
                            transaction.getId(), points, "0", "", new Date());
                    pointMag.insertSelective(epSend);

                    EcPoints epReceive = new EcPoints(userReceiveDapp.getUserId(), userReceiveDapp.getDappId(),
                            userReceiveDapp.getId(), transaction.getId(), points, "1", "", new Date());
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

                    changeWalletValue(userSendDapp.getUserId(), new BigDecimal(-points), "");
                    changeWalletValue(userReceiveDapp.getUserId(), new BigDecimal(points), "");

                    return transaction.getId().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return transaction.getId().toString();
            }
        };

        return executor.run();
    }

    public void changeWalletValue(Long userId, BigDecimal points, String remark) throws InterruptedException {
        //更新数据库中ec_wallet value
        int flag2 = 0;
        while (flag2 == 0) {
            EcWallet wallet2 = walletService.selectByUserIdType(userId, WalletType.ECP.getIndex());
            if (wallet2 == null) {
                break;
            }
            flag2 = walletService.updateValue(userId, WalletType.ECP.getIndex(), wallet2.getValue().add(points),
                    wallet2.getValue());
        }
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

                    EcPoints ecPoints = new EcPoints(user.getId(), 0L, 0L, 0L, allPoints,
                            "0", "积分取现", new Date());
                    pointMag.insertSelective(ecPoints);

                    EcPoints ecAdminPoints = new EcPoints(adminPoints.getUserId(), 0L, 0L, 0L, free,
                            "2", "取现手续费", new Date());
                    pointMag.insertSelective(ecAdminPoints);

                    EcTakePoints takePoints = new EcTakePoints(userWallet.getId(), allPoints - free, "1", "0", new Date());
                    takePointsDao.insertSelective(takePoints);

                    EcPointsPool pool = pointsPoolDao.selectByPrimaryKey(1l);
                    if (pool != null) {
                        pool.setAllPoints(pool.getAllPoints() - allPoints + free);
                        pool.setNowPoints(pool.getNowPoints() - allPoints + free);
                        pointsPoolDao.updateByPrimaryKeySelective(pool);
                    }

                    changeWalletValue(user.getId(), new BigDecimal(-allPoints), "积分取现");
                    changeWalletValue(adminPoints.getUserId(), new BigDecimal(free), "取现手续费");
                    changeWalletValue(adminPoints.getUserId(), new BigDecimal(allPoints - free), "积分取现");

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
