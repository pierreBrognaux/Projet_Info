package com.example.projet.vue;

import com.example.projet.Navigation;
import com.example.projet.controller.ArticleController;
import com.example.projet.controller.ClientController;
import com.example.projet.modele.Article;
import com.example.projet.modele.Marque;
import java.io.File;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VueCatalogue extends JPanel {

    public VueCatalogue(Navigation navigation, ArticleController articleController, ClientController clientController) {
        setLayout(new BorderLayout());

        // --- Filtre par marque ---
        List<Marque> marques = articleController.getToutesLesMarques();
        marques.add(0, new Marque(0, "Toutes les marques"));
        JComboBox<Marque> filtreMarque = new JComboBox<>(marques.toArray(new Marque[0]));
        JButton boutonFiltrer = new JButton("Filtrer");

        JPanel filtrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtrePanel.add(new JLabel("Filtrer par marque :"));
        filtrePanel.add(filtreMarque);
        filtrePanel.add(boutonFiltrer);

        add(filtrePanel, BorderLayout.BEFORE_FIRST_LINE); // 🔁 au-dessus du titre


        JLabel titre = new JLabel("NOM DU SITE", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setOpaque(true);
        titre.setBackground(new Color(24, 64, 55));
        titre.setForeground(Color.WHITE);
        add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));

        List<Article> articles = articleController.getTousLesArticles();
        afficherArticles(centre, articles, clientController);


        JScrollPane scroll = new JScrollPane(centre);
        add(scroll, BorderLayout.CENTER);

        boutonFiltrer.addActionListener(e -> {
            Marque m = (Marque) filtreMarque.getSelectedItem();
            List<Article> filtres;
            if (m.getId() == 0) {
                filtres = articleController.getTousLesArticles();
            } else {
                filtres = articleController.getArticlesParMarque(m.getId());
            }
            afficherArticles(centre, filtres, clientController);
        });


        JPanel basPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton deconnexion = new JButton("🚪 Déconnexion");
        deconnexion.addActionListener(e -> {
            clientController.setClientActif(null); // ⛔ Déconnecter le client
            navigation.afficherConnexion();       // 🔁 Retour à l'écran de login
        });

        JButton voirHistorique = new JButton("📄 Historique de mes commandes");
        voirHistorique.addActionListener(e -> navigation.afficherHistoriqueCommandes());

        JButton voirPanier = new JButton("🛒 Voir mon panier");
        voirPanier.addActionListener(e -> navigation.afficherPanier());

        basPanel.add(deconnexion);
        basPanel.add(voirHistorique);
        basPanel.add(voirPanier);


        add(basPanel, BorderLayout.SOUTH);

    }

    private void afficherArticles(JPanel container, List<Article> articles, ClientController clientController) {
        container.removeAll();

        for (Article a : articles) {
            JPanel ligne = new JPanel(new BorderLayout());
            ligne.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            ligne.setMaximumSize(new Dimension(700, 100));

            JLabel imageLabel;
            if (a.getImagePath() != null) {
                File imageFile = new File(a.getImagePath());
                if (imageFile.exists()) {
                    ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
                    Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    imageLabel = new JLabel(new ImageIcon(scaled));
                } else {
                    imageLabel = new JLabel("[Image introuvable]");
                }
            } else {
                imageLabel = new JLabel("[Pas d'image]");
            }
            ligne.add(imageLabel, BorderLayout.WEST);

            JPanel infos = new JPanel(new GridLayout(2, 1));
            infos.add(new JLabel(a.getNom()));
            infos.add(new JLabel(a.getPrixUnitaire() + " €"));
            ligne.add(infos, BorderLayout.CENTER);

            JPanel actions = new JPanel();
            JComboBox<Integer> quantites = new JComboBox<>(new Integer[]{1, 2, 3, 5, 10});
            JButton ajouter = new JButton("Ajouter au panier");

            ajouter.addActionListener(e -> {
                int qte = (int) quantites.getSelectedItem();
                clientController.ajouterAuPanier(a, qte);
                JOptionPane.showMessageDialog(this, "Ajouté au panier !");
            });

            actions.add(quantites);
            actions.add(ajouter);
            ligne.add(actions, BorderLayout.EAST);

            container.add(ligne);
        }

        container.revalidate();
        container.repaint();
    }

}
