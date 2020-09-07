package com.demkom58.jaslab1.jar;

import com.demkom58.jaslab1.util.ClassUtil;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.jar.JarFile;

public class SimpleClassMapper extends ClassMapper {
    public SimpleClassMapper(ClassMapper next) {
        super(next);
    }

    @Override
    protected boolean process(DefaultMutableTreeNode rootNode, JarFile jarFile, Class<?> clazz) {
        final DefaultMutableTreeNode packageNode = getPackageNode(rootNode, clazz);
        addInfo(packageNode, clazz, ClassUtil.getClass(clazz));
        return true;
    }

}

