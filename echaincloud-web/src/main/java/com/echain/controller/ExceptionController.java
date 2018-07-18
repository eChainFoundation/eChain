package com.echain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController {

    @RequestMapping(value = {"/", "/index"}, produces = "text/html;charset=UTF-8")
    public String index() {
        return "index";
    }

    /**
     * 404页面
     */
    @RequestMapping(value = "/404", produces = "text/html;charset=UTF-8")
    public String page404() {
        return "404";
    }
}
