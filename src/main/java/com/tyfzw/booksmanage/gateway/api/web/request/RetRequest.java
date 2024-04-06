package com.tyfzw.booksmanage.gateway.api.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 19:26
 */
@Data
public class RetRequest {

    /**
     * 借阅id
     */
    @NotBlank(message = "borrowId is null")
    private String borrowId;




}
