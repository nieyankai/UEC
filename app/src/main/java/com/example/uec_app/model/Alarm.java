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

    @DatabaseField(columnName = "type",dataType = DataType.STRING)
    private String type;

    @DatabaseField(columnName = "content",dataType = DataType.STRING)
    private String content;

    @DatabaseField(columnName = "status",dataType = DataType.STRING)
    private String status;

    @DatabaseField(columnName = "time",dataType = DataType.LONG)
    private long time;

    public Alarm() {
    }

    public Alarm(String type, String content, String status, long time) {
        this.type = type;
        this.content = content;
        this.status = status;
        this.time = time;
    }


    public Alarm(long id, String type, String content, String status, long time) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.status = status;
        this.time = time;
    }

    public Alarm(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", time=" + time +
                '}';
    }

    public String getTimeString(){
        SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formater.format(new Date(time));
    }
}
