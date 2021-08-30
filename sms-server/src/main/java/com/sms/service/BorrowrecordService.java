package com.sms.service;

import com.sms.pojo.Borrowrecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BorrowrecordService {

    List<Borrowrecord> selectAllInfoByRaccount(@Param("raccount")String raccount);

    int insert(Borrowrecord borrowrecord);

    int delete(Borrowrecord borrowrecord);

    Borrowrecord selectByPrimaryKey(int bid);

}
