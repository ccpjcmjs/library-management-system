package com.sms.service.impl;

import com.sms.mapper.AlbumMapper;
import com.sms.pojo.Album;
import com.sms.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;

    public void setAlbumMapper(AlbumMapper albumMapper) {
        this.albumMapper = albumMapper;
    }

    @Override
    public List<Album> selectByTitle(String title) {
        return albumMapper.selectByTitle(title);
    }

    @Override
    public int insert(Album album) {
        return albumMapper.insert(album);
    }

    @Override
    public Album selectById(int aid) {
        return albumMapper.selectById(aid);
    }

    @Override
    public int updateByPrimaryKey(Album album) {
        return albumMapper.updateById(album);
    }

    @Override
    public int delete(int aid) {
        return albumMapper.deleteById(aid);
    }

    @Override
    public int update(Album album) {
        return albumMapper.updateById(album);
    }
}
