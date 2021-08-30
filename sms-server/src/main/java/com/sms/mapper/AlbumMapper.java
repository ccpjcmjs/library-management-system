package com.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sms.pojo.Album;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("albumMapper")
public interface AlbumMapper extends BaseMapper<Album> {
    //crud已经完成
    @Select("select * from t_album where aid=#{aid}")
    @Results({
            @Result(id=true,column="aid",property = "aid"),
            @Result(column = "title",property = "title"),
            @Result(column = "author",property = "author"),
            @Result(column = "publisher",property = "publisher"),
            @Result(column = "publishtime",property = "publishtime"),
            @Result(column = "num",property = "num"),
            @Result(column = "descri",property = "descri"),
            @Result(column = "time",property = "time"),
            @Result(column="aid",property = "subalbums",
                    many=@Many(select="com.sms.mapper.SubalbumMapper.selectByAid")
            )
    })
    Album selectById(@Param("aid") int aid);

    @Select("<script> "+
            "select * from t_album"+
            " <where> "+
            " title like concat('%',concat(#{title},'%'))"+
            " </where> "+
            " </script> "
    )
    @Results({
            @Result(id=true,column="aid",property = "aid"),
            @Result(column = "title",property = "title"),
            @Result(column = "author",property = "author"),
            @Result(column = "publisher",property = "publisher"),
            @Result(column = "publishtime",property = "publishtime"),
            @Result(column = "num",property = "num"),
            @Result(column = "descri",property = "descri"),
            @Result(column = "time",property = "time"),
            @Result(column="aid",property = "subalbums",
                    many=@Many(select="com.sms.mapper.SubalbumMapper.selectByAid")
            )
    })
    List<Album> selectByTitle(@Param("title")String title);
}
