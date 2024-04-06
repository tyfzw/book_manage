package com.tyfzw.booksmanage.domain.bo;

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
public class AdminOrderBO {

    /**
     * 借阅id
     */
    private String borrowId;
}
