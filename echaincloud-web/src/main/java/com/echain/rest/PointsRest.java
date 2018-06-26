package com.echain.rest;

import com.echain.common.BaseResponse;
import com.echain.constant.EchainConstant;
import com.echain.entity.*;
import com.echain.service.DappService;
import com.echain.service.PointService;
import com.echain.service.UserService;
import com.echain.vo.rest.request.ChangePointsRequestVo;
import com.echain.vo.rest.request.TakePointsRequestVo;
import com.echain.vo.rest.response.AppPointsDetailResponseVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/rest/point", produces = "application/json")
@RestController
public class PointsRest {

    @Autowired
    UserService userService;

    @Autowired
    PointService pointService;

    @Autowired
    DappService dappService;

    @ApiOperation(value = "根据手机号获取用户积分")
    @ApiImplicitParam(name = "phoneNumber", value = "手机号", required = true, dataType = "String", paramType = "query")
    @GetMapping(value = "/getPointByPhoneNumber")
    public BaseResponse<Long> getUserByPhoneNumber(@RequestParam(required = true) String phoneNumber) {
        EcUser user = this.userService.getUserByPhoneNumber(phoneNumber);
        if (user != null) {
            EcUserPoints userPoint = this.userService.getUserPointByUserId(user.getId());
            if (userPoint != null) {
                Long nowPoints = userPoint.getNowPoints();
                if (nowPoints == null) {
                    nowPoints = 0L;
                }

                return new BaseResponse<>(200, "成功", nowPoints);
            }
        }
        return new BaseResponse<>(500, "成功", 0L);
    }

    /**
     * 返回值 0--积分发送方，接受方都是平台 1--划转的积分数小于等于0 2--发送方的积分小于要划转的积分数 4--成功
     * <p>
     * 200 成功
     * 400 请求失败，积分发送方，接受方都是平台
     * 401 划转的积分数小于等于0
     * 402 发送方的积分小于要划转的积分数
     * 500 服务器内部错误
     */
    @ApiOperation(value = "积分划转接口")
    @ApiImplicitParam(name = "requestVo", value = "请求体", required = true, dataType = "ChangePointsRequestVo")
    @PostMapping(value = "/changePoints")
    public BaseResponse<String> changePoints(@RequestBody ChangePointsRequestVo requestVo) {
        if (StringUtils.isEmpty(requestVo)) {
            return new BaseResponse(400, "失败", "");
        }
        if (requestVo.getPoints() <= 0) {
            return new BaseResponse(401, "失败", "");
        }
        if (StringUtils.isEmpty(requestVo.getSendPhoneNumber())) {
            requestVo.setSendPhoneNumber("1");
        }
        if (StringUtils.isEmpty(requestVo.getReceivePhoneNumber())) {
            requestVo.setReceivePhoneNumber("1");
        }
        if ("1".equals(requestVo.getSendPhoneNumber()) && ("1".equals(requestVo.getReceivePhoneNumber()))) {
            return new BaseResponse(400, "失败", "");
        }
        EcUserPoints userSendPoint = null;
        EcUserPoints userReceivePoint = null;

        EcUser userSend = this.userService.getUserByPhoneNumber(requestVo.getSendPhoneNumber());
        if (userSend != null) {
            userSendPoint = this.userService.getUserPointByUserId(userSend.getId());
        }

        EcUser userReceive = this.userService.getUserByPhoneNumber(requestVo.getReceivePhoneNumber());
        if (userReceive != null) {
            userReceivePoint = this.userService.getUserPointByUserId(userReceive.getId());
        }

        if (userSendPoint.getNowPoints() != null && userSendPoint.getNowPoints() < requestVo.getPoints()) {
            return new BaseResponse(402, "失败", "");
        }

        EcDapp dapp = this.userService.getDappByDappname(requestVo.getDappName());
        EcUserDapp userSendDapp = this.userService.getUserDappByUserIdAndDappId(userSend.getId(), dapp.getId(),
                requestVo.getIsUploadSingle());
        EcUserDapp userRecevieDapp = this.userService.getUserDappByUserIdAndDappId(userReceive.getId(), dapp.getId(),
                requestVo.getIsUploadSingle());

        String code = this.pointService.changePoints(userSendPoint, userReceivePoint, requestVo.getDescText(),
                requestVo.getPoints(), requestVo.getTransactionId(),
                dapp, userSendDapp, userRecevieDapp);
        if (StringUtils.isEmpty(code)) {
            return new BaseResponse(500, "失败", "");
        } else {
            return new BaseResponse(200, "成功", code);
        }
    }

    @ApiOperation(value = "获取积分详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "appId", value = "appId", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "isUploadSingle", value = "是否单独上链", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "details")
    public BaseResponse<AppPointsDetailResponseVo> appPointsDetail(@RequestParam("userId") Long userId, @RequestParam("appId") Long appId,
                                                                   @RequestParam(required = false) String isUploadSingle) {
        EcDapp ecDapp = dappService.selectByPrimaryKey(appId);
        List<EcPoints> points = pointService.selectPointsByUserIdAndAppId(userId, appId);
        EcUserDapp userDapp = userService.getUserDappByUserIdAndDappId(userId, appId, isUploadSingle);
        return new BaseResponse<>(200, "成功", new AppPointsDetailResponseVo(ecDapp, points, userDapp.getGetPoints()));
    }

    @ApiOperation(value = "积分提现")
    @ApiImplicitParam(name = "requestVo", value = "请求体", required = true, dataType = "TakePointsRequestVo")
    @PostMapping(value = "take_points")
    public BaseResponse takePoints(@RequestBody TakePointsRequestVo requestVo) {
        EcUser user = userService.getUserByPhoneNumberNoInsert(requestVo.getPhoneNumber());
        EcUserWallet userWallet = userService.saveOrUpdateUserWallet(user, requestVo.getWallet());
        EcUser admin = userService.getUserByPhoneNumberNoInsert(EchainConstant.DEFALUT_USER_PHONE);
        if (user == null || user.getPhoneNumber().equals(EchainConstant.DEFALUT_USER_PHONE)) {
            return new BaseResponse(500, "失败", "");
        }
        EcUserPoints userPoints = this.userService.getUserPointByUserId(user.getId());
        if (userPoints.getNowPoints() < requestVo.getAllPoints()) {
            return new BaseResponse(501, "失败", "");
        }
        EcUserPoints adminPoints = this.userService.getUserPointByUserId(admin.getId());
        String result = pointService.takePoints(user, userPoints, requestVo.getAllPoints(), adminPoints, requestVo.getFree(), userWallet);

        return "2".equals(result) ? new BaseResponse(200, "成功", "")
                : new BaseResponse(500, "失败", "");
    }
}
