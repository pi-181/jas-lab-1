package com.demkom58.jaslab1.jar;

import com.demkom58.jaslab1.jar.type.ObjectInfo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Collection;
import java.util.Iterator;
import java.util.jar.JarEntry;

public abstract class ResourceMapper extends GenericMapper<JarEntry> {

    protected ResourceMapper(ResourceMapper next) {
        super(next);
    }

    @Override
    protected DefaultMutableTreeNode getPackageNode(DefaultMutableTreeNode rootNode, JarEntry entry) {
        String name = entry.getName();

        final int delimiterIndex = name.lastIndexOf('/');
        if (delimiterIndex != -1)
            name = name.substring(0, delimiterIndex);

        return super.getPackageNode(rootNode, name, "/");
    }

    protected String getLastPartName(String name, String delimiter) {
        final int delimiterIndex = name.lastIndexOf(delimiter);
        if (delimiterIndex != -1)
            name = name.substring(delimiterIndex + 1);

        return name;
    }

    @Override
    protected void addInfo(DefaultMutableTreeNode packageNode, JarEntry jarEntry, Collection<String> info) {
        String name = getLastPartName(jarEntry.getName(), "/");

        final DefaultListModel<String> description = new DefaultListModel<>();
        final Iterator<String> iterator = info.iterator();
        for (int i = 0; iterator.hasNext(); i++)
            description.add(i, iterator.next());

        final DefaultMutableTreeNode objectNode
                = new DefaultMutableTreeNode(new ObjectInfo(name, description));

        packageNode.add(objectNode);
    }

}
