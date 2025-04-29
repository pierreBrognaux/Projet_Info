package com.example.projet.modele;

import java.util.Date;

public class Commande {
    private int id;
    private int idClient;
    private Date dateCommande;

    public Commande(int id, int idClient, Date dateCommande) {
        this.id = id;
        this.idClient = idClient;
        this.dateCommande = dateCommande;
    }

    public Commande(int idClient) {
        this.idClient = idClient;
        this.dateCommande = new Date();
    }

    public int getId() { return id; }
    public int getIdClient() { return idClient; }
    public Date getDateCommande() { return dateCommande; }
}