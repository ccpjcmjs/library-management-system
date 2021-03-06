package com.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sms.pojo.Reader;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("readerMapper")
public interface ReaderMapper extends BaseMapper<Reader> {

    @Select("select * from t_reader where rid=#{rid}")
    Reader selectById(@Param("rid") int rid);

    @Select("select * from t_reader where account=#{account}")
    Reader selectWholeByAccount(@Param("account") String account);

    @Select("select * from t_reader where account like concat('%',concat(#{account},'%'))")
    List<Reader> selectByAccount(@Param("account") String account);
}
