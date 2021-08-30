package com.sms.service.impl;

import com.sms.mapper.BorrowrecordMapper;
import com.sms.pojo.Borrowrecord;
import com.sms.service.BorrowrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowrecordServiceImpl implements BorrowrecordService {

    @Autowired
    private BorrowrecordMapper borrowrecordMapper;

    public void setBorrowrecordMapper(BorrowrecordMapper borrowrecordMapper) {
        this.borrowrecordMapper = borrowrecordMapper;
    }

    @Override
    public List<Borrowrecord> selectAllInfoByRaccount(String raccount) {
        return borrowrecordMapper.selectAllInfoByRaccount(raccount);
    }

    @Override
    public int insert(Borrowrecord borrowrecord) {
        return borrowrecordMapper.insert(borrowrecord);
    }

    @Override
    public int delete(Borrowrecord borrowrecord) {
        return borrowrecordMapper.deleteById(borrowrecord.getBid());
    }

    @Override
    public Borrowrecord selectByPrimaryKey(int bid) {
        return borrowrecordMapper.selectById(bid);
    }
}
