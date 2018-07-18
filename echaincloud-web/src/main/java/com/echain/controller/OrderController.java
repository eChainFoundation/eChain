package com.echain.controller;

import com.alibaba.fastjson.JSON;
import com.echain.dao.EcOrderDao;
import com.echain.entity.EcOrder;
import com.echain.vo.ImportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/order")
@Controller
public class OrderController {

    @Autowired
    private EcOrderDao orderDao;

    @RequestMapping(value = "list", produces = "text/html;charset=UTF-8")
    public String list(ModelMap modelMap) throws Exception {
        List<EcOrder> list = orderDao.getByFlag();

        if (list == null) {
            modelMap.put("orderList", new ArrayList<>());
            modelMap.put("total", 0);
        } else {
            modelMap.put("orderList", list);
            modelMap.put("total", list.size());
        }

        return "echain/orderList";
    }

    @RequestMapping(value = "perfectInfo", produces = "text/html;charset=UTF-8")
    public String perfectInfo() throws Exception {

        return "echain/perfectInfo";
    }

    @RequestMapping(value = "jump.do", produces = "text/html;charset=UTF-8")
    public String jump(@RequestParam("orderId") String orderId, ModelMap modelMap) throws Exception {
        EcOrder order = orderDao.getByOrderId(orderId);
        modelMap.put("order", order);
        return "echain/jump";
    }

    @RequestMapping(value = "info.do", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getOrderById(@RequestParam("orderId") String orderId) throws Exception {
        EcOrder order = orderDao.getByOrderId(orderId);

        Map<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isEmpty(order.getDescribeMd5())) {
            map.put("hash", "");
        } else {
            map.put("hash", order.getDescribeMd5());
        }

        order.setFlag(1);
        orderDao.updateByPrimaryKeySelective(order);

        Thread.sleep(5000);

        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "detail.do", produces = "text/html;charset=UTF-8")
    public String detail(@RequestParam("orderId") String orderId, ModelMap modelMap) {
        EcOrder order = orderDao.getByOrderId(orderId);
        String desc = order.getDescribeText();
        ImportVo vo = JSON.parseObject(desc, ImportVo.class);

        modelMap.put("order", order);
        modelMap.put("vo", vo);
        return "echain/traceability";
    }
}
