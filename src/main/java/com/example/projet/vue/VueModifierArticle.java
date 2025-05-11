package com.example.projet.vue;

import com.example.projet.controller.ArticleController;
import com.example.projet.modele.Article;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import com.example.projet.Navigation;



public class VueModifierArticle extends JPanel {

    private final Navigation navigation;
    private JComboBox<Article> articleCombo;
    private JTextField nomField, prixField, prixGrosField, quantiteGrosField;
    private JLabel imageLabel;
    private String imagePath = null;



    public VueModifierArticle(Navigation navigation, ArticleController articleController) {
        this.navigation = navigation;
        setLayout(new BorderLayout());

        // Titre
        JLabel titre = new JLabel("Modifier un article", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        titre.setOpaque(true);
        titre.setBackground(new Color(24, 64, 55));
        titre.setForeground(Color.WHITE);
        titre.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(titre, BorderLayout.NORTH);

        // Formulaire
        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        List<Article> articles = articleController.getTousLesArticles();
        articleCombo = new JComboBox<>(articles.toArray(new Article[0]));

        nomField = new JTextField();
        prixField = new JTextField();
        prixGrosField = new JTextField();
        quantiteGrosField = new JTextField();
        imageLabel = new JLabel("[Aucune image sélectionnée]");

        form.add(new JLabel("Article à modifier :"));
        form.add(articleCombo);
        form.add(new JLabel("Nom :"));
        form.add(nomField);
        form.add(new JLabel("Prix unitaire :"));
        form.add(prixField);
        form.add(new JLabel("Prix de gros :"));
        form.add(prixGrosField);
        form.add(new JLabel("Quantité de gros :"));
        form.add(quantiteGrosField);
        form.add(new JLabel("Image :"));
        JButton imageButton = new JButton("Choisir une image");
        form.add(imageButton);

        form.add(new JLabel());
        JButton modifierBtn = new JButton("Enregistrer les modifications");
        form.add(modifierBtn);

        add(form, BorderLayout.CENTER);
        add(imageLabel, BorderLayout.SOUTH);

        // Actions
        articleCombo.addActionListener(e -> remplirFormulaire((Article) articleCombo.getSelectedItem()));

        imageButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File selected = chooser.getSelectedFile();
                imagePath = "images/" + selected.getName(); // à adapter selon stratégie de copie
                imageLabel.setText("📸 " + imagePath);
            }
        });

        modifierBtn.addActionListener(e -> {
            Article a = (Article) articleCombo.getSelectedItem();
            if (a == null) return;
            String nom = nomField.getText();
            double prix = Double.parseDouble(prixField.getText());
            Double prixGros = prixGrosField.getText().isEmpty() ? null : Double.parseDouble(prixGrosField.getText());
            Integer qteGros = quantiteGrosField.getText().isEmpty() ? null : Integer.parseInt(quantiteGrosField.getText());

            Article modifie = new Article(a.getId(), nom, prix, prixGros, qteGros, a.getIdMarque(), imagePath);
            boolean success = articleController.modifierArticle(modifie);

            if (success) {
                JOptionPane.showMessageDialog(this, "Article mis à jour avec succès !");
                navigation.afficherMenuAdmin(); // ✅ redirection vers menu admin
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification.");
            }
        });

        // Préremplir au démarrage
        if (!articles.isEmpty()) {
            remplirFormulaire(articles.get(0));
        }
    }

    private void remplirFormulaire(Article article) {
        nomField.setText(article.getNom());
        prixField.setText(String.valueOf(article.getPrixUnitaire()));
        prixGrosField.setText(article.getPrixGros() != null ? article.getPrixGros().toString() : "");
        quantiteGrosField.setText(article.getQuantiteGros() != null ? article.getQuantiteGros().toString() : "");
        imageLabel.setText(article.getImagePath() != null ? "📸 " + article.getImagePath() : "[Aucune image]");
        imagePath = article.getImagePath();
    }
}
