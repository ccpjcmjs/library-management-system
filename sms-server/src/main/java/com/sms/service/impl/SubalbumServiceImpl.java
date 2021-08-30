package com.sms.service.impl;

import com.sms.mapper.SubalbumMapper;
import com.sms.pojo.Subalbum;
import com.sms.service.SubalbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubalbumServiceImpl implements SubalbumService {

    @Autowired
    private SubalbumMapper subalbumMapper;

    public void setSubalbumMapper(SubalbumMapper subalbumMapper) {
        this.subalbumMapper = subalbumMapper;
    }

    @Override
    public int insert(Subalbum subalbum) {
        return subalbumMapper.insert(subalbum);
    }

    @Override
    public Subalbum selectById(int sid) {
        return subalbumMapper.selectById(sid);
    }

    @Override
    public Subalbum selectByNumber(String number) {
        return subalbumMapper.selectByNumber(number);
    }

    @Override
    public int updateByPrimaryKey(Subalbum subalbum) {
        return subalbumMapper.updateById(subalbum);
    }
}
