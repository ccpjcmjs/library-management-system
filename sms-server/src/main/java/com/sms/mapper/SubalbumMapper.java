package com.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sms.pojo.Subalbum;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("subalbumMapper")
public interface SubalbumMapper extends BaseMapper<Subalbum> {

    @Select("select * from t_subalbum where sid=#{sid}")
    Subalbum selectById(@Param("sid") int sid);

    @Select("select * from t_subalbum where number=#{number}")
    Subalbum selectByNumber(@Param("number") String number);

    @Select("select * from t_subalbum where aid=#{aid}")
    List<Subalbum> selectByAid(@Param("aid")int aid);
}
