package com.spiderdt.common.filters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fivebit on 2017/6/30.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /*
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }*/
    @ExceptionHandler(value = AppException.class)
    @ResponseBody
    public JSONObject jsonErrorHandler(HttpServletRequest req, AppException e) throws Exception {
        JSONObject ret = new JSONObject();
        ret.put("status",e.getStatus());
        ret.put("code",e.getCode());
        ret.put("data",e.getData());
        return ret;
    }
}
