package com.echain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController {

    @RequestMapping(value = {"/error", "/exception"}, produces = "text/html;charset=UTF-8")
    public String error() {
        return "error";
    }

    @RequestMapping(value = {"/", "/index"}, produces = "text/html;charset=UTF-8")
    public String index() {
        return "index";
    }
}
