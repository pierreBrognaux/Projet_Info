package com.example.projet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/projetinfo"; // Remplace "achats" par le nom de ta BD
    private static final String UTILISATEUR = "root"; // Ou ton propre login
    private static final String MOT_DE_PASSE = "";    // Mets ton mot de passe MySQL (souvent vide par défaut avec XAMPP)

    public static Connection getConnexion() {
        try {
            return DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
