package top.vimio.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description: Jackson Object Mapper
 * @Author: Vimio
 * @Date: 2024/9/3 8:57
 */
public class JacksonUtils {
    // 创建一个全局静态的 ObjectMapper 对象，用于 JSON 处理
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将对象序列化为 JSON 字符串
     *
     * @param value 要序列化的对象
     * @return JSON 字符串，如果序列化失败则返回空字符串
     */
    public static String writeValueAsString(Object value) {
        try {
            // 尝试将对象序列化为 JSON 字符串
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            // 如果发生异常，打印堆栈跟踪并返回空字符串
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将 JSON 字符串反序列化为指定类型的对象
     *
     * @param content JSON 字符串
     * @param valueType 目标对象的类型
     * @return 反序列化后的对象，如果反序列化失败则返回 null
     */
    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            // 尝试将 JSON 字符串反序列化为指定类型的对象
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            // 如果发生异常，打印堆栈跟踪并返回 null
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将 JSON 字符串反序列化为指定类型的对象
     *
     * @param content JSON 字符串
     * @param valueTypeRef 目标对象的类型引用
     * @return 反序列化后的对象，如果反序列化失败则返回 null
     */
    public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
        try {
            // 尝试将 JSON 字符串反序列化为指定类型的对象
            return objectMapper.readValue(content, valueTypeRef);
        } catch (IOException e) {
            // 如果发生异常，打印堆栈跟踪并返回 null
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将输入流中的 JSON 数据反序列化为指定类型的对象
     *
     * @param src 输入流，包含要反序列化的 JSON 数据
     * @param valueType 目标对象的类型
     * @return 反序列化后的对象，如果反序列化失败则返回 null
     */
    public static <T> T readValue(InputStream src, Class<T> valueType) {
        try {
            // 尝试将输入流中的 JSON 数据反序列化为指定类型的对象
            return objectMapper.readValue(src, valueType);
        } catch (IOException e) {
            // 如果发生异常，打印堆栈跟踪并返回 null
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将一个对象转换为另一个对象
     *
     * @param fromValue 要转换的对象
     * @param toValueType 目标对象的类型
     * @return 转换后的对象，如果转换失败则返回 null
     */
    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        // 使用 ObjectMapper 的 convertValue 方法进行对象转换
        return objectMapper.convertValue(fromValue, toValueType);
    }
}
