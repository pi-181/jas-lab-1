package com.demkom58.jaslab1.util;

import java.lang.reflect.Field;

public final class ClassUtil {

    public static String getInfo(Class<?> clazz) {
        final StringBuilder buf = new StringBuilder();

        if (clazz.isAnnotation())
            buf.append("Annotation ");
        else if (clazz.isInterface())
            buf.append("Interface ");
        else if (clazz.isEnum())
            buf.append("Enum ");
        else
            buf.append("Class ");

        buf.append(clazz.getName() + " ");
        if (clazz.isArray())
            buf.append("array");

        if (clazz.isLocalClass())
            buf.append("local");
        buf.append("\n");
        buf.append(getFieldNames(clazz));

        return buf.toString();
    }

    public static String getFieldNames(Class<?> clazz) {
        StringBuilder builder = new StringBuilder();

        try {
            Field[] publicFields = clazz.getDeclaredFields();
            for (Field publicField : publicFields) {
                try {
                    String fieldName = publicField.getName();
                    builder.append("Name: ").append(fieldName);

                    Class<?> typeClass = publicField.getType();
                    String fieldType = typeClass.getName();
                    builder.append(", Type: ")
                            .append(fieldType)
                            .append("\n");

                } catch (Exception e) {
                    builder.append("\n");
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}