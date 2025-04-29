package com.example.projet.modele;

public class ArticlePanier {

    private Article article;
    private int quantite;

    public ArticlePanier(Article article, int quantite) {
        this.article = article;
        this.quantite = quantite;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getTotal() {
        // Si le prix de gros est défini et que la quantité dépasse le seuil, on applique la remise
        if (article.getPrixGros() != null && article.getQuantiteGros() != null) {
            int qGros = article.getQuantiteGros();
            if (quantite >= qGros) {
                int lots = quantite / qGros;
                int reste = quantite % qGros;
                return lots * article.getPrixGros() + reste * article.getPrixUnitaire();
            }
        }
        return quantite * article.getPrixUnitaire();
    }
}
