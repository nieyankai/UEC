package com.example.uec_app.model;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@DatabaseTable(tableName = "tb_region")
public class Region {

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(columnName = "name", dataType = DataType.STRING)
    private String name;
    @DatabaseField(columnName = "comment", dataType = DataType.STRING)
    private String comment;
    @DatabaseField(columnName = "dev_num", dataType = DataType.LONG)
    private long dev_num;
    @DatabaseField(columnName = "time",dataType = DataType.LONG)
    private long time;

    public Region() {
    }

    public Region(String name, String comment, long dev_num, long time) {
        this.name = name;
        this.comment = comment;
        this.dev_num = dev_num;
        this.time = time;
    }

    public Region(long id, String name, String comment, long dev_num, long time) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.dev_num = dev_num;
        this.time = time;
    }

    public Region(String name, String comment) {
        this.name = name;
        this.comment = comment;
        this.dev_num = 0;
        this.time = System.currentTimeMillis();
    }

    public Region(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getDev_num() {
        return dev_num;
    }

    public void setDev_num(long dev_num) {
        this.dev_num = dev_num;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", dev_num=" + dev_num +
                ", time=" + time +
                '}';
    }

    public Map<String,Object> toMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("comment",comment);
        map.put("dev_num",dev_num);
        map.put("time",getTimeString());
        return map;
    }

    public String getTimeString(){
        SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formater.format(new Date(time));
    }
}
