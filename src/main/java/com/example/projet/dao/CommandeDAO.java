package com.example.projet.dao;

import com.example.projet.modele.Commande;
import com.example.projet.modele.ArticlePanier;
import com.example.projet.modele.CommandeArticle;
import java.sql.*;
import java.util.*;

public class CommandeDAO implements DAO<Commande> {
    private Connection conn = ConnexionBD.getConnexion();

    @Override
    public boolean create(Commande commande) {
        try {
            String sql = "INSERT INTO commande (id_client, date_commande) VALUES (?, NOW())";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, commande.getIdClient());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int createAndReturnId(Commande commande) {
        try {
            String sql = "INSERT INTO commande (id_client, date_commande) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, commande.getIdClient());
            ps.setTimestamp(2, new Timestamp(commande.getDateCommande().getTime()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // retourne l'id_commande généré
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean enregistrerArticles(int idCommande, List<ArticlePanier> panier) {
        try {
            String sql = "INSERT INTO commande_article (id_commande, id_article, quantite, note) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (ArticlePanier item : panier) {
                ps.setInt(1, idCommande);
                ps.setInt(2, item.getArticle().getId());
                ps.setInt(3, item.getQuantite());
                ps.setString(4, ""); // note vide pour l'instant
                ps.addBatch();
            }

            ps.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Commande> findByClientId(int clientId) {
        List<Commande> liste = new ArrayList<>();
        try {
            String sql = "SELECT * FROM commande WHERE id_client = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(new Commande(
                        rs.getInt("id_commande"),
                        rs.getInt("id_client"),
                        rs.getTimestamp("date_commande")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<String> getArticlesDescriptionPourCommande(int idCommande) {
        List<String> result = new ArrayList<>();
        try {
            String sql = """
            SELECT a.nom_article, ca.quantite
            FROM commande_article ca
            JOIN article a ON ca.id_article = a.id_article
            WHERE ca.id_commande = ?
        """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idCommande);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("nom_article");
                int quantite = rs.getInt("quantite");
                result.add(nom + " (x" + quantite + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<CommandeArticle> getCommandeArticlesByCommande(int idCommande) {
        List<CommandeArticle> liste = new ArrayList<>();
        try {
            String sql = "SELECT * FROM commande_article WHERE id_commande = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idCommande);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CommandeArticle ca = new CommandeArticle();
                ca.setId(rs.getInt("id_commande_article"));
                ca.setIdCommande(rs.getInt("id_commande"));
                ca.setIdArticle(rs.getInt("id_article"));
                ca.setQuantite(rs.getInt("quantite"));
                ca.setNote(rs.getString("note"));
                liste.add(ca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }


    @Override
    public Commande find(int id) { return null; }
    @Override
    public List<Commande> findAll() { return null; }
    @Override
    public boolean update(Commande obj) { return false; }
    @Override
    public boolean delete(Commande obj) { return false; }
}
