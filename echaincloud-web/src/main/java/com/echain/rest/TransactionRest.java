package com.echain.rest;

import com.alibaba.fastjson.JSON;
import com.echain.common.BaseResponse;
import com.echain.common.util.CommonUtil;
import com.echain.conf.ParamsProperties;
import com.echain.entity.*;
import com.echain.enumaration.UpEchainFrequency;
import com.echain.service.*;
import com.echain.util.Md5Util;
import com.echain.vo.EcLogisticsRecordVo;
import com.echain.vo.EcTransactionVo;
import com.echain.vo.rest.request.MatcheRequestVo;
import com.echain.vo.rest.request.ReceiveLogisticsRequestVo;
import com.echain.vo.rest.request.ReceiveTransactionsPointsRequestVo;
import com.echain.vo.rest.request.ReceiveTransactionsRequestVo;
import com.echain.vo.rest.response.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * paramType 有五个可选值 ： path, query, body, header, form
 */
@RequestMapping(value = "/rest/transaction", produces = "application/json")
@RestController
public class TransactionRest {

    @Autowired
    TransferDataService transferService;

    @Autowired
    UserService userService;

    @Autowired
    PointService pointService;

    @Autowired
    LogisticsCompanyService logisticsCompanyService;

    @Autowired
    UserDappService userDappService;

    @Autowired
    TaskService taskService;

    @Autowired
    LogisticsService logisticsService;

    @Autowired
    ParamsProperties paramsProperties;

    /**
     * 导入交易数据
     * <p>
     * 200 成功
     * 400 请求失败，积分发送方，接受方都是平台
     * 401 划转的积分数小于等于0
     * 402 发送方的积分小于要划转的积分数
     * 500 服务器内部错误
     *
     * @author lijun
     */
    @ApiOperation(value = "导入交易数据")
    @ApiImplicitParam(name = "requestVo", value = "请求体", required = true, dataType = "ReceiveTransactionsRequestVo")
    @PostMapping(value = "receiveTransactions")
    public BaseResponse<Long> receiveTransactions(@RequestBody ReceiveTransactionsRequestVo requestVo) {
        if (!UpEchainFrequency.isInclude(requestVo.getUpEchainFrequency())) {
            return new BaseResponse<>(500, "上链频率参数异常", 0L);
        }

        if (CommonUtil.isEmpty(requestVo.getCountry())) {
            requestVo.setCountry("86");
        }

        EcUser user = userService.getUserByPhoneNumber(requestVo.getPhoneNumber(), requestVo.getCountry());
        EcDapp dapp = userService.getDappByDappname(requestVo.getDappName());
        EcLogisticsCompany logisticsCompany = null;
        if (!StringUtils.isEmpty(requestVo.getLogisticsCompanyName())) {
            logisticsCompany = userService.getLogisticsCompanyByCompanyname(requestVo.getLogisticsCompanyName());
        }
        EcUserDapp userDapp = userService.getUserDappByUserIdAndDappId(user.getId(), dapp.getId(), requestVo.getIsUploadSingle(),
                requestVo.getUpEchainFrequency());

        EcTransaction transaction = new EcTransaction(user.getId(), dapp.getId(), userDapp.getId(),
                Md5Util.encode(requestVo.getDescribeText()), requestVo.getStatus(), new Date(), requestVo.getDescribeText(),
                requestVo.getTransactionNo());

        if (logisticsCompany != null) {
            transaction.setLogisticsCompanyId(logisticsCompany.getId());
        }
        if (!StringUtils.isEmpty(requestVo.getLogisticsNo())) {
            transaction.setLogisticsNo(requestVo.getLogisticsNo());
        }
        if (!StringUtils.isEmpty(requestVo.getTransactionPlatform())) {
            transaction.setTransactionPlatform(requestVo.getTransactionPlatform());
        }

        transaction = transferService.saveTransferData(transaction);

        return transaction != null ? new BaseResponse<>(transaction.getId())
                : new BaseResponse<>(500, "失败", 0L);
    }

    /**
     * 根据交易ID获取交易信息
     *
     * @param transactionId 交易Id
     */
    @ApiOperation(value = "根据交易ID获取交易信息")
    @ApiImplicitParam(name = "transactionId", value = "交易Id", required = true, dataType = "long", paramType = "query")
    @GetMapping(value = "/getTransactionsById")
    public BaseResponse<EcTransactionResponseVo> getTransactionsById(@RequestParam Long transactionId) {
        EcTransaction transaction = transferService.selectTransactionById(transactionId);
        EcTransactionResponseVo responseVo = new EcTransactionResponseVo(transaction);

        return transaction != null ? new BaseResponse<>(200, "成功", responseVo)
                : new BaseResponse<>(500, "失败", null);
    }

