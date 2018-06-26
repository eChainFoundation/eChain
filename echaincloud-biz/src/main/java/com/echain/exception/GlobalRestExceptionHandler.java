package com.echain.exception;

import com.alibaba.fastjson.JSON;
import com.echain.common.ApplicationContextWare;
import com.echain.common.BaseResponse;
import com.echain.common.constant.StatusCode;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.freemud.cn
 *
 * @version V1.0
 * @Title: GlobalRestExceptionHandler
 * @Description: 全局异常处理类
 * @author: lijun
 * @date: 2017/5/13 12:48
 */
//@ControllerAdvice(annotations=RestController.class)
//@ControllerAdvice(basePackages={"com.xxx","com.ooo"})
@ControllerAdvice(basePackages = {"com.echain.rest"})
@ResponseBody
public class GlobalRestExceptionHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResponse<List<ArgumentInvalidResult>> MethodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
            invalidArgument.setDefaultMessage(error.getDefaultMessage());
            invalidArgument.setField(error.getField());
            invalidArgument.setRejectedValue(error.getRejectedValue());
            invalidArguments.add(invalidArgument);
        }
        BaseResponse response = new BaseResponse(StatusCode.STATUS_CODE_102.getStatusCode(), StatusCode.STATUS_CODE_102.getMessage(), invalidArguments);
        logger.error("接口响应-" + JSON.toJSONString(response));
        return response;
    }

    @ExceptionHandler(value = {LogicException.class})
    public BaseResponse LogicExceptionHandler(LogicException ex) {
        logger.error("系统出现异常", ex);
        return new BaseResponse(StatusCode.STATUS_CODE_101.getStatusCode(), ex.getMessage());
    }

    @ExceptionHandler(value = {ClientAbortException.class})
    public void ClientAbortExceptionHandler(ClientAbortException ex) {
        logger.warn("客户端关闭异常，已忽略");
    }

    @ExceptionHandler(value = {Exception.class})
    public BaseResponse handle(Exception ex, HttpServletRequest request) {
        logger.error("系统出现异常", ex);
        ErrorLog.errorConvertJson(ApplicationContextWare.getAppName(), request.getHeader("trackingNo"), GlobalRestExceptionHandler.class, ex);

        return new BaseResponse(StatusCode.STATUS_CODE_101.getStatusCode(), StatusCode.STATUS_CODE_101.getMessage());
    }

}
