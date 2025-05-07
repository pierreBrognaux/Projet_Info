package com.example.projet.vue;

import com.example.projet.Navigation;
import com.example.projet.controller.UtilisateurController;
import com.example.projet.modele.Client;

import javax.swing.*;
import java.awt.*;

public class VueInscription extends JPanel {

    public VueInscription(Navigation navigation, UtilisateurController utilisateurController) {
        setLayout(new GridLayout(7, 2, 10, 10));

        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"ancien", "nouveau"});
        JButton valider = new JButton("S'inscrire");
        JButton retour = new JButton("← Retour");

        add(new JLabel("Nom :")); add(nomField);
        add(new JLabel("Prénom :")); add(prenomField);
        add(new JLabel("Email :")); add(emailField);
        add(new JLabel("Mot de passe :")); add(passwordField);
        add(new JLabel("Type client :")); add(typeCombo);
        add(valider); add(retour);

        valider.addActionListener(e -> {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String mdp = new String(passwordField.getPassword());
            String type = (String) typeCombo.getSelectedItem();

            Client nouveau = new Client(0, nom, prenom, email, mdp, type);
            if (utilisateurController.sInscrire(nouveau)) {
                JOptionPane.showMessageDialog(this, "Compte créé !");
                navigation.afficherConnexion();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription.");
            }
        });

        retour.addActionListener(e -> navigation.afficherConnexion());
    }
}
