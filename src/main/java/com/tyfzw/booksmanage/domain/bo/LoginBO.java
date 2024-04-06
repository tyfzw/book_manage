package com.tyfzw.booksmanage.domain.bo;

import lombok.Data;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-13
 * Time: 11:09
 */
@Data
public class LoginBO {

    /**
     * 用户id
     */
    private String email;

    /**
     * 密码
     */
    private String password;
}
