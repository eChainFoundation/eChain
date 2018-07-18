package com.echain.controller;

import com.alibaba.fastjson.JSON;
import com.echain.constant.EchainConstant;
import com.echain.entity.*;
import com.echain.service.DappService;
import com.echain.service.PointService;
import com.echain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/point")
@Controller
public class PointsController {

    @Autowired
    private UserService userService;

    @Autowired
    private PointService pointService;

    @Autowired
    private DappService dappService;

    /**
     * 根据手机号获取用户积分
     *
     * @param phoneNumber
     * @param request
     * @param response
     * @return 200 成功 400 请求失败，积分发送方，接受方都是平台 401 划转的积分数小于等于0 402 发送方的积分小于要划转的积分数 500
     * 服务器内部错误
     * @throws Exception http://106.15.59.49:8090/point/getUserByPhoneNumber?phoneNumber=15026561611
     */
    @RequestMapping(value = "getUserByPhoneNumber", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getUserByPhoneNumber(@RequestParam(required = true) String phoneNumber,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        EcUser user = this.userService.getUserByPhoneNumber(phoneNumber);
        if (user != null) {
            EcUserPoints userPoint = this.userService.getUserPointByUserId(user.getId());
            if (userPoint != null) {
                Long nowPoints = userPoint.getNowPoints();
                map.put("code", 200);
                if (nowPoints == null)
                    nowPoints = 0l;
                map.put("points", nowPoints);
                return JSON.toJSONString(map);
            }
        }
        map.put("code", 500);
        map.put("points", 0);
        return JSON.toJSONString(map);
    }

    /**
     * 积分划转接口 返回值 0--积分发送方，接受方都是平台 1--划转的积分数小于等于0 2--发送方的积分小于要划转的积分数 4--成功
     *
     * @param sendPhoneNumber
     * @param receivePhoneNumber
     * @param dappName
     * @param transactionId
     * @param points
     * @return 200 成功 400 请求失败，积分发送方，接受方都是平台 401 划转的积分数小于等于0 402 发送方的积分小于要划转的积分数 500
     * 服务器内部错误
     * @throws Exception http://106.15.59.49:8090/point/changePoints?sendPhoneNumber=15026561611&receivePhoneNumber=15026561961&dappName=doushabao&transactionId=1&points=100
     */
    @RequestMapping(value = "changePoints", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String changePoints(@RequestParam(required = false) String sendPhoneNumber,
                        @RequestParam(required = false) String receivePhoneNumber,
                        @RequestParam(required = false) String dappName,
                        @RequestParam(required = false) String isUploadSingle,
                        @RequestParam(required = false) Long transactionId,
                        @RequestParam(required = false) String descText,
                        @RequestParam(required = false) Long points) {

        Map<String, Object> map = new HashMap<String, Object>();

        if (sendPhoneNumber == null && receivePhoneNumber == null) {
            map.put("code", 400);
            return JSON.toJSONString(map);
        }
        if (points <= 0) {
            map.put("code", 401);
            return JSON.toJSONString(map);
        }
        if (sendPhoneNumber == null)
            sendPhoneNumber = "1";
        if (receivePhoneNumber == null)
            receivePhoneNumber = "1";
        if ("1".equals(sendPhoneNumber) && ("1".equals(receivePhoneNumber))) {
            map.put("code", 400);
            return JSON.toJSONString(map);
        }
        EcUserPoints userSendPoint = null;
        EcUserPoints userReceivePoint = null;

        EcUser userSend = this.userService.getUserByPhoneNumber(sendPhoneNumber);
        if (userSend != null) {
            userSendPoint = this.userService.getUserPointByUserId(userSend.getId());
        }

        EcUser userReceive = this.userService.getUserByPhoneNumber(receivePhoneNumber);
        if (userReceive != null) {
            userReceivePoint = this.userService.getUserPointByUserId(userReceive.getId());
        }

        if (userSendPoint.getNowPoints() != null && userSendPoint.getNowPoints() < points) {
            map.put("code", 402);
            return JSON.toJSONString(map);
        }

        EcDapp dapp = this.userService.getDappByDappname(dappName);
        EcUserDapp userSendDapp = this.userService.getUserDappByUserIdAndDappId(userSend.getId(), dapp.getId(),
                isUploadSingle);
        EcUserDapp userRecevieDapp = this.userService.getUserDappByUserIdAndDappId(userReceive.getId(), dapp.getId(),
                isUploadSingle);

        String code = this.pointService.changePoints(userSendPoint.getUserId(), userReceivePoint.getUserId(), descText, points, transactionId,
                dapp, userSendDapp, userRecevieDapp);
        if (code == null || "".equals(code)) {
            map.put("code", 500);
            return JSON.toJSONString(map);
        } else {
            map.put("code", code);
            return JSON.toJSONString(map);
        }
    }

    @RequestMapping(value = "details.do", produces = "text/html;charset=UTF-8")
    public String appPointsDetail(@RequestParam("userId") Long userId, @RequestParam("appId") Long appId,
                                  @RequestParam(required = false) String isUploadSingle, ModelMap modelMap) {
        EcDapp ecDapp = dappService.selectByPrimaryKey(appId);
        modelMap.put("ecDapp", ecDapp);
        List<EcPoints> points = pointService.selectPointsByUserIdAndAppId(userId, appId);
        modelMap.put("points", points);
        EcUserDapp userDapp = userService.getUserDappByUserIdAndDappId(userId, appId, isUploadSingle);
        modelMap.put("getPoints", userDapp.getGetPoints());
        return "echain/ePoint";
    }

    /**
     * 积分提现
     *
     * @param phoneNumber
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "take_points.action", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String takePoints(@RequestParam("phoneNumber") String phoneNumber,
                      @RequestParam("wallet") String wallet, @RequestParam("free") Long free,
                      @RequestParam("all_points") Long allPoints, ModelMap modelMap) {
        EcUser user = userService.getUserByPhoneNumberNoInsert(phoneNumber);
        EcUserWallet userWallet = this.userService.saveOrUpdateUserWallet(user, wallet);
        EcUser admin = userService.getUserByPhoneNumberNoInsert(EchainConstant.DEFALUT_USER_PHONE);
        if (user == null || user.getPhoneNumber().equals(EchainConstant.DEFALUT_USER_PHONE)) {
            return "0";
        }
        EcUserPoints userPoints = this.userService.getUserPointByUserId(user.getId());
        if (userPoints.getNowPoints() < allPoints) {
            return "1";
        }
        EcUserPoints adminPoints = this.userService.getUserPointByUserId(admin.getId());
        return this.pointService.takePoints(user, userPoints, allPoints, adminPoints, free, userWallet);
    }

    /**
     * 积分提现
     *
     * @param phoneNumber
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "take_points.html", produces = "text/html;charset=UTF-8")
    public String takePointsHtml(@RequestParam("phoneNumber") String phoneNumber, ModelMap modelMap) {
        EcUser user = userService.getUserByPhoneNumberNoInsert(phoneNumber);
        EcUserWallet userWallet = this.userService.getUserWalletByUserId(user.getId());
        EcUserPoints userPoints = this.userService.getUserPointByUserId(user.getId());

        modelMap.put("user", user);
        modelMap.put("userWallet", userWallet);
        modelMap.put("userPoints", userPoints);

        return "echain/withdraw";
    }
}
