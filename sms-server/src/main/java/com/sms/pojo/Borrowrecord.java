package com.sms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_borrowrecord")
public class Borrowrecord {
    @TableId(value = "bid",type = IdType.AUTO)
    private int bid;
    @TableField("rid")
    private int rid;
    @TableField("raccount")
    private String raccount;
    @TableField("aid")
    private int aid;
    @TableField("sid")
    private int sid;
    @TableField("time")
    private String time;
    @TableField("backtime")
    private String backtime;
    @TableField("inttime")
    private String inttime;

    @TableField(exist = false)
    private Reader reader;

    @TableField(exist = false)
    private Album album;

    @TableField(exist = false)
    private Subalbum subalbum;

    public Borrowrecord(int rid, String raccount, int aid, int sid, String time, String backtime, String inttime) {
        this.rid = rid;
        this.raccount = raccount;
        this.aid = aid;
        this.sid = sid;
        this.time = time;
        this.backtime = backtime;
        this.inttime = inttime;
    }
}
