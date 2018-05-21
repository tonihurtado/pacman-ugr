/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comecocos;

import data.Figura;
import data.Rejilla;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Toni
 */
public class RejillaPanel extends javax.swing.JPanel {

   
    private ComecocosFrame frame;
    private Timer timer = new Timer();
    private int anchoCelda = 15;
    
    private boolean paused = false;
    
    private boolean fase = true;
    private boolean faseFantasmas = true;
    
    private boolean parpadea = false;
    private boolean parpadeaF = false;
    private int fParpadeo = -1;
    
    
    
    public RejillaPanel() {
        initComponents();
    }
    
    public RejillaPanel(ComecocosFrame fr){
        this();
        frame = fr;
    }
    
    public void dibujaRejilla(Graphics g){
        
        Rejilla rejilla = frame.getRejilla();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        for(int i=0;i<rejilla.getAltura(); i++){
            for(int j = 0;j<rejilla.getAnchura(); j++){
                
                if(rejilla.getCelda(i,j) == 'B'){
                    g.setColor(Color.BLUE.darker());
                    g.fillRect(j*anchoCelda,i*anchoCelda,anchoCelda,anchoCelda);
                }else if(rejilla.getCelda(i, j) == 'P'){
                    g.setColor(Color.WHITE);
                    g.drawLine(j*anchoCelda, 
                               i*anchoCelda+3*anchoCelda/4,
                               (j+1)*anchoCelda,
                               i*anchoCelda+3*anchoCelda/4);
                    g.drawLine(j*anchoCelda, 
                               i*anchoCelda+2*anchoCelda/4,
                               (j+1)*anchoCelda,
                               i*anchoCelda+2*anchoCelda/4);
                }else if(rejilla.getCelda(i, j) == '.'){
                    g.setColor(Color.WHITE);
                    g.drawArc(j*anchoCelda+anchoCelda/2, 
                              i*anchoCelda+anchoCelda/2, 
                              2, 2, 0, 360);
                    g.fillArc(j*anchoCelda+anchoCelda/2, 
                              i*anchoCelda+anchoCelda/2, 
                              2, 2, 0, 360);
                }
                else if(rejilla.getCelda(i, j) == 'o'){
                    g.setColor(Color.WHITE);
                    g.drawArc(j*anchoCelda+anchoCelda/3, 
                              i*anchoCelda+anchoCelda/3, 
                              6, 6, 0, 360);
                    g.fillArc(j*anchoCelda+anchoCelda/3, 
                              i*anchoCelda+anchoCelda/3, 
                              6, 6, 0, 360);
                }
                
            }
        }   
    }
    
    public void dibujaComecocos(Graphics g, Figura comecocos){
        
        int barrido;
        int posicionX;
        int posicionY;

        int[] angulo={125,45,310,210};
            Rejilla rejilla = frame.getRejilla();
            g.setColor(Color.YELLOW);
  
        if(!fase){
            
           barrido = 280;
           posicionX= anchoCelda*comecocos.getI();
           posicionY= anchoCelda*comecocos.getJ();
           fase = true;
        }
        else{
            int dir = comecocos.getDireccion();
            
             if(parpadea){
               g.setColor(Color.BLACK);
           }
            if(frame.getRejilla().colision(frame.getComecocos(),frame.getComecocos().getDireccion())){
                dir = -1;
            }
                barrido = 360;

                switch(dir){
                    case 0:
                        posicionX= anchoCelda*comecocos.getI();
                        posicionY= anchoCelda*comecocos.getJ() - anchoCelda/2;
                        break;
                    case 1:
                        posicionX= anchoCelda*comecocos.getI() + anchoCelda/2;
                        posicionY= anchoCelda*comecocos.getJ();
                        break;
                    case 2:
                        posicionX= anchoCelda*comecocos.getI();
                        posicionY= anchoCelda*comecocos.getJ() + anchoCelda/2;
                        break;
                    case 3:
                        posicionX= anchoCelda*comecocos.getI() - anchoCelda/2;
                        posicionY= anchoCelda*comecocos.getJ();
                        break;
                    default:
                        posicionX= anchoCelda*comecocos.getI();
                        posicionY= anchoCelda*comecocos.getJ();
                }
            fase = false; 
            }
 
        g.fillArc(posicionX, posicionY, anchoCelda, anchoCelda, angulo[comecocos.getDireccion()], barrido);
      
    }
    
