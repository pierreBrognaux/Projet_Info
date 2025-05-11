package com.example.projet.controller;

import com.example.projet.dao.StatistiqueVenteDAO;

import java.util.Map;

public class StatistiquesController {

    private final StatistiqueVenteDAO statistiqueDAO = new StatistiqueVenteDAO();

    public Map<String, Integer> getQuantitesCommandeesParMarque() {
        return statistiqueDAO.getRepartitionCommandesParMarque();
    }

    public double getPrixPanierMoyen() {
        return statistiqueDAO.getPrixMoyenPanier();
    }

    public double getQuantitePanierMoyenne() {
        return statistiqueDAO.getQuantiteMoyennePanier();
    }
}
