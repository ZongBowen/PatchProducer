package org.zbw.patchproducer.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 序列化json
     *
     * @param obj obj
     * @param <T> 泛型
     * @return json
     */
    public static <T> String obj2String(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            LogUtil.log("序列化失败:" + LogUtil.getStack(ex));
            return null;
        }
    }

    /**
     * 反序列化json
     *
     * @param str           字符串
     * @param typeReference 泛型描述
     * @param <T>           泛型
     * @return obj
     */
    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (!StringUtils.isBlank(str) && typeReference != null) {
            try {
                return objectMapper.readValue(str, typeReference);
            } catch (IOException ex) {
                LogUtil.log("反序列化失败:【异常信息】" + LogUtil.getStack(ex) + "【反序列化字符串】" + str);
                return null;
            }
        }
        return null;
    }

    /**
     * 反序列化json
     *
     * @param str             字符串
     * @param collectionClazz 类
     * @param elementClazzes  元素类
     * @param <T>             泛型
     * @return obj
     */
    public static <T> T string2Obj(String str, Class<?> collectionClazz, Class<?>... elementClazzes) {
        if (!StringUtils.isBlank(str) && collectionClazz != null && elementClazzes != null) {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClazz, elementClazzes);
            try {
                return objectMapper.readValue(str, javaType);
            } catch (IOException ex) {
                LogUtil.log("反序列化失败:【异常信息】" + LogUtil.getStack(ex) + "【反序列化字符串】" + str);
                return null;
            }
        }
        return null;
    }

    /**
     * 反序列化json
     *
     * @param fromValue   反序列化值
     * @param toValueType 反序列化类型
     * @param <T>         泛型
     * @return obj
     */
    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        if (fromValue != null && toValueType != null) {
            try {
                return objectMapper.convertValue(fromValue, toValueType);
            } catch (IllegalArgumentException ex) {
                LogUtil.log("反序列化失败:" + LogUtil.getStack(ex));
                return null;
            }
        }
        return null;
    }

    /**
     * 反序列化json
     *
     * @param fromValue      反序列化值
     * @param toValueTypeRef 反序列化类型
     * @param <T>            泛型
     * @return obj
     */
    public static <T> T convertValue(Object fromValue, TypeReference<T> toValueTypeRef) {
        if (fromValue != null && toValueTypeRef != null) {
            try {
                return objectMapper.convertValue(fromValue, toValueTypeRef);
            } catch (IllegalArgumentException ex) {
                LogUtil.log("反序列化失败:" + LogUtil.getStack(ex));
                return null;
            }
        }
        return null;
    }

    /**
     * 获取序列化对象
     *
     * @return 序列化对象
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        objectMapper.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        objectMapper.setTimeZone(TimeZone.getDefault());
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer deserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS a"));
        module.addDeserializer(LocalDateTime.class, deserializer);
        objectMapper.registerModule(module);
    }
}