        public void dibujaFantasma(Graphics g, Figura[] fantasmas){
        
        Rejilla rejilla = frame.getRejilla();
        
        int posicionX;
        int posicionY;
  
        if(!faseFantasmas){
                
            for(int i=0;i<fantasmas.length; i++){
                
                posicionX= anchoCelda*fantasmas[i].getI();
                posicionY= anchoCelda*fantasmas[i].getJ();
                
                drawIt(g,posicionX,posicionY,i);
            }
            
            faseFantasmas = true;
            
        }else{
            for(int j=0;j<fantasmas.length; j++){
                
                int dir = fantasmas[j].getDireccion();
                
               if(parpadeaF && fParpadeo == j){
                    g.setColor(Color.BLACK);
               }
                
               if(frame.getRejilla().colision(fantasmas[j],dir)){
                   dir = -1;
               }
                   
                switch(dir){
  
                    case 0:
                        posicionX= anchoCelda*fantasmas[j].getI();
                        posicionY= anchoCelda*fantasmas[j].getJ() - anchoCelda/2;
                        break;
                    case 1:
                        posicionX= anchoCelda*fantasmas[j].getI() + anchoCelda/2;
                        posicionY= anchoCelda*fantasmas[j].getJ();
                        break;
                    case 2:
                        posicionX= anchoCelda*fantasmas[j].getI();
                        posicionY= anchoCelda*fantasmas[j].getJ() + anchoCelda/2;
                        break;
                    case 3:
                        posicionX= anchoCelda*fantasmas[j].getI() - anchoCelda/2;
                        posicionY= anchoCelda*fantasmas[j].getJ();
                        break;
                    default:
                        posicionX= anchoCelda*fantasmas[j].getI();
                        posicionY= anchoCelda*fantasmas[j].getJ();
                        break;
                }
              
               drawIt(g,posicionX,posicionY,j);
            }
        faseFantasmas = false;  
        }                        
    }
        
    public void drawIt(Graphics g,int posicionX,int posicionY,int i){
        
        Color[] color;
        
            if(frame.azules()){
               color = new Color[] {Color.BLUE,Color.BLUE,Color.BLUE,Color.BLUE};
            }else{
               color = new Color[] {Color.PINK,Color.ORANGE,Color.CYAN,Color.RED};
            }
            
            g.setColor(color[i]);
            g.fillArc(posicionX, posicionY-1, anchoCelda, anchoCelda+1 , 310,280);


            int[] x = {posicionX, posicionX+8,posicionX +anchoCelda};
            int[] y = {posicionY+anchoCelda/2, posicionY+13,posicionY+anchoCelda/2};

            g.setColor(color[i]);
            g.fillPolygon(x,y,3);
            
            if(frame.azules()){
               g.setColor(Color.WHITE); 
            }else{
               g.setColor(Color.BLACK);
            }    
            g.fillOval(posicionX+4, posicionY+anchoCelda/5,anchoCelda/5, anchoCelda/5);
            g.fillOval(posicionX+8, posicionY+anchoCelda/5,anchoCelda/5, anchoCelda/5);
    }
    
    public void setParpadeoComecocos(){
        
        this.timer.cancel(); 
        this.timer = new Timer();

        parpadea = true;
        
        TimerTask action = new TimerTask() {
            public void run() {
                parpadea = false;
            }
        };

        this.timer.schedule(action, 2000); //this starts the task
    }
    
     public boolean getParpadeoComecocos(){
        return parpadea;
    }
    
    public void setParpadeoFantasma(int i){
        
        this.timer.cancel(); 
        this.timer = new Timer();
        
        parpadeaF = true;
        fParpadeo = i;
       
        TimerTask action = new TimerTask() {
            public void run() {
                parpadeaF = false;
            }
        };

        this.timer.schedule(action, 4000); //this starts the task
    }
    
   
    
  
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        dibujaRejilla(g);
        dibujaComecocos(g,frame.getComecocos());
        dibujaFantasma(g,frame.getFantasmas());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(424, 470));
        setRequestFocusEnabled(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RejillaPanel.this.keyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 470, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void keyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyPressed
       if(!paused){
        
            if (evt.getKeyCode() == KeyEvent.VK_LEFT){
                if(!frame.getRejilla().colision(frame.getComecocos(),3)){
                    frame.getComecocos().setDireccion(3);
                }    
            }else if (evt.getKeyCode() == KeyEvent.VK_RIGHT){
                if(!frame.getRejilla().colision(frame.getComecocos(),1)){
                    frame.getComecocos().setDireccion(1);
                }    
            }else if (evt.getKeyCode() == KeyEvent.VK_UP){
                if(!frame.getRejilla().colision(frame.getComecocos(),0)){
                    frame.getComecocos().setDireccion(0);
                }    
            }else if (evt.getKeyCode() == KeyEvent.VK_DOWN){
                if(!frame.getRejilla().colision(frame.getComecocos(),2)){
                    frame.getComecocos().setDireccion(2);
                }    
            }
        }
       
        if (evt.getKeyCode() == KeyEvent.VK_SPACE){
            
            if(frame.mueve.getParado()){
                frame.mueve.reanudar();
                paused = false;
            }else{
                frame.mueve.suspender();
                paused=true;
            }
        }
       
    }//GEN-LAST:event_keyPressed

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
          requestFocus();
    }//GEN-LAST:event_formMouseEntered



    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
