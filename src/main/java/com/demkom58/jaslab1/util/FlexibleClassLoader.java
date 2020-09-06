package com.demkom58.jaslab1.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FlexibleClassLoader extends ClassLoader {
    private final File file;
    private final JarFile jar;
    private final Map<String, Class<?>> loaded = new HashMap<>();

    public FlexibleClassLoader(File jarFile) throws IOException {
        super(FlexibleClassLoader.class.getClassLoader());
        this.file = jarFile;
        this.jar = new JarFile(jarFile);
    }

    public Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = loaded.get(name);
        if (clazz != null)
            return clazz;

        try {
            return findSystemClass(name);
        } catch (Exception ignored) { }

        byte[] b;
        try {
            b = loadClassData(name);
            clazz = defineClass(name, b, 0, b.length);
            loaded.put(name, clazz);
        } catch (Throwable e) {
            throw new ClassNotFoundException(e.getMessage());
        }

        return clazz;
    }

    public Map<String, Class<?>> getLoaded() {
        return Collections.unmodifiableMap(loaded);
    }

    private byte[] loadClassData(String name) throws ClassNotFoundException {
        String entryName = name.replace('.', '/') + ".class";
        byte[] buf;

        try {
            JarEntry entry = jar.getJarEntry(entryName);
            if (entry == null)
                throw new ClassNotFoundException(name);

            InputStream input = jar.getInputStream(entry);
            int size = (int) entry.getSize();
            buf = new byte[size];

            int count = input.read(buf);
            if (count < size)
                throw new ClassNotFoundException("Error reading class '" + name + "' from :" + file.getPath());

        } catch (IOException e1) {
            throw new ClassNotFoundException(e1.getMessage());
        }

        return buf;
    }

}
