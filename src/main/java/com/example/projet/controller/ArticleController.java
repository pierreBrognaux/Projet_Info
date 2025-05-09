package com.example.projet.controller;

import com.example.projet.dao.ArticleDAO;
import com.example.projet.modele.Article;

import java.util.List;

public class ArticleController {

    private final ArticleDAO articleDAO;

    public ArticleController() {
        this.articleDAO = new ArticleDAO();
    }

    // 🔁 Charge tous les articles depuis la base de données
    public List<Article> getTousLesArticles() {
        return articleDAO.findAll();
    }

    // Tu peux ajouter ici : recherche par nom, filtre par prix ou marque...
}
