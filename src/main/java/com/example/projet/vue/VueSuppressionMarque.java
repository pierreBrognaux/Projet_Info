package com.example.projet.vue;

import com.example.projet.Navigation;
import com.example.projet.controller.MarqueController;
import com.example.projet.modele.Marque;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VueSuppressionMarque extends JPanel {

    public VueSuppressionMarque(Navigation navigation, MarqueController marqueController) {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Supprimer une marque", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setOpaque(true);
        titre.setBackground(new Color(120, 20, 20));
        titre.setForeground(Color.WHITE);
        add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));

        List<Marque> marques = marqueController.getToutesLesMarques();
        if (marques.isEmpty()) {
            centre.add(new JLabel("Aucune marque enregistrée."));
        } else {
            for (Marque m : marques) {
                JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT));
                ligne.add(new JLabel("🏷 " + m.getNom()));

                JButton supprimer = new JButton("🗑 Supprimer");
                supprimer.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Supprimer la marque \"" + m.getNom() + "\" ?",
                            "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean ok = marqueController.supprimerMarque(m.getId());
                        if (ok) {
                            JOptionPane.showMessageDialog(this, "Marque supprimée !");
                            navigation.afficherSuppressionMarque(); // recharge
                        } else {
                            JOptionPane.showMessageDialog(this, "Impossible : la marque est liée à un ou plusieurs articles.");
                        }
                    }
                });

                ligne.add(supprimer);
                centre.add(ligne);
            }
        }

        JScrollPane scroll = new JScrollPane(centre);
        add(scroll, BorderLayout.CENTER);

        JButton retour = new JButton("← Retour");
        retour.addActionListener(e -> navigation.afficherMenuAdmin());
        add(retour, BorderLayout.SOUTH);
    }
}
