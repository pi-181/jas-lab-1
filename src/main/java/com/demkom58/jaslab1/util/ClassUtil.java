package com.demkom58.jaslab1.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ClassUtil {

    public static String getName(Class<?> clazz) {
        final StringBuilder buf = new StringBuilder();

        if (clazz.isAnnotation())
            buf.append("Annotation ");
        else if (clazz.isInterface())
            buf.append("Interface ");
        else if (clazz.isEnum())
            buf.append("Enum ");
        else
            buf.append("Class ");

        buf.append(clazz.getName()).append(" ");
        if (clazz.isArray())
            buf.append("array");

        if (clazz.isLocalClass())
            buf.append("local");
        buf.append("\n");

        return buf.toString();
    }

    public static Collection<String> getFields(Class<?> clazz) {
        List<String> fields = new ArrayList<>();

        try {
            for (Field field : clazz.getDeclaredFields()) {
                try {
                    fields.add(
                            getModifier(field.getModifiers()) +
                                    field.getType().getName() + " " +
                                    field.getName() + ";\n"
                    );
                } catch (Exception ignored) {
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return fields;
    }

    public static Collection<String> getMethods(Class<?> clazz) {
        List<String> methods = new ArrayList<>();

        try {
            for (Method method : clazz.getDeclaredMethods()) {
                try {
                    final Class<?>[] parameterTypes = method.getParameterTypes();
                    final StringBuilder builder = new StringBuilder();

                    boolean appended = false;
                    for (Class<?> parameterType : parameterTypes) {
                        if (!appended) {
                            appended = true;
                        } else builder.append(", ");

                        builder.append(parameterType.getName());
                    }

                    methods.add(
                            getModifier(method.getModifiers()) +
                                    method.getReturnType().getName() + " " +
                                    method.getName() + "(" + builder.toString() + ");\n"
                    );
                } catch (Exception ignored) {
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return methods;
    }

    public static Collection<String> getAnnotations(Class<?> clazz) {
        List<String> annotations = new ArrayList<>();

        try {
            for (Annotation annotation : clazz.getAnnotations()) {
                try {
                    annotations.add(annotation.getClass().getName());
                } catch (Exception ignored) {
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return annotations;
    }

    public static Collection<String> getClass(Class<?> clazz) {
        List<String> description = new ArrayList<>();
        description.add(ClassUtil.getName(clazz));

        final Collection<String> annotations = ClassUtil.getAnnotations(clazz);
        if (!annotations.isEmpty()) {
            description.add(" ");
            description.add("Annotations: ");
            description.addAll(annotations);
        }

        final Collection<String> fields = ClassUtil.getFields(clazz);
        if (!fields.isEmpty()) {
            description.add(" ");
            description.add("Fields: ");
            description.addAll(fields);
        }

        final Collection<String> methods = ClassUtil.getMethods(clazz);
        if (!methods.isEmpty()) {
            description.add(" ");
            description.add("Methods: ");
            description.addAll(methods);
        }

        final Class<?>[] declaredClasses = clazz.getDeclaredClasses();
        if (declaredClasses.length > 0) {
            description.add(" ");
            description.add("Classes of " + clazz.getSimpleName() + ": ");
            for (Class<?> declaredClass : declaredClasses)
                description.addAll(ClassUtil.getClass(declaredClass));
        }

        return description;
    }

    private static String getModifier(int modifiers) {
        StringBuilder builder = new StringBuilder();

        if (Modifier.isPublic(modifiers))
            builder.append("public ");

        if (Modifier.isPrivate(modifiers))
            builder.append("private ");

        if (Modifier.isProtected(modifiers))
            builder.append("protected ");

        if (Modifier.isStatic(modifiers))
            builder.append("static ");

        if (Modifier.isTransient(modifiers))
            builder.append("transient ");

        if (Modifier.isVolatile(modifiers))
            builder.append("volatile ");

        if (Modifier.isSynchronized(modifiers))
            builder.append("synchronized ");

        if (Modifier.isStrict(modifiers))
            builder.append("strictfp ");

        if (Modifier.isAbstract(modifiers))
            builder.append("abstract ");

        if (Modifier.isNative(modifiers))
            builder.append("native ");

        if (Modifier.isInterface(modifiers))
            builder.append("interface ");

        if (Modifier.isFinal(modifiers))
            builder.append("final ");

        return builder.toString();
    }

}