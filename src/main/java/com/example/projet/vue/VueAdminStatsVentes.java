package com.example.projet.vue;

import com.example.projet.controller.StatistiquesController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import com.example.projet.Navigation;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class VueAdminStatsVentes extends JPanel {
    private final Navigation navigation;

    public VueAdminStatsVentes(Navigation navigation, StatistiquesController statsController) {
        this.navigation = navigation;
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Statistiques des ventes", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        titre.setOpaque(true);
        titre.setForeground(Color.WHITE);
        titre.setBackground(new Color(24, 64, 55));
        titre.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel(new GridLayout(1, 2));

        // Camembert : répartition des commandes par marque
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        Map<String, Integer> repartition = statsController.getQuantitesCommandeesParMarque();
        for (Map.Entry<String, Integer> entry : repartition.entrySet()) {
            pieDataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Commandes par marque",
                pieDataset,
                true, true, false
        );

        ChartPanel pieChartPanel = new ChartPanel(chart);
        centre.add(pieChartPanel);

        // Statistiques moyennes
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        double prixMoyen = statsController.getPrixPanierMoyen();
        double qteMoyenne = statsController.getQuantitePanierMoyenne();

        statsPanel.add(new JLabel("💶 Prix moyen par panier : " + String.format("%.2f", prixMoyen) + " €"));
        statsPanel.add(Box.createVerticalStrut(20));
        statsPanel.add(new JLabel("📦 Quantité moyenne d'articles par panier : " + String.format("%.2f", qteMoyenne)));

        centre.add(statsPanel);

        add(centre, BorderLayout.CENTER);

        JButton retourBtn = new JButton("← Retour au menu admin");
        retourBtn.setBackground(new Color(200, 220, 200));
        retourBtn.setFocusPainted(false);
        retourBtn.addActionListener(e -> navigation.afficherMenuAdmin());

        JPanel bas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bas.add(retourBtn);
        add(bas, BorderLayout.SOUTH);

    }
}
