package com.example.projet.vue;

import com.example.projet.Navigation;
import com.example.projet.controller.MarqueController;

import javax.swing.*;
import java.awt.*;

public class VueAjoutMarque extends JPanel {

    public VueAjoutMarque(Navigation navigation, MarqueController marqueController) {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Ajouter une marque", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setOpaque(true);
        titre.setBackground(new Color(24, 64, 55));
        titre.setForeground(Color.WHITE);
        add(titre, BorderLayout.NORTH);

        JPanel form = new JPanel(new FlowLayout());
        JTextField champNom = new JTextField(20);
        JButton valider = new JButton("➕ Ajouter");

        form.add(new JLabel("Nom de la marque :"));
        form.add(champNom);
        form.add(valider);

        valider.addActionListener(e -> {
            String nom = champNom.getText().trim();
            if (!nom.isEmpty()) {
                boolean success = marqueController.ajouterMarque(nom);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Marque ajoutée !");
                    navigation.afficherMenuAdmin();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur : la marque existe déjà ou la BDD a échoué.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Le champ est vide.");
            }
        });

        add(form, BorderLayout.CENTER);

        JButton retour = new JButton("← Retour");
        retour.addActionListener(e -> navigation.afficherMenuAdmin());
        add(retour, BorderLayout.SOUTH);
    }
}
