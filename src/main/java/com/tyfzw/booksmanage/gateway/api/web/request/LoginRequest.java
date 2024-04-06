package com.tyfzw.booksmanage.gateway.api.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2022-07-05
 * Time: 13:23
 */
@Data
public class LoginRequest {

    /**
     * 用户id
     */
    @NotBlank(message = "email is null")
    private String email;


    /**
     * 密码
     */
    @NotBlank(message = "password is null")
    private String password;
}
