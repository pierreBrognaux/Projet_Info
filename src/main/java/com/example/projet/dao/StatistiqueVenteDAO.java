package com.example.projet.dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class StatistiqueVenteDAO {

    private final Connection conn = ConnexionBD.getConnexion();

    public Map<String, Integer> getRepartitionCommandesParMarque() {
        Map<String, Integer> map = new HashMap<>();
        try {
            String sql = """
                SELECT m.nom_marque, SUM(ca.quantite) as total
                FROM commande_article ca
                JOIN article a ON ca.id_article = a.id_article
                JOIN marque m ON a.id_marque = m.id_marque
                GROUP BY m.nom_marque
            """;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                map.put(rs.getString("nom_marque"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public double getPrixMoyenPanier() {
        try {
            String sql = """
                SELECT AVG(s.total) as prix_moyen
                FROM (
                    SELECT SUM(ca.quantite * a.prix_unitaire) AS total
                    FROM commande_article ca
                    JOIN article a ON ca.id_article = a.id_article
                    GROUP BY ca.id_commande
                ) s
            """;
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) return rs.getDouble("prix_moyen");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public double getQuantiteMoyennePanier() {
        try {
            String sql = """
                SELECT AVG(s.total_qte) as qte_moyenne
                FROM (
                    SELECT SUM(ca.quantite) AS total_qte
                    FROM commande_article ca
                    GROUP BY ca.id_commande
                ) s
            """;
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) return rs.getDouble("qte_moyenne");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
