package data;
import comecocos.ComecocosFrame;
import sound.Sonido;


public class Mueve implements Runnable{
     
    private int delay;
    private boolean continuar;
    private boolean suspendFlag;
    private Fantasmas algoritmo;
    private boolean fase = true;
    private ComecocosFrame frame;
       
    /**
     * Constructor de la clase, que inicializa la referencia utilizadas por
     * la hebra al TetrisMidlet, establece el retardo en milisegundos
     * entre movimiento y movimiento de la Figura actual, y comienza a ejecutar
     * la hebra. 
     */
    public Mueve(ComecocosFrame fr){
        frame=fr;
        delay= 175;
        continuar=true;
        suspendFlag=false;
        algoritmo = new Fantasmas(fr,fr.getFantasmas());
    }
    
    /**
     * Código que constituye las sentencias de la tarea. Se encarga
     * de hacer que se mueva hacia abajo continuamente tanto el comecocos como
     * los fantasmas.
     * También se encarga de ir refrescando la pantalla dónde se dibuja todo. 
     */
    public void run(){
        try{
            while(continuar){
                synchronized(this){
                    while(suspendFlag){
                        wait();
                    }
                }
                Thread.sleep(delay);
                if(!fase){
                    
                 if(!frame.getRejilla().colision(frame.getComecocos(),frame.getComecocos().getDireccion())){ 
                    frame.getRejilla().comePunto(frame.getComecocos(), frame.getComecocos().getDireccion());
                 }
                 
                 for(int i=0;i<frame.getFantasmas().length;i++){
                    
                    if(i%2 == 0){
                       frame.getFantasmas()[i].setDireccion(algoritmo.movimientoFantasmaEuclideo(i));
                    }else{
                       frame.getFantasmas()[i].setDireccion(algoritmo.movimientoFantasmaAleatorio(i)); 
                    }

                 }
                 
                if(algoritmo.colisionComecocos(frame.getComecocos())){
                    
                    if(frame.azules()){
                        frame.fantasmaMuerto(algoritmo.getFantasmaPosicion(frame.getComecocos().getI(),frame.getComecocos().getJ()));
                    }else{
                        frame.muerto();
                    }
                 }
                 
                 frame.repaint();
                 fase = true;
                }
                else{
                    
                 if(!frame.getRejilla().colision(frame.getComecocos(),frame.getComecocos().getDireccion())){
                    frame.getComecocos().mueve(frame.getComecocos().getDireccion());     
                 }   
                 

                 for(int i=0;i<frame.getFantasmas().length;i++){
                    frame.getFantasmas()[i].mueve(frame.getFantasmas()[i].getDireccion());
                    if(!frame.sonando){
                        Sonido sonido = new Sonido(500,"chomp.wav");
                        sonido.start();
                    }
                 }
                 fase = false;
                 frame.repaint();
                }
            }// end while(continuar)
        } catch(InterruptedException e){
            System.out.println("Hebra MueveFigura interrumpida");
        }   
    }
    
    /**
     * Detiene momentaneamente la ejecución de la hebra, haciendo que la Figura actual
     * quede parada.
     */
    synchronized public void suspender(){
        frame.getPanel().repaint();
        suspendFlag=true;
    }
    
    /**
     * Reanuda el movimiento de la hebra. La Figura actual vuelve  a moverse.
     */
    public synchronized void reanudar(){
        if(frame.getPanel()!=null)
            frame.getPanel().repaint();
        suspendFlag = false;
        notify();
    }
    
    /**
     * Termina la ejecución de la hebra.
     */
    public void terminar(){
        continuar=false;
    }
    
    /**
     * Devuelve si la hebra ha terminado su ejecución
     * @return true si la hebra ha terminado su ejecución
     */
    public boolean getTerminado(){
        return !continuar;
    }
    
    /**
     * Nos dice si la hebra está o no parada.
     * @return true si la hebra de movimiento está parada, false en otro caso
     */
    synchronized public boolean getParado(){
        return suspendFlag;
    }
    
    public void nextLevel(){
        delay-=25;
    }
    
    /**
     * La siguiente función actualiza el retardo que espera la hebra
     * para mover la Figura actual. El nivel más lento será
     * el 0 (retardo 700) y el más rápido el 10 (retardo 50)
     */
    private int actualizaRetardo(int nivel) {
        if (nivel>10) nivel=10;
        else if (nivel<0) nivel=0;
        return ( 400-(nivel*35) );
    }
}
