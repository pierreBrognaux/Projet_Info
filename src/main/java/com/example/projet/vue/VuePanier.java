package com.example.projet.vue;

import com.example.projet.Navigation;
import com.example.projet.controller.ClientController;
import com.example.projet.controller.CommandeController;
import com.example.projet.modele.ArticlePanier;
import com.example.projet.modele.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VuePanier extends JPanel {

    public VuePanier(Navigation navigation, ClientController clientController, CommandeController commandeController) {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Contenu de votre panier", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setOpaque(true);
        titre.setBackground(new Color(24, 64, 55));
        titre.setForeground(Color.WHITE);
        add(titre, BorderLayout.NORTH);

        JPanel articlesPanel = new JPanel();
        articlesPanel.setLayout(new BoxLayout(articlesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(articlesPanel);

        double total = 0.0;
        List<ArticlePanier> panier = clientController.getPanier();

        if (panier.isEmpty()) {
            articlesPanel.add(new JLabel("🛒 Votre panier est vide."));
        } else {
            for (ArticlePanier item : panier) {
                JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT));
                ligne.add(new JLabel("🛒 " + item.getArticle().getNom()));
                ligne.add(new JLabel(" | Quantité : " + item.getQuantite()));
                ligne.add(new JLabel(" | Prix unitaire : " + item.getArticle().getPrixUnitaire() + " €"));
                ligne.add(new JLabel(" | Total : " + String.format("%.2f", item.getTotal()) + " €"));
                total += item.getTotal();
                articlesPanel.add(ligne);
            }
        }

        add(scrollPane, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        JLabel totalLabel = new JLabel("Total : " + String.format("%.2f", total) + " €");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        footer.add(totalLabel, BorderLayout.WEST);

        JButton commander = new JButton("Valider la commande");
        commander.addActionListener(e -> {
            Client client = clientController.getClientActif();
            if (panier.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Votre panier est vide.");
                return;
            }
            boolean success = commandeController.validerCommande(client, panier);
            if (success) {
                JOptionPane.showMessageDialog(this, "Commande confirmée !");
                clientController.viderPanier();
                navigation.afficherCatalogue();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement de la commande.");
            }
        });

        JButton retour = new JButton("← Retour au catalogue");
        retour.addActionListener(e -> navigation.afficherCatalogue());

        JPanel boutons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        boutons.add(retour);
        boutons.add(commander);

        footer.add(boutons, BorderLayout.EAST);

        add(footer, BorderLayout.SOUTH);
    }
}
