package com.gam0zing.newnew_origins.utils;

import com.gam0zing.newnew_origins.data.DataSources;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ModTools {

    /// 反射获取静态类的静态字段，类型转换后返回列表
    public static <T> List<T> getFieldsAsList(Class<?> object, Class<T> targetType) {

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

    public static <T> List<DataSources.ID_Holder<T>> getHolderFieldsAsList(Class<?> object, Class<T> targetType) {
        List<DataSources.ID_Holder<T>> ret = new ArrayList<>();

        for (Field field : object.getFields()) {
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()) ||
                    !java.lang.reflect.Modifier.isPublic(field.getModifiers())) {
                continue;
            }

            if (!DataSources.ID_Holder.class.isAssignableFrom(field.getType())) {
                continue;
            }

            if (!matchType(field, targetType)) {
                continue;
            }

            try {
                Object fieldValue = field.get(null);
                if (fieldValue == null) {
                    continue;
                }

                @SuppressWarnings("unchecked")
                DataSources.ID_Holder<T> value = (DataSources.ID_Holder<T>) fieldValue;
                ret.add(value);
            } catch (IllegalAccessException e) {
                System.err.println("无法访问静态字段: " + field.getName() + ", 错误: " + e.getMessage());
            }
        }
        return ret;
    }

    private static <T> boolean matchType(Field field, Class<T> targetType) {
        try {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType pt) {
                Type[] actualTypeArguments = pt.getActualTypeArguments();
                if (actualTypeArguments.length > 0) {
                    Type actualType = actualTypeArguments[0];
                    if (actualType instanceof Class) {
                        return targetType.isAssignableFrom((Class<?>) actualType);
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
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

    /// 修改起源JsonElement中icon下的id为item
    public static JsonElement fixIcon(JsonElement jsonElement) {
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement iconElement = jsonObject.get("icon");
            if (iconElement != null && iconElement.isJsonObject()) {
                JsonObject iconObject = iconElement.getAsJsonObject();
                if (iconObject.has("id")) {
                    iconObject.add("item", iconObject.remove("id"));
                }
            }
        }
        return jsonElement;
    }
}
