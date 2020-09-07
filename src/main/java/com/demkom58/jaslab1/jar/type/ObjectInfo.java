package com.demkom58.jaslab1.jar.type;

import javax.swing.*;
import java.util.Objects;

public class ObjectInfo {
    private final String name;
    private final DefaultListModel<String> listModel;

    public ObjectInfo(String name, DefaultListModel<String> listModel) {
        this.name = name;
        this.listModel = listModel;
    }

    public String getName() {
        return name;
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectInfo classInfo = (ObjectInfo) o;
        return Objects.equals(name, classInfo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
