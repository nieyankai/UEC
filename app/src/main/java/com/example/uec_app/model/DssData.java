package com.example.uec_app.model;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;


@DatabaseTable(tableName = "tb_dss_data")//创建表名
public class DssData {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(columnName = "dss_config",foreign = true,canBeNull = false,foreignAutoRefresh = true)
    private DssConfig dssConfig;
    @DatabaseField(columnName = "value",dataType = DataType.STRING)
    private String value;
    @DatabaseField(columnName = "time",dataType = DataType.LONG)
    private long time;



    public String getTimeString(){
        SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formater.format(new Date(time));
    }

    public DssData() {
    }

    public DssData(long id, DssConfig dssConfig, String value, long time) {
        this.id = id;
        this.dssConfig = dssConfig;
        this.value = value;
        this.time = time;
    }

    public DssData(DssConfig dssConfig, String value, long time) {
        this.dssConfig = dssConfig;
        this.value = value;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DssConfig getDssConfig() {
        return dssConfig;
    }

    public void setDssConfig(DssConfig dssConfig) {
        this.dssConfig = dssConfig;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }




    @Override
    public String toString() {
        return "DssData{" +
                "id=" + id +
                ", dssConfig=" + dssConfig +
                ", value='" + value + '\'' +
                ", time=" + time +
                '}';
    }
}
