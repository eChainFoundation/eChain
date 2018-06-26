package com.echain.rest;

import com.echain.common.BaseResponse;
import com.echain.entity.EcDapp;
import com.echain.entity.EcUser;
import com.echain.entity.EcUserDapp;
import com.echain.entity.EcUserPoints;
import com.echain.service.UserService;
import com.echain.vo.rest.response.UserDetailResponseVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

@RequestMapping(value = "/rest/user", produces = "application/json")
@RestController
public class UserRest {

    @Autowired
    UserService userService;

    @ApiOperation(value = "获取积分详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phoneNumber", value = "手机号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", required = false, dataType = "String", paramType = "query"),
    })
    @GetMapping(value = "user_detail")
    public BaseResponse personlPage(@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                    @RequestParam(value = "type", required = false) String type) {
        EcUser user = userService.getUserByPhoneNumberNoInsert(phoneNumber);
        if (user == null || !type.equals("emall")) {
            return new BaseResponse(500, "失败", null);
        }
        // 剩余总积分
        EcUserPoints userPoint = userService.getUserPointByUserIdNoInsert(user.getId());
        // 每个app获取积分总数
        LinkedHashMap<EcDapp, Object> appPoints = new LinkedHashMap<>();
        List<EcUserDapp> userDapps = userService.getUserDappPointsByUserId(user.getId());
        userDapps.stream().sorted(Comparator.comparing(EcUserDapp::getId));
        for (EcUserDapp ecUserDapp : userDapps) {
            Long dappId = ecUserDapp.getDappId();
            if (dappId == 1) {
                continue;
            }
            EcDapp dapp = userService.getDappByDappId(dappId);
            appPoints.put(dapp, ecUserDapp.getGetPoints());
        }
        return new BaseResponse(200, "成功", new UserDetailResponseVo(user.getPhoneNumber(),
                user.getId(), userPoint.getNowPoints(), appPoints));
    }

}
