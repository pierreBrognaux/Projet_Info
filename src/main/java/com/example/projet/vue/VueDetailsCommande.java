package com.example.projet.vue;

import com.example.projet.Navigation;
import com.example.projet.controller.CommandeController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VueDetailsCommande extends JPanel {

    public VueDetailsCommande(Navigation navigation, CommandeController commandeController, int idCommande) {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Détails de la commande #" + idCommande, SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setOpaque(true);
        titre.setBackground(new Color(24, 64, 55));
        titre.setForeground(Color.WHITE);
        add(titre, BorderLayout.NORTH);

        JPanel contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));

        List<String> details = commandeController.getDetailsPourCommande(idCommande);

        if (details.isEmpty()) {
            contenu.add(new JLabel("Aucun article trouvé pour cette commande."));
        } else {
            for (String ligne : details) {
                contenu.add(new JLabel("🛒 " + ligne));
            }
        }

        JScrollPane scroll = new JScrollPane(contenu);
        add(scroll, BorderLayout.CENTER);

        JButton retour = new JButton("← Retour à l’historique");
        retour.addActionListener(e -> navigation.afficherHistoriqueCommandes());

        add(retour, BorderLayout.SOUTH);
    }
}
