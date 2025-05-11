package com.example.projet.vue;
import java.util.List; // ✅ Le bon List avec types génériques


import com.example.projet.Navigation;
import com.example.projet.controller.ArticleController;
import com.example.projet.modele.Article;
import com.example.projet.controller.MarqueController;
import com.example.projet.modele.Marque;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class VueAjoutProduit extends JPanel {

    private File imageFile;

    public VueAjoutProduit(Navigation navigation, ArticleController articleController, MarqueController marqueController)
    {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Ajouter un nouvel article", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setOpaque(true);
        titre.setBackground(new Color(24, 64, 55));
        titre.setForeground(Color.WHITE);
        add(titre, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JTextField nomField = new JTextField();
        JTextField prixField = new JTextField();
        JTextField prixGrosField = new JTextField();
        JTextField quantiteGrosField = new JTextField();
        List<Marque> marques = marqueController.getToutesLesMarques();
        JComboBox<Marque> marqueCombo = new JComboBox<>(marques.toArray(new Marque[0]));
        JLabel imageLabel = new JLabel("Aucune image sélectionnée");

        JButton parcourir = new JButton("Choisir une image");
        parcourir.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int retour = chooser.showOpenDialog(this);
            if (retour == JFileChooser.APPROVE_OPTION) {
                imageFile = chooser.getSelectedFile();
                imageLabel.setText(imageFile.getName());
            }
        });

        JButton valider = new JButton("Ajouter");
        valider.addActionListener(e -> {
            try {
                String nom = nomField.getText();
                double prix = Double.parseDouble(prixField.getText());
                Double prixGros = prixGrosField.getText().isEmpty() ? null : Double.parseDouble(prixGrosField.getText());
                Integer quantiteGros = quantiteGrosField.getText().isEmpty() ? null : Integer.parseInt(quantiteGrosField.getText());
                Marque marque = (Marque) marqueCombo.getSelectedItem();
                int idMarque = (marque != null) ? marque.getId() : -1;

                Article article = new Article(0, nom, prix, prixGros, quantiteGros, idMarque);

                boolean success = articleController.ajouterArticle(article, imageFile);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Article ajouté !");
                    navigation.afficherMenuAdmin();
                } else {
                    JOptionPane.showMessageDialog(this, "Échec de l’ajout.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        });

        formPanel.add(new JLabel("Nom :"));
        formPanel.add(nomField);
        formPanel.add(new JLabel("Prix unitaire (€) :"));
        formPanel.add(prixField);
        formPanel.add(new JLabel("Prix de gros (€) :"));
        formPanel.add(prixGrosField);
        formPanel.add(new JLabel("Quantité pour prix de gros :"));
        formPanel.add(quantiteGrosField);
        formPanel.add(new JLabel("Marque :"));
        formPanel.add(marqueCombo);
        formPanel.add(parcourir);
        formPanel.add(imageLabel);

        add(formPanel, BorderLayout.CENTER);
        add(valider, BorderLayout.SOUTH);
    }
}
