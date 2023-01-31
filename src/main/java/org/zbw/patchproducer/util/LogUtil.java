package org.zbw.patchproducer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {
    public static void log(String value) {
        System.out.println(value);
    }

    /**
     * 信息级日志
     *
     * @param value 日志内容
     */
    public static void infoLog(String value) {
        Logger logger = LoggerFactory.getLogger(LogUtil.class);
        logger.info(value);
    }

    /**
     * 错误级日志
     *
     * @param value 日志内容
     */
    public static void errorLog(String value) {
        Logger logger = LoggerFactory.getLogger(LogUtil.class);
        logger.error(value);
    }

    /**
     * 警告级日志
     *
     * @param value 日志内容
     */
    public static void warningLog(String value) {
        Logger logger = LoggerFactory.getLogger(LogUtil.class);
        logger.warn(value);
    }

    /**
     * 信息级日志
     *
     * @param e 异常
     */
    public static void info(Throwable e) {
        Logger logger = LoggerFactory.getLogger(LogUtil.class);
        logger.info(getStack(e));
    }

    /**
     * 警告级日志
     *
     * @param e 异常
     */
    public static void warning(Throwable e) {
        Logger logger = LoggerFactory.getLogger(LogUtil.class);
        logger.info(getStack(e));
    }

    /**
     * 错误级日志
     *
     * @param e 异常
     */
    public static void error(Throwable e) {
        Logger logger = LoggerFactory.getLogger(LogUtil.class);
        logger.info(getStack(e));
    }

    /**
     * 获取异常堆栈
     *
     * @param e 异常
     * @return 堆栈
     */
    public static String getStack(Throwable e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer, true));
        return writer.toString();
    }
}
