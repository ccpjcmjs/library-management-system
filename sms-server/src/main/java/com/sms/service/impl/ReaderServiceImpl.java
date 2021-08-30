package com.sms.service.impl;

import com.sms.mapper.ReaderMapper;
import com.sms.pojo.Reader;
import com.sms.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderMapper readerMapper;

    public void setReaderMapper(ReaderMapper readerMapper) {
        this.readerMapper = readerMapper;
    }

    @Override
    public Reader selectWholeByAccount(String account) {
        return readerMapper.selectWholeByAccount(account);
    }

    @Override
    public List<Reader> selectByAccount(String account) {
        return readerMapper.selectByAccount(account);
    }

    @Override
    public int insert(Reader reader) {
        return readerMapper.insert(reader);
    }

    @Override
    public int delete(int rid) {
        return readerMapper.deleteById(rid);
    }

    @Override
    public int update(Reader reader) {
        return readerMapper.updateById(reader);
    }


}
