package com.tyfzw.booksmanage.infrastructure.gateway.database.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-22
 * Time: 9:13
 */
@Data
@Table(name = "log")
public class LogDO {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 系统id
     */
    private String systemId;

    /**
     * 系统名
     */
    private String systemName;

    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * 接口输入
     */
    private String interfaceInput;

    /**
     * 接口输出
     */
    private String interfaceOutput;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 运行时间
     */
    private Long systemCallTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remarks;
}
