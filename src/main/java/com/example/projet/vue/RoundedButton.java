package com.example.projet.vue;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private final int radius = 40;

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setBackground(new Color(24, 64, 55));
        setFont(new Font("SansSerif", Font.BOLD, 14));
        setPreferredSize(new Dimension(180, 40));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Supprime la bordure par défaut
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
