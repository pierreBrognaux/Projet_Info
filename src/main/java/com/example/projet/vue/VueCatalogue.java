package com.example.projet.vue;

import com.example.projet.Navigation;
import com.example.projet.controller.ArticleController;
import com.example.projet.controller.ClientController;
import com.example.projet.modele.Article;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VueCatalogue extends JPanel {

    public VueCatalogue(Navigation navigation, ArticleController articleController, ClientController clientController) {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("NOM DU SITE", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setOpaque(true);
        titre.setBackground(new Color(24, 64, 55));
        titre.setForeground(Color.WHITE);
        add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));

        List<Article> articles = articleController.getTousLesArticles();
        for (Article a : articles) {
            JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT));
            ligne.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            ligne.setMaximumSize(new Dimension(700, 60));

            JLabel nom = new JLabel(a.getNom() + " - " + a.getPrixUnitaire() + " €");
            JComboBox<Integer> quantites = new JComboBox<>(new Integer[]{1, 2, 3, 5, 10});
            JButton ajouter = new JButton("Ajouter au panier");

            ajouter.addActionListener(e -> {
                int qte = (int) quantites.getSelectedItem();
                clientController.ajouterAuPanier(a, qte);
                JOptionPane.showMessageDialog(this, "Ajouté au panier !");
            });

            ligne.add(nom);
            ligne.add(quantites);
            ligne.add(ajouter);
            centre.add(ligne);
        }

        JScrollPane scroll = new JScrollPane(centre);
        add(scroll, BorderLayout.CENTER);

        JPanel basPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton voirHistorique = new JButton("📄 Historique de mes commandes");
        voirHistorique.addActionListener(e -> navigation.afficherHistoriqueCommandes());

        JButton voirPanier = new JButton("🛒 Voir mon panier");
        voirPanier.addActionListener(e -> navigation.afficherPanier());

        basPanel.add(voirHistorique);
        basPanel.add(voirPanier);

        add(basPanel, BorderLayout.SOUTH);

    }
}
