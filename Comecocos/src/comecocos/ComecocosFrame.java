/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comecocos;

import data.Fantasmas;
import data.Figura;
import data.Mueve;
import data.Puntuacion;
import data.Rejilla;
import sound.Sonido;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Toni
 */
public class ComecocosFrame extends javax.swing.JFrame {

    private final Rejilla rejilla;
    public Mueve mueve;
    private Puntuacion puntuacion = new Puntuacion();
    private Figura comecocos;
    private Sonido sonido;
    
    private final int nFantasmas = 4;
    private Figura[] fantasmas = new Figura[nFantasmas];
    
    private int vidas;

    private boolean azules;
    public boolean sonando;
    private Timer timer = new Timer();
    
    /**
     * Creates new form ComecocosFrame
     */
    public ComecocosFrame() {
        
        initComponents();
        this.rejilla = new Rejilla(this);
        this.init(); 
    }
   
    public Rejilla getRejilla(){
        return rejilla;
    }
    
    public RejillaPanel getPanel(){
        return rejillaPanel1;
    }
       
    public void init(){
        
        comecocos = new Figura(26,14,1);
        
        int pos = 12;
        for(int i=0;i<nFantasmas;i++){
            fantasmas[i]= new Figura(pos,13,2);
            pos++;
        }
        
        this.vidas = 3;
        this.azules = false;
        
        playSound(5000,"pacman_beginning.wav");
        
                
        vida1.setVisible(true);
        vida2.setVisible(true);
        vida3.setVisible(true);
        puntos.setVisible(true);
        gameover.setVisible(false);
        
        mueve = new Mueve(this);
        if(mueve!=null && !mueve.getTerminado())
            mueve.terminar();
        
        mueve=new Mueve(this);
        
        Thread t=new Thread(mueve);
        t.start();
        mueve.reanudar();
    }
    
    public Figura getComecocos(){
        return comecocos;
    }
    
         
    public void muerto(){
        comecocos.setI(1);
        comecocos.setJ(14);
        comecocos.setDireccion(1);
        getPanel().setParpadeoComecocos();
        Sonido sonido = new Sonido(2000,"pacman_death.wav");
        sonido.start();
        
        vidas--;
        gestionaVidas();
    }
    
     public Figura[] getFantasmas(){
        return fantasmas;
    }
     
     public void setDireccionFantasma(int i, int direccion){
         fantasmas[i].setDireccion(direccion);
         fantasmas[i].mueve(direccion);
     }
     
     public boolean azules(){
         return azules;
     }
     
     public void setAzules(){
        azules = true;
        this.timer.cancel(); 
        this.timer = new Timer();
        playSound(1000,"pacman_eatfruit.wav");
        playSound(10000,"pacman_intermission.wav");

        TimerTask action = new TimerTask() {
            public void run() {
                azules = false;
            }
        };

        this.timer.schedule(action, 10000); //this starts the task
    }
    
    public void fantasmaMuerto(int i){
        
        fantasmas[i].setI(11);
        fantasmas[i].setJ(14);
        fantasmas[i].setDireccion(1);
        
        getPanel().setParpadeoFantasma(i);
        addPuntos(200);
    }
    
    public void addPuntos(int p){
        puntuacion.addPuntos(p);
        puntos.setText(String.valueOf(puntuacion.getPuntuacion()));
        
    }
    
    public void nextLevel(){
        
        comecocos.setI(1);
        comecocos.setJ(14);
        comecocos.setDireccion(1);
        mueve.nextLevel();
        getPanel().setParpadeoComecocos();
        
        vidas++;
        playSound(1000,"pacman_extrapac.wav");
        gestionaVidas();
        
        this.azules = false;
        
        int pos = 12;
        for(int i=0; i<fantasmas.length;i++){
            fantasmas[i].setI(11);
            fantasmas[i].setJ(pos++);
            fantasmas[i].setDireccion(2);
        }
    }
    
