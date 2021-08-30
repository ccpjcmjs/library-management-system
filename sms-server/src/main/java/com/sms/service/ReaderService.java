package com.sms.service;

import com.sms.pojo.Reader;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ReaderService {

    Reader selectWholeByAccount(@Param("account") String account);

    List<Reader> selectByAccount(@Param("account") String account);

    int insert(Reader reader);

    int delete(@Param("rid")int rid);

    int update(Reader reader);

}
