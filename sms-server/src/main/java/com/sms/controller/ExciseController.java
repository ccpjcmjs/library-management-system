package com.sms.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sms.pojo.Album;
import com.sms.pojo.Borrowrecord;
import com.sms.pojo.Reader;
import com.sms.pojo.Subalbum;
import com.sms.service.*;
import com.sms.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/excise")
public class ExciseController {

    @Autowired(required = false)
    private ReaderService readerService;
    @Autowired(required = false)
    private AlbumService albumService;
    @Autowired(required = false)
    private SubalbumService subalbumService;
    @Autowired(required = false)
    private BorrowrecordService borrowrecordService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String,Object> login(@RequestParam("account") String account,
                                    @RequestParam("password") String password){
        Map<String, Object> map = new HashMap<>();
        Reader reader = readerService.selectWholeByAccount(account);
        if (reader!=null&&reader.getPassword().equals(password)){
            System.out.println("hahaha"+reader.getPassword().equals(password));
            map.put("result","yes");
            map.put("loginUser",reader);
            if (reader.getCondi()==0){
                map.put("condi",0);
            }else if(reader.getCondi()==1){
                map.put("condi",1);
            }else{
                map.put("condi",2);
            }
            return map;
        }
        map.put("result","no");
        return map;
    }

    @RequestMapping(value = "/getAllReaders",method = RequestMethod.POST)
    public Map<String,Object> getAllReaders(@RequestParam("account") String account,
                                            @RequestParam("currentPage") int currentPage){
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(currentPage,10);
        List<Reader> list = readerService.selectByAccount(account);
        PageInfo<Reader> pageInfo = new PageInfo<>(list);
        map.put("readers",list);
        map.put("pageInfo",pageInfo);
        return map;
    }

    @RequestMapping(value = "/getAllAlbums",method = RequestMethod.POST)
    public Map<String,Object> getAllAlbums(@RequestParam("title")String title,
                                           @RequestParam("currentPage")int currentPage){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(currentPage,10);
        List<Album> list = albumService.selectByTitle(title);
        PageInfo<Album> pageInfo=new PageInfo<>(list);
        map.put("pageInfo",pageInfo);
        map.put("albums",list);
        return map;
    }

    @RequestMapping(value = "/getAllBorrowRecords",method = RequestMethod.POST)
    public Map<String,Object> getAllBorrowRecords(@RequestParam("raccount")String raccount,
                                                  @RequestParam("currentPage")int currentPage){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(currentPage,10);
        List<Borrowrecord> list = borrowrecordService.selectAllInfoByRaccount(raccount);
        PageInfo<Borrowrecord> pageInfo = new PageInfo<>(list);
        map.put("pageInfo",pageInfo);
        map.put("borrowrecords",list);
        return map;
    }

    @RequestMapping(value = "/addReader",method = RequestMethod.POST)
    public Map<String,Object> addReader(@RequestParam("account")String account,
                                        @RequestParam("password")String password,
                                        @RequestParam("name")String name,
                                        @RequestParam("sex")String sex,
                                        @RequestParam("condi")int condi){
        Map<String,Object> map = new HashMap<>();
        if (readerService.selectWholeByAccount(account)!=null){
            map.put("status","no");
        }else {
            readerService.insert(new Reader(account,password,name,sex, DateTimeUtil.getDate(),condi));
            map.put("status","ok");
        }
        return map;
    }

    @RequestMapping(value = "/removeReader",method = RequestMethod.POST)
    public Map<String,Object> removeReader(@RequestParam("account")String account){
        Map<String,Object> map = new HashMap<>();
        int rows = readerService.delete(readerService.selectWholeByAccount(account).getRid());
        System.out.println(rows);
        if (rows == 0){
            map.put("status", "no");
        }else {
            map.put("status", "yes");
        }
        return map;
    }

    @RequestMapping(value = "/updateReader",method = RequestMethod.POST)
    public Map<String,Object> updateReader(@RequestParam("rid")int rid,
                                           @RequestParam("account")String account,
                                           @RequestParam("name")String name,
                                           @RequestParam("password")String password,
                                           @RequestParam("sex")String sex,
                                           @RequestParam("condi")int condi){
        Map<String,Object> map = new HashMap<>();
        Reader reader = new Reader();
        reader.setRid(rid);
        reader.setAccount(account);
        reader.setPassword(password);
        reader.setName(name);
        reader.setSex(sex);
        reader.setCondi(condi);
        int update = readerService.update(reader);
        if (update == 0){
            map.put("status","no");
        }else {
            map.put("status","ok");
        }
        return map;
    }