    public void gestionaVidas(){
        if(vidas == 2){
            vida3.setVisible(false);
            vida1.setVisible(true);
            vida2.setVisible(true);
        }
        else if (vidas == 1){
            vida3.setVisible(false);
            vida2.setVisible(false);
            vida1.setVisible(true);
        }
        else if (vidas == 0){
            vida3.setVisible(false);
            vida2.setVisible(false);
            vida1.setVisible(false);
            
            mueve.terminar();
            gameover.setVisible(true);
            //MUERTO
        }
    }
    
    public void playSound(int delay, String name){
        if(!sonando){
            this.timer.cancel(); 
            this.timer = new Timer();
            sonando = true;

            TimerTask action = new TimerTask() {
                public void run() {
                    sonando = false;
                }
            };

            this.timer.schedule(action, delay);
            sonido = new Sonido(delay,name);
            sonido.start();
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

        jPanel1 = new javax.swing.JPanel();
        vidasTitle = new java.awt.Label();
        lives = new javax.swing.JPanel();
        vida1 = new javax.swing.JLabel();
        vida2 = new javax.swing.JLabel();
        vida3 = new javax.swing.JLabel();
        puntosTitle = new java.awt.Label();
        puntos = new java.awt.Label();
        rejillaPanel1 = new RejillaPanel(this);
        gameover = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(430, 590));

        jPanel1.setLayout(new java.awt.GridLayout(1, 6));

        vidasTitle.setAlignment(java.awt.Label.CENTER);
        vidasTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        vidasTitle.setFont(new java.awt.Font("Trebuchet MS", 1, 15)); // NOI18N
        vidasTitle.setMinimumSize(new java.awt.Dimension(30, 22));
        vidasTitle.setText("VIDAS:");
        jPanel1.add(vidasTitle);

        lives.setMinimumSize(new java.awt.Dimension(92, 42));
        lives.setLayout(new java.awt.GridLayout(1, 0));

        vida1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/vida.png"))); // NOI18N
        lives.add(vida1);

        vida2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/vida.png"))); // NOI18N
        lives.add(vida2);

        vida3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/vida.png"))); // NOI18N
        vida3.setToolTipText("");
        lives.add(vida3);

        jPanel1.add(lives);

        puntosTitle.setAlignment(java.awt.Label.RIGHT);
        puntosTitle.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        puntosTitle.setText("PUNTUACION:");
        jPanel1.add(puntosTitle);

        puntos.setAlignment(java.awt.Label.CENTER);
        puntos.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        puntos.setText("0");
        jPanel1.add(puntos);
        puntos.getAccessibleContext().setAccessibleName("00:00");

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        rejillaPanel1.setMinimumSize(new java.awt.Dimension(28, 31));

        gameover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gameover.png"))); // NOI18N

        javax.swing.GroupLayout rejillaPanel1Layout = new javax.swing.GroupLayout(rejillaPanel1);
        rejillaPanel1.setLayout(rejillaPanel1Layout);
        rejillaPanel1Layout.setHorizontalGroup(
            rejillaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rejillaPanel1Layout.createSequentialGroup()
                .addComponent(gameover)
                .addGap(0, 184, Short.MAX_VALUE))
        );
        rejillaPanel1Layout.setVerticalGroup(
            rejillaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rejillaPanel1Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(gameover)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        getContentPane().add(rejillaPanel1, java.awt.BorderLayout.PAGE_START);

        pack();
        setLocationRelativeTo(null);
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ComecocosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ComecocosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ComecocosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ComecocosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ComecocosFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel gameover;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel lives;
    private java.awt.Label puntos;
    private java.awt.Label puntosTitle;
    private comecocos.RejillaPanel rejillaPanel1;
    private javax.swing.JLabel vida1;
    private javax.swing.JLabel vida2;
    private javax.swing.JLabel vida3;
    private java.awt.Label vidasTitle;
    // End of variables declaration//GEN-END:variables
}
