package com.example.projet.controller;

import com.example.projet.dao.ClientDAO;
import com.example.projet.modele.Client;

public class UtilisateurController {
    private final ClientDAO clientDAO = new ClientDAO();
    private Client utilisateurActif;

    public boolean seConnecter(String email, String motDePasse) {
        Client client = clientDAO.findByEmailAndPassword(email, motDePasse);
        if (client != null) {
            utilisateurActif = client;
            return true;
        }
        return false;
    }

    public boolean sInscrire(Client nouveauClient) {
        ClientDAO clientDAO = new ClientDAO();
        return clientDAO.create(nouveauClient);
    }

    public boolean isAdministrateur() {
        return clientDAO.isAdministrateur();
    }

    public Client getUtilisateurActif() {
        return utilisateurActif;
    }

    public boolean estConnecte() {
        return utilisateurActif != null;
    }

    public void seDeconnecter() {
        utilisateurActif = null;
    }
}
