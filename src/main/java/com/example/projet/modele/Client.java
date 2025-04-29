package com.example.projet.modele;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String typeClient;

    public Client(int id, String nom, String prenom, String email, String motDePasse, String typeClient) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.typeClient = typeClient;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getMotDePasse() { return motDePasse; }
    public String getTypeClient() { return typeClient; }
}