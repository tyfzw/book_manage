package com.tyfzw.booksmanage.infrastructure.common.enums;

import lombok.Getter;


@Getter
public enum ResultCode {

    SUCCESS("000000", "操作成功"),
    PARAM_CHECK_FAIL("000001", "基本参数校验失败"),
    USER_NOT_FIND("000002", "用户未找到"),
    USERID_PASSWORD_ERROR("000003", "账号或密码错误"),
    USER_ALREADY_FREEZE("000004", "此用户已冻结，请等待解冻"),
    SYSTEM_ERROR("999999", "系统异常"),
    USER_REGISTER_FAIL("000005", "用户注册失败"),
    USER_ALREADY_EXISTS("000006", "此用户名已存在"),
    USER_NOT_LOGIN("000008", "用户没有登录"),
    USER_ALREADY_FORBIDDEN("000009", "用户已被封禁，无法操作"),
    BOOKS_NOT_FIND("0000010", "此书本未找到"),
    BOOKS_BORROW_FAIL("0000011", "图书借阅失败"),
    BOOK_ALREADY_DELETE("0000012", "此书已下架"),
    BOOK_ALREADY_BORROW("0000013", "此书已被借阅"),
    BORROW_INFO_NOT_FIND("0000014", "借阅信息未找到"),
    BOOK_RET_FAIL("0000015", "书本归还失败"),
    BOOK_CREATE_FAIL("000016", "书本创建失败"),
    ORDER_OUT_TIME("0000017", "预约已过期"),
    BORROW_DELETE_FAIL("0000018", "借阅删除失败"),
    BOOK_ALREADY_RET("0000019", "已归还"),
    BOOK_DELETE_FAIL("0000020", "图书删除失败"),
    CLEAR_BORROW_FAIL("0000021", "清除借阅记录失败"),
    OUT_TIME_NOT_FIND("0000022", "未找到过期时间"),
    STATUS_UPDATE_FAIL("00000023", "状态更新失败"),
    LOG_CREATE_FAIL("0000024", "日志创建失败"),
    BOOK_NO_BORROW("0000026", "此书不外借"),
    BOOK_UPDATE_FAIL("0000027", "书本更新失败"),
    USER_STATUS_NOT_FIND("000028", "用户状态未找到"),
    USER_UPDATE_FAIL("000029", "用户更新失败"),
    USER_DELETE_FAIL("0000030", "用户删除失败"),
    USER_STATUS_FAIL("0000031", "用户状态错误");


    private String code;


    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
