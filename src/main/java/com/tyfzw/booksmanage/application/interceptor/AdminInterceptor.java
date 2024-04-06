package com.tyfzw.booksmanage.application.interceptor;

import com.alibaba.fastjson.JSON;
import com.tyfzw.booksmanage.domain.vo.UserVO;
import com.tyfzw.booksmanage.gateway.api.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;




/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2022-03-21
 * Time: 18:36
 */
@Component
public class AdminInterceptor extends BaseController implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String uri = request.getRequestURI();
            if (uri.endsWith("js")||uri.endsWith("css")||uri.endsWith("jpg")||uri.endsWith("svg")){
                return true ;
            }
            UserVO userVO = getUserInfo(request);
            if (Objects.isNull(userVO)) {
                request.getRequestDispatcher("/user/login").forward(request, response);
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
