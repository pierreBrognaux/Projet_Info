package com.example.projet.vue;

import com.example.projet.Navigation;
import com.example.projet.controller.ClientController;
import com.example.projet.controller.CommandeController;
import com.example.projet.modele.Article;
import com.example.projet.modele.ArticlePanier;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class VuePanier extends JPanel {

    public VuePanier(Navigation navigation, ClientController clientController, CommandeController commandeController) {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("🛒 Votre Panier", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        titre.setOpaque(true);
        titre.setBackground(new Color(24, 64, 55));
        titre.setForeground(Color.WHITE);
        add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));

        List<ArticlePanier> panier = clientController.getPanier();
        double total = 0.0;

        for (ArticlePanier item : panier) {
            Article article = item.getArticle();
            int quantite = item.getQuantite();

            JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT));
            ligne.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            ligne.setMaximumSize(new Dimension(800, 100));

            // --- Image
            JLabel imageLabel;
            if (article.getImagePath() != null && new File(article.getImagePath()).exists()) {
                ImageIcon icon = new ImageIcon(article.getImagePath());
                Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(scaled));
            } else {
                imageLabel = new JLabel("[Image]");
            }
            ligne.add(imageLabel);

            // --- Infos article
            String nom = article.getNom();
            int lot = (article.getQuantiteGros() != null) ? quantite / article.getQuantiteGros() : 0;
            int reste = (article.getQuantiteGros() != null) ? quantite % article.getQuantiteGros() : quantite;
            double prixTotal = lot * (article.getPrixGros() != null ? article.getPrixGros() : 0.0) + reste * article.getPrixUnitaire();
            total += prixTotal;

            JPanel infos = new JPanel(new GridLayout(3, 1));
            infos.add(new JLabel(nom));
            infos.add(new JLabel("Quantité : " + quantite));
            infos.add(new JLabel("Total : " + String.format("%.2f", prixTotal) +
                    " €  (Lots : " + lot + " × " + article.getPrixGros() + " € + " +
                    reste + " × " + article.getPrixUnitaire() + " €)"));
            ligne.add(infos);

            // --- Modification
            JPanel actions = new JPanel(new GridLayout(2, 1, 5, 5));
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(quantite, 1, 100, 1));
            JButton maj = new JButton("✔ Modifier");
            JButton supprimer = new JButton("🗑 Supprimer");

            maj.addActionListener(e -> {
                int nouvelle = (int) spinner.getValue();
                clientController.modifierQuantitePanier(article.getId(), nouvelle);
                navigation.afficherPanier();
            });

            supprimer.addActionListener(e -> {
                clientController.supprimerDuPanier(article.getId());
                navigation.afficherPanier();
            });

            actions.add(spinner);
            JPanel boutons = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            boutons.add(maj);
            boutons.add(supprimer);
            actions.add(boutons);

            ligne.add(actions);
            centre.add(ligne);
        }

        if (panier.isEmpty()) {
            centre.add(new JLabel("Votre panier est vide."));
        }

        JScrollPane scroll = new JScrollPane(centre);
        add(scroll, BorderLayout.CENTER);

        // --- Bas : Total + Valider
        JPanel bas = new JPanel(new BorderLayout());
        JLabel totalLabel = new JLabel("Total du panier : " + String.format("%.2f", total) + " €");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        bas.add(totalLabel, BorderLayout.WEST);

        JButton commander = new JButton("Valider la commande");
        commander.setBackground(new Color(46, 125, 50));
        commander.setForeground(Color.WHITE);
        commander.setFocusPainted(false);

        commander.addActionListener(e -> {
            if (panier.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Panier vide !");
                return;
            }
            boolean ok = commandeController.validerCommande(clientController.getClientActif(), panier);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Commande confirmée !");
                clientController.viderPanier();
                navigation.afficherCatalogue();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la commande.");
            }
        });

        bas.add(commander, BorderLayout.EAST);
        add(bas, BorderLayout.SOUTH);
    }
}
