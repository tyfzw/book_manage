package com.tyfzw.booksmanage.application.handler;

import com.tyfzw.booksmanage.application.service.UserService;
import com.tyfzw.booksmanage.domain.bo.LoginBO;
import com.tyfzw.booksmanage.domain.bo.RegisterBO;
import com.tyfzw.booksmanage.domain.bo.UpdateUserBO;
import com.tyfzw.booksmanage.domain.bo.UserListBO;
import com.tyfzw.booksmanage.domain.vo.BookVO;
import com.tyfzw.booksmanage.domain.vo.UserVO;
import com.tyfzw.booksmanage.gateway.api.web.response.DataGridDataSource;
import com.tyfzw.booksmanage.gateway.api.web.response.UserResponse;
import com.tyfzw.booksmanage.infrastructure.common.enums.ResultCode;
import com.tyfzw.booksmanage.infrastructure.common.enums.UserStatusEnum;
import com.tyfzw.booksmanage.infrastructure.common.execption.BizException;
import com.tyfzw.booksmanage.infrastructure.common.utils.CalendarUtils;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.UserDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.UserListDO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-13
 * Time: 11:11
 */
@Service
public class UserHandler {

    @Autowired
    private UserService userService;

    public  boolean saveUser(RegisterBO registerBO) {
        UserDO userDO = userService.selectUserByEmail(registerBO.getEmail());
        if (!Objects.isNull(userDO)) {
            throw new BizException(ResultCode.USER_ALREADY_EXISTS);
        }
        int i = userService.insertUser(registerBO.getNickname(), registerBO.getPassword(), registerBO.getEmail());
        if (i != 1) {
            throw new BizException(ResultCode.USER_REGISTER_FAIL);
        }
        return true;
    }

    public  UserVO login(LoginBO loginBO) {
        UserDO userDO = userService.selectUserByEmailAndPassword(loginBO.getEmail(), loginBO.getPassword());
        if (Objects.isNull(userDO)) {
            throw new BizException(ResultCode.USER_NOT_FIND);
        }
        if (!loginBO.getPassword().equals(userDO.getPassword())) {
            throw new BizException(ResultCode.USERID_PASSWORD_ERROR);
        }
        if (UserStatusEnum.FREEZE.name().equals(userDO.getStatus())) {
            throw new BizException(ResultCode.USER_ALREADY_FREEZE);
        }
        if (UserStatusEnum.FORBIDDEN.name().equals(userDO.getStatus())) {
            throw new BizException(ResultCode.USER_ALREADY_FORBIDDEN);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        userVO.setCreateTime(CalendarUtils.dateToString(userDO.getCreateTime()));
        userVO.setUpdateTime(CalendarUtils.dateToString(userDO.getUpdateTime()));
        return userVO;
    }

    public UserResponse userInfo(String userId) {
        if (Strings.isBlank(userId)) {
            throw new IllegalArgumentException("userId is null");
        }
        UserDO userDO = userService.selectUserByUserId(userId);
        if (Objects.isNull(userDO)) {
            throw new BizException(ResultCode.USER_NOT_FIND);
        }
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userDO, userResponse);
        return userResponse;
    }

    public DataGridDataSource<UserVO> selectTotalUser(UserListBO userListBO) {
        UserListDO<List<UserVO>> userListDO = userService.selectTotalUser(userListBO);
        DataGridDataSource<UserVO> dataGridDataSource = new DataGridDataSource<>();
        dataGridDataSource.setRows(userListDO.getData());
        dataGridDataSource.setTotal(userListDO.getTotal());
        return dataGridDataSource;
    }

    public boolean updateUser(UpdateUserBO updateUserBO) {
        UserDO userDO = userService.selectUserByUserId(updateUserBO.getUserId());
        if (Objects.isNull(userDO)) {
            throw new BizException(ResultCode.USER_NOT_FIND);
        }
        if (Strings.isBlank(userDO.getStatus())) {
            throw new BizException(ResultCode.USER_STATUS_NOT_FIND);
        }
//        if (UserStatusEnum.FREEZE.name().equals(userDO.getStatus())) {
//            throw new BizException(ResultCode.USER_ALREADY_FREEZE);
//        }
//        if (UserStatusEnum.FORBIDDEN.name().equals(userDO.getStatus())) {
//            throw new BizException(ResultCode.USER_ALREADY_FORBIDDEN);
//        }
        int i = userService.updateUser(updateUserBO);
        if (i != 1) {
            throw new BizException(ResultCode.USER_UPDATE_FAIL);
        }
        return true;
    }

    public boolean deleteUserByUserId(String userId) {
        if (Strings.isBlank(userId)) {
            throw new IllegalArgumentException("userId is null");
        }
        UserDO userDO = userService.selectUserByUserId(userId);
        if (Objects.isNull(userDO)) {
            throw new BizException(ResultCode.USER_NOT_FIND);
        }
        if (Strings.isBlank(userDO.getStatus())) {
            throw new BizException(ResultCode.USER_STATUS_NOT_FIND);
        }
        int i = userService.deleteUserByUserId(userId);
        if (i != 1) {
            throw new BizException(ResultCode.USER_DELETE_FAIL);
        }
        return true;
    }

    public boolean enableUser(String userId) {
        if (Strings.isBlank(userId)) {
            throw new IllegalArgumentException("userId is null");
        }
        UserDO userDO = userService.selectUserByUserId(userId);
        if (Objects.isNull(userDO)) {
            throw new BizException(ResultCode.USER_NOT_FIND);
        }
        if (Strings.isBlank(userDO.getStatus())) {
            throw new BizException(ResultCode.USER_STATUS_NOT_FIND);
        }
        if (UserStatusEnum.ACTIVE.name().equals(userDO.getStatus())) {
            throw new BizException(ResultCode.USER_STATUS_FAIL);
        }
        int i = userService.updateUserGeneral(userId, UserStatusEnum.ACTIVE.name(), userDO.getStatus());
        if (i != 1) {
            throw new BizException(ResultCode.USER_UPDATE_FAIL);
        }
        return true;
    }

    public boolean disableUser(String userId) {
        if (Strings.isBlank(userId)) {
            throw new IllegalArgumentException("userId is null");
        }
        UserDO userDO = userService.selectUserByUserId(userId);
        if (Objects.isNull(userDO)) {
            throw new BizException(ResultCode.USER_NOT_FIND);
        }
        if (Strings.isBlank(userDO.getStatus())) {
            throw new BizException(ResultCode.USER_STATUS_NOT_FIND);
        }
        if (UserStatusEnum.FORBIDDEN.name().equals(userDO.getStatus())) {
            throw new BizException(ResultCode.USER_STATUS_FAIL);
        }
        int i = userService.updateUserGeneral(userId, UserStatusEnum.FORBIDDEN.name(), userDO.getStatus());
        if (i != 1) {
            throw new BizException(ResultCode.USER_UPDATE_FAIL);
        }
        return true;
    }
}
