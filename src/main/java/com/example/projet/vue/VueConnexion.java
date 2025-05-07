package com.example.projet.vue;

import com.example.projet.Navigation;
import com.example.projet.controller.UtilisateurController;
import com.example.projet.controller.ClientController;
import com.example.projet.dao.ClientDAO;

import javax.swing.*;
import java.awt.*;

public class VueConnexion extends JPanel {

    public VueConnexion(Navigation navigation, UtilisateurController utilisateurController, ClientController clientController) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Connexion");
        JButton registerButton = new JButton("Créer un compte");

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Email :"), gbc);
        gbc.gridx = 1; add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1; add(passwordField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; add(loginButton, gbc);
        gbc.gridy = 3; add(registerButton, gbc);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String mdp = new String(passwordField.getPassword());
            boolean ok = utilisateurController.seConnecter(email, mdp);
            if (ok) {
                clientController.setClientActif(utilisateurController.getUtilisateurActif());
                if (clientDAO.isAdministrateur()) {
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
