package com.example.projet.modele;

public class CommandeArticle {
    private int id;
    private int idCommande;
    private int idArticle;
    private int quantite;
    private String note;

    public CommandeArticle() {
        // Constructeur vide pour initialisation
    }

    public CommandeArticle(int id, int idCommande, int idArticle, int quantite, String note) {
        this.id = id;
        this.idCommande = idCommande;
        this.idArticle = idArticle;
        this.quantite = quantite;
        this.note = note;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getNote() {
        return note;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
