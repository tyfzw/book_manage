package com.tyfzw.booksmanage.application.job;

import com.tyfzw.booksmanage.BooksManageApplication;
import com.tyfzw.booksmanage.BooksManageApplicationTests;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-22
 * Time: 11:13
 */
class ScheduledJobTest extends BooksManageApplicationTests {

    @Autowired
    private ScheduledJob scheduledJob;

    @Test
    void updateBorrow() {
        scheduledJob.updateBorrow();

    }
}