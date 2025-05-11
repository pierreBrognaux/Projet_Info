package com.example.projet.dao;

import com.example.projet.modele.Client;

import java.sql.*;
import java.util.List;

public class ClientDAO implements DAO<Client> { // 🔁 Implémente bien l'interface DAO
    private Connection conn = ConnexionBD.getConnexion();

    private boolean administrateur = false;

    public boolean isAdministrateur() {
        return administrateur;
    }

    public Client findByEmailAndPassword(String email, String password) {
        try {
            // 🔍 1. Rechercher dans client
            String sqlClient = "SELECT * FROM client WHERE email = ? AND mot_de_passe = ?";
            PreparedStatement psClient = conn.prepareStatement(sqlClient);
            psClient.setString(1, email);
            psClient.setString(2, password);
            ResultSet rsClient = psClient.executeQuery();
            if (rsClient.next()) {
                administrateur = false;
                return new Client(
                        rsClient.getInt("id_client"),
                        rsClient.getString("nom"),
                        rsClient.getString("prenom"),
                        rsClient.getString("email"),
                        rsClient.getString("mot_de_passe"),
                        rsClient.getString("type_client")
                );
            }

            // 🔍 2. Rechercher dans administrateur
            String sqlAdmin = "SELECT * FROM administrateur WHERE email = ? AND mot_de_passe = ?";
            PreparedStatement psAdmin = conn.prepareStatement(sqlAdmin);
            psAdmin.setString(1, email);
            psAdmin.setString(2, password);
            ResultSet rsAdmin = psAdmin.executeQuery();
            if (rsAdmin.next()) {
                administrateur = true;
                // 🛑 On retourne un Client "virtuel" pour les administrateurs (ou tu peux créer une classe Admin)
                return new Client(
                        -1,
                        rsAdmin.getString("nom"),
                        "", // prénom vide
                        rsAdmin.getString("email"),
                        rsAdmin.getString("mot_de_passe"),
                        "admin"
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean create(Client client) {
        try {
            String sql = "INSERT INTO client (nom, prenom, email, mot_de_passe, type_client) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getMotDePasse());
            ps.setString(5, client.getTypeClient());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔁 Tu peux laisser ces méthodes vides ou les implémenter plus tard
    @Override public Client find(int id) { return null; }
    @Override public List<Client> findAll() { return null; }
    @Override public boolean update(Client obj) { return false; }
    @Override public boolean delete(Client obj) { return false; }
}
