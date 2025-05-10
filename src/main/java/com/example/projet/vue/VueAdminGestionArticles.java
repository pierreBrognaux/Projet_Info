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

        JPanel centre = new JPanel(new GridLayout(4, 1, 20, 20));
        centre.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton ajouter = new JButton("➕ Ajouter un produit");
        JButton supprimer = new JButton("🗑 Supprimer un produit");
        JButton modifier = new JButton("✏️ Modifier un produit");
        JButton statistiques = new JButton("📊 Voir les statistiques de vente");

        ajouter.addActionListener(e -> navigation.afficherAjoutProduit());
        supprimer.addActionListener(e -> navigation.afficherSuppressionProduit());
        modifier.addActionListener(e -> navigation.afficherModificationProduit());
        statistiques.addActionListener(e -> navigation.afficherStatistiques());

        centre.add(ajouter);
        centre.add(modifier);
        centre.add(supprimer);
        centre.add(statistiques);

        add(centre, BorderLayout.CENTER);

        JButton retour = new JButton("← Retour à la connexion");
        retour.addActionListener(e -> navigation.afficherConnexion());
        add(retour, BorderLayout.SOUTH);
    }
}
