package com.example.projet.controller;

import com.example.projet.dao.MarqueDAO;
import com.example.projet.modele.Marque;

import java.util.List;

public class MarqueController {
    private final MarqueDAO marqueDAO = new MarqueDAO();

    public boolean ajouterMarque(String nom) {
        return marqueDAO.create(nom);
    }

    public boolean supprimerMarque(int id) {
        return marqueDAO.deleteById(id);
    }

    public List<Marque> getToutesLesMarques() {
        return marqueDAO.findAll();
    }
}
