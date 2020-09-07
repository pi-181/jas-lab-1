package com.demkom58.jaslab1.jar;

import com.demkom58.jaslab1.jar.type.ObjectInfo;
import com.demkom58.jaslab1.jar.type.PackageInfo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Collection;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

public abstract class GenericMapper<T> {
    private final GenericMapper<T> next;

    protected GenericMapper(GenericMapper<T> next) {
        this.next = next;
    }

    public void handle(DefaultMutableTreeNode rootNode, JarFile jarFile, T t) {
        if (!this.process(rootNode, jarFile, t) && next != null)
            next.handle(rootNode, jarFile, t);
    }

    protected DefaultMutableTreeNode getPackageNode(DefaultMutableTreeNode rootNode, String packageName, String delimiter) {
        final String[] packages = packageName.split(Pattern.quote(delimiter));

        DefaultMutableTreeNode pkg = rootNode;

        for (int i = 0; i < packages.length; i++) {
            final String currentPackageName = packages[i].intern();
            final DefaultMutableTreeNode foundPackageNode = findPackageNode(pkg, currentPackageName);
            if (foundPackageNode != null) {
                pkg = foundPackageNode;
                continue;
            }

            final PackageInfo info = new PackageInfo(currentPackageName);
            final DefaultMutableTreeNode createdPackageNode = new DefaultMutableTreeNode(info);
            pkg.add(createdPackageNode);
            pkg = createdPackageNode;
        }

        return pkg;
    }

    protected DefaultMutableTreeNode findPackageNode(DefaultMutableTreeNode node, String packageName) {
        final int childCount = node.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final DefaultMutableTreeNode childAt = (DefaultMutableTreeNode) node.getChildAt(i);
            final Object userObject = childAt.getUserObject();
            if (!(userObject instanceof PackageInfo))
                continue;

            final PackageInfo info = (PackageInfo) userObject;
            if (packageName == info.getName().intern())
                return childAt;
        }

        return null;
    }

    protected abstract boolean process(DefaultMutableTreeNode rootNode, JarFile jarFile, T t);

    protected abstract DefaultMutableTreeNode getPackageNode(DefaultMutableTreeNode rootNode, T t);

    protected abstract void addInfo(DefaultMutableTreeNode packageNode, T t, Collection<String> info);

}
