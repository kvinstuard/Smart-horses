/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.Color;
import java.util.List;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Tablero {

    public int icb, jcb, icn, jcn;
    public int matrizT[][];
    public List<Integer> posiciones = new ArrayList<>();
    public List<List<Integer>> movientosPosibles = new ArrayList<>();

    public int[][] matrizRandom() {
        int matriz[][] = new int[8][8];
        Random random = new Random();
        int i, j, c = 1;
        while (c < 10) {
            i = random.nextInt(7);
            j = random.nextInt(7);
            System.out.println(i + " " + j);

            if (matriz[i][j] == 0) {
                matriz[i][j] = c;
                if (c == 8) {
                    icn = i;
                    jcn = j;
                    System.out.println(i + " " + j + " " + c);
                }
                if (c == 9) {
                    icb = i;
                    jcb = j;
                    System.out.println(i + " " + j + " " + c);
                }
                c++;
            }
        }
        return matriz;
    }

    public void pintarNuevoTablero(JPanel panel, JButton[][] tablero) {
        matrizT = matrizRandom();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tablero[i][j].setOpaque(true);
                tablero[i][j].setBounds(new Rectangle(50, 51)); // se dibuja como un rectangulo.
                ImageIcon imagen = seleccionarIcono(matrizT[i][j]);
                tablero[i][j].setLocation(j * 50, i * 50);
                tablero[i][j].setIcon(imagen);
                tablero[i][j].setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
                panel.add(tablero[i][j]);
            }
        }
        mostrarMovimientos(panel, tablero, icn, jcn);
    }

    public void pintarTablero(int matriz[][], JPanel panel, JButton[][] tablero) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tablero[i][j].setOpaque(true);
                tablero[i][j].setBounds(new Rectangle(50, 51)); // se dibuja como un rectangulo.
                ImageIcon imagen = seleccionarIcono(matriz[i][j]);
                tablero[i][j].setLocation(j * 50, i * 50);
                tablero[i][j].setIcon(imagen);
                tablero[i][j].setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
                panel.add(tablero[i][j]);
            }
        }
        mostrarMovimientos(panel, tablero, icn, jcn);
    }

    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public void mostrarMovimientos(JPanel panel, JButton[][] tablero, int posI, int posJ) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tablero[i][j].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
            }
        }

        int nr = posI - 2;
        int nc = posJ + 1;
        if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
            posiciones.add(nr);
            posiciones.add(nc);
            movientosPosibles.add(posiciones);
            tablero[nr][nc].setBorder(BorderFactory.createLineBorder(Color.green, 3));
            System.out.println("nueva posición: [" + nr + ", " + nc + "]");

        }

        nr = posI - 1;
        nc = posJ + 2;
        if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
            posiciones.clear();
            movientosPosibles.clear();
            posiciones.add(nr);
            posiciones.add(nc);
            movientosPosibles.add(posiciones);
            tablero[nr][nc].setBorder(BorderFactory.createLineBorder(Color.green, 3));
            System.out.println("nueva posición: [" + nr + ", " + nc + "]");
        }

        nr = posI + 1;
        nc = posJ + 2;
        if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
            posiciones.clear();
            movientosPosibles.clear();
            posiciones.add(nr);
            posiciones.add(nc);
            tablero[nr][nc].setBorder(BorderFactory.createLineBorder(Color.green, 3));
            System.out.println("nueva posición: [" + nr + ", " + nc + "]");
        }

        nr = posI + 2;
        nc = posJ + 1;
        if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
            posiciones.clear();
            movientosPosibles.clear();
            posiciones.add(nr);
            posiciones.add(nc);
            tablero[nr][nc].setBorder(BorderFactory.createLineBorder(Color.green, 3));

            System.out.println("nueva posición: [" + nr + ", " + nc + "]");

        }

        nr = posI - 2;
        nc = posJ - 1;
        if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
            posiciones.clear();
            movientosPosibles.clear();
            posiciones.add(nr);
            posiciones.add(nc);
            tablero[nr][nc].setBorder(BorderFactory.createLineBorder(Color.green, 3));
            System.out.println("nueva posición: [" + nr + ", " + nc + "]");
        }

        nr = posI - 1;
        nc = posJ - 2;
        if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
            posiciones.clear();
            movientosPosibles.clear();
            posiciones.add(nr);
            posiciones.add(nc);
            tablero[nr][nc].setBorder(BorderFactory.createLineBorder(Color.green, 3));
            System.out.println("nueva posición: [" + nr + ", " + nc + "]");
        }

        nr = posI + 1;
        nc = posJ - 2;
        if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
            posiciones.clear();
            movientosPosibles.clear();
            posiciones.add(nr);
            posiciones.add(nc);
            tablero[nr][nc].setBorder(BorderFactory.createLineBorder(Color.green, 3));
            System.out.println("nueva posición: [" + nr + ", " + nc + "]");
        }

        nr = posI + 2;
        nc = posJ - 1;
        if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
            posiciones.clear();
            movientosPosibles.clear();
            posiciones.add(nr);
            posiciones.add(nc);
            tablero[nr][nc].setBorder(BorderFactory.createLineBorder(Color.green, 3));
            System.out.println("nueva posición: [" + nr + ", " + nc + "]");
        }
        /*for (int i = 0; i < rowMoves.length; i++) {
                int nr = posI + rowMoves[i];
                int nc = posJ + colMoves[i];

                if (isValidMove(nr, nc)) {
                    tablero[nr][nc].setBackground(Color.red);
                    tablero[nr][nc].setEnabled(false);
                    
                }
            }*/
        System.out.println("rrrrrrrrrrrrrr " + movientosPosibles.get(0).get(1));
    }

    public void moverCaballo(JPanel panel, JButton[][] tablero, int posI, int posJ) {
        for (int i = 0; i < movientosPosibles.size(); i++) {
            if (posI == movientosPosibles.get(i).get(0) && posJ == movientosPosibles.get(i).get(1)) {
                matrizT[posI][posJ] = 8;
                matrizT[icn][jcn] = 0;
                icn = posI;
                jcn = posJ;
                pintarTablero(matrizT, panel, tablero);
            }
            else {
                JOptionPane.showMessageDialog(null, "Movimiento no permitido");
            }
        }
    }

    public ImageIcon seleccionarIcono(int laberinto) {
        ImageIcon imagen;
        imagen = switch (laberinto) {
            case 1 ->
                new ImageIcon("src/img/1.png");
            case 2 ->
                new ImageIcon("src/img/2.png");
            case 3 ->
                new ImageIcon("src/img/3.png");
            case 4 ->
                new ImageIcon("src/img/4.png");
            case 5 ->
                new ImageIcon("src/img/5.png");
            case 6 ->
                new ImageIcon("src/img/6.png");
            case 7 ->
                new ImageIcon("src/img/7.png");
            case 8 ->
                new ImageIcon("src/img/caballo-negro-r.png");
            case 9 ->
                new ImageIcon("src/img/caballo-blanco-r.png");
            default ->
                new ImageIcon("src/img/casillaLibre.png");
        };
        return imagen;
    }
}
