package com.tyfzw.booksmanage.application.service;

import cn.hutool.core.util.IdUtil;
import com.tyfzw.booksmanage.domain.bo.LogBO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.dao.LogDAO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.LogDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-22
 * Time: 9:11
 */
@Service
public class LogService {

    @Autowired
    private LogDAO logDAO;


    public int insertLog(LogBO logBO) {
        if (Objects.isNull(logBO)) {
            throw new IllegalArgumentException("logBO is null");
        }
        LogDO logDO = new LogDO();
        BeanUtils.copyProperties(logBO, logDO);
        logDO.setSystemId(Long.toString(IdUtil.getSnowflake().nextId()));
        logDO.setCreateTime(new Date());
        logDO.setUpdateTime(new Date());
        return logDAO.insert(logDO);
    }

}
