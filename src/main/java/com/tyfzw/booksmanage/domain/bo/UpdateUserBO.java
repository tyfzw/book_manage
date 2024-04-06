package com.tyfzw.booksmanage.domain.bo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-24
 * Time: 11:19
 */
@Data
public class UpdateUserBO {

    private String userId;


    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户邮箱
     */
    private String email;

}
