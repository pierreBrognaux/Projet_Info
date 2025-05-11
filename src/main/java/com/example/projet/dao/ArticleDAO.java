package com.example.projet.dao;

import com.example.projet.modele.Article;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.*;

public class ArticleDAO {
    private Connection conn = ConnexionBD.getConnexion();

    public List<Article> findAll() {
        List<Article> liste = new ArrayList<>();
        try {
            String sql = "SELECT * FROM article";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("id_article"),
                        rs.getString("nom_article"),
                        rs.getDouble("prix_unitaire"),
                        rs.getObject("prix_gros") != null ? rs.getDouble("prix_gros") : null,
                        rs.getObject("quantite_gros") != null ? rs.getInt("quantite_gros") : null,
                        rs.getInt("id_marque"),
                        rs.getString("image_path") // ✅ récupération du chemin de l'image
                );
                liste.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }


    public boolean create(Article article, File imageFile) {
        try {
            // Définir le chemin de l'image à enregistrer dans la BDD
            String imagePath = (imageFile != null) ? "images/" + imageFile.getName() : null;

            String sql = "INSERT INTO article (nom_article, prix_unitaire, prix_gros, quantite_gros, id_marque, image_path) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, article.getNom());
            ps.setDouble(2, article.getPrixUnitaire());

            if (article.getPrixGros() != null) {
                ps.setDouble(3, article.getPrixGros());
            } else {
                ps.setNull(3, Types.DOUBLE);
            }

            if (article.getQuantiteGros() != null) {
                ps.setInt(4, article.getQuantiteGros());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setInt(5, article.getIdMarque());

            if (imagePath != null) {
                ps.setString(6, imagePath);
            } else {
                ps.setNull(6, Types.VARCHAR);
            }

            ps.executeUpdate();

            // Enregistrer physiquement l'image sur le disque
            if (imageFile != null) {
                File dest = new File("images/" + imageFile.getName());
                dest.getParentFile().mkdirs(); // crée le dossier s’il n’existe pas
                Files.copy(imageFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(int idArticle) {
        try {
            String sql = "DELETE FROM article WHERE id_article = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idArticle);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Article> findByMarque(int idMarque) {
        List<Article> articles = new ArrayList<>();
        try {
            String sql = "SELECT * FROM article WHERE id_marque = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idMarque);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articles.add(new Article(
                        rs.getInt("id_article"),
                        rs.getString("nom_article"),
                        rs.getDouble("prix_unitaire"),
                        rs.getObject("prix_gros") != null ? rs.getDouble("prix_gros") : null,
                        rs.getObject("quantite_gros") != null ? rs.getInt("quantite_gros") : null,
                        rs.getInt("id_marque"),
                        rs.getString("image_path")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public boolean update(Article a) {
        try {
            String sql = "UPDATE article SET nom_article = ?, prix_unitaire = ?, prix_gros = ?, quantite_gros = ?, image_path = ? WHERE id_article = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, a.getNom());
            ps.setDouble(2, a.getPrixUnitaire());
            if (a.getPrixGros() != null) ps.setDouble(3, a.getPrixGros()); else ps.setNull(3, Types.DECIMAL);
            if (a.getQuantiteGros() != null) ps.setInt(4, a.getQuantiteGros()); else ps.setNull(4, Types.INTEGER);
            ps.setString(5, a.getImagePath());
            ps.setInt(6, a.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Article find(int id) {
        try {
            String sql = "SELECT * FROM article WHERE id_article = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Article(
                        rs.getInt("id_article"),
                        rs.getString("nom_article"),
                        rs.getDouble("prix_unitaire"),
                        rs.getObject("prix_gros") != null ? rs.getDouble("prix_gros") : null,
                        rs.getObject("quantite_gros") != null ? rs.getInt("quantite_gros") : null,
                        rs.getInt("id_marque"),
                        rs.getString("image_path")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}