    @ApiOperation(value = "根据dappName、交易平台、交易单号获取交易数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dappName", value = "dappName", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "transactionPlatform", value = "交易平台", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "transactionNo", value = "交易单号", required = true, dataType = "String", paramType = "query")
    })

    @GetMapping(value = "/getTransactionsByPlatform")
    public BaseResponse<EcTransactionResponseVo> getTransactionsByPlatform(@RequestParam String dappName,
                                                                           @RequestParam String transactionPlatform,
                                                                           @RequestParam String transactionNo) {
        EcTransaction transaction = transferService.getTransactionsByPlatform(dappName, transactionPlatform, transactionNo);
        EcTransactionResponseVo responseVo = new EcTransactionResponseVo(transaction);

        return transaction != null ? new BaseResponse<>(200, "成功", responseVo)
                : new BaseResponse<>(500, "失败", null);
    }

    /**
     * 导入物流数据
     * <p>
     * 200 成功
     * 400 请求失败，积分发送方，接受方都是平台
     * 401 划转的积分数小于等于0
     * 402 发送方的积分小于要划转的积分数
     * 500 服务器内部错误
     */
    @ApiOperation(value = "导入物流数据")
    @ApiImplicitParam(name = "requestVo", value = "请求体", required = true, dataType = "ReceiveLogisticsRequestVo")
    @PostMapping(value = "receiveLogistics")
    public BaseResponse<Long> receiveLogistics(@RequestBody ReceiveLogisticsRequestVo requestVo) {
        EcLogisticsCompany logisticsCompany = userService.getLogisticsCompanyByCompanyname(requestVo.getLogisticsCompanyName());

        EcLogisticsRecord logistics = new EcLogisticsRecord();
        logistics.setCreateTime(new Date());
        logistics.setLogisticsNo(requestVo.getLogisticsNo());
        logistics.setLogisticsText(requestVo.getLogisticsText());
        logistics.setLogisticsMd5(Md5Util.encode(requestVo.getLogisticsText()));
        logistics.setLogisticsCompanyId(logisticsCompany.getId());

        logistics = logisticsService.saveLogistics(logistics);

        return logistics != null ? new BaseResponse<>(logistics.getId())
                : new BaseResponse<>(500, "失败", 0L);
    }

    /**
     * 根据物流ID获取物流信息
     *
     * @param logisticsId 物流Id
     */
    @ApiOperation(value = "根据物流ID获取物流信息")
    @ApiImplicitParam(name = "logisticsId", value = "物流Id", required = true, dataType = "long", paramType = "query")
    @GetMapping(value = "/getLogisticsById")
    public BaseResponse<String> getLogisticsById(@RequestParam Long logisticsId) {
        EcLogisticsRecord logistic = logisticsService.selectLogisticById(logisticsId);

        return logistic != null ? new BaseResponse<>(200, "成功", logistic.getLogisticsText())
                : new BaseResponse<>(500, "失败", "");
    }

    /**
     * 积分划转接口
     * <p>
     * 返回值 0--积分发送方，接受方都是平台
     * 1--划转的积分数小于等于0
     * 2--发送方的积分小于要划转的积分数
     * 4--成功
     * <p>
     * 200 成功
     * 400 请求失败，积分发送方，接受方都是平台
     * 401 划转的积分数小于等于0
     * 402 发送方的积分小于要划转的积分数
     * 500 服务器内部错误
     */
    @ApiOperation(value = "积分划转")
    @ApiImplicitParam(name = "requestVo", value = "请求体", required = true, dataType = "ReceiveTransactionsPointsRequestVo")
    @PostMapping(value = "receiveTransactionsPoints")
    public BaseResponse<String> receiveTransactionsPoints(@RequestBody ReceiveTransactionsPointsRequestVo requestVo) {

        if (StringUtils.isEmpty(requestVo.getSendPhoneNumber())
                && StringUtils.isEmpty(requestVo.getReceivePhoneNumber())) {
            return new BaseResponse<>(400, "请求失败，积分发送方、接受方都是平台", "0");
        }

        if (StringUtils.isEmpty(requestVo.getPoints())) {
            return new BaseResponse<>(401, "划转的积分数小于等于0", "0");
        }

        if (CommonUtil.isEmpty(requestVo.getCountry())) {
            requestVo.setCountry("86");
        }

        String sendPhoneNumber = "0";
        String receivePhoneNumber = "0";
        if (!StringUtils.isEmpty(requestVo.getSendPhoneNumber())) {
            sendPhoneNumber = requestVo.getSendPhoneNumber();
        }
        if (!StringUtils.isEmpty(requestVo.getReceivePhoneNumber())) {
            receivePhoneNumber = requestVo.getReceivePhoneNumber();
        }

        if ("0".equals(sendPhoneNumber) && ("0".equals(receivePhoneNumber))) {
            return new BaseResponse<>(400, "请求失败，积分发送方、接受方都是平台", "0");
        }
        EcUserPoints userSendPoint = null;
        EcUserPoints userReceivePoint = null;

        EcUser userSend = userService.getUserByPhoneNumber(sendPhoneNumber);
        if (userSend != null) {
            userSendPoint = userService.getUserPointByUserId(userSend.getId());
        }

        EcUser userReceive = userService.getUserByPhoneNumber(receivePhoneNumber);
        if (userReceive != null) {
            userReceivePoint = userService.getUserPointByUserId(userReceive.getId());
        }

        EcUser user = userService.getUserByPhoneNumber(requestVo.getPhoneNumber(), requestVo.getCountry());
        EcDapp dapp = userService.getDappByDappname(requestVo.getDappName());

        EcLogisticsCompany logisticsCompany = null;
        if (!StringUtils.isEmpty(requestVo.getLogisticsCompanyName())) {
            logisticsCompany = userService.getLogisticsCompanyByCompanyname(requestVo.getLogisticsCompanyName());
        }
        EcUserDapp userDapp = userService.getUserDappByUserIdAndDappId(user.getId(), dapp.getId(), requestVo.getIsUploadSingle());

        EcTransaction transaction = new EcTransaction();
        transaction.setDappId(dapp.getId());
        transaction.setCreateTime(new Date());
        if (logisticsCompany != null) {
            transaction.setLogisticsCompanyId(logisticsCompany.getId());
        }
        if (!StringUtils.isEmpty(requestVo.getLogisticsNo())) {
            transaction.setLogisticsNo(requestVo.getLogisticsNo());
        }
        transaction.setTransactionNo(requestVo.getTransactionNo());
        if (!StringUtils.isEmpty(requestVo.getTransactionPlatform())) {
            transaction.setTransactionPlatform(requestVo.getTransactionPlatform());
        }
        transaction.setUserId(user.getId());
        transaction.setUserDappId(userDapp.getId());
        transaction.setStatus(requestVo.getStatus());
        transaction.setDescribeText(requestVo.getDescribeText());
        transaction.setDescribeMd5(Md5Util.encode(requestVo.getDescribeText()));

        EcUserDapp userSendDapp = userService.getUserDappByUserIdAndDappId(userSend.getId(), dapp.getId(),
                requestVo.getIsUploadSingle());
        EcUserDapp userReceiveDapp = userService.getUserDappByUserIdAndDappId(userReceive.getId(), dapp.getId(),
                requestVo.getIsUploadSingle());

        String transactionId = pointService.changeTransferPoints(transaction, userSendPoint, userReceivePoint,
                requestVo.getPoints(), dapp, userSendDapp, userReceiveDapp);

        return StringUtils.isEmpty(transactionId) ? new BaseResponse<>(500, "失败", "0")
                : new BaseResponse<>(200, "成功", transactionId);
    }

    @ApiOperation(value = "订单图片上传")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long", paramType = "query")
    @PostMapping(value = "/upload")
    public BaseResponse<EcUser> uploadImage(@RequestParam("userId") Long userId, HttpServletRequest request) throws Exception {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");

        if (files == null || files.size() == 0) {
            return new BaseResponse<>(500, "失败", null);
        }

        MultipartFile file = files.get(0);

        String separator = File.separator;// 分隔符

        Calendar now = Calendar.getInstance();// 获取当前日期
        String time = "" + now.get(Calendar.YEAR) + (now.get(Calendar.MONTH) + 1) + now.get(Calendar.DAY_OF_MONTH);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        String realPathEnd = separator + time + separator + userId + separator + uuid + "."
                + file.getOriginalFilename().split("\\.")[1];
        transferService.saveTransferData(userId, file, paramsProperties.getUploadImgPath(), realPathEnd);

        EcUser user = userService.selectUserByPrimaryKey(userId);
        return new BaseResponse<>(200, "订单图片上传成功", user);
    }

    @ApiOperation(value = "订单列表")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long", paramType = "path")
    @GetMapping(value = "/{userId}/uploadList")
    public BaseResponse<UploadListResponseVo> uploadList(@PathVariable Long userId) {
        List<EcTransaction> transfers = transferService.selectEchainListByUserId(userId);

        List<EcTransaction> succesList = new ArrayList<>();
        List<EcTransaction> faildedList = new ArrayList<>();
        List<EcTransaction> checkingList = new ArrayList<>();
        List<EcLogisticsCompany> lcList = new ArrayList<>();

        for (EcTransaction ecTransaction : transfers) {
            String status = ecTransaction.getStatus();
            if ("1".equals(status)) {
                succesList.add(ecTransaction);
            } else if ("2".equals(status)) {
                faildedList.add(ecTransaction);
            } else {
                checkingList.add(ecTransaction);
            }
        }
        // 查询物流公司
        List<EcLogisticsCompany> list = logisticsCompanyService.selectAll();
        if (lcList != null && lcList.size() > 0) {
            lcList.addAll(list);
        }

        UploadListResponseVo responseVo = new UploadListResponseVo(succesList, faildedList, checkingList, lcList);
        return new BaseResponse<>(200, "", responseVo);
    }

    @ApiOperation(value = "订单更新物流信息")
    @ApiImplicitParam(name = "requestVo", value = "请求体", required = true, dataType = "MatcheRequestVo")
    @PostMapping(value = "/matche")
    public BaseResponse matche(@RequestBody MatcheRequestVo requestVo) {
        EcTransaction ecTransaction = new EcTransaction();
        ecTransaction.setId(requestVo.getTransactionId());
        ecTransaction.setLogisticsCompanyId(requestVo.getCompanyId());
        ecTransaction.setLogisticsNo(requestVo.getLogisticsNo());
        boolean flag = transferService.updateEcTransaction(ecTransaction);

        return flag ? new BaseResponse(200, "订单更新物流信息成功", "")
                : new BaseResponse(500, "订单更新物流信息失败", "");
    }

    @ApiOperation(value = "获取交易上链hash")
    @ApiImplicitParam(name = "transactionId", value = "交易Id", required = true, dataType = "Long", paramType = "query")
    @GetMapping(value = "/getTransactionHash")
    public BaseResponse<GetTransactionHashResponseVo> getTransactionHash(@RequestParam("transactionId") Long transactionId) {
        EcTransaction transaction = transferService.selectTransactionById(transactionId);
        GetTransactionHashResponseVo responseVo =
                new GetTransactionHashResponseVo(transaction.getTransactionHash(), transaction.getBlockNo());
        return transaction != null ? new BaseResponse<>(200, "成功", responseVo)
                : new BaseResponse<>(500, "失败", null);
    }

    @ApiOperation(value = "订单审核页面")
    @ApiImplicitParam(name = "index", value = "index", required = true, dataType = "int")
    @GetMapping(value = "/upload_list")
    public BaseResponse<UploadListForCheckResponseVo> uploadListForCheck(
            @RequestParam(value = "index", defaultValue = "1") Integer index) {
        if (index <= 0) {
            index = 1;
        }
        List<EcTransaction> list = transferService.selectListForCheckWithLimit(index);
        Map<Long, EcUser> userMap = new HashMap<>();
        list.forEach((tr) -> {
            EcUser user = userService.selectUserByPrimaryKey(tr.getUserId());
            userMap.put(user.getId(), user);
        });
        UploadListForCheckResponseVo responseVo = new UploadListForCheckResponseVo(list, index, userMap);
        return new BaseResponse<>(200, "成功", responseVo);
    }

    @ApiOperation(value = "用户上传订单审核方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "check", value = "是否通过审核", required = true, dataType = "boolean", paramType = "query")
    })
    @PostMapping("/check_upload")
    public BaseResponse checkUpload(@RequestParam(value = "id") Long id, @RequestParam(value = "check") boolean check) {
        boolean flag = transferService.updateEcTransaction(id, check);

        return flag ? new BaseResponse(200, "成功", "")
                : new BaseResponse(500, "失败", "");
    }

    @ApiOperation(value = "根据userId 和 DappId 获取交易信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "dappId", value = "dappId", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping(value = "/transaction_list")
    public BaseResponse<GetTransactionListResponseVo> getTransactionList(@RequestParam("userId") Long userId, @RequestParam("dappId") Long dappId) {
        EcUserDapp userDapp = userDappService.selectByUserIdAndDappId(userId, dappId);

        if (userDapp == null) {
            return new BaseResponse(500, "失败", null);
        }

        List<EcTransaction> list = taskService.selectListTransactionByUserDappId(Long.toString(userDapp.getId()));

        if (list != null && list.size() > 0) {
            List<EcTransactionVo> voList = new ArrayList<>();

            String transactionKey = "0";
            for (EcTransaction transaction : list) {
                EcTransactionVo vo = new EcTransactionVo(transaction);
                voList.add(vo);

                if ("0".equals(transactionKey) && !"0".equals(transaction.getTransactionKey())) {
                    transactionKey = transaction.getTransactionKey();
                }
            }

            return new BaseResponse(200, "成功",
                    new GetTransactionListResponseVo(voList, transactionKey, userId, dappId));
        } else {
            return new BaseResponse(200, "成功",
                    new GetTransactionListResponseVo(new ArrayList<>(), "", userId, dappId));
        }
    }

    @ApiOperation(value = "根据logisticsCompanyId 和 logisticsNo 获取物流信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logisticsCompanyId", value = "物流公司Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "logisticsNo", value = "物流编号", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/logistics_list")
    public BaseResponse getLogisticsList(@RequestParam("logisticsCompanyId") Long logisticsCompanyId,
                                         @RequestParam("logisticsNo") String logisticsNo) {
        List<EcLogisticsRecord> records = logisticsService.selectByCompanyIdAndNo(logisticsCompanyId, logisticsNo);

        if (records != null && records.size() > 0) {
            List<EcLogisticsRecordVo> voList = records.stream().map(m -> new EcLogisticsRecordVo(m))
                    .collect(Collectors.toList());
            return new BaseResponse<>(200, "成功", voList);
        } else {
            return new BaseResponse<>(200, "成功", new ArrayList<>());
        }
    }

    @ApiOperation(value = "上链数据的验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "dappId", value = "dappId", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping(value = "/check")
    public BaseResponse<String> matche(@RequestParam("userId") Long userId, @RequestParam("dappId") Long dappId) {
        EcUserDapp userDapp = userDappService.selectByUserIdAndDappId(userId, dappId);

        if (userDapp == null) {
            return new BaseResponse(500, "失败", "");
        }

        List<EcTransaction> list = taskService.selectListTransactionMds5ByUserDappIds(Long.toString(userDapp.getId()), null);

        if (list == null || list.size() == 0) {
            return new BaseResponse(500, "失败", "");
        }

        Map<String, Object> map = new HashMap<>();
        list.stream().forEach(r -> {
            List<String> recoreds = new ArrayList<>();

            List<EcLogisticsRecord> recordList = r.getRecords();
            if (recordList != null && recordList.size() > 0) {
                recordList.stream().forEach(m -> {
                    recoreds.add(m.getLogisticsMd5());
                });
            }

            map.put(r.getDescribeMd5(), recoreds);
        });

        if (map != null && map.size() > 0) {
            return new BaseResponse(200, "成功", Md5Util.encode(JSON.toJSONString(map)));
        } else {
            return new BaseResponse(201, "MD5值不存在", "");
        }
    }

    @ApiOperation(value = "验证是否存在交易记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "dappId", value = "dappId", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping(value = "/check_order")
    public BaseResponse<String> checkOrder(@RequestParam("userId") Long userId, @RequestParam("dappId") Long dappId) {
        EcUserDapp userDapp = userDappService.selectByUserIdAndDappId(userId, dappId);

        if (userDapp == null) {
            return new BaseResponse<>(500, "失败", "");
        }

        List<EcTransaction> list = taskService.selectListTransactionMds5ByUserDappIds(Long.toString(userDapp.getId()), null);

        if (list == null || list.size() == 0) {
            return new BaseResponse<>(201, "交易记录不存在", "");
        }

        return new BaseResponse<>(200, "交易记录存在", "");
    }
}
