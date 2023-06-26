/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

////
package GUI;

import Model.Minimax;
import Model.Estado;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Nicolas
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    int nivel = 0, ax = 0, ay = 0;
    double ptsBlanco = 0.0, ptsNegro = 0.0;
    Point[] punt = null; //posiciones
    Estado raiz = Estado.crearEstadoInicial(8);
    Estado Nuevo;
    int primeraJugada = 0;
    JButton tablero[][];
    Tablero tb = new Tablero();

    public VentanaPrincipal() {
        initComponents();
        setTitle("Smart Horses");
        setLocationRelativeTo(null);
        jComboDificultad.setBackground(Color.white);
        btnComenzar.setBackground(Color.white);
        jComboDificultad.addItem("Seleccione Dificultad");
        jComboDificultad.addItem("Principiante");
        jComboDificultad.addItem("Amateur");
        jComboDificultad.addItem("Experto");
        etiExplicacion.setText("<html><p>La máquina siempre empezará<br>"
                + "el juego y jugará con el<br>"
                + "caballo blanco.</p></html>");
        etiPtsJ3.setText("<html><p>Puntos<br>"
                + "jugador 1</p></html>");
        etiPtsJ2.setText("<html><p>Puntos<br>"
                + "jugador 2</p></html>");
        tablero = new JButton[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                final int currentRow = i;
                final int currentCol = j;
                tablero[i][j] = new JButton();
                tablero[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Acciones a realizar cuando se presione el botón
                        //System.out.println("Posición: " + currentRow + ", " + currentCol);
                        //tb.moverCaballo(panelTablero, tablero, currentRow, currentCol);
                        movimientoJugador2(currentRow, currentCol);
                    }
                });
            }
        }
    }

    public boolean verDificultad() {
        if (jComboDificultad.getSelectedItem() == "Seleccione Dificultad") {
            return false;
        } else {
            return true;
        }
    }

    public void movimientoJugador1() {
        if (verDificultad() == false) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una dificultad");
            btnComenzar.setEnabled(true);
        } else {
            if (primeraJugada == 0) {
                tb.pintarTablero(raiz.getTablero(), panelTablero, tablero);
                tb.pintarTablero(raiz.getTablero(), panelTablero, tablero);
                primeraJugada++;
            }
            btnComenzar.setEnabled(false);
            jComboDificultad.setEnabled(false);
            if ((Double.parseDouble(puntosJ1.getText()) + Double.parseDouble(puntosJ2.getText())) < 28) {

                //Selecciona el nivel de dificultad
                if (jComboDificultad.getSelectedItem() == "Principiante") {
                    nivel = 2;
                } else if (jComboDificultad.getSelectedItem() == "Amateur") {
                    nivel = 4;
                } else if (jComboDificultad.getSelectedItem() == "Experto") {
                    nivel = 6;
                }

                Minimax mov = new Minimax(raiz);
                mov.decisionMax(raiz, nivel);
                Point movida = mov.getMovida();
                Nuevo = raiz.resultado(movida);
                ptsBlanco = ptsBlanco + Nuevo.getPuntosB();
                String puntosBlanco = Double.toString(ptsBlanco);
                puntosJ1.setText(puntosBlanco);

                Timer timer = new Timer();
                TimerTask tarea = new TimerTask() {
                    int contador = 1;

                    @Override
                    public void run() {
                        if (contador > 0) {
                            contador--;
                        } else {
                            tb.pintarTablero(Nuevo.getTablero(), panelTablero, tablero);
                            timer.cancel(); // Detener el temporizador
                        }
                    }
                };
                if(primeraJugada == 1){
                    timer.scheduleAtFixedRate(tarea, 0, 1000);
                    primeraJugada++;
                }else{
                    tb.pintarTablero(Nuevo.getTablero(), panelTablero, tablero);
                }
                

                Minimax mov1 = new Minimax(Nuevo);
                punt = mov1.decisionMin(Nuevo, nivel);
                Point movida1 = mov1.getMovida();
                
                String estadoJuego;
                if (ptsNegro > 14.0) {
                    estadoJuego = "Ganaste";
                    JOptionPane.showMessageDialog(null, estadoJuego);
                    System.exit(1);
                }

                if (ptsBlanco > 14.0) {
                    estadoJuego = "Perdiste";
                    JOptionPane.showMessageDialog(null, estadoJuego);
                    System.exit(1);

                }
                
            } else {
                String estadoJuego = "Empate";
                JOptionPane.showMessageDialog(null, estadoJuego);
                System.exit(1);
            } 
        }
    }

    public void movimientoJugador2(int row, int col) {
        if (verDificultad() == false) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una dificultad");
        } else {
            if ((Double.parseDouble(puntosJ1.getText()) + Double.parseDouble(puntosJ2.getText())) < 28) {

                int entradax = row;
                int entraday = col;
                boolean val = false;

                for (int i = 0; i < punt.length; i++) {
                    Point nuevop = punt[i];
                    int axs = (int) nuevop.getX();
                    int ays = (int) nuevop.getY();

                    if ((entradax == axs) && (entraday == ays)) {
                        ax = axs;
                        ay = ays;
                        val = true;
                    }
                }

                if (val) {

                    Point movidanueva = new Point(ax, ay);
                    raiz = Nuevo.resultado(movidanueva);
                    ptsNegro = ptsNegro + raiz.getPuntosNegro();

                    puntosJ2.setText(Double.toString(ptsNegro));
                    tb.pintarTablero(raiz.getTablero(), panelTablero, tablero);
                    String estadoJuego;
                    if (ptsNegro > 14.0) {
                        estadoJuego = "Ganaste";
                        JOptionPane.showMessageDialog(null, estadoJuego);
                        System.exit(1);
                    }

                    if (ptsBlanco > 14.0) {
                        estadoJuego = "Perdiste";
                        JOptionPane.showMessageDialog(null, estadoJuego);
                        System.exit(1);
                    }
                    movimientoJugador1();
                } else {
                    JOptionPane.showMessageDialog(null, "Jugada invalida");
                }

            } else {
                String estadoJuego;
                if (ptsBlanco < ptsNegro) {
                    estadoJuego = "Ganaste";
                    JOptionPane.showMessageDialog(null, estadoJuego);
                    System.out.println("HOLIIIIIgggggg");
                    System.exit(1);

                }
                if (ptsBlanco > ptsNegro) {
                    estadoJuego = "Perdiste";
                    JOptionPane.showMessageDialog(null, estadoJuego);
                    System.out.println("HOLIIIIIPPPPP");
                    System.exit(1);

                }
                if (ptsBlanco == ptsNegro) {
                    estadoJuego = "Empate";
                    JOptionPane.showMessageDialog(null, estadoJuego);
                    System.exit(1);
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        panelIzquierdo = new javax.swing.JPanel();
        jComboDificultad = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnComenzar = new javax.swing.JButton();
        etiExplicacion = new javax.swing.JLabel();
        panelDerecho = new javax.swing.JPanel();
        panelIzquierdo3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        etiExplicacionPuntos = new javax.swing.JLabel();
        etiExplicacion3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        etiExplicacion4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        etiExplicacion5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        etiExplicacion6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        etiExplicacion7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        etiExplicacion8 = new javax.swing.JLabel();
        etiExplicacion9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        etiPtsJ3 = new javax.swing.JLabel();
        puntosJ1 = new javax.swing.JLabel();
        panelTablero = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        etiPtsJ2 = new javax.swing.JLabel();
        puntosJ2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        panelIzquierdo.setBackground(new java.awt.Color(0, 0, 0));

        jComboDificultad.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboDificultad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Seleccione el nivel de dificultad");

        btnComenzar.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        btnComenzar.setText("Comenzar");
        btnComenzar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnComenzar.setFocusPainted(false);
        btnComenzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComenzarActionPerformed(evt);
            }
        });

        etiExplicacion.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        etiExplicacion.setForeground(new java.awt.Color(255, 255, 255));
        etiExplicacion.setText("Seleccione el nivel de dificultad");

        javax.swing.GroupLayout panelIzquierdoLayout = new javax.swing.GroupLayout(panelIzquierdo);
        panelIzquierdo.setLayout(panelIzquierdoLayout);
        panelIzquierdoLayout.setHorizontalGroup(
            panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIzquierdoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIzquierdoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jComboDificultad, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))
                    .addGroup(panelIzquierdoLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(btnComenzar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(etiExplicacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelIzquierdoLayout.setVerticalGroup(
            panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIzquierdoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(etiExplicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jComboDificultad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnComenzar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelDerecho.setBackground(new java.awt.Color(255, 255, 255));

        panelIzquierdo3.setBackground(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/3.png"))); // NOI18N

        etiExplicacionPuntos.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        etiExplicacionPuntos.setForeground(new java.awt.Color(255, 255, 255));
        etiExplicacionPuntos.setText("Sistema de puntos");

        etiExplicacion3.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        etiExplicacion3.setForeground(new java.awt.Color(255, 255, 255));
        etiExplicacion3.setText("= 3 puntos");

        jLabel6.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/4.png"))); // NOI18N

        etiExplicacion4.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        etiExplicacion4.setForeground(new java.awt.Color(255, 255, 255));
        etiExplicacion4.setText("= 4 puntos");

        jLabel7.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/5.png"))); // NOI18N

        etiExplicacion5.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        etiExplicacion5.setForeground(new java.awt.Color(255, 255, 255));
        etiExplicacion5.setText("= 5 puntos");

        jLabel8.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/6.png"))); // NOI18N

        etiExplicacion6.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        etiExplicacion6.setForeground(new java.awt.Color(255, 255, 255));
        etiExplicacion6.setText("= 6 puntos");

        jLabel9.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/7.png"))); // NOI18N

        etiExplicacion7.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        etiExplicacion7.setForeground(new java.awt.Color(255, 255, 255));
        etiExplicacion7.setText("= 7 puntos");

        jLabel10.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/2.png"))); // NOI18N

        etiExplicacion8.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        etiExplicacion8.setForeground(new java.awt.Color(255, 255, 255));
        etiExplicacion8.setText("= 2 puntos");

        etiExplicacion9.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        etiExplicacion9.setForeground(new java.awt.Color(255, 255, 255));
        etiExplicacion9.setText("= 1 punto");

        jLabel12.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/1.png"))); // NOI18N

        javax.swing.GroupLayout panelIzquierdo3Layout = new javax.swing.GroupLayout(panelIzquierdo3);
        panelIzquierdo3.setLayout(panelIzquierdo3Layout);
        panelIzquierdo3Layout.setHorizontalGroup(
            panelIzquierdo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIzquierdo3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelIzquierdo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiExplicacionPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelIzquierdo3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiExplicacion3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelIzquierdo3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiExplicacion4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelIzquierdo3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiExplicacion5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelIzquierdo3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiExplicacion6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelIzquierdo3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiExplicacion7, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelIzquierdo3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiExplicacion8, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelIzquierdo3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiExplicacion9, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        panelIzquierdo3Layout.setVerticalGroup(
            panelIzquierdo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIzquierdo3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(etiExplicacionPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelIzquierdo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(etiExplicacion9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelIzquierdo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(etiExplicacion8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelIzquierdo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(etiExplicacion3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelIzquierdo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(etiExplicacion4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelIzquierdo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(etiExplicacion5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelIzquierdo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(etiExplicacion6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelIzquierdo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(etiExplicacion7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        etiPtsJ3.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        etiPtsJ3.setText("Jugador 1");

        puntosJ1.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        puntosJ1.setText("0");

        panelTablero.setBackground(new java.awt.Color(255, 255, 255));
        panelTablero.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelTablero.setPreferredSize(new java.awt.Dimension(400, 401));

        javax.swing.GroupLayout panelTableroLayout = new javax.swing.GroupLayout(panelTablero);
        panelTablero.setLayout(panelTableroLayout);
        panelTableroLayout.setHorizontalGroup(
            panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );
        panelTableroLayout.setVerticalGroup(
            panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("SANSON", 1, 36)); // NOI18N
        jLabel2.setText("Smart Horses");

        etiPtsJ2.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        etiPtsJ2.setText("Jugador 2");

        puntosJ2.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        puntosJ2.setText("0");

        jLabel11.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/caballo-negro-r.png"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("SANSON", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/caballo-blanco-r.png"))); // NOI18N

        javax.swing.GroupLayout panelDerechoLayout = new javax.swing.GroupLayout(panelDerecho);
        panelDerecho.setLayout(panelDerechoLayout);
        panelDerechoLayout.setHorizontalGroup(
            panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDerechoLayout.createSequentialGroup()
                .addGroup(panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDerechoLayout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDerechoLayout.createSequentialGroup()
                        .addGroup(panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDerechoLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(puntosJ1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(etiPtsJ3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelDerechoLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel13)))
                        .addGap(18, 18, 18)
                        .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDerechoLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(etiPtsJ2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(puntosJ2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelDerechoLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel11)))))
                .addGap(27, 27, 27)
                .addComponent(panelIzquierdo3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        panelDerechoLayout.setVerticalGroup(
            panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelIzquierdo3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelDerechoLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addGroup(panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDerechoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDerechoLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(etiPtsJ2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(puntosJ2))
                            .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelDerechoLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(etiPtsJ3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(puntosJ1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addComponent(panelIzquierdo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelDerecho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(400, 400, 400))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelIzquierdo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelDerecho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 1131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnComenzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComenzarActionPerformed
        movimientoJugador1();
    }//GEN-LAST:event_btnComenzarActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new VentanaPrincipal().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnComenzar;
    private javax.swing.JButton btnComenzar1;
    private javax.swing.JButton btnComenzar2;
    private javax.swing.JLabel etiExplicacion;
    private javax.swing.JLabel etiExplicacion1;
    private javax.swing.JLabel etiExplicacion2;
    private javax.swing.JLabel etiExplicacion3;
    private javax.swing.JLabel etiExplicacion4;
    private javax.swing.JLabel etiExplicacion5;
    private javax.swing.JLabel etiExplicacion6;
    private javax.swing.JLabel etiExplicacion7;
    private javax.swing.JLabel etiExplicacion8;
    private javax.swing.JLabel etiExplicacion9;
    private javax.swing.JLabel etiExplicacionPuntos;
    private javax.swing.JLabel etiPtsJ2;
    private javax.swing.JLabel etiPtsJ3;
    private javax.swing.JComboBox<String> jComboDificultad;
    private javax.swing.JComboBox<String> jComboDificultad1;
    private javax.swing.JComboBox<String> jComboDificultad2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelDerecho;
    private javax.swing.JPanel panelIzquierdo;
    private javax.swing.JPanel panelIzquierdo1;
    private javax.swing.JPanel panelIzquierdo2;
    private javax.swing.JPanel panelIzquierdo3;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel panelTablero;
    private javax.swing.JLabel puntosJ1;
    private javax.swing.JLabel puntosJ2;
    // End of variables declaration//GEN-END:variables
}
