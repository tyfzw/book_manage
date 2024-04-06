package com.tyfzw.booksmanage.domain.bo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 10:53
 */
@Data
public class OrderBO {

    /**
     * 书本id
     */
    private String bookId;
}
