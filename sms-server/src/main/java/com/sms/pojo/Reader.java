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
@TableName("t_reader")
public class Reader {
    @TableId(value = "rid",type = IdType.AUTO)
    private int rid;
    @TableField("account")
    private String account;
    @TableField("password")
    private String password;
    @TableField("name")
    private String name;
    @TableField("sex")
    private String sex;
    @TableField("time")
    private String time;
    @TableField("condi")
    private int condi;

    public Reader(String account, String password, String name, String sex, String time, int condi) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.time = time;
        this.condi = condi;
    }
}
