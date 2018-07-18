package com.echain.controller;

import com.echain.common.util.DateUtil;
import com.echain.solidity.UpDownData;
import com.echain.task.UpTransactionTask;
import com.echain.vo.ImportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.protocol.Web3j;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * 简介：测试类
 * <p>
 * code review 2018-03-09 16:16
 *
 * @author roc
 *
 */
@RequestMapping("/test")
@Controller
public class TestController {

    @Autowired
    UpTransactionTask task;

    @Autowired
    UpDownData upDownData;

    @Resource(name = "web3j")
    Web3j web3j;

    @Resource(name = "web3jTwo")
    Web3j web3jTwo;

    @RequestMapping(value = "test.html", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String process(@RequestParam(value = "date", required = false) String date,
                   @RequestParam(value = "frequency", required = false) String frequency,
                   @RequestParam(value = "net", required = false) Integer net,
                   @RequestParam(value = "price", required = false) Integer price) {
        if (!DateUtil.isValidDate(date, DateUtil.FORMAT_DEFAULT)) {
            return "参数date格式不正确，格式为：yyyyMMddHHmmss";
        }

        String f = StringUtils.isEmpty(frequency) ? "all" : frequency;
        Web3j web = (net == null || net == 1) ? web3j : web3jTwo;

        if ("day".equals(f)) {
            Date t = StringUtils.isEmpty(date) ? new Date() : DateUtil.convert2Date(date, DateUtil.FORMAT_DEFAULT);
            task.testHandler(t, f, web, BigInteger.valueOf(price * 1000000000L));
        } else {
            task.testHandler(null, f, web, BigInteger.valueOf(price * 1000000000L));
        }

        return "success";
    }

    @RequestMapping(value = "task", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String task() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(
                "DSBB059201806058181141510281931,黄先生,13828849854,59,海淘正品险,0.399,2018/6/5 14:45,2018/6/5 14:45,LROAAH,SO97143563166,399,无,620354653830,意大利你好,直邮,2018/6/1 0:38,暂无,");
        list.add(
                "DSBB059201806058181142026886258,徐犹文,15980276753,59,海淘正品险,0.395,2018/6/5 14:45,2018/6/5 14:45,LROAAH,SO97143668337,395,无,889934862156597325,意大利你好,直邮,2018/6/1 0:39,暂无,");
        list.add(
                "DSBB059201806058181142431860769,李烨,13985070994,59,海淘正品险,1.088,2018/6/5 14:45,2018/6/5 14:45,LROAAH,SO97143685158,1088,无无,620354653867,意大利你好,直邮,2018/6/1 0:39,暂无暂无,");
        list.add(
                "DSBB059201806058181142835434200,付磊,18845034444,59,海淘正品险,1.999,2018/6/5 14:45,2018/6/5 14:45,LROAAH,SO97143994996,1999,无,620354654146,意大利你好,直邮,2018/6/1 0:40,暂无,");

        for (String str : list) {
            String[] sp = str.split(",");

            ImportVo vo = new ImportVo();

            vo.setOrderId(sp[0]);
            vo.setUserName(sp[1]);
            vo.setPhoneNumber(sp[2]);
            vo.setProductId(sp[3]);
            vo.setProductName(sp[4]);
            vo.setPremium(sp[5]);
            vo.setOrderTime(sp[6]);
            vo.setInsureTime(sp[7]);
            vo.setSource(sp[8]);
            vo.setPurchasOrderNo(sp[9]);
            vo.setPurchasOrderAmount(sp[10]);
            vo.setCategory(sp[11]);
            vo.setExpressNo(sp[12]);
            vo.setExpressCompanyName(sp[13]);
            vo.setExpressChannel(sp[14]);
            vo.setExpressTime(sp[15]);
            vo.setBrand(sp[16]);

            task.toChain(vo);
        }
        return "success";
    }
}
