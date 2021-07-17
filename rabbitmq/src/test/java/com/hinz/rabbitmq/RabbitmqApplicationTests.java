package com.hinz.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;

//@SpringBootTest
class RabbitmqApplicationTests {


    @Test
    void fun(){


    }


    @Test
    void contextLoads() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("a", new Date());
        list.add(map);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String string = mapper.writeValueAsString(list);
            System.out.println("string = " + string);
            String json = RabbitmqApplicationTests.getJson(list);
            System.out.println("json = " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static String getJson(Object object) {
        return getJson(object, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 设置其他日期格式
     *
     * @param object     传入的待转换格式的日期对象
     * @param dateFormat 自定义的日期格式
     * @return json字符串
     */
    public static String getJson(Object object, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        //关闭时间戳的功能
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //转换时间格式
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        //让mapper指定时间日期格式为SimpleDateFormat
        mapper.setDateFormat(sdf);
        //将日期对象转换为json字符串格式
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
