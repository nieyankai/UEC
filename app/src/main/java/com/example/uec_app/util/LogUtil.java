package com.example.uec_app.util;

import android.content.Context;
import android.util.Log;

import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicy;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

public class LogUtil {


    private static RollingFileAppender<ILoggingEvent> rollingAppender;

    public static String getStackTrace(Throwable exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        exception.printStackTrace(pw);
        return sw.toString();
    }

    public static Logger getLogger(Context context, String tag) {
        Logger logger = (Logger) LoggerFactory.getLogger(tag);
        logger.addAppender(getRollingAppender(context));
        logger.setLevel(Level.DEBUG);
        logger.setAdditive(true); /* set to true if root should log too */

        return logger;
    }

    private static Appender<ILoggingEvent> getSimpleFileAppender(Context context) {
        String storagePath = context.getExternalFilesDir(null).getAbsolutePath();
        if(storagePath==null) storagePath = context.getFilesDir().getAbsolutePath();
        Log.i("LogUtil", "storage path: "+storagePath);

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder ple = new PatternLayoutEncoder();

        ple.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
        ple.setContext(lc);
        ple.start();

        // for simple file append
        FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
        fileAppender.setFile(storagePath+ File.separator+"IConnectSetup.log");
        fileAppender.setEncoder(ple);
        fileAppender.setContext(lc);
        fileAppender.start();

        return fileAppender;
    }

    private static Appender<ILoggingEvent> getRollingAppender(Context context) {
        if(rollingAppender==null){
            String storagePath = context.getExternalFilesDir(null).getAbsolutePath();
            if(storagePath==null) storagePath = context.getFilesDir().getAbsolutePath();
            Log.i("LogUtil", "storage path: "+storagePath);

            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            rollingAppender = new RollingFileAppender<ILoggingEvent>();
            rollingAppender.setFile(storagePath+ File.separator+"IConnectApp.log");
            rollingAppender.setAppend(true);
            rollingAppender.setContext(lc);

            TimeBasedRollingPolicy rolling = new TimeBasedRollingPolicy();
            rolling.setContext(lc);
            rolling.setParent(rollingAppender);
            rolling.setFileNamePattern(storagePath+ File.separator+"IConnectApp" + "-%d{yyyy-MM-dd}.txt");
            TimeBasedFileNamingAndTriggeringPolicy policy = new DefaultTimeBasedFileNamingAndTriggeringPolicy();
            rolling.setTimeBasedFileNamingAndTriggeringPolicy(policy);
            rolling.setMaxHistory(5);
            rolling.start();
            rollingAppender.setRollingPolicy(rolling);

            PatternLayoutEncoder layout = new PatternLayoutEncoder();
            layout.setContext(lc);
            layout.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %F:%L %M - %msg%n");
//        layout.setCharset(Charset.forName(getEncoding()));
            layout.start();
            rollingAppender.setEncoder(layout);
            rollingAppender.start();
        }
        return rollingAppender;
    }

}
