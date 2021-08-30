package com.sms.service;

import com.sms.pojo.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumService {

    List<Album> selectByTitle(@Param("title")String title);

    int insert(Album album);

    Album selectById(@Param("aid") int aid);

    int updateByPrimaryKey(Album album);

    int delete(@Param("aid") int aid);

    int update(Album album);

}
