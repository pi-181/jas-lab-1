package com.demkom58.jaslab1.ui;

import com.demkom58.jaslab1.jar.*;
import com.demkom58.jaslab1.util.FlexibleClassLoader;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class TreeModelMapper {
    private final JTree jtree;
    private DefaultMutableTreeNode rootNode;
    private DefaultMutableTreeNode classesNode;
    private DefaultMutableTreeNode resNode;

    private final ClassMapper classMapper = new SimpleClassMapper(null);
    private final ResourceMapper resourceMapper = new PropertiesResourceMapper(new SimpleResourceMapper(null));

    public TreeModelMapper(JTree jTree) {
        this.jtree = jTree;
    }

    private void resetTree(String rootName) {
        rootNode = new DefaultMutableTreeNode(rootName);

        classesNode = new DefaultMutableTreeNode("Classes");
        rootNode.add(classesNode);
        resNode = new DefaultMutableTreeNode("Resources");
        rootNode.add(resNode);

        jtree.setModel(new DefaultTreeModel(rootNode));
    }

    public void open(File file) throws IOException {
        resetTree(file.getName());

        FlexibleClassLoader classLoader = new FlexibleClassLoader(file);
        JarFile jarFile = new JarFile(file);
        Enumeration<JarEntry> enumeration = jarFile.entries();

        while (enumeration.hasMoreElements()) {
            JarEntry entry = enumeration.nextElement();
            if (entry.getName().endsWith(".class")) {
                String className = entry.getName().replace('/', '.');
                className = className.substring(0, className.length() - 6);

                try {
                    Class<?> foundClass = Class.forName(className, false, classLoader);
                    classMapper.handle(classesNode, jarFile, foundClass);
                } catch (Exception e2) {
                    System.out.println("Error:" + e2.getMessage());
                }
            } else {
                if (!entry.getName().endsWith("/"))
                    resourceMapper.handle(resNode, jarFile, entry);
            }
        }
        jarFile.close();
    }

}
