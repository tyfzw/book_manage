package com.tyfzw.booksmanage.application.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tyfzw.booksmanage.domain.bo.UpdateUserBO;
import com.tyfzw.booksmanage.domain.bo.UserListBO;
import com.tyfzw.booksmanage.domain.vo.BookVO;
import com.tyfzw.booksmanage.domain.vo.UserVO;
import com.tyfzw.booksmanage.infrastructure.common.enums.UserStatusEnum;
import com.tyfzw.booksmanage.infrastructure.common.utils.CalendarUtils;
import com.tyfzw.booksmanage.infrastructure.gateway.database.dao.UserDAO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BookDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BookListDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.UserDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.UserListDO;
import org.apache.catalina.User;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.dc.pr.PRError;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-13
 * Time: 11:22
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;




    public UserDO selectUserByEmailAndPassword(String email, String password) {
        if (Strings.isBlank(email)) {
            throw new IllegalArgumentException("email is null");
        }
        if (Strings.isBlank(password)) {
            throw new IllegalArgumentException("password is null");
        }
        Example example = new Example(UserDO.class);
        example.createCriteria()
                .andEqualTo("email", email)
                .andEqualTo("password", password);

        return userDAO.selectOneByExample(example);
    }

    public UserDO selectUserByUserId(String userId) {
        if (Strings.isBlank(userId)) {
            throw new IllegalArgumentException("userId is null");
        }
        Example example = new Example(UserDO.class);
        example.createCriteria().andEqualTo("userId", userId);
        return userDAO.selectOneByExample(example);
    }

    public UserListDO<List<UserVO>> selectTotalUser(UserListBO userListBO) {
        if (Objects.isNull(userListBO)) {
            throw new IllegalArgumentException("userListBO is null");
        }
        int pageNum = userListBO.getPage();
        int pageSize = userListBO.getRows();
        PageHelper.startPage(pageNum, pageSize);
        List<UserDO> userDOS = userDAO.selectTotalUser(userListBO.getNickname(), userListBO.getEmail());
        PageInfo<UserDO> pageInfo = new PageInfo<>(userDOS);
        long total = pageInfo.getTotal();
        ArrayList<UserVO> userVOS = new ArrayList<>();
        for (UserDO userDO : pageInfo.getList()) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(userDO, userVO);
            userVO.setCreateTime(CalendarUtils.dateToString(userDO.getCreateTime()));
            userVO.setUpdateTime(CalendarUtils.dateToString(userDO.getUpdateTime()));
            userVOS.add(userVO);
        }
        UserListDO<List<UserVO>> userListDO = new UserListDO<>();
        userListDO.setData(userVOS);
        userListDO.setTotal(total);
        return userListDO;
    }

    public UserDO selectUserByEmail(String email) {
        if (Strings.isBlank(email)) {
            throw new IllegalArgumentException("email is null");
        }
        Example example = new Example(UserDO.class);
        example.createCriteria().andEqualTo("email", email);
        return userDAO.selectOneByExample(example);
    }

    public int insertUser(String nickname, String password, String email) {
        if (Strings.isBlank(nickname)) {
            throw new IllegalArgumentException("nickname is null");
        }
        if (Strings.isBlank(password)) {
            throw new IllegalArgumentException("password is null");
        }
        if (Strings.isBlank(email)) {
            throw new IllegalArgumentException("email is null");
        }
        UserDO userDO = new UserDO();
        userDO.setUserId(Long.toString(IdUtil.getSnowflake().nextId()));
        userDO.setNickname(nickname);
        userDO.setEmail(email);
        userDO.setPassword(password);
        userDO.setCreateTime(new Date());
        userDO.setUpdateTime(new Date());
        userDO.setStatus(UserStatusEnum.ACTIVE.name());
        userDO.setIdentity("USER");
        return userDAO.insert(userDO);
    }

    public int updateUser(UpdateUserBO updateUserBO) {
        if (Objects.isNull(updateUserBO)) {
            throw new IllegalArgumentException("updateUserBO is null");
        }
        if (Strings.isBlank(updateUserBO.getUserId())) {
            throw new IllegalArgumentException("userId is null");
        }
        Example example = new Example(UserDO.class);
        example.createCriteria().andEqualTo("userId", updateUserBO.getUserId());
        UserDO userDO = new UserDO();
        userDO.setNickname(updateUserBO.getNickname());
        userDO.setEmail(updateUserBO.getEmail());
        return userDAO.updateByExampleSelective(userDO, example);
    }

    public int deleteUserByUserId(String userId) {
        if (Strings.isBlank(userId)) {
            throw new IllegalArgumentException("userId is null");
        }
        Example example = new Example(UserDO.class);
        example.createCriteria().andEqualTo("userId", userId);
        UserDO userDO = new UserDO();
        userDO.setStatus(UserStatusEnum.DELETE.name());
        userDO.setUpdateTime(new Date());
        return userDAO.updateByExampleSelective(userDO, example);
    }

    public int updateUserGeneral(String userId, String newStatus, String oldStatus) {
        if (Strings.isBlank(userId)) {
            throw new IllegalArgumentException("userId is null");
        }
        if (Strings.isBlank(oldStatus)) {
            throw new IllegalArgumentException("oldStatus is null");
        }
        if (Strings.isBlank(newStatus)) {
            throw new IllegalArgumentException("newStatus is null");
        }
        Example example = new Example(UserDO.class);
        example.createCriteria()
                .andEqualTo("userId", userId)
                .andEqualTo("status", oldStatus);
        UserDO userDO = new UserDO();
        userDO.setStatus(newStatus);
        userDO.setUpdateTime(new Date());
        return userDAO.updateByExampleSelective(userDO, example);
    }
}
