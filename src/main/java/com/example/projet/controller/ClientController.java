package com.example.projet.controller;

import com.example.projet.dao.ClientDAO;
import com.example.projet.modele.Article;
import com.example.projet.modele.ArticlePanier;
import com.example.projet.modele.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientController {

    private Client clientActif;
    private final List<ArticlePanier> panier;

    public ClientController() {
        this.panier = new ArrayList<>();
    }

    // --- Gestion du client connecté ---
    public void setClientActif(Client client) {
        this.clientActif = client;
    }

    public Client getClientActif() {
        return clientActif;
    }

    public boolean estConnecte() {
        return clientActif != null;
    }

    // --- Gestion du panier ---
    public void ajouterAuPanier(Article article, int quantite) {
        for (ArticlePanier item : panier) {
            if (item.getArticle().getId() == article.getId()) {
                item.setQuantite(item.getQuantite() + quantite); // Ajout à la quantité
                return;
            }
        }
        panier.add(new ArticlePanier(article, quantite));
    }

    public void supprimerDuPanier(int idArticle) {
        panier.removeIf(item -> item.getArticle().getId() == idArticle);
    }

    public List<ArticlePanier> getPanier() {
        return panier;
    }

    public void viderPanier() {
        panier.clear();
    }

    public double getTotalPanier() {
        return panier.stream().mapToDouble(ArticlePanier::getTotal).sum();
    }

    // --- Enregistrement du client si nécessaire ---
    public boolean enregistrerClient(Client client) {
        ClientDAO dao = new ClientDAO();
        return dao.create(client);
    }

    public void modifierQuantitePanier(int idArticle, int quantite) {
        for (ArticlePanier ap : panier) {
            if (ap.getArticle().getId() == idArticle) {
                ap.setQuantite(quantite);
                break;
            }
        }
    }
}
