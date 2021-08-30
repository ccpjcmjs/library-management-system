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
@TableName("t_subalbum")
@Accessors(chain = true)
public class Subalbum {
    @TableId(value = "sid",type = IdType.AUTO)
    private int sid;
    @TableField("aid")
    private int aid;
    @TableField("number")
    private String number;
    @TableField("condi")
    private int condi;
    @TableField("time")
    private String time;

    @TableField(exist = false)
    private Album album;

    public Subalbum(int aid, String number, int condi, String time) {
        this.aid = aid;
        this.number = number;
        this.condi = condi;
        this.time = time;
    }
}
