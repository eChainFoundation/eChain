package com.echain.exception;

import com.echain.common.ApplicationContextWare;
import com.echain.common.exception.ArgumentInvalidResult;
import com.echain.common.exception.LogicException;
import com.echain.common.logger.ErrorLog;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @Title: GlobalExceptionHandler
 * @Description: 全局异常处理类
 * @author: lijun
 * @date: 2017/5/13 12:48
 */
//@ControllerAdvice(annotations=RestController.class)
//@ControllerAdvice(basePackages={"com.xxx","com.ooo"})
@ControllerAdvice(basePackages = {"com.echain.controller"})
@ResponseBody
public class GlobalExceptionHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public void MethodArgumentNotValidHandler(HttpServletRequest request, MethodArgumentNotValidException exception) {
        List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
            invalidArgument.setDefaultMessage(error.getDefaultMessage());
            invalidArgument.setField(error.getField());
            invalidArgument.setRejectedValue(error.getRejectedValue());
            invalidArguments.add(invalidArgument);
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        try {
            response.sendRedirect("/exception");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ExceptionHandler(value = {LogicException.class})
    public void LogicExceptionHandler(LogicException ex) {
        logger.error("系统出现异常", ex);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        try {
            response.sendRedirect("/exception");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ExceptionHandler(value = {ClientAbortException.class})
    public void ClientAbortExceptionHandler(ClientAbortException ex) {
        logger.warn("客户端关闭异常，已忽略");
    }

    @ExceptionHandler(value = {Exception.class})
    public void handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        logger.error("系统出现异常", ex);
        ErrorLog.errorConvertJson(ApplicationContextWare.getAppName(), request.getHeader("trackingNo"),
                GlobalExceptionHandler.class, ex);
        try {
            response.sendRedirect("/exception");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
