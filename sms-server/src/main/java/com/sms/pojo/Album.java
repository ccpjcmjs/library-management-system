package com.sms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@TableName("t_album")
@Accessors(chain = true)
public class Album {
    @TableId(value = "aid",type = IdType.AUTO)
    private int aid;
    @TableField("title")
    private String title;
    @TableField("author")
    private String author;
    @TableField("publisher")
    private String publisher;
    @TableField("publishtime")
    private String publishtime;
    @TableField("num")
    private int num;
    @TableField("descri")
    private String descri;
    @TableField("time")
    private String time;

    @TableField(exist = false)
    private List<Subalbum> subalbums;

    public Album(String title, String author, String publisher, String publishtime, int num, String descri, String time) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishtime = publishtime;
        this.num = num;
        this.descri = descri;
        this.time = time;
    }
}
