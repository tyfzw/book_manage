package com.tyfzw.booksmanage.domain.bo;

import lombok.Data;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-17
 * Time: 13:29
 */
@Data
public class BookBO {

    /**
     * 当前页数
     */
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 10;



    public void setPageNum(String pageNum) {
        if (pageNum == null) {
            this.pageNum = 1;
        } else {
            String[] str = pageNum.split(" ");
            int num;
            if (str[str.length - 1].equals("-")) {
                num = Integer.parseInt(str[0]) - 1;
            } else if (str[str.length - 1].equals("a")) {
                num = Integer.parseInt(str[0]) + 1;
                int MaxNum = Integer.parseInt(str[str.length - 2]);
                num = Math.min(num, MaxNum);
            } else {
                num = Integer.parseInt(str[0]);
            }
            this.pageNum = Math.max(num, 1);
        }

    }
}
