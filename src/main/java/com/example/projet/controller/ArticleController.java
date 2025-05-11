package com.example.projet.controller;

import com.example.projet.dao.ArticleDAO;
import com.example.projet.modele.Article;
import com.example.projet.modele.Marque;
import com.example.projet.dao.MarqueDAO;
import java.io.File;


import java.util.List;

public class ArticleController {

    private final ArticleDAO articleDAO;
    private final MarqueDAO marqueDAO;



    public ArticleController() {
        this.articleDAO = new ArticleDAO();
        this.marqueDAO = new MarqueDAO();
    }

    // 🔁 Charge tous les articles depuis la base de données
    public List<Article> getTousLesArticles() {
        return articleDAO.findAll();
    }

    public boolean ajouterArticle(Article article, File imageFile) {
        return articleDAO.create(article, imageFile);
    }

    public boolean supprimerArticle(int idArticle) {
        return articleDAO.deleteById(idArticle);
    }

    public List<Marque> getToutesLesMarques() {
        return marqueDAO.findAll();
    }

    public List<Article> getArticlesParMarque(int idMarque) {
        return articleDAO.findByMarque(idMarque);
    }

    public boolean modifierArticle(Article a) {
        return articleDAO.update(a);
    }

    // Tu peux ajouter ici : recherche par nom, filtre par prix ou marque...
}
