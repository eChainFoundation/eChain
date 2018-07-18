package com.echain.rest;

import com.echain.common.BaseResponse;
import com.echain.common.util.CommonUtil;
import com.echain.common.util.ExceptionUtil;
import com.echain.common.util.IdWorker;
import com.echain.conf.ParamsProperties;
import com.echain.dao.EcPointsPoolDao;
import com.echain.entity.EcPointsPool;
import com.echain.entity.EcUser;
import com.echain.entity.EcWallet;
import com.echain.entity.EcWithdrawRecord;
import com.echain.enumaration.WalletType;
import com.echain.service.MessageService;
import com.echain.service.PointService;
import com.echain.service.UserService;
import com.echain.service.WalletService;
import com.echain.util.QrcodeUtil;
import com.echain.vo.TokenTransferEvent;
import com.echain.vo.TransferVo;
import com.echain.vo.rest.response.EcWalletVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.admin.Admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(value = "/rest/wallet", produces = "application/json")
@RestController
public class WalletRest {

    private static final Logger logger = LoggerFactory.getLogger(WalletRest.class);

    @Autowired
    ParamsProperties paramsProperties;

    @Autowired
    WalletService walletService;

    @Autowired
    UserService userService;

    @Autowired
    PointService pointService;

    @Autowired
    MessageService messageService;

    @Resource(name = "admin")
    Admin admin;

    @Resource(name = "adminTwo")
    Admin adminTwo;

    @Autowired
    EcPointsPoolDao pointsPoolDao;

