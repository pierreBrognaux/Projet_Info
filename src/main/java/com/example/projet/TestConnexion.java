package com.example.projet;

import java.sql.Connection;

public class TestConnexion {
    public static void main(String[] args) {
        Connection connexion = ConnexionBD.getConnexion();
        if (connexion != null) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("Échec de la connexion.");
        }
    }
}
