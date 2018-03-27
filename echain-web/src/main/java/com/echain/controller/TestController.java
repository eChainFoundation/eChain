package com.echain.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.echain.service.ITransferDataService;

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
    private ITransferDataService transferService;

    /**
     * 简介：测试报告导出
     * <p>
     * 线上环境参数ID可以为空，测试和本地开发环境，ID参数是必须的。
     * 
     * @author jemond
     * @date 2017年9月4日 下午3:06:44
     * @param id 导出任务ID
     * @param isFbiScore 是否标题比对作品名，可以为空。
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "transfer", produces = "text/html;charset=UTF-8")
    public @ResponseBody String transferData(@RequestParam(required=true) String id,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	transferService.transferData(Long.parseLong(id));
        return "SUCCESS";
    }

}
