package com.demkom58.jaslab1.ui;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class MainView extends JFrame {
    private final DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode());

    private JPanel rootPanel;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openFileMenuItem;

    private JTree jarTree;
    private JList infoList;

    public MainView() {
        setContentPane(rootPanel);
        setSize(600, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Jar Viewer");

        jarTree.setModel(treeModel);
        openFileMenuItem.addActionListener(e -> System.out.println("ifa"));
    }

    private void createUIComponents() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        openFileMenuItem = new JMenuItem("Open");
        fileMenu.add(openFileMenuItem);
    }
}
