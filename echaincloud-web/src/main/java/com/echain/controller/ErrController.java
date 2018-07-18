package com.echain.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/error", "/exception"})
public class ErrController implements ErrorController {


    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping
    public String error() {
        return getErrorPath();
    }

}