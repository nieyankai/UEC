package com.example.uec_app.model;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "tb_res_type")//创建表名
public class ResType {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(columnName = "name",dataType = DataType.STRING)
    private String name;
    @DatabaseField(columnName = "unit",dataType = DataType.STRING)
    private String unit;
    @DatabaseField(columnName = "comment", dataType = DataType.STRING)
    private String comment;
    @DatabaseField(columnName = "time",dataType = DataType.LONG)
    private long time;

    public ResType() {
    }

    public ResType(String name, String unit, String comment, long time) {
        this.name = name;
        this.unit = unit;
        this.comment = comment;
        this.time = time;
    }

    public ResType(long id, String name, String unit, String comment, long time) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.comment = comment;
        this.time = time;
    }

    public ResType(String name, String unit, String comment) {
        this.name = name;
        this.unit = unit;
        this.comment = comment;
        this.time = System.currentTimeMillis();
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "ResType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", comment='" + comment + '\'' +
                ", time=" + time +
                '}';
    }

    public String getTimeString(){
        SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formater.format(new Date(time));
    }
}
