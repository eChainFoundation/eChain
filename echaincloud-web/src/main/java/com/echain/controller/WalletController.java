package com.echain.controller;

import com.echain.common.exception.LogicException;
import com.echain.common.util.CommonUtil;
import com.echain.conf.ParamsProperties;
import com.echain.entity.*;
import com.echain.enumaration.WalletType;
import com.echain.service.UserService;
import com.echain.service.WalletService;
import com.echain.vo.TransferVo;
import com.echain.vo.rest.response.EcWalletRecordVo;
import com.echain.vo.rest.response.EcWalletVo;
import com.echain.vo.rest.response.EcWithdrawRecordVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/wallet")
@Controller
public class WalletController {

    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    ParamsProperties paramsProperties;

    @Autowired
    WalletService walletService;

    @Autowired
    UserService userService;

    /**
     * 钱包页面
     */
    @RequestMapping(value = "", produces = "text/html;charset=UTF-8")
    public String list(@RequestParam(value = "userId") String userId, ModelMap modelMap) throws Exception {
        TransferVo vo = walletService.transferToETH("1000");
        TransferVo vo2 = walletService.transferToETH("10000");
        TransferVo vo3 = walletService.transferToETH("100000");

        modelMap.put("transfer", vo);
        modelMap.put("transfer2", vo2);
        modelMap.put("transfer3", vo3);

        EcUser ecUser = userService.selectUserByPrimaryKey(Long.parseLong(userId));
        modelMap.put("phone", ecUser.getPhoneNumber());

        List<EcWallet> ecWallets = walletService.selectByUserId(Long.parseLong(userId));

        BigDecimal[] total = {new BigDecimal(0.00)};

        if (CommonUtil.isEmpty(ecWallets)) {
            modelMap.put("list", ecWallets);
            modelMap.put("total", total[0].stripTrailingZeros().toPlainString());

            return "echain/wallet";
        }

        List<EcWalletVo> list = new ArrayList<>();

        ecWallets.stream().forEach(m -> {
            EcWalletVo v = new EcWalletVo(m);
            if (WalletType.ETH.getName().equals(v.getType())) {
                BigDecimal val = m.getValue();
                v.setValue(val.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                BigDecimal rmb = val.divide(BigDecimal.valueOf(Double.parseDouble(vo.getRate()) * 100), 2, BigDecimal.ROUND_DOWN);
                total[0] = total[0].add(rmb);
                v.setRmb(rmb.stripTrailingZeros().toPlainString());
            } else if (WalletType.ECP.getName().equals(v.getType())) {
                BigDecimal val2 = m.getValue();
                v.setValue(val2.setScale(8, BigDecimal.ROUND_DOWN)
                        .stripTrailingZeros().toPlainString());
                BigDecimal rmb2 = val2.divide(BigDecimal.valueOf(100L), 2, BigDecimal.ROUND_DOWN);
                total[0] = total[0].add(rmb2);
                v.setRmb(rmb2.stripTrailingZeros().toPlainString());
            }
            list.add(v);
        });

        modelMap.put("list", list);
        modelMap.put("total", total[0].stripTrailingZeros().toPlainString());

        return "echain/wallet";
    }

    /**
     * 钱包详情页面
     */
    @RequestMapping(value = "/detail", produces = "text/html;charset=UTF-8")
    public String walletDetail(@RequestParam(value = "userId") String userId, @RequestParam(value = "type") String type,
                               ModelMap modelMap) throws Exception {
        if (!WalletType.isInclude(type)) {
            logger.error("钱包类型不存在");
            throw new LogicException("钱包类型不存在");
        }

        int walletType = WalletType.getByName(type).getIndex();
        Long uid = Long.parseLong(userId);

        EcUser ecUser = userService.selectUserByPrimaryKey(uid);
        modelMap.put("phone", ecUser.getPhoneNumber());

//        TransferVo vo = walletService.transferToETH("1000");

        EcWallet m = walletService.selectByUserIdType(uid, walletType);

        if (m != null) {
            modelMap.put("wallet", getEcWalletVo(m));

            List<EcWalletRecord> recordList = walletService.getRecordByType(uid, walletType);

            if (CommonUtil.isEmpty(recordList)) {
                modelMap.put("recordList", new ArrayList<>());
            } else {
                List<EcWalletRecordVo> list = recordList.stream().map(r -> new EcWalletRecordVo(r, true)).collect(Collectors.toList());
                modelMap.put("recordList", list);
            }
        } else {
            modelMap.put("wallet", new EcWalletVo(type, "0", "0"));
            modelMap.put("recordList", new ArrayList<>());
        }

        return "echain/recentRecord";
    }

    private EcWalletVo getEcWalletVo(EcWallet m) {
        EcWalletVo v = new EcWalletVo(m);
        if (WalletType.ETH.getName().equals(v.getType())) {
            BigDecimal val = m.getValue();
            v.setValue(val.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
//            BigDecimal rmb = val.divide(BigDecimal.valueOf(Double.parseDouble(vo.getRate()) * 100), 2, BigDecimal.ROUND_DOWN);
//            v.setRmb(rmb.stripTrailingZeros().toPlainString());
        } else if (WalletType.ECP.getName().equals(v.getType())) {
            BigDecimal val2 = m.getValue();
            v.setValue(val2.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
//            BigDecimal rmb2 = val2.divide(BigDecimal.valueOf(100L), 2, BigDecimal.ROUND_DOWN);
//            v.setRmb(rmb2.stripTrailingZeros().toPlainString());
        }
        return v;
    }

    /**
     * 钱包二维码页面
     */
    @RequestMapping(value = "/img", produces = "text/html;charset=UTF-8")
    public String walletImg(@RequestParam(value = "userId") String userId, @RequestParam(value = "type") String type,
                            ModelMap modelMap) throws Exception {
        Long uid = Long.parseLong(userId);
        int walletType = WalletType.getByName(type).getIndex();

        EcUser ecUser = userService.selectUserByPrimaryKey(uid);
        modelMap.put("phone", ecUser.getPhoneNumber());

        EcWallet wallet = walletService.selectByUserIdType(uid, walletType);

        if (wallet == null) {
            //用户绑定钱包
            if (WalletType.ETH.getIndex() == walletType || WalletType.ECP.getIndex() == walletType) {
                EcWallet w = new EcWallet();
                int flag = 0;
                while (flag == 0) {
                    w = walletService.selectByTypeWithoutUser(WalletType.ETH.getIndex());

                    if (w == null) {
                        logger.error("没有空闲钱包供用户绑定");
                        throw new LogicException("系统出现异常，请稍后再试");
                    }

                    w.setUserId(uid);
                    flag = walletService.updateUserId(w);
                }

                walletService.updateUserId(new EcWallet(uid, w.getWallet(), WalletType.ECP.getIndex()));

                EcWallet wallet2 = walletService.selectByUserIdType(uid, walletType);
                modelMap.put("qImg", wallet2.getQimgFile());
                modelMap.put("wallet", wallet2.getWallet());
            }
        } else {
            modelMap.put("qImg", wallet.getQimgFile());
            modelMap.put("wallet", wallet.getWallet());
        }

        modelMap.put("type", type);

        return "echain/rechargeCode";
    }

    /**
     * 钱包交易记录详情页面
     */
    @RequestMapping(value = "/recordDetail", produces = "text/html;charset=UTF-8")
    public String recordDetail(@RequestParam(value = "id") Long id, ModelMap modelMap) throws Exception {
        EcWalletRecord record = walletService.getRecordById(id);
        EcWalletRecordVo vo = new EcWalletRecordVo(record, false);

        modelMap.put("record", vo);

        return "echain/tradeRecord";
    }

    /**
     * 钱包提现页面
     */
    @RequestMapping(value = "/withdraw", produces = "text/html;charset=UTF-8")
    public String withdraw(@RequestParam(value = "userId") String userId, @RequestParam(value = "type") String type,
                           ModelMap modelMap) throws Exception {
        Long uid = Long.parseLong(userId);
        int walletType = WalletType.getByName(type).getIndex();

//        TransferVo vo = walletService.transferToETH("1000");

        EcWallet m = walletService.selectByUserIdType(uid, walletType);

        modelMap.put("wallet", m != null ? getEcWalletVo(m) : new EcWalletVo(type, "0", "0"));

        EcUserWallet address = userService.getUserWalletByUserId(uid);
        modelMap.put("address", address);

        EcUser ecUser = userService.selectUserByPrimaryKey(uid);
        modelMap.put("phone", ecUser.getPhoneNumber());
        modelMap.put("country", ecUser.getCountry());

        return "echain/embody";
    }

    /**
     * 钱包提现记录页面
     */
    @RequestMapping(value = "/withdrawHistory", produces = "text/html;charset=UTF-8")
    public String withdrawHistory(@RequestParam(value = "userId") String userId, @RequestParam(value = "type") String type,
                                  ModelMap modelMap) {
        Long uid = Long.parseLong(userId);
        int walletType = WalletType.getByName(type).getIndex();

        List<EcWithdrawRecord> list = walletService.getWithdrawRecordListByUser(uid, walletType);

        if (CommonUtil.isEmpty(list)) {
            modelMap.put("list", new ArrayList<>());
        } else {
            List<EcWithdrawRecordVo> result = list.stream().map(m -> new EcWithdrawRecordVo(m)).collect(Collectors.toList());
            modelMap.put("list", result);
        }

        return "echain/presentRecord";
    }
}
