package com.example.projet;

import com.example.projet.controller.*;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UtilisateurController utilisateurController = new UtilisateurController();
            ClientController clientController = new ClientController();
            ArticleController articleController = new ArticleController();
            CommandeController commandeController = new CommandeController();
            MarqueController marqueController = new MarqueController();
            StatistiquesController statistiquesController = new StatistiquesController();

            AppFrame appFrame = new AppFrame(null); // temporaire
            Navigation navigation = new Navigation(appFrame, utilisateurController, clientController, articleController, commandeController, marqueController, statistiquesController);
            appFrame.setVisible(true); // si pas déjà fait dans le constructeur
            navigation.afficherConnexion(); // vue par défaut
        });
    }
}
