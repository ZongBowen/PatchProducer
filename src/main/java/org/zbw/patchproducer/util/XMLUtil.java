package org.zbw.patchproducer.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class XMLUtil {
    /**
     * 序列化xml
     *
     * @param obj obj
     * @param <T> 泛型
     * @return json
     */
    public static <T> String obj2String(T obj) {
        JAXBContext jc = null;
        try {
            // 生成上下文对象
            jc = JAXBContext.newInstance(obj.getClass());
            // 从上下文中获取Marshaller对象，用作将bean转换为xml
            Marshaller ma = jc.createMarshaller();
            // 配置
            // 格式化输出，按标签自动换行
            ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // 设置utf-8编码
            ma.setProperty(Marshaller.JAXB_ENCODING, "utf-16");
            // 不省略xml头信息
            ma.setProperty(Marshaller.JAXB_FRAGMENT, true);
            // 转换
            StringWriter writer = new StringWriter();
            ma.marshal(obj, writer);
            return writer.toString();
        } catch (JAXBException e) {
            LogUtil.error(e);
            return "";
        }
    }

    /**
     * 序列化xml
     *
     * @param obj obj
     * @param <T> 泛型
     * @return json
     */
    public static <T> String obj2String(T obj, boolean format, boolean fragment) {
        JAXBContext jc = null;
        try {
            // 生成上下文对象
            jc = JAXBContext.newInstance(obj.getClass());
            // 从上下文中获取Marshaller对象，用作将bean转换为xml
            Marshaller ma = jc.createMarshaller();
            // 配置
            // 格式化输出，按标签自动换行
            ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // 设置utf-8编码
            ma.setProperty(Marshaller.JAXB_ENCODING, "utf-16");
            // 不省略xml头信息
            ma.setProperty(Marshaller.JAXB_FRAGMENT, true);
            // 转换
            StringWriter writer = new StringWriter();
            ma.marshal(obj, writer);
            return writer.toString();
        } catch (JAXBException e) {
            LogUtil.error(e);
            return "";
        }
    }
}
