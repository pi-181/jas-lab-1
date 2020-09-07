package com.demkom58.jaslab1.jar;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Arrays;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SimpleResourceMapper extends ResourceMapper {
    public SimpleResourceMapper(ResourceMapper next) {
        super(next);
    }

    @Override
    protected boolean process(DefaultMutableTreeNode rootNode, JarFile jarFile, JarEntry jarEntry) {
        final DefaultMutableTreeNode packageNode = getPackageNode(rootNode, jarEntry);
        addInfo(packageNode, jarEntry, Arrays.asList(
                "Size: " + jarEntry.getSize(),
                "Modification date: " + jarEntry.getLastModifiedTime()
        ));

        return true;
    }
}
