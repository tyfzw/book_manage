package com.tyfzw.booksmanage.application.service;

import cn.hutool.core.date.chinese.SolarTerms;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tyfzw.booksmanage.domain.bo.AddBO;
import com.tyfzw.booksmanage.domain.bo.BookListBO;
import com.tyfzw.booksmanage.domain.bo.PageBO;
import com.tyfzw.booksmanage.domain.vo.BookVO;
import com.tyfzw.booksmanage.domain.vo.BorrowVO;
import com.tyfzw.booksmanage.gateway.api.web.request.UpdateRequest;
import com.tyfzw.booksmanage.infrastructure.common.enums.BookStatusEnum;
import com.tyfzw.booksmanage.infrastructure.common.enums.BorrowStatusEnum;
import com.tyfzw.booksmanage.infrastructure.common.utils.CalendarUtils;
import com.tyfzw.booksmanage.infrastructure.gateway.database.dao.BooksDAO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.dao.BorrowDAO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BookDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BookListDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BorrowDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.UserDO;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.PAData;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 9:32
 */
@Service
public class BookService {

    @Autowired
    private BooksDAO booksDAO;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    public  PageBO<List<BookVO>> selectTotalBooks(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BookDO> bookDOS = booksDAO.selectTotalBooks();
        PageInfo<BookDO> pageInfo = new PageInfo<>(bookDOS);
        long total = pageInfo.getTotal();
        long pageCount = pageInfo.getPages();
        ArrayList<BookVO> bookVOS = new ArrayList<>();
        for (BookDO bookDO : pageInfo.getList()) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(bookDO, bookVO);
            bookVO.setCreateTime(CalendarUtils.dateToString(bookDO.getCreateTime()));
            bookVO.setUpdateTime(CalendarUtils.dateToString(bookDO.getUpdateTime()));
            bookVO.setPublishTime(CalendarUtils.dataToStringDay(bookDO.getPublishTime()));
            bookVOS.add(bookVO);
        }
        PageBO<List<BookVO>> pageBO = new PageBO<>();
        pageBO.setPageNum(pageInfo.getPageNum());
        pageBO.setCount(total);
        pageBO.setData(bookVOS);
        pageBO.setPageCount(pageCount);
        return pageBO;
    }



    public PageBO<List<BookVO>> selectAdminTotalBooks(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(BookDO.class);
        example.createCriteria().andEqualTo("status", BookStatusEnum.NORMAL.name());
        List<BookDO> bookDOS = booksDAO.selectByExample(example);
        PageInfo<BookDO> pageInfo = new PageInfo<>(bookDOS);
        long total = pageInfo.getTotal();
        long pageCount = pageInfo.getPages();
        ArrayList<BookVO> bookVOS = new ArrayList<>();
        for (BookDO bookDO : pageInfo.getList()) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(bookDO, bookVO);
            bookVO.setCreateTime(CalendarUtils.dateToString(bookDO.getCreateTime()));
            bookVO.setUpdateTime(CalendarUtils.dateToString(bookDO.getUpdateTime()));
            bookVO.setPublishTime(CalendarUtils.dataToStringDay(bookDO.getPublishTime()));
            bookVOS.add(bookVO);
        }
        PageBO<List<BookVO>> pageBO = new PageBO<>();
        pageBO.setPageNum(pageInfo.getPageNum());
        pageBO.setCount(total);
        pageBO.setData(bookVOS);
        pageBO.setPageCount(pageCount);
        return pageBO;
    }

    public int order(String bookId) {
        if (Strings.isBlank(bookId)) {
            throw new IllegalArgumentException("bookId is null");
        }
        Example example = new Example(BookDO.class);
        example.createCriteria().andEqualTo("bookId", bookId);
        BookDO bookDO = new BookDO();
        bookDO.setBorrowFlag("YES");
        bookDO.setUpdateTime(new Date());
        return booksDAO.updateByExampleSelective(bookDO, example);
    }

    public PageBO<List<BookVO>> selectBooksLikeName(Integer pageNum, Integer pageSize,String name) {
        PageHelper.startPage(pageNum, pageSize);
        List<BookDO> bookDOS = booksDAO.selectBooksLikeName(name);
        PageInfo<BookDO> pageInfo = new PageInfo<>(bookDOS);
        long total = pageInfo.getTotal();
        long pageCount = pageInfo.getPages();
        ArrayList<BookVO> bookVOS = new ArrayList<>();
        for (BookDO bookDO : pageInfo.getList()) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(bookDO, bookVO);
            bookVO.setCreateTime(CalendarUtils.dateToString(bookDO.getCreateTime()));
            bookVO.setUpdateTime(CalendarUtils.dateToString(bookDO.getUpdateTime()));
            bookVO.setPublishTime(CalendarUtils.dataToStringDay(bookDO.getPublishTime()));
            bookVOS.add(bookVO);
        }
        PageBO<List<BookVO>> pageBO = new PageBO<>();
        pageBO.setPageNum(pageInfo.getPageNum());
        pageBO.setCount(total);
        pageBO.setData(bookVOS);
        pageBO.setPageCount(pageCount);
        return pageBO;
    }


    public BookDO selectBookById(String bookId) {
        if (Strings.isBlank(bookId)) {
            throw new IllegalArgumentException("bookId is null");
        }
        Example example = new Example(BookDO.class);
        example.createCriteria().andEqualTo("bookId", bookId).andNotEqualTo("status", BookStatusEnum.DELETE.name());
        return booksDAO.selectOneByExample(example);

    }

    public int updateFlagByBookId(String bookId, String borrowFlag) {
        if (Strings.isBlank(bookId)) {
            throw new IllegalArgumentException("bookId is null");
        }
        if (Strings.isBlank(borrowFlag)) {
            throw new IllegalArgumentException("borrowFlag is null");
        }
        Example example = new Example(BookDO.class);
        example.createCriteria().andEqualTo("bookId", bookId).andEqualTo("borrowFlag", borrowFlag);
        BookDO bookDO = new BookDO();
        bookDO.setUpdateTime(new Date());
        bookDO.setBorrowFlag("NO");
        return booksDAO.updateByExampleSelective(bookDO, example);
    }



    public void insertBook(AddBO addBO) {
        if (Objects.isNull(addBO)) {
            throw new IllegalArgumentException("addBO is null");
        }
        ArrayList<BookDO> bookDOS = new ArrayList<>();
        for (int i = 0; i < addBO.getNumber(); i++) {
            BookDO bookDO = new BookDO();
            bookDO.setBookId(Long.toString(IdUtil.getSnowflake().nextId()));
            bookDO.setName(addBO.getName());
            bookDO.setCreateTime(new Date());
            bookDO.setUpdateTime(new Date());
            bookDO.setPublisher(addBO.getPublisher());
            bookDO.setWriter(addBO.getWriter());
            bookDO.setPublishTime(addBO.getPublishTime());
            bookDO.setIsbn(addBO.getIsbn());
            bookDO.setLocation(addBO.getLocation());
            bookDO.setStatus(BookStatusEnum.NORMAL.name());
            bookDO.setBorrowFlag("NO");
            bookDO.setRemarks(addBO.getRemarks());
            bookDOS.add(bookDO);
        }
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        bookDOS.stream().forEach(subOrdersDO -> booksDAO.insert(subOrdersDO));
        sqlSession.commit();
        sqlSession.clearCache();
    }

    public int deleteBookById(String bookId) {
        if (Strings.isBlank(bookId)) {
            throw new IllegalArgumentException("bookId is null");
        }
        Example example = new Example(BookDO.class);
        example.createCriteria().andEqualTo("bookId", bookId);
        BookDO bookDO = new BookDO();
        bookDO.setStatus(BookStatusEnum.DELETE.name());
        return booksDAO.updateByExampleSelective(bookDO, example);
    }


    public BookListDO<List<BookVO>> selectBookByCriteria(BookListBO bookListBO) {
        if (Objects.isNull(bookListBO)) {
            throw new IllegalArgumentException("bookListBO is null");
        }
        int pageNum = bookListBO.getPage();
        int pageSize = bookListBO.getRows();
        PageHelper.startPage(pageNum, pageSize);
        List<BookDO> bookDOS = booksDAO.selectBookByCriteria(bookListBO.getName(), bookListBO.getIsbn(), bookListBO.getWriter());
        PageInfo<BookDO> pageInfo = new PageInfo<>(bookDOS);
        long total = pageInfo.getTotal();
        ArrayList<BookVO> bookVOS = new ArrayList<>();
        for (BookDO bookDO : pageInfo.getList()) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(bookDO, bookVO);
            bookVO.setCreateTime(CalendarUtils.dateToString(bookDO.getCreateTime()));
            bookVO.setUpdateTime(CalendarUtils.dateToString(bookDO.getUpdateTime()));
            bookVO.setPublishTime(CalendarUtils.dataToStringDay(bookDO.getPublishTime()));
            bookVOS.add(bookVO);
        }
        BookListDO<List<BookVO>> bookListDO = new BookListDO<>();
        bookListDO.setData(bookVOS);
        bookListDO.setTotal(total);
        return bookListDO;
    }


    public BookListDO<List<BookVO>> selectUserBookByCriteria(BookListBO bookListBO) {
        if (Objects.isNull(bookListBO)) {
            throw new IllegalArgumentException("bookListBO is null");
        }
        int pageNum = bookListBO.getPage();
        int pageSize = bookListBO.getRows();
        PageHelper.startPage(pageNum, pageSize);
        List<BookDO> bookDOS = booksDAO.selectUserBookByCriteria(bookListBO.getName(), bookListBO.getIsbn(), bookListBO.getWriter());
        PageInfo<BookDO> pageInfo = new PageInfo<>(bookDOS);
        long total = pageInfo.getTotal();
        ArrayList<BookVO> bookVOS = new ArrayList<>();
        for (BookDO bookDO : pageInfo.getList()) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(bookDO, bookVO);
            bookVO.setCreateTime(CalendarUtils.dateToString(bookDO.getCreateTime()));
            bookVO.setUpdateTime(CalendarUtils.dateToString(bookDO.getUpdateTime()));
            bookVO.setPublishTime(CalendarUtils.dataToStringDay(bookDO.getPublishTime()));
            bookVOS.add(bookVO);
        }
        BookListDO<List<BookVO>> bookListDO = new BookListDO<>();
        bookListDO.setData(bookVOS);
        bookListDO.setTotal(total);
        return bookListDO;
    }

    public int updateBook(UpdateRequest updateRequest) {
        if (Objects.isNull(updateRequest)) {
            throw new IllegalArgumentException("updateRequest is null");
        }
        if (Strings.isBlank(updateRequest.getBookId())) {
            throw new IllegalArgumentException("bookId is null");
        }
        Example example = new Example(BookDO.class);
        example.createCriteria().andEqualTo("bookId", updateRequest.getBookId());
        BookDO bookDO = new BookDO();
        bookDO.setIsbn(updateRequest.getIsbn());
        bookDO.setWriter(updateRequest.getWriter());
        bookDO.setName(updateRequest.getName());
        bookDO.setPublisher(updateRequest.getPublisher());
        bookDO.setPublishTime(CalendarUtils.stringToDateDay(updateRequest.getPublishTime()));
        bookDO.setLocation(updateRequest.getLocation());
        bookDO.setRemarks(updateRequest.getRemarks());
        return booksDAO.updateByExampleSelective(bookDO, example);
    }



}
