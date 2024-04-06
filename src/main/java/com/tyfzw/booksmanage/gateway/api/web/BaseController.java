package com.tyfzw.booksmanage.gateway.api.web;

import com.alibaba.fastjson.JSON;

import com.tyfzw.booksmanage.domain.vo.UserVO;
import com.tyfzw.booksmanage.gateway.api.web.response.ResponseResult;
import com.tyfzw.booksmanage.infrastructure.common.enums.ResultCode;
import com.tyfzw.booksmanage.infrastructure.common.execption.BizException;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
* @Author jiguangzhi
* @Date 2022/2/18
*/
@ControllerAdvice
@Slf4j
@Controller
public class BaseController {




   @ExceptionHandler
   @ResponseBody
   public ResponseResult handler(Exception e) {

       if (e instanceof IllegalArgumentException) {
           IllegalArgumentException illegalArgumentException = (IllegalArgumentException) e;
           return ResponseResult.fail(ResultCode.PARAM_CHECK_FAIL, illegalArgumentException.getMessage());
       }


       if (e instanceof BizException) {
           BizException bizException = (BizException) e;
           return ResponseResult.fail(bizException.getResultCode());
       }

       log.info("e", e);
       return ResponseResult.fail();
   }


    public UserVO getUserInfo(HttpServletRequest request) {
        return (UserVO) request.getSession().getAttribute("user");
    }






}
