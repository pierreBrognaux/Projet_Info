package com.example.projet.dao;

import com.example.projet.modele.Marque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarqueDAO implements DAO<Marque> {

    private final Connection conn = ConnexionBD.getConnexion();

    @Override
    public boolean create(Marque marque) {
        return create(marque.getNom());
    }

    // ➕ Créer une marque à partir d’un nom
    public boolean create(String nom) {
        try {
            String sql = "INSERT INTO marque (nom_marque) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nom);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur ajout marque : " + e.getMessage());
            return false;
        }
    }

    // 🗑 Supprimer une marque par son ID
    public boolean deleteById(int id) {
        try {
            String sql = "DELETE FROM marque WHERE id_marque = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Suppression échouée (marque liée ?) : " + e.getMessage());
            return false;
        }
    }

    // 📥 Récupérer toutes les marques
    @Override
    public List<Marque> findAll() {
        List<Marque> marques = new ArrayList<>();
        try {
            String sql = "SELECT * FROM marque";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                marques.add(new Marque(
                        rs.getInt("id_marque"),
                        rs.getString("nom_marque")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marques;
    }

    // 🚫 Non utilisé dans ce projet (mais nécessaires pour interface)
    @Override public Marque find(int id) { return null; }
    @Override public boolean update(Marque marque) { return false; }
    @Override public boolean delete(Marque marque) { return deleteById(marque.getId()); }
}
