// --- FenetrePrincipale.java ---
package com.example.projet.vue;

import com.example.projet.controller.ControleurPrincipal;
import com.example.projet.modele.Article;
import com.example.projet.modele.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FenetrePrincipale extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton; // 🔁 Ajout bouton création de compte
    private ControleurPrincipal controleur;

    public FenetrePrincipale() {
        setTitle("Shopping App");
        setSize(800, 600); // 🔁 Même taille que le catalogue
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        controleur = new ControleurPrincipal();

        // --- Zone de connexion (mise à jour layout)
        JPanel loginPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        loginPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        loginPanel.add(emailField);

        loginPanel.add(new JLabel("Mot de passe:"));
        passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        loginButton = new JButton("Connexion");
        loginPanel.add(loginButton);

        registerButton = new JButton("Créer un compte"); // 🔁 Nouveau bouton
        loginPanel.add(registerButton);

        add(loginPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String mdp = new String(passwordField.getPassword());
            if (controleur.loginClient(email, mdp) != null) {
                afficherCatalogue();
            } else {
                JOptionPane.showMessageDialog(this, "Échec de la connexion");
            }
        });

        registerButton.addActionListener(e -> afficherFormulaireInscription()); // 🔁 Action création de compte

        setVisible(true);
    }

    // 🔁 Nouvelle méthode : formulaire de création de compte
    private void afficherFormulaireInscription() {
        JDialog dialog = new JDialog(this, "Créer un compte", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(6, 2, 10, 10));

        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"ancien", "nouveau"});

        dialog.add(new JLabel("Nom:"));
        dialog.add(nomField);
        dialog.add(new JLabel("Prénom:"));
        dialog.add(prenomField);
        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);
        dialog.add(new JLabel("Mot de passe:"));
        dialog.add(passwordField);
        dialog.add(new JLabel("Type client:"));
        dialog.add(typeCombo);

        JButton valider = new JButton("S'inscrire");
        dialog.add(valider);

        valider.addActionListener(e -> {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String mdp = new String(passwordField.getPassword());
            String type = (String) typeCombo.getSelectedItem();

            Client nouveau = new Client(0, nom, prenom, email, mdp, type);
            if (controleur.inscrireClient(nouveau)) {
                JOptionPane.showMessageDialog(dialog, "Compte créé avec succès !");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Erreur lors de l'inscription.");
            }
        });

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void afficherCatalogue() {
        getContentPane().removeAll();
        setSize(800, 600);

        JLabel titre = new JLabel("NOM DU SITE", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(Color.WHITE);
        titre.setOpaque(true);
        titre.setBackground(new Color(24, 64, 55));
        add(titre, BorderLayout.NORTH);

        JPanel filtrePanel = new JPanel(new FlowLayout());
        filtrePanel.setBorder(BorderFactory.createTitledBorder("Filtre de recherche"));
        filtrePanel.add(new JComboBox<>(new String[]{"Catégorie"}));
        filtrePanel.add(new JComboBox<>(new String[]{"Marque"}));
        filtrePanel.add(new JTextField("Prix min", 6));
        filtrePanel.add(new JTextField("Prix max", 6));
        filtrePanel.add(new JButton("RECHERCHER"));
        add(filtrePanel, BorderLayout.CENTER);

        JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));

        List<Article> articles = controleur.chargerArticles();
        for (Article a : articles) {
            JPanel articlePanel = new JPanel();
            articlePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            articlePanel.setLayout(new GridLayout(2, 2));
            articlePanel.setMaximumSize(new Dimension(700, 80));

            articlePanel.add(new JLabel("[Image Produit]"));
            articlePanel.add(new JLabel(a.getNom()));

            JPanel qtePanel = new JPanel();
            qtePanel.add(new JLabel("Quantité "));
            qtePanel.add(new JComboBox<>(new String[]{"1", "2", "3"}));
            articlePanel.add(qtePanel);

            JPanel boutonPanel = new JPanel();
            boutonPanel.setLayout(new BorderLayout());
            boutonPanel.add(new JLabel(a.getPrixUnitaire() + " €"), BorderLayout.WEST);
            boutonPanel.add(new JButton("AJOUTER AU PANIER"), BorderLayout.EAST);
            articlePanel.add(boutonPanel);

            centre.add(articlePanel);
        }

        JScrollPane scroll = new JScrollPane(centre);
        add(scroll, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
}