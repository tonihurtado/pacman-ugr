/*
////////////////////////////////////////////////////////////////////////////////
/////           PRACTICA 8 COMPLEMENTO DE PROGRAMACIÓN - COMECOCOS         /////
////////////////////////////////////////////////////////////////////////////////
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

////////////////////////////////////////////////////////////////////////////////
/**
 * Programa e interfaz principal del comecocos.
 * Contiene todos los objetos y variables del programa.
 * Sobre el se disponen el resto de componentes y gráficos.

 * @author Jose Antonio Hurtado Morón
 */
public class ComecocosFrame extends javax.swing.JFrame {

//Declaramos el resto de objetos que compondrán el programa

    private final Rejilla rejilla;
    public Mueve mueve;
    private Puntuacion puntuacion = new Puntuacion();
    private Figura comecocos;
    private Sonido sonido;

//Creamos un array de Figuras que contendrá los n fantasmas del juego

    private final int nFantasmas = 4;
    private Figura[] fantasmas = new Figura[nFantasmas];

//Numero de vidas de la partida

    private int vidas;

//El boolean azules indica si los fantasmas son comestibles o no, y el timer
//nos servirá para que este modo (azules) solo dure un tiempo limitado.

    private boolean azules;
    public boolean sonando;
    private Timer timer = new Timer();

////////////////////////////////////////////////////////////////////////////////
//                              CONSTRUCTOR ()                                //
////////////////////////////////////////////////////////////////////////////////

    /**
     * Inicializacion de variables y creación de objetos miembro.
     */

    public ComecocosFrame() {

        initComponents();
        this.rejilla = new Rejilla(this);
        this.init();
    }

    /**
     * En este metodo complementario al constructor tenemos las variables y
     * objetos que hay que reiniciar cuando empecemos una nueva partida cuando
     * subimos de nivel.
     */

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

////////////////////////////////////////////////////////////////////////////////
//                                METODOS ()                                  //
////////////////////////////////////////////////////////////////////////////////

//_______________________________REJILLA________________________________________

    /**
     * GET objeto rejilla.
     * @return rejilla
     */

    public Rejilla getRejilla(){
        return rejilla;
    }

    /**
     * GET objeto rejillaPanel (graphics).
     * @return rejillaPanel1
     */

    public RejillaPanel getPanel(){
        return rejillaPanel1;
    }

//_______________________________COMECOCOS______________________________________

    /**
     * GET figura comecocos.
     * @return comecocos
     */

    public Figura getComecocos(){
        return comecocos;
    }

    /**
     *Metodo que reinicia la posición del comecocos cuando un fantasma se lo
     come.También disminuye en uno el numero de vidas.

     * @return VOID
     */

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

//_______________________________FANTASMAS______________________________________

    /**
     * GET array de fantasmas.
     * @return fantasmas[]
     */

     public Figura[] getFantasmas(){
        return fantasmas;
    }

    //Metodo auxiliar para el movimiento de los fantasmas (sirve de puente entre
    //los metodos de la clase Fantasmas y el Runnable Mueve que implementa el
    //movimiento)

     public void setDireccionFantasma(int i, int direccion){
         fantasmas[i].setDireccion(direccion);
         fantasmas[i].mueve(direccion);
     }

     //Metodo para comprobar si el juego se encuentra en modo "Fantasmas
     // comestibles"

     public boolean azules(){
         return azules;
     }

     /**
     *Metodo que enciende el boolean azules durante 10 segundos, durante este
     *tiempo todo el modo de juego cambiará.
     */

     public void setAzules(){
        azules = true;
        this.timer.cancel();
        this.timer = new Timer();

        //sonido

        playSound(1000,"pacman_eatfruit.wav");
        playSound(10000,"pacman_intermission.wav");

        //el timer se programa para que se ejecute dentro de 10 segundos.

        TimerTask action = new TimerTask() {
            public void run() {
                azules = false;
            }
        };

        this.timer.schedule(action, 10000); //this starts the task
    }

    /**
    *Este metodo devuelve el fantasma a su posición inicial en el centro del
    *mapa y da 200 puntos al usuario. Esto solo sucedera si los fantasmas son
    *comestibles.
    */

    public void fantasmaMuerto(int i){

        fantasmas[i].setI(11);
        fantasmas[i].setJ(14);
        fantasmas[i].setDireccion(1);

        getPanel().setParpadeoFantasma(i);
        addPuntos(200);
    }

//_______________________________GUI & OTHERS___________________________________

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

    //Implementamos el sonido

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
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jMenu1.setText("File");

        jMenuItem1.setText("New");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
       System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
              
        rejilla.reiniciar();
        this.init();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
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
