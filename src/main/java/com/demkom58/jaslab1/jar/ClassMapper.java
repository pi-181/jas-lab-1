package com.demkom58.jaslab1.jar;

import com.demkom58.jaslab1.jar.type.ObjectInfo;
import com.demkom58.jaslab1.util.ClassUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Collection;
import java.util.jar.JarEntry;

public abstract class ClassMapper extends GenericMapper<Class<?>> {

    protected ClassMapper(ClassMapper next) {
        super(next);
    }

    @Override
    protected DefaultMutableTreeNode getPackageNode(DefaultMutableTreeNode rootNode, Class<?> clazz) {
        return super.getPackageNode(rootNode, clazz.getPackageName(), ".");
    }

    @Override
    protected void addInfo(DefaultMutableTreeNode packageNode, Class<?> clazz, Collection<String> info) {
        final DefaultListModel<String> description = new DefaultListModel<>();
        description.addAll(info);

        String name = clazz.getName();
        final int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf != -1)
            name = name.substring(lastIndexOf + 1);

        final ObjectInfo classInfo = new ObjectInfo(name, description);
        final DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(classInfo);
        packageNode.add(classNode);
    }

}
