package com.example.uec_app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.uec_app.model.Alarm;
import com.example.uec_app.model.DssConfig;
import com.example.uec_app.model.DssData;
import com.example.uec_app.model.Region;
import com.example.uec_app.model.ResType;
import com.example.uec_app.model.UserInfo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static String dbname = "next.db";
    private static int version = 10;
    private static DBHelper instance;

    public DBHelper(Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, DssData.class);
            TableUtils.createTable(connectionSource, DssConfig.class);
            TableUtils.createTable(connectionSource, Alarm.class);
            TableUtils.createTable(connectionSource, Region.class);
            TableUtils.createTable(connectionSource, ResType.class);
            TableUtils.createTable(connectionSource, UserInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        if (oldVersion < version) {
            try {
                //删除表..
                TableUtils.dropTable(connectionSource, DssData.class, true);
                TableUtils.dropTable(connectionSource, DssConfig.class, true);
                TableUtils.dropTable(connectionSource, Alarm.class, true);
                TableUtils.dropTable(connectionSource, Region.class, true);
                TableUtils.dropTable(connectionSource, ResType.class, true);
                TableUtils.dropTable(connectionSource, UserInfo.class, true);
                onCreate(sqLiteDatabase, connectionSource);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    public static synchronized DBHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper(context);
                }
            }
        }
        return instance;
    }
}
