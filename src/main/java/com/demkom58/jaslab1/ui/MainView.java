package com.demkom58.jaslab1.ui;

import com.demkom58.jaslab1.jar.type.ObjectInfo;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MainView extends JFrame {
    private JPanel rootPanel;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openFileMenuItem;

    private JTree jarTree;
    private JList<String> infoList;

    private TreeModelMapper modelMapper;

    public MainView() {
        setContentPane(rootPanel);
        setSize(600, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Jar Viewer");

    }

    private void createUIComponents() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        openFileMenuItem = new JMenuItem("Open");
        fileMenu.add(openFileMenuItem);

        openFileMenuItem.addActionListener(event -> {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.setName("Select Jar file");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Java archive (*.jar)", "jar"));

            final int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                final File file = fileChooser.getSelectedFile();
                try {
                    modelMapper.open(file);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "An error occurred", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        jarTree = new JTree();
        modelMapper = new TreeModelMapper(jarTree);
        jarTree.setModel(new DefaultTreeModel(null));
        jarTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainView.this.onItemView(e);
            }
        });
    }

    public void onItemView(MouseEvent me) {
        final TreePath pathForLocation = jarTree.getPathForLocation(me.getX(), me.getY());
        if (pathForLocation == null)
            return;

        final Object pathComponent = pathForLocation.getLastPathComponent();
        if (!(pathComponent instanceof DefaultMutableTreeNode))
            return;

        final DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) pathComponent;
        final Object userObject = treeNode.getUserObject();
        if (!(userObject instanceof ObjectInfo))
            return;

        infoList.setModel(((ObjectInfo) userObject).getListModel());
    }
}
