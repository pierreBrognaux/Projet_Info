package com.example.projet.controller;

import com.example.projet.dao.ClientDAO;
import com.example.projet.modele.Client;

public class UtilisateurController {

    private Client utilisateurActif;

    public boolean seConnecter(String email, String motDePasse) {
        ClientDAO clientDAO = new ClientDAO();
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
