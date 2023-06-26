/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package smarthorses;

import GUI.VentanaPrincipal;

/**
 *
 * @author Nicolas
 */
public class SmartHorses {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VentanaPrincipal vp = new VentanaPrincipal();
        vp.setVisible(true);
        World mundo = new World(8, 8);
        mundo.RandomWorld();
        mundo.printWorld();
    }
    
}
