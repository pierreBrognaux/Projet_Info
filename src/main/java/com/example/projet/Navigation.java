package com.example.projet;

import com.example.projet.controller.*;
import com.example.projet.vue.*;

public class Navigation {
    private final AppFrame appFrame;
    private final UtilisateurController utilisateurController;
    private final ClientController clientController;
    private final ArticleController articleController;
    private final CommandeController commandeController;
    private final MarqueController marqueController;
    private final StatistiquesController statistiquesController;

    public Navigation(AppFrame appFrame,
                      UtilisateurController utilisateurController,
                      ClientController clientController,
                      ArticleController articleController,
                      CommandeController commandeController,
                      MarqueController marqueController,
                      StatistiquesController statistiquesController) {
        this.appFrame = appFrame;
        this.utilisateurController = utilisateurController;
        this.clientController = clientController;
        this.articleController = articleController;
        this.commandeController = commandeController;
        this.marqueController = marqueController;
        this.statistiquesController = statistiquesController;
    }

    public void afficherConnexion() {
        appFrame.changerVue(new VueConnexion(this, utilisateurController, clientController));
    }

    public void afficherInscription() {
        appFrame.changerVue(new VueInscription(this, utilisateurController));
    }

    public void afficherCatalogue() {
        appFrame.changerVue(new VueCatalogue(this, articleController, clientController));
    }

    public void afficherPanier() {
        appFrame.changerVue(new VuePanier(this, clientController, commandeController));
    }

    public void afficherHistoriqueCommandes() {
        appFrame.changerVue(new VueHistoriqueCommandes(this, clientController, commandeController));
    }

    public void afficherDetailsCommande(int idCommande) {
        appFrame.changerVue(new VueDetailsCommande(this, commandeController, idCommande));
    }

    public void afficherMenuAdmin() {
        appFrame.changerVue(new VueAdminGestionArticles(this));
    }

    public void afficherAjoutProduit() {
        appFrame.changerVue(new VueAjoutProduit(this, articleController, marqueController));
    }

    public void afficherSuppressionProduit() {
        appFrame.changerVue(new VueSupprimerProduit(this, articleController));
    }

    public void afficherAjoutMarque() {
        appFrame.changerVue(new VueAjoutMarque(this, marqueController));
    }

    public void afficherSuppressionMarque() {
        appFrame.changerVue(new VueSuppressionMarque(this, marqueController));
    }

    public void afficherModificationProduit() {
        appFrame.changerVue(new VueModifierArticle(this, articleController));
    }
    public void afficherStatistiques() {
        appFrame.changerVue(new VueAdminStatsVentes(this, statistiquesController));
    }

    public void afficherStatsAdmin() {
        appFrame.changerVue(new VueAdminStatsVentes(this, statistiquesController));
    }
}
