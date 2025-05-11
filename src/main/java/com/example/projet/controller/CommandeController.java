package com.example.projet.controller;

import com.example.projet.dao.ArticleDAO;
import com.example.projet.dao.CommandeDAO;
import com.example.projet.modele.Article;
import com.example.projet.modele.ArticlePanier;
import com.example.projet.modele.Client;
import com.example.projet.modele.Commande;
import com.example.projet.modele.CommandeArticle;

import java.util.List;

public class CommandeController {

    private final CommandeDAO commandeDAO;
    private final ArticleDAO articleDAO;

    public CommandeController() {
        this.commandeDAO = new CommandeDAO();
        this.articleDAO = new ArticleDAO();
    }

    /**
     * Enregistre une commande à partir du panier d’un client.
     * @param client client connecté
     * @param panier liste des articles avec quantités
     * @return true si succès
     */
    public boolean validerCommande(Client client, List<ArticlePanier> panier) {
        if (client == null || panier.isEmpty()) {
            return false;
        }

        Commande commande = new Commande(client.getId());

        // Appel DAO : insérer commande principale
        int idCommande = commandeDAO.createAndReturnId(commande);

        if (idCommande == -1) return false;

        // Insérer les articles de la commande
        return commandeDAO.enregistrerArticles(idCommande, panier);
    }

    public List<String> getDetailsPourCommande(int idCommande) {
        return commandeDAO.getArticlesDescriptionPourCommande(idCommande);
    }

    public List<Commande> getCommandesPourClient(Client client) {
        return commandeDAO.findByClientId(client.getId());
    }

    // ✅ Nouvelle méthode : liste des articles pour une commande
    public List<CommandeArticle> getCommandeArticlesByCommande(int idCommande) {
        return commandeDAO.getCommandeArticlesByCommande(idCommande);
    }

    // ✅ Nouvelle méthode : récupérer un article par son ID
    public Article getArticleById(int idArticle) {
        return articleDAO.find(idArticle);
    }
}
