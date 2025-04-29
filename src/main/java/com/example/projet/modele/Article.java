package com.example.projet.modele;

public class Article {
    private int id;
    private String nom;
    private double prixUnitaire;
    private Double prixGros;
    private Integer quantiteGros;
    private int idMarque;

    public Article(int id, String nom, double prixUnitaire, Double prixGros, Integer quantiteGros, int idMarque) {
        this.id = id;
        this.nom = nom;
        this.prixUnitaire = prixUnitaire;
        this.prixGros = prixGros;
        this.quantiteGros = quantiteGros;
        this.idMarque = idMarque;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public double getPrixUnitaire() { return prixUnitaire; }
    public Double getPrixGros() { return prixGros; }
    public Integer getQuantiteGros() { return quantiteGros; }
    public int getIdMarque() { return idMarque; }
}
