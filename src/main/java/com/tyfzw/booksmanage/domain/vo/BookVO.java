package com.tyfzw.booksmanage.domain.vo;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 9:29
 */
@Data
public class BookVO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 书本id
     */
    private String bookId;

    /**
     * 书本名
     */
    private String name;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更行时间
     */
    private String updateTime;

    /**
     * 发行方
     */
    private String publisher;

    /**
     * 作者
     */
    private String writer;

    /**
     * 发行时间
     */
    private String publishTime;

    /**
     * 国际标准书号
     */
    private String isbn;

    /**
     * 位置
     */
    private String location;

    /**
     * 状态
     */
    private String status;

    /**
     * 借阅标记（yes表示已借阅，no表示未借阅）
     */
    private String borrowFlag;

    private String remarks;

}
