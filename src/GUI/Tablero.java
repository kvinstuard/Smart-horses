/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Tablero {
    
    public void pintarTablero(int matriz[][], JPanel panel, JLabel[][] tablero){
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
    }
    
   public ImageIcon seleccionarIcono(int laberinto) {
        ImageIcon imagen;
        switch (laberinto) {
            case 1:
                imagen = new ImageIcon("src/img/1.png");
                break;
            case 2:
                imagen = new ImageIcon("src/img/2.png");
                break;
            case 3:
                imagen = new ImageIcon("src/img/3.png");
                break;
            case 4:
                imagen = new ImageIcon("src/img/4.png");
                break;
            case 5:
                imagen = new ImageIcon("src/img/5.png");
                break;
            case 6:
                imagen = new ImageIcon("src/img/6.png");
                break;
            case 7:
                imagen = new ImageIcon("src/img/7.png");
                break;
            case 8:
                imagen = new ImageIcon("src/img/caballo-negro-r.png");
                break;
            case 9:
                imagen = new ImageIcon("src/img/caballo-blanco-r.png");
                break;
            default:
                imagen = new ImageIcon("src/img/casillaLibre.png");
        }
        return imagen;
    }
}
