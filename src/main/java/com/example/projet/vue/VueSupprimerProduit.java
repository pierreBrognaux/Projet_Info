package com.example.projet.vue;

import com.example.projet.Navigation;
import com.example.projet.controller.ArticleController;
import com.example.projet.modele.Article;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VueSupprimerProduit extends JPanel {

    public VueSupprimerProduit(Navigation navigation, ArticleController articleController) {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Supprimer un article", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setOpaque(true);
        titre.setBackground(new Color(120, 20, 20));
        titre.setForeground(Color.WHITE);
        add(titre, BorderLayout.NORTH);

        JPanel listePanel = new JPanel();
        listePanel.setLayout(new BoxLayout(listePanel, BoxLayout.Y_AXIS));

        List<Article> articles = articleController.getTousLesArticles();

        if (articles.isEmpty()) {
            listePanel.add(new JLabel("Aucun article à afficher."));
        } else {
            for (Article a : articles) {
                JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT));
                ligne.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                ligne.setMaximumSize(new Dimension(700, 60));

                JLabel label = new JLabel(a.getNom() + " - " + a.getPrixUnitaire() + " €");
                JButton supprimer = new JButton("🗑 Supprimer");

                supprimer.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Voulez-vous vraiment supprimer \"" + a.getNom() + "\" ?",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean ok = articleController.supprimerArticle(a.getId());
                        if (ok) {
                            JOptionPane.showMessageDialog(this, "Article supprimé !");
                            navigation.afficherSuppressionProduit(); // recharge la page
                        } else {
                            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
                        }
                    }
                });

                ligne.add(label);
                ligne.add(supprimer);
                listePanel.add(ligne);
            }
        }

        JScrollPane scroll = new JScrollPane(listePanel);
        add(scroll, BorderLayout.CENTER);

        JButton retour = new JButton("← Retour");
        retour.addActionListener(e -> navigation.afficherMenuAdmin());
        add(retour, BorderLayout.SOUTH);
    }
}
