package com.example.projet.vue;

import com.example.projet.Navigation;
import com.example.projet.controller.CommandeController;
import com.example.projet.modele.Article;
import com.example.projet.modele.CommandeArticle;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class VueDetailsCommande extends JPanel {

    public VueDetailsCommande(Navigation navigation, CommandeController commandeController, int idCommande) {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Détails de la commande #" + idCommande, SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setOpaque(true);
        titre.setBackground(new Color(24, 64, 55));
        titre.setForeground(Color.WHITE);
        add(titre, BorderLayout.NORTH);

        JPanel contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));

        List<CommandeArticle> articlesCommande = commandeController.getCommandeArticlesByCommande(idCommande);

        if (articlesCommande.isEmpty()) {
            contenu.add(new JLabel("Aucun article trouvé pour cette commande."));
        } else {
            for (CommandeArticle ca : articlesCommande) {
                Article article = commandeController.getArticleById(ca.getIdArticle());
                if (article == null) continue;

                JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
                ligne.setMaximumSize(new Dimension(900, 80));
                ligne.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

                // Image
                JLabel imageLabel;
                if (article.getImagePath() != null && new File(article.getImagePath()).exists()) {
                    ImageIcon icon = new ImageIcon(article.getImagePath());
                    Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                    imageLabel = new JLabel(new ImageIcon(scaled));
                } else {
                    imageLabel = new JLabel("[Image indisponible]");
                }
                ligne.add(imageLabel);

                // Détails
                String nom = article.getNom();
                int quantite = ca.getQuantite();

                String prixDetail;
                double total;
                if (article.getPrixGros() != null && article.getQuantiteGros() != null && quantite >= article.getQuantiteGros()) {
                    int lot = quantite / article.getQuantiteGros();
                    int reste = quantite % article.getQuantiteGros();
                    double prixGros = lot * article.getPrixGros();
                    double prixUnit = reste * article.getPrixUnitaire();
                    total = prixGros + prixUnit;
                    prixDetail = lot + "x" + article.getQuantiteGros() + " (gros à " + article.getPrixGros() + "€) + " +
                            reste + "x (unitaire à " + article.getPrixUnitaire() + "€)";
                } else {
                    total = quantite * article.getPrixUnitaire();
                    prixDetail = quantite + " x " + article.getPrixUnitaire() + "€ (prix unitaire)";
                }

                // Texte sur une ligne
                JLabel details = new JLabel("<html><b>" + nom + "</b> - Quantité : " + quantite +
                        "<br/>" + prixDetail + "<br/>💰 Total : <b>" + String.format("%.2f", total) + " €</b></html>");

                ligne.add(details);
                contenu.add(ligne);
            }
        }

        JScrollPane scroll = new JScrollPane(contenu);
        add(scroll, BorderLayout.CENTER);

        JButton retour = new JButton("← Retour à l’historique");
        retour.addActionListener(e -> navigation.afficherHistoriqueCommandes());
        JPanel bas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bas.add(retour);

        add(bas, BorderLayout.SOUTH);
    }
}
