package com.demkom58.jaslab1.jar;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class PropertiesResourceMapper extends ResourceMapper {
    public PropertiesResourceMapper(ResourceMapper next) {
        super(next);
    }

    @Override
    protected boolean process(DefaultMutableTreeNode rootNode, JarFile jarFile, JarEntry jarEntry) {
        final DefaultMutableTreeNode packageNode = getPackageNode(rootNode, jarEntry);
        final String format = getLastPartName(jarEntry.getName(), ".").toLowerCase();

        if (!format.equals("properties") && !format.equals("mf"))
            return false;

        final List<String> description = new ArrayList<>(Arrays.asList(
                "Size: " + jarEntry.getSize(),
                "Modification date: " + jarEntry.getLastModifiedTime(),
                "Content:"
        ));


        try {
            final List<String> collect = new BufferedReader(new InputStreamReader(jarFile.getInputStream(jarEntry)))
                    .lines()
                    .collect(Collectors.toList());
            description.addAll(collect);
        } catch (IOException e) {
            return false;
        }

        addInfo(packageNode, jarEntry, description);
        return true;
    }
}
