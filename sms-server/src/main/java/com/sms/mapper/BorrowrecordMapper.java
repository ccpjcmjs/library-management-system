package com.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sms.pojo.Borrowrecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("borrowrecordMapper")
public interface BorrowrecordMapper extends BaseMapper<Borrowrecord> {
    @Select("<script> "+
            "select * from t_borrowrecord"+
            " <where> "+
            " rid=#{rid}"+
            " </where> "+
            " </script> "
    )
    @Results({
            @Result(id=true,column="bid",property = "bid"),
            @Result(column = "rid",property = "rid"),
            @Result(column = "raccount",property = "raccount"),
            @Result(column = "aid",property = "aid"),
            @Result(column = "sid",property = "sid"),
            @Result(column = "time",property = "time"),
            @Result(column = "inttime",property = "inttime"),
            @Result(column="aid",property = "album",
                    one=@One(select="com.sms.mapper.AlbumMapper.selectById")
            ),
            @Result(column="sid",property = "subalbum",
                    one=@One(select="com.sms.mapper.SubalbumMapper.selectById")
            )
    })
    List<Borrowrecord> selectAllInfoByrid(@Param("rid")int rid);

    @Select("<script> "+
            "select * from t_borrowrecord"+
            " <where> "+
            " raccount like concat('%',concat(#{raccount},'%'))"+
            " </where> "+
            " </script> "
    )
    @Results({
            @Result(id=true,column="bid",property = "bid"),
            @Result(column = "rid",property = "rid"),
            @Result(column = "raccount",property = "raccount"),
            @Result(column = "aid",property = "aid"),
            @Result(column = "sid",property = "sid"),
            @Result(column = "time",property = "time"),
            @Result(column = "inttime",property = "inttime"),
            @Result(column="rid",property = "reader",
                    one=@One(select="com.sms.mapper.ReaderMapper.selectById")
            ),
            @Result(column="aid",property = "album",
                    one=@One(select="com.sms.mapper.AlbumMapper.selectById")
            ),
            @Result(column="sid",property = "subalbum",
                    one=@One(select="com.sms.mapper.SubalbumMapper.selectById")
            )
    })
    List<Borrowrecord> selectAllInfoByRaccount(@Param("raccount")String raccount);
}
