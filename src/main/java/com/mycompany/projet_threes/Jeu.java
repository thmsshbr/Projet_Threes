/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projet_threes;
import java.util.Random;

/**
 *
 * @author scham
 */
public class Jeu {
    public int[][] grille = new int[4][4];
private Random rand = new Random();


public Jeu() {
ajouterNombre();
ajouterNombre();
}


public void ajouterNombre() {
int valeur = rand.nextBoolean() ? 1 : 2;
int i, j;
do {
i = rand.nextInt(4);
j = rand.nextInt(4);
} while (grille[i][j] != 0);
grille[i][j] = valeur;
}


public boolean deplacerGauche() {
boolean mouvement = false;
for (int i = 0; i < 4; i++) {
for (int j = 1; j < 4; j++) {
if (grille[i][j] != 0) {
int k = j;
while (k > 0 && grille[i][k - 1] == 0) {
grille[i][k - 1] = grille[i][k];
grille[i][k] = 0;
k--;
mouvement = true;
}
if (k > 0 && fusionPossible(grille[i][k - 1], grille[i][k])) {
grille[i][k - 1] = fusion(grille[i][k - 1], grille[i][k]);
grille[i][k] = 0;
mouvement = true;
}
}
}
}
return mouvement;
}


private boolean fusionPossible(int a, int b) {
return (a == b && a >= 3) || (a == 1 && b == 2) || (a == 2 && b == 1);
}


private int fusion(int a, int b) {
if ((a == 1 && b == 2) || (a == 2 && b == 1)) return 3;
return a * 2;
}
}