    @RequestMapping(value = "/addAlbum",method = RequestMethod.POST)
    public Map<String,Object> addAlbum(@RequestParam("title")String title,
                                       @RequestParam("author")String author,
                                       @RequestParam("publisher")String publisher,
                                       @RequestParam("publishtime")String publishtime,
                                       @RequestParam("descri")String descri){
        Map<String,Object> map = new HashMap<>();
        albumService.insert(new Album(title, author, publisher, publishtime, 0, descri, DateTimeUtil.getDate()));
        map.put("status", "ok");
        return map;
    }

    @RequestMapping(value = "/removeAlbum",method = RequestMethod.POST)
    public Map<String,Object> addAlbum(@RequestParam("aid")int aid){
        Map<String,Object> map = new HashMap<>();
        int rows = albumService.delete(aid);
        if (rows == 0){
            map.put("status", "no");
        }else {
            map.put("status", "yes");
        }
        return map;
    }

    @RequestMapping(value = "/updateAlbum",method = RequestMethod.POST)
    public Map<String,Object> updateAlbum(@RequestParam("aid")int aid,
                                           @RequestParam("title")String title,
                                           @RequestParam("author")String author,
                                           @RequestParam("publisher")String publisher,
                                           @RequestParam("publishtime")String publishtime,
                                           @RequestParam("descri")String descri){
        Map<String,Object> map = new HashMap<>();
        Album album = new Album();
        album.setAid(aid);
        album.setTitle(title);
        album.setAuthor(author);
        album.setPublisher(publisher);
        album.setPublishtime(publishtime);
        album.setDescri(descri);
        int update = albumService.update(album);
        if (update == 0){
            map.put("status","no");
        }else {
            map.put("status","ok");
        }
        return map;
    }

    @RequestMapping(value = "/addSubAlbum",method = RequestMethod.POST)
    public Map<String,Object> addSubAlbum(@RequestParam("aid")int aid,
                                          @RequestParam("number")String number){
        Map<String,Object> map = new HashMap<>();
        if (subalbumService.selectByNumber(number)!=null){
            map.put("status","no");
        }else {
            Album album = albumService.selectById(aid);
            album.setNum(album.getNum()+1);
            albumService.updateByPrimaryKey(album);
            subalbumService.insert(new Subalbum(aid, number, 0, DateTimeUtil.getDate()));
            map.put("status","ok");
        }
        return map;
    }


    @RequestMapping(value = "/borrow",method = RequestMethod.POST)
    public Map<String,Object> borrow(@RequestParam("aid")int aid,
                                     @RequestParam("rid")int rid,
                                     @RequestParam("raccount")String raccount){
        Map<String,Object> map = new HashMap<>();
        if (rid!=0){
            Album album = albumService.selectById(aid);
            int count = 0;
            //找到可借的书id
            int enableBorrowSAid = 0;
            for (int i = 0; i < album.getSubalbums().size(); i++) {
                if (album.getSubalbums().get(i).getCondi()==0){
                    enableBorrowSAid = album.getSubalbums().get(i).getSid();
                    count++;
                }
            }
            if (count != 0){
                map.put("status", "ok");
                Borrowrecord borrowrecord = new Borrowrecord(rid, raccount, aid,
                        enableBorrowSAid,
                        DateTimeUtil.getDate(),
                        DateTimeUtil.getDateAfter15(),
                        DateTimeUtil.getDateNumber());
                borrowrecordService.insert(borrowrecord);
                Subalbum subalbum = subalbumService.selectById(enableBorrowSAid);
                subalbum.setCondi(1);
                subalbumService.updateByPrimaryKey(subalbum);
            }else {
                map.put("status", "no");
            }
        }
        return map;
    }

    @RequestMapping(value = "/reback",method = RequestMethod.POST)
    public Map<String,Object> reback(@RequestParam("bid")int bid,
                                     @RequestParam("sid")int sid){
        Map<String,Object> map = new HashMap<>();
        if (bid!=0 && sid!=0){
            borrowrecordService.delete(borrowrecordService.selectByPrimaryKey(bid));
            Subalbum subalbum = subalbumService.selectById(sid);
            subalbum.setCondi(0);
            subalbumService.updateByPrimaryKey(subalbum);
            map.put("status", "yes");
        }else {
            map.put("status", "no");
        }
        return map;
    }
}
