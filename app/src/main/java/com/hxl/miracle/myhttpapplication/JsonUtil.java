package com.hxl.miracle.myhttpapplication;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

public class JsonUtil {
    /**
     * 1 public static final Object parse(String text); //
     * 把JSON文本parse为JSONObject或者JSONArray 2 public static final JSONObject
     * parseObject(String text)； // 把JSON文本parse成JSONObject 3 public static
     * final T parseObject(String text, Class clazz); // 把JSON文本parse为JavaBean 4
     * public static final JSONArray parseArray(String text); //
     * 把JSON文本parse成JSONArray 5 public static final List parseArray(String text,
     * Class clazz); //把JSON文本parse成JavaBean集合 6 public static final String
     * toJSONString(Object object); // 将JavaBean序列化为JSON文本 7 public static final
     * String toJSONString(Object object, boolean prettyFormat); //
     * 将JavaBean序列化为带格式的JSON文本 8 public static final Object toJSON(Object
     * javaObject); 将JavaBean转换为JSONObject或者JSONArray。
     */
    /**
     * 把Java对象转换为JSON数据格式
     *
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * 把JSON数据格式转换为JAVA对象
     *
     * @param <T>
     * @param clz
     * @return
     */
    public static <T> T parseObject(String text, Class<T> clz) {
        try {
            if (TextUtils.isEmpty(text)) {
                return JSON.parseObject(text, clz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把JSON数据格式转换为list形式的JAVA对象
     *
     * @param <T>
     * @return
     */
    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        try {
            if (TextUtils.isEmpty(text)) {
                return JSON.parseArray(text.trim(), clazz);
            }
        } catch (Exception e) {
        }

        return null;
    }

    public static <T> List<T> parseArray(String text, String key, Class<T> clazz) {
        if (TextUtils.isEmpty(text)) {
            return JSON
                    .parseArray(JSON.parseObject(text).getString(key), clazz);
        }
        return null;
    }

    public static JSONArray parseArray(String jsonData) {
        // JSON.parseArray(jsonData);
        return JSON.parseArray(jsonData);

    }

    public static Map<String, Object> parseMap(String jsonData) {
        Map<String, Object> map = JSON.parseObject(jsonData,
                new TypeReference<Map<String, Object>>() {
                });
        return map;
    }
}
