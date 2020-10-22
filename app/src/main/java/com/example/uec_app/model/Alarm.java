package com.example.uec_app.model;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "tb_alarm")
public class Alarm {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(columnName = "value",dataType = DataType.STRING)
    private String value;

    @DatabaseField(columnName = "type",dataType = DataType.STRING)
    private String type;

    @DatabaseField(columnName = "region",dataType = DataType.STRING)
    private String region;

    @DatabaseField(columnName = "dss",dataType = DataType.STRING)
    private String dss;

    @DatabaseField(columnName = "time",dataType = DataType.LONG)
    private long time;

    @DatabaseField(columnName = "resolved",dataType = DataType.STRING)
    private String resolved;

    public Alarm() {
    }


    public Alarm(long id, String value, String type, String region, String dss, long time, String resolved) {
        this.id = id;
        this.value = value;
        this.type = type;
        this.region = region;
        this.dss = dss;
        this.time = time;
        this.resolved = resolved;
    }

    public Alarm(String value, String type, String region, String dss, long time) {
        this.value = value;
        this.type = type;
        this.region = region;
        this.dss = dss;
        this.time = time;
        this.resolved = "解除";
    }

    public Alarm(String value, String type, String region, String dss) {
        this.value = value;
        this.type = type;
        this.region = region;
        this.dss = dss;
        this.time = System.currentTimeMillis();
        this.resolved = "解除";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDss() {
        return dss;
    }

    public void setDss(String dss) {
        this.dss = dss;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                ", region='" + region + '\'' +
                ", dss='" + dss + '\'' +
                ", time=" + time +
                ", resolved='" + resolved + '\'' +
                '}';
    }

    public String getTimeString(){
        SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formater.format(new Date(time));
    }
}
