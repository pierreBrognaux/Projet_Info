package com.example.projet.vue;
import com.example.projet.vue.RoundedButton;



import javax.swing.border.AbstractBorder;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Color;



import com.example.projet.Navigation;
import com.example.projet.controller.UtilisateurController;
import com.example.projet.controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class VueConnexion extends JPanel {

    public VueConnexion(Navigation navigation, UtilisateurController utilisateurController, ClientController clientController) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // fond doux

        // --- Bandeau supérieur avec logo à gauche et titre centré ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(24, 64, 55)); // fond vert foncé
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); // hauteur + marges

// Logo à gauche
        ImageIcon logoIcon = new ImageIcon("images/logo.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // logo petit
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        topPanel.add(logoLabel, BorderLayout.CENTER);

// Titre au centre

        add(topPanel, BorderLayout.NORTH);




        // --- Zone centrale
        JPanel zoneCentre = new JPanel();
        zoneCentre.setBackground(new Color(230, 240, 240));
        zoneCentre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 200), 2, true),
                BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));
        zoneCentre.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        zoneCentre.add(new JLabel("Email :"), gbc);

        JTextField emailField = new JTextField(20);
        gbc.gridx = 1;
        zoneCentre.add(emailField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        zoneCentre.add(new JLabel("Mot de passe :"), gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        zoneCentre.add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;


        RoundedButton loginButton = new RoundedButton("Connexion");
        zoneCentre.add(loginButton, gbc);

        gbc.gridy++;
        RoundedButton registerButton = new RoundedButton("Créer un compte");
        zoneCentre.add(registerButton, gbc);


        add(zoneCentre, BorderLayout.CENTER);

        // --- Actions
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String mdp = new String(passwordField.getPassword());
            boolean ok = utilisateurController.seConnecter(email, mdp);
            if (ok) {
                clientController.setClientActif(utilisateurController.getUtilisateurActif());
                if (utilisateurController.isAdministrateur()) {
                    navigation.afficherMenuAdmin();
                } else {
                    navigation.afficherCatalogue();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect");
            }
        });

        registerButton.addActionListener(e -> navigation.afficherInscription());
    }
}
