package com.echain.rest;

import com.echain.common.BaseResponse;
import com.echain.conf.ParamsProperties;
import com.echain.service.WalletService;
import com.echain.vo.TokenTransferEvent;
import com.okcoin.rest.vo.TickerVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@RequestMapping(value = "/rest/wallet", produces = "application/json")
@RestController
public class WalletRest {

    @Autowired
    ParamsProperties paramsProperties;

    @Autowired
    WalletService walletService;

    @ApiOperation(value = "获取以太坊账户余额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账户地址", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getETHBalance")
    public BaseResponse<BigInteger> getETHBalance(@RequestParam(value = "account") String account) throws Exception {
        BigInteger balance = walletService.getETHBalance(account);

        return new BaseResponse(200, "成功", balance);
    }

    @ApiOperation(value = "将ECT转账为ETH")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账户地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "points", value = "积分数量", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "transferToETH")
    public BaseResponse<String> transferToETH(@RequestParam(value = "account") String account,
                                              @RequestParam(value = "points") int points) throws Exception {
        walletService.transferToETH(account, points);

        return new BaseResponse(200, "成功", "");
    }

    @ApiOperation(value = "获取币币行情")
    @GetMapping(value = "ticker")
    public BaseResponse<Float> ticker() throws Exception {
        TickerVo vo = walletService.ticker();
        Float result = Float.parseFloat(vo.getTicker().getLast());

        return new BaseResponse(200, "成功", result);
    }

    @ApiOperation(value = "获取美元对人民币汇率")
    @GetMapping(value = "rate")
    public BaseResponse<Float> rate() {
        Float rest = walletService.getRate();

        return new BaseResponse(200, "成功", rest);
    }

    /*************************************** 以下是合约的 *********************************/

    @ApiOperation(value = "获取账户余额")
    @ApiImplicitParam(name = "account", value = "账户地址", required = true, dataType = "String", paramType = "query")
    @GetMapping(value = "getBalance")
    public BaseResponse<String> getBalance(@RequestParam(value = "account") String account) throws Exception {
        BigInteger balance = walletService.balanceOf(account);

        BigDecimal ether = new BigDecimal(balance).divide(BigDecimal.valueOf(Math.pow(10, paramsProperties.getDecimals())));
        String result = ether.stripTrailingZeros().toPlainString();

        return new BaseResponse(200, "获取账户余额成功", result);
    }

    @ApiOperation(value = "从主账号转账到用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账户地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "number", value = "代币数量", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "transfer")
    public BaseResponse<String> transfer(@RequestParam(value = "account") String account,
                                         @RequestParam(value = "number") String number) throws Exception {
        BigDecimal wei = new BigDecimal(number).multiply(BigDecimal.valueOf(Math.pow(10, paramsProperties.getDecimals())))
                .setScale(0, BigDecimal.ROUND_DOWN);
        BigInteger value = wei.toBigInteger();

        walletService.transfer(account, value);

        return new BaseResponse(200, "转账成功", "");
    }

    @ApiOperation(value = "两个账号之间转账")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "from", value = "from账户地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "to", value = "to账户地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "number", value = "代币数量", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "transferFrom")
    public BaseResponse<String> transferFrom(@RequestParam(value = "from") String from,
                                             @RequestParam(value = "to") String to,
                                             @RequestParam(value = "number") String number) throws Exception {
        BigDecimal wei = new BigDecimal(number).multiply(BigDecimal.valueOf(Math.pow(10, paramsProperties.getDecimals())))
                .setScale(0, BigDecimal.ROUND_DOWN);
        BigInteger value = wei.toBigInteger();

        walletService.transferFrom(from, to, value);

        return new BaseResponse(200, "转账成功", "");
    }

    @ApiOperation(value = "增加代币到用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账户地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "number", value = "代币数量", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "mintToken")
    public BaseResponse<String> mintToken(@RequestParam(value = "account") String account,
                                          @RequestParam(value = "number") String number) throws Exception {
        BigDecimal wei = new BigDecimal(number).multiply(BigDecimal.valueOf(Math.pow(10, paramsProperties.getDecimals())))
                .setScale(0, BigDecimal.ROUND_DOWN);
        BigInteger value = wei.toBigInteger();

        walletService.mintToken(account, value);

        return new BaseResponse(200, "成功", "");
    }

    @ApiOperation(value = "燃烧主账号余额")
    @ApiImplicitParam(name = "number", value = "代币数量", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "burn")
    public BaseResponse<String> burn(@RequestParam(value = "number") String number) throws Exception {
        BigDecimal wei = new BigDecimal(number).multiply(BigDecimal.valueOf(Math.pow(10, paramsProperties.getDecimals())))
                .setScale(0, BigDecimal.ROUND_DOWN);
        BigInteger value = wei.toBigInteger();

        walletService.burn(value);

        return new BaseResponse(200, "成功", "");
    }

    @ApiOperation(value = "燃烧账号余额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账户地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "number", value = "代币数量", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "burnFrom")
    public BaseResponse<String> burnFrom(@RequestParam(value = "account") String account,
                                         @RequestParam(value = "number") String number) throws Exception {
        BigDecimal wei = new BigDecimal(number).multiply(BigDecimal.valueOf(Math.pow(10, paramsProperties.getDecimals())))
                .setScale(0, BigDecimal.ROUND_DOWN);
        BigInteger value = wei.toBigInteger();

        walletService.burnFrom(account, value);

        return new BaseResponse(200, "成功", "");
    }

    @ApiOperation(value = "设置主账号")
    @ApiImplicitParam(name = "account", value = "账户地址", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "transferOwnership")
    public BaseResponse<String> transferOwnership(@RequestParam(value = "account") String account) throws Exception {
        walletService.transferOwnership(account);

        return new BaseResponse(200, "成功", "");
    }

    @ApiOperation(value = "获取合约钱包地址的交易记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账户地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "分页大小", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "按时间排序：顺序-asc、倒序-desc", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getTokenTransferEvents")
    public BaseResponse<List<TokenTransferEvent>> getTokenTransferEvents(@RequestParam(value = "account") String account,
                                                                         @RequestParam(value = "page") int page,
                                                                         @RequestParam(value = "size") int size,
                                                                         @RequestParam(value = "sort") String sort) {
        List<TokenTransferEvent> result = walletService.getTokenTransferEvents(account, page, size, sort);

        return new BaseResponse(200, "成功", result);
    }

    @ApiOperation(value = "获取合约的交易记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "分页大小", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "按时间排序：顺序-asc、倒序-desc", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getAllTokenTransferEvents")
    public BaseResponse<List<TokenTransferEvent>> getAllTokenTransferEvents(@RequestParam(value = "page") int page,
                                                                            @RequestParam(value = "size") int size,
                                                                            @RequestParam(value = "sort") String sort) {
        List<TokenTransferEvent> result = walletService.getTokenTransferEvents(page, size, sort);

        return new BaseResponse(200, "成功", result);
    }
}