    @ApiOperation(value = "获取以太坊账户余额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账户地址", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getETHBalance")
    public BaseResponse<String> getETHBalance(@RequestParam(value = "account") String account) throws Exception {
        BigInteger balance = walletService.getETHBalance(account);

        BigDecimal ether = new BigDecimal(balance).divide(BigDecimal.valueOf(Math.pow(10, 18)));
        String result = ether.stripTrailingZeros().toPlainString();

        return new BaseResponse(200, "成功", result);
    }

    @ApiOperation(value = "确认充值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "points", value = "积分数量", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "transferETH")
    public BaseResponse<String> transferETH(@RequestParam(value = "userId") Long userId,
                                            @RequestParam(value = "points") int points) throws Exception {
        walletService.transferETH(userId, points);

        return new BaseResponse(200, "成功", "");
    }

    @ApiOperation(value = "获取币币行情")
    @GetMapping(value = "ticker")
    public BaseResponse<Float> ticker() throws Exception {
        Float result = walletService.getTicker();

        return new BaseResponse(200, "成功", result);
    }

    @ApiOperation(value = "获取美元对人民币汇率")
    @GetMapping(value = "rate")
    public BaseResponse<Float> rate() {
        Float result = walletService.getRate();

        return new BaseResponse(200, "成功", result);
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
    @PostMapping(value = "transferETC")
    public BaseResponse<String> transferETC(@RequestParam(value = "account") String account,
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

    @ApiOperation(value = "销毁主账号的代币")
    @ApiImplicitParam(name = "number", value = "代币数量", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "burn")
    public BaseResponse<String> burn(@RequestParam(value = "number") String number) throws Exception {
        BigDecimal wei = new BigDecimal(number).multiply(BigDecimal.valueOf(Math.pow(10, paramsProperties.getDecimals())))
                .setScale(0, BigDecimal.ROUND_DOWN);
        BigInteger value = wei.toBigInteger();

        walletService.burn(value);

        return new BaseResponse(200, "成功", "");
    }

    @ApiOperation(value = "销毁账号的代币")
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

    @ApiOperation(value = "代币换算为以太币")
    @ApiImplicitParam(name = "number", value = "代币数量", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "transferToETH")
    public BaseResponse<TransferVo> transferToETH(@RequestParam(value = "number") String number) throws Exception {
        TransferVo vo = walletService.transferToETH(number);

        return new BaseResponse(200, "成功", vo);
    }

    @ApiOperation(value = "以太币换算为人民币")
    @ApiImplicitParam(name = "number", value = "以太币数量", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "transferToRMB")
    public BaseResponse<String> transferToRMB(@RequestParam(value = "number") String number) throws Exception {
        String rmb = walletService.transferToRMB(number);

        return new BaseResponse(200, "成功", rmb);
    }

    @ApiOperation(value = "创建账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", value = "创建账号的数量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "账号类型", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "net", value = "RPCIP，1:172.31.6.100；2:47.100.36.250", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "createAccount")
    public @ResponseBody
    BaseResponse<String> createAccount(@RequestParam(value = "number", required = false) int number,
                                       @RequestParam(value = "type", required = false) int type,
                                       @RequestParam(value = "net", required = false) Integer net) throws Exception {
        Admin a = (net == null || net == 1) ? admin : adminTwo;

        int count = 0;
        for (int i = 0; i < number; i++) {
            String password = Long.toString(IdWorker.generateId());
            String account = walletService.createAccount(password, a);

            if (CommonUtil.isEmpty(account)) {
                continue;
            }

            if (WalletType.ETH.getIndex() == type || WalletType.ECP.getIndex() == type) {
                walletService.save(account, password, WalletType.ECP.getIndex());
                walletService.save(account, password, WalletType.ETH.getIndex());
            } else {
                walletService.save(account, password, type);
            }

            QrcodeUtil.createQrImg(account, paramsProperties.getWalletQimgPath() + account + ".png");
            String qrcode = "/static/wallet/" + account + ".png";

            walletService.updateImg(account, WalletType.ETH.getIndex(), qrcode);
            walletService.updateImg(account, WalletType.ECP.getIndex(), qrcode);

            count++;
        }

        return new BaseResponse(200, "成功", "成功创建" + count + "个钱包地址");
    }

    @ApiOperation(value = "更新钱包文件路径")
    @PostMapping(value = "updateWalletFile")
    public @ResponseBody
    BaseResponse<String> updateWalletFile() {
        List<EcWallet> wallets = walletService.selectByWithoutFile();

        if (CommonUtil.isEmpty(wallets)) {
            return new BaseResponse<>(200, "成功", "");
        }

        List<String> fileList = new ArrayList<>();

        String walletFilePath = paramsProperties.getWalletFilePath();

        File f = new File(walletFilePath);
        if (!f.exists()) {
            return new BaseResponse<>(500, walletFilePath + " not exists", "");
        }
        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isFile()) {
                fileList.add(fs.getName());
            }
        }

        for (EcWallet wallet : wallets) {
            for (String filePath : fileList) {
                if (filePath.endsWith(wallet.getWallet().substring(2, wallet.getWallet().length()))) {
                    wallet.setWalletFile(walletFilePath + filePath);
                    walletService.updateByPrimaryKeySelective(wallet);
                }
            }
        }

        return new BaseResponse<>(200, "成功", "");
    }

    @ApiOperation(value = "用户绑定钱包")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "钱包类型", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "bindWallet")
    public BaseResponse<String> bindWallet(@RequestParam(value = "userId") String userId,
                                           @RequestParam(value = "type") Integer type) {
        Long uid = Long.parseLong(userId);

        EcWallet wallet = walletService.selectByUserIdType(uid, type);

        if (wallet != null && wallet.getId() != null && wallet.getId() != 0L) {
            return new BaseResponse(500, "用户已绑定过此类型的钱包", "");
        }
        if (WalletType.ETH.getIndex() == type || WalletType.ECP.getIndex() == type) {
            EcWallet w = new EcWallet();
            int flag = 0;
            while (flag == 0) {
                w = walletService.selectByTypeWithoutUser(WalletType.ETH.getIndex());
                w.setUserId(uid);
                flag = walletService.updateUserId(w);
            }

            walletService.updateUserId(new EcWallet(uid, w.getWallet(), WalletType.ECP.getIndex()));
        } else {
            return new BaseResponse(500, "此钱包类型暂不支持", "");
        }

        return new BaseResponse(200, "成功", "");
    }

    @ApiOperation(value = "获取用户钱包列表")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "query")
    @GetMapping(value = "list")
    public BaseResponse<List<EcWalletVo>> list(@RequestParam(value = "userId") String userId) {
        List<EcWallet> ecWallets = walletService.selectByUserId(Long.parseLong(userId));

        if (CommonUtil.isEmpty(ecWallets)) {
            return new BaseResponse<>(200, "成功", new ArrayList<>());
        }

        List<EcWalletVo> list = ecWallets.stream().map(m -> new EcWalletVo(m)).collect(Collectors.toList());

        return new BaseResponse(200, "成功", list);
    }

    @Transactional
    @ApiOperation(value = "提现")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "代币类型", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "number", value = "数量", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "to", value = "收款地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "verificationCode", value = "验证码", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "withdraw")
    public BaseResponse<String> withdraw(@RequestParam(value = "userId") String userId, @RequestParam(value = "type") String type,
                                         @RequestParam(value = "number") String number, @RequestParam(value = "to") String to,
                                         @RequestParam(value = "verificationCode") String verificationCode,
                                         HttpServletRequest request) throws Exception {
        Object verCode = request.getSession().getAttribute("verCode");
        if (verCode == null || CommonUtil.isEmpty(verificationCode) || !verCode.toString().equals(verificationCode)) {
            return new BaseResponse<>(500, "验证码有误，请稍后再试");
        }

        Long uid = Long.parseLong(userId);
        int walletType = WalletType.getByName(type).getIndex();
        BigDecimal value = new BigDecimal(number);

        //check wallet length
        if (CommonUtil.isEmpty(to) || to.length() != 42) {
            return new BaseResponse<>(500, "提现地址有误，请重新输入");
        }

        //更新user_wallet
        EcUser ecUser = userService.selectByPrimaryKey(uid);
        userService.saveOrUpdateUserWallet(ecUser, to);

        EcWallet wallet = walletService.selectByUserIdType(uid, walletType);

        //手续费 10元
        BigDecimal fee = new BigDecimal(10L);
        if (WalletType.ETH.getIndex() == walletType) {
            fee = fee.divide(BigDecimal.valueOf(walletService.getRate() * walletService.getTicker()), 8, BigDecimal.ROUND_HALF_UP);
        } else if (WalletType.ECP.getIndex() == walletType) {
            fee = fee.multiply(BigDecimal.valueOf(100L));
        }

        if (value.compareTo(wallet.getValue().subtract(fee)) == 1) {
            return new BaseResponse<>(500, "提现数量大于可转额度！", "");
        }

        if (WalletType.ECP.getIndex() == walletType) {
//            walletService.transferFrom(wallet.getWallet(), to, value.multiply(BigDecimal.valueOf(
//                    Math.pow(10, paramsProperties.getDecimals()))).setScale(0, BigDecimal.ROUND_DOWN).toBigInteger());

            // update wallet value
            int flag = 0;
            while (flag == 0) {
                wallet = walletService.selectByUserIdType(uid, walletType);
                flag = walletService.updateValue(uid, walletType, wallet.getValue().subtract(value).subtract(fee), wallet.getValue());
            }

        } else if (WalletType.ETH.getIndex() == walletType) {
//            Credentials credentials = walletService.getCredentials(wallet.getPassword(), wallet.getWalletFile());
//            walletService.transferToETH(to, value, credentials);

            // update wallet value
            int flag2 = 0;
            while (flag2 == 0) {
                wallet = walletService.selectByUserIdType(uid, walletType);
                flag2 = walletService.updateValue(uid, walletType, wallet.getValue().subtract(value).subtract(fee), wallet.getValue());
            }
        }

        // save record
        walletService.saveWithDrawRecord(new EcWithdrawRecord(IdWorker.generateId(), uid, walletType, wallet.getWallet(), to,
                value.multiply(new BigDecimal(-1)), 0, "ECP提现" + value + type));
        walletService.saveWithDrawRecord(new EcWithdrawRecord(IdWorker.generateId(), uid, walletType, wallet.getWallet(), to,
                fee.multiply(new BigDecimal(-1)), 0, "ECP提现手续费" + fee + type));

        if (WalletType.ECP.getIndex() == walletType) {
            //积分划转
            pointService.changePoints2(uid, value.longValue(), "ECP提现", '1');
            pointService.changePoints2(uid, fee.longValue(), "ECP提现手续费", '1');

            // 更新积分池
            int flag = 0;
            while (flag == 0) {
                EcPointsPool pool = pointsPoolDao.selectByPrimaryKey(1L);
                if (pool == null) {
                    break;
                }
                flag = pointsPoolDao.updatePoint(1L,
                        pool.getNowPoints() - value.longValue() + fee.longValue(), pool.getNowPoints(),
                        pool.getConsumePoints() + value.longValue(), pool.getConsumePoints(),
                        pool.getAllPoints() - value.longValue() + fee.longValue(), pool.getAllPoints());
            }
        }

        return new BaseResponse(200, "成功", "");
    }

    @ApiOperation(value = "获取验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "country", value = "国家代码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phoneNumber", value = "手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "代币名称", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "getVerificationCode")
    public BaseResponse<String> getVerificationCode(@RequestParam(required = false) String country,
                                                    @RequestParam(value = "phoneNumber") String phoneNumber,
                                                    @RequestParam(value = "name") String name,
                                                    HttpServletRequest request) {
        int rand = (int) ((Math.random() * 9 + 1) * 100000); // 验证码逻辑
        request.getSession().setAttribute("verCode", rand);
        try {
            if (CommonUtil.isEmpty(country)) {
                country = "86";
            }
            if ("0".equals(country)) {
                country = "86";
            }
            messageService.sendMessage(phoneNumber, rand + "", 60, name, "+" + country.trim(),
                    MessageService.withdraw_code);
            return new BaseResponse<>(200, "获取验证码成功", rand + "");
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionMsg(e));
            return new BaseResponse<>(500, "获取验证码失败，请稍后再试", "");
        }
    }
}
