package com.example.projet.vue;

import com.example.projet.Navigation;
import com.example.projet.controller.ClientController;
import com.example.projet.controller.CommandeController;
import com.example.projet.modele.Commande;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VueHistoriqueCommandes extends JPanel {

    public VueHistoriqueCommandes(Navigation navigation, ClientController clientController, CommandeController commandeController) {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Historique de vos commandes", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setOpaque(true);
        titre.setBackground(new Color(24, 64, 55));
        titre.setForeground(Color.WHITE);
        add(titre, BorderLayout.NORTH);

        JPanel listePanel = new JPanel();
        listePanel.setLayout(new BoxLayout(listePanel, BoxLayout.Y_AXIS));

        List<Commande> commandes = commandeController.getCommandesPourClient(clientController.getClientActif());

        if (commandes.isEmpty()) {
            listePanel.add(new JLabel("Vous n'avez encore passé aucune commande."));
        } else {
            for (Commande c : commandes) {
                JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT));
                ligne.add(new JLabel("Commande #" + c.getId()));
                ligne.add(new JLabel(" | Date : " + c.getDateCommande()));

                JButton details = new JButton("📋 Détails");
                details.addActionListener(e -> navigation.afficherDetailsCommande(c.getId()));
                ligne.add(details);
                listePanel.add(ligne);
            }

        }

        JScrollPane scroll = new JScrollPane(listePanel);
        add(scroll, BorderLayout.CENTER);

        JButton retour = new JButton("← Retour");
        retour.addActionListener(e -> navigation.afficherCatalogue());
        add(retour, BorderLayout.SOUTH);
    }
}
