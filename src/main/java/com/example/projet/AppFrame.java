package com.example.projet;

import javax.swing.*;

public class AppFrame extends JFrame {
    private final Navigation navigation;
    private JPanel currentView;

    public AppFrame(Navigation navigation) {
        this.navigation = navigation;
        setTitle("Application Shopping");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void changerVue(JPanel nouvelleVue) {
        if (currentView != null) {
            remove(currentView);
        }
        currentView = nouvelleVue;
        add(currentView);
        revalidate();
        repaint();
    }
}
