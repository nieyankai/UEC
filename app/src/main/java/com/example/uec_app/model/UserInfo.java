package com.example.uec_app.model;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "tb_user_info")
public class UserInfo {

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(columnName = "name",dataType = DataType.STRING)
    private String name;
    @DatabaseField(columnName = "password",dataType = DataType.STRING)
    private String password;
    @DatabaseField(columnName = "time",dataType = DataType.LONG)
    private long time;

    public UserInfo() {
    }

    public UserInfo(String name, String password) {
        this.name = name;
        this.password = password;
        this.time = System.currentTimeMillis();
    }

    public UserInfo(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.time = System.currentTimeMillis();
    }

    public UserInfo(long id, String name, String password, long time) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.time = time;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }



    public String getTimeString(){
        SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formater.format(new Date(time));
    }


}
