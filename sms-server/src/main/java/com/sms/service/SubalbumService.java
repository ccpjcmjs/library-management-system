package com.sms.service;

import com.sms.pojo.Borrowrecord;
import com.sms.pojo.Subalbum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubalbumService {

    int insert(Subalbum subalbum);

    Subalbum selectById(@Param("sid") int sid);

    Subalbum selectByNumber(@Param("number") String number);

    int updateByPrimaryKey(Subalbum subalbum);
}
