package com.gam0zing.newnew_origins.util;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ModTools {

    /// 反射获取静态类的静态字段，类型转换后返回列表
    public static <T> List<T> getFieldsAsList(Class<?> object, Class<? extends T> targetType) {

        List<T> ret = new ArrayList<>();

        for (Field field : object.getFields()) {
            // 查找静态公共字段
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()) ||
                    !java.lang.reflect.Modifier.isPublic(field.getModifiers()) ||
                    !targetType.isAssignableFrom(field.getType())) {
                continue;
            }

            try {
                T value = targetType.cast(field.get(null));
                if (value != null) {
                    ret.add(value);
                }
            }
            catch (IllegalAccessException e) {
                System.err.println("无法访问静态字段: " + field.getName() + ", 错误: " + e.getMessage());
            }
            catch (ClassCastException e) {
                System.err.println("类型转换失败: " + field.getName() + ", 错误: " + e.getMessage());
            }
        }

        return ret;
    }

    /// 根据CODEC生成与对象对应的JsonElement
    public static <T> JsonElement getJsonElement(Codec<T> codec, T instance) {
        DataResult<JsonElement> ret = codec.encodeStart(JsonOps.INSTANCE, instance);

        if (ret.result().isPresent()) {
            return ret.result().get();
        }
        else {
            return null;
        }
    }

    /// 获取全称ID
    public static String fullID(String id) {
        return NewNewOrigins.MODID + ":" + id;
    }

    /// 获取整数10进制颜色
    public static int intColor(int red, int green, int blue) {
        red = Math.max(Math.min(red, 255), 0);
        green = Math.max(Math.min(green, 255), 0);
        blue = Math.max(Math.min(blue, 255), 0);

        return (red<<16) + (green<<8) + blue;
    }
}
