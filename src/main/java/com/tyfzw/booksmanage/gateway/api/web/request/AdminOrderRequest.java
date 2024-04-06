package com.tyfzw.booksmanage.gateway.api.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-17
 * Time: 8:35
 */
@Data
public class AdminOrderRequest {

    /**
     * 借阅id
     */
    @NotBlank(message = "borrowId is null")
    private String borrowId;
}
