package com.tyfzw.booksmanage.gateway.api.web.controller;

import com.tyfzw.booksmanage.application.handler.UserHandler;
import com.tyfzw.booksmanage.domain.bo.LoginBO;
import com.tyfzw.booksmanage.domain.bo.RegisterBO;
import com.tyfzw.booksmanage.domain.bo.UpdateUserBO;
import com.tyfzw.booksmanage.domain.bo.UserListBO;
import com.tyfzw.booksmanage.domain.vo.BookVO;
import com.tyfzw.booksmanage.domain.vo.UserVO;
import com.tyfzw.booksmanage.gateway.api.web.BaseController;
import com.tyfzw.booksmanage.gateway.api.web.request.LoginRequest;
import com.tyfzw.booksmanage.gateway.api.web.request.RegisterRequest;
import com.tyfzw.booksmanage.gateway.api.web.request.UpdateUserRequest;
import com.tyfzw.booksmanage.gateway.api.web.response.*;
import com.tyfzw.booksmanage.infrastructure.common.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-12
 * Time: 0:00
 */
@Controller
@RequestMapping("user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private HttpServletRequest httpServletRequest;



    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/detail_user")
    public String userDetail() {
        return "detail_user";
    }

    @GetMapping(value = "/detail_admin")
    public String adminDetail() {
        return "detail_admin";
    }

    /**
     * 登录
     * @param loginRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public ResponseResult<LoginResponse> login(Model model, HttpServletRequest request, @RequestBody LoginRequest loginRequest) {
        ValidationUtils.validate(loginRequest);
        LoginBO loginBO = new LoginBO();
        BeanUtils.copyProperties(loginRequest, loginBO);
        UserVO userVO = userHandler.login(loginBO);
        model.addAttribute("user", userVO);
        request.getSession().setAttribute("user", userVO);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setIdentity(userVO.getIdentity());
        return ResponseResult.success(loginResponse);
    }



    /**
     * 查查询用户信息(借书管理)
     * @param userId
     * @return
     */
    @PostMapping("/userInfo")
    @ResponseBody
    public  ResponseResult<UserResponse> userInfo(String userId) {
        UserResponse userResponse = userHandler.userInfo(userId);
        return ResponseResult.success(userResponse);
    }



    @PostMapping("/list")
    @ResponseBody
    public DataGridDataSource<UserVO> getUserList(@RequestParam(value = "userName", required = false, defaultValue = "") String nickname,
                                                  @RequestParam(value = "userEmail", required = false, defaultValue = "") String email,
                                                  @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                  @RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows) {

        UserListBO userListBO = new UserListBO();
        userListBO.setEmail(email);
        userListBO.setNickname(nickname);
        userListBO.setPage(page);
        userListBO.setRows(rows);
        return userHandler.selectTotalUser(userListBO);
    }


    @PostMapping("/save")
    @ResponseBody
    public ResponseResult<AddResponse> saveUser(RegisterRequest registerRequest) {
        ValidationUtils.validate(registerRequest);
        RegisterBO registerBO = new RegisterBO();
        BeanUtils.copyProperties(registerRequest, registerBO);
        boolean flag = userHandler.saveUser(registerBO);
        AddResponse addResponse = new AddResponse();
        addResponse.setSuccess(flag);
        return ResponseResult.success(addResponse);
    }


    @PutMapping("/update")
    @ResponseBody
    public ResponseResult<UpdateResponse> updateUser(UpdateUserRequest updateUserRequest) {
        ValidationUtils.validate(updateUserRequest);
        UpdateUserBO updateUserBO = new UpdateUserBO();
        BeanUtils.copyProperties(updateUserRequest, updateUserBO);
        boolean flag = userHandler.updateUser(updateUserBO);
        UpdateResponse updateResponse = new UpdateResponse();
        return ResponseResult.success(updateResponse);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseResult<DeleteResponse> deleteUser(@RequestParam(value = "userId") String userId) {
        boolean flag = userHandler.deleteUserByUserId(userId);
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setSuccess(flag);
        return ResponseResult.success(deleteResponse);
    }


    /**
     * 启用
     *
     * @param userId
     * @return
     */
    @PostMapping("/enable")
    @ResponseBody
    public ResponseResult<UpdateResponse> enable(@RequestParam(value = "userId") String userId) {
        boolean flag = userHandler.enableUser(userId);
        UpdateResponse updateResponse = new UpdateResponse();
        updateResponse.setSuccess(flag);
        return ResponseResult.success(updateResponse);
    }


    @PostMapping("/disable")
    @ResponseBody
    public ResponseResult<UpdateResponse> disable(@RequestParam(value = "userId") String userId) {
        boolean flag = userHandler.disableUser(userId);
        UpdateResponse updateResponse = new UpdateResponse();
        updateResponse.setSuccess(flag);
        return ResponseResult.success(updateResponse);
    }


}
