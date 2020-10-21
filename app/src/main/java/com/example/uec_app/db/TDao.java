package com.example.uec_app.db;

import android.app.DownloadManager;
import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TDao<T> {

    private Dao mDao;
    private DBHelper helper;


    public TDao(Context ctx, Class clazz){
        try{
            helper = DBHelper.getHelper(ctx);
            mDao = helper.getDao(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public int insert(T t){
        int result = -1;
        try{
            result = mDao.create(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int insertList(List<T> tList){
        int res = -1;
        try{
            res = mDao.create(tList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public int insertOrUpdate(T t){
        try{
            return mDao.createOrUpdate(t).getNumLinesChanged();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }



    public int delete(T t){
        int res = -1;
        try{
            res = mDao.delete(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public long deleteById(long id){
        int res = -1;
        try {
            res = mDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public int deleteByColumn(String collumn,Object value){
        int res = -1;
        try {

            DeleteBuilder builder = mDao.deleteBuilder();
            builder.where().eq(collumn,value);
            res = builder.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public int deleteAll(){
        int res = -1;
        try{
            res = mDao.deleteBuilder().delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public int update(T t){
        int res = -1;
        try{
            res = mDao.update(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<T> queryAll(){
        List<T> tList = new ArrayList<>();
        try {
            tList.addAll(mDao.queryForAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tList;
    }

    public List<T> queryForN(Long num){
        List<T> tList = new ArrayList<>();
        try{
            QueryBuilder builder = mDao.queryBuilder();
            builder.where().isNotNull("time");
            builder.orderBy("time",false).limit(num);
            tList = builder.query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tList;
    }

    public List<T> queryForN(String collumn,Object value,Long num){
        List<T> tList = new ArrayList<>();
        try{
            QueryBuilder builder = mDao.queryBuilder();
            builder.where().eq(collumn,value).and().isNotNull("time");
            builder.orderBy("time",false).limit(num);
            tList = builder.query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tList;
    }


    public List<T> queryBetween(String collumn,Object value, long low, long high){
        List<T> tList = new ArrayList<>();
        try{
            QueryBuilder builder = mDao.queryBuilder();
            builder.where().eq(collumn,value).between("time", low, high);
            tList = builder.query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tList;
    }

    public T queryById(long id){
        T t = null;
        try {
            t = (T) mDao.queryForId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public List<T> queryByColumn(String collumn,Object value){
        List<T> tList = new ArrayList<>();
        try {
            tList.addAll(mDao.queryForEq(collumn, value));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tList;
    }

    public T queryLatest(String collumn,Object value){
        T t = null;
        try{
            QueryBuilder builder = mDao.queryBuilder();
            builder.where().eq(collumn,value).and().isNotNull("time");
            builder.orderBy("time",false);
            t = (T) builder.queryForFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }



    public T queryActive(String collumn,Object value){
        T t = null;
        try{
            QueryBuilder builder = mDao.queryBuilder();
            builder.where().eq(collumn,value).and().isNotNull("time").and().ge("time",System.currentTimeMillis()-60000);
            builder.orderBy("time",false);
            t = (T) builder.queryForFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public T queryLatest(){
        T t = null;
        try{
            QueryBuilder builder = mDao.queryBuilder();
            builder.orderBy("time",false);
            t = (T) builder.queryForFirst();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }




    public long count(){
        long res = 0;
        try {
            res = mDao.countOf();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public long countByCollumn(String collumn, Object value){
        long res = 0;
        try {
            QueryBuilder builder = mDao.queryBuilder();
            builder.where().eq(collumn,value);
            res = mDao.countOf(builder.prepare());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


}