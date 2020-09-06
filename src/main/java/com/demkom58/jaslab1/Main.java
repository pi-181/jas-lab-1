package com.demkom58.jaslab1;

import com.demkom58.jaslab1.ui.MainView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            final MainView mainView = new MainView();
            mainView.setVisible(true);
        });
    }
}
