package com.example.projet.vue;

import com.example.projet.Navigation;

import javax.swing.*;
import java.awt.*;

public class VueAdminGestionArticles extends JPanel {

    public VueAdminGestionArticles(Navigation navigation) {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Espace Administrateur", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        titre.setOpaque(true);
        titre.setBackground(new Color(55, 85, 115));
        titre.setForeground(Color.WHITE);
        add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel(new GridLayout(6, 1, 20, 20));
        centre.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JButton ajouterArticle = new JButton("➕ Ajouter un article");
        JButton supprimerArticle = new JButton("🗑 Supprimer un article");
        JButton modifierArticle = new JButton("✏️ Modifier un article");
        JButton statistiques = new JButton("📊 Voir les statistiques");

        JButton ajouterMarque = new JButton("🏷 Ajouter une marque");
        JButton supprimerMarque = new JButton("❌ Supprimer une marque");

        ajouterArticle.addActionListener(e -> navigation.afficherAjoutProduit());
        supprimerArticle.addActionListener(e -> navigation.afficherSuppressionProduit());
        modifierArticle.addActionListener(e -> navigation.afficherModificationProduit());
        statistiques.addActionListener(e -> navigation.afficherStatistiques());

        ajouterMarque.addActionListener(e -> navigation.afficherAjoutMarque());
        supprimerMarque.addActionListener(e -> navigation.afficherSuppressionMarque());

        centre.add(ajouterArticle);
        centre.add(modifierArticle);
        centre.add(supprimerArticle);
        centre.add(ajouterMarque);
        centre.add(supprimerMarque);
        centre.add(statistiques);

        add(centre, BorderLayout.CENTER);

        JButton retour = new JButton("← Déconnexion");
        retour.addActionListener(e -> navigation.afficherConnexion());
        add(retour, BorderLayout.SOUTH);
    }
}
