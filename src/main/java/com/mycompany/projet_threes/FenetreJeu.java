/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.projet_threes;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
    /*
 *
 * @author scham
 */
public class FenetreJeu extends javax.swing.JFrame {
    private Jeu jeu ; 
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FenetreJeu.class.getName());

    
    /**
     * Creates new form FenetreJeu
     */
    
    private static final int TAILLE = 4;
    private JLabel[][] cases = new JLabel[TAILLE][TAILLE];
 
    private int[][] grille = new int[TAILLE][TAILLE];
    private Random random = new Random();
    private int score = 0;
    private JLabel labelScore;
    
    
    public FenetreJeu() {
        setTitle("Threes");
        setSize(420, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        labelScore = new JLabel("Score : 0", SwingConstants.CENTER);
        labelScore.setFont(new Font("Arial", Font.BOLD, 16));
        add(labelScore, BorderLayout.NORTH);


        JPanel panel = new JPanel(new GridLayout(4, 4, 5, 5));
        panel.setBackground(Color.DARK_GRAY);

        Font font = new Font("Arial", Font.BOLD, 28);

        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                cases[i][j] = new JLabel("", SwingConstants.CENTER);
                cases[i][j].setFont(font);
                cases[i][j].setOpaque(true);
                cases[i][j].setBackground(Color.LIGHT_GRAY);
                panel.add(cases[i][j]);
            }
        }

        add(panel);
        initialiserJeu();
        rafraichir();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean deplacement = false;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> deplacement = gauche();
                    case KeyEvent.VK_RIGHT -> deplacement = droite();
                    case KeyEvent.VK_UP -> deplacement = haut();
                    case KeyEvent.VK_DOWN -> deplacement = bas();
                }

                if (deplacement) {
                    ajouterNombre();
                    rafraichir();
                
                if (jeuTermine()) {
                FenetreFin_Jeu fin = new FenetreFin_Jeu(score);
                fin.setVisible(true);
                dispose();
            }
                }   
            }
        });

        setFocusable(true);
    }

    private void initialiserJeu() {
        ajouterNombre();
        ajouterNombre();
    }

    private void ajouterNombre() {
        int valeur = random.nextBoolean() ? 1 : 2;

        int i, j;
        do {
            i = random.nextInt(4);
            j = random.nextInt(4);
        } while (grille[i][j] != 0);

        grille[i][j] = valeur;
    }

    private boolean fusion(int a, int b) {
        return (a == 1 && b == 2) || (a == 2 && b == 1) || (a >= 3 && a == b);
    }

    private int valeurFusion(int a, int b) {
        score += (a + b);
        return a + b;
    }

    private boolean gauche() {
        boolean bouge = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                if (grille[i][j] != 0) {
                    int k = j;
                    while (k > 0 && grille[i][k - 1] == 0) {
                        grille[i][k - 1] = grille[i][k];
                        grille[i][k] = 0;
                        k--;
                        bouge = true;
                    }
                    if (k > 0 && fusion(grille[i][k - 1], grille[i][k])) {
                        grille[i][k - 1] = valeurFusion(grille[i][k - 1], grille[i][k]);
                        grille[i][k] = 0;
                        bouge = true;
                    }
                }
            }
        }
        return bouge;
    }

    private boolean droite() {
        boolean bouge = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j >= 0; j--) {
                if (grille[i][j] != 0) {
                    int k = j;
                    while (k < 3 && grille[i][k + 1] == 0) {
                        grille[i][k + 1] = grille[i][k];
                        grille[i][k] = 0;
                        k++;
                        bouge = true;
                    }
                    if (k < 3 && fusion(grille[i][k + 1], grille[i][k])) {
                        grille[i][k + 1] = valeurFusion(grille[i][k + 1], grille[i][k]);
                        grille[i][k] = 0;
                        bouge = true;
                    }
                }
            }
        }
        return bouge;
    }

    private boolean haut() {
        boolean bouge = false;
        for (int j = 0; j < 4; j++) {
            for (int i = 1; i < 4; i++) {
                if (grille[i][j] != 0) {
                    int k = i;
                    while (k > 0 && grille[k - 1][j] == 0) {
                        grille[k - 1][j] = grille[k][j];
                        grille[k][j] = 0;
                        k--;
                        bouge = true;
                    }
                    if (k > 0 && fusion(grille[k - 1][j], grille[k][j])) {
                        grille[k - 1][j] = valeurFusion(grille[k - 1][j], grille[k][j]);
                        grille[k][j] = 0;
                        bouge = true;
                    }
                }
            }
        }
        return bouge;
    }

    private boolean bas() {
        boolean bouge = false;
        for (int j = 0; j < 4; j++) {
            for (int i = 2; i >= 0; i--) {
                if (grille[i][j] != 0) {
                    int k = i;
                    while (k < 3 && grille[k + 1][j] == 0) {
                        grille[k + 1][j] = grille[k][j];
                        grille[k][j] = 0;
                        k++;
                        bouge = true;
                    }
                    if (k < 3 && fusion(grille[k + 1][j], grille[k][j])) {
                        grille[k + 1][j] = valeurFusion(grille[k + 1][j], grille[k][j]);
                        grille[k][j] = 0;
                        bouge = true;
                    }
                }
            }
        }
        return bouge;
    }

    private void rafraichir() {
        labelScore.setText("Score : " + score);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grille[i][j] == 0) {
                    cases[i][j].setText("");
                    cases[i][j].setBackground(Color.LIGHT_GRAY);
                } else {
                    cases[i][j].setText(String.valueOf(grille[i][j]));
                    cases[i][j].setBackground(new Color(255 - grille[i][j] * 5, 200, 120));
                }
            }
        }
    }
        private boolean jeuTermine() {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                if (grille[i][j] == 0) return false;
                if (j < TAILLE - 1 && fusion(grille[i][j], grille[i][j + 1])) return false;
                if (i < TAILLE - 1 && fusion(grille[i][j], grille[i + 1][j])) return false;
            }
        }
        return true;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FenetreJeu().setVisible(true));
    }

    

}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

