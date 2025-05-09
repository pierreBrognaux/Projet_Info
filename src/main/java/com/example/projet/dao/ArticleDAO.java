package com.example.projet.dao;

import com.example.projet.modele.Article;
import java.sql.*;
import java.util.*;

public class ArticleDAO {
    private Connection conn = ConnexionBD.getConnexion();

    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>();
        try {
            String sql = "SELECT * FROM article";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                articles.add(new Article(
                        rs.getInt("id_article"),
                        rs.getString("nom_article"),
                        rs.getDouble("prix_unitaire"),
                        rs.getObject("prix_gros", Double.class),
                        rs.getObject("quantite_gros", Integer.class),
                        rs.getInt("id_marque")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }
}