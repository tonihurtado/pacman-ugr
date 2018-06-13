/*
////////////////////////////////////////////////////////////////////////////////
/////           PRACTICA 8 - COMECOCOS - CLASE MUEVE                       /////
////////////////////////////////////////////////////////////////////////////////
 */

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
     * la hebra al ComecocosMidlet, establece el retardo en milisegundos
     * entre movimiento y movimiento de la Figura actual, y comienza a ejecutar
     * la hebra.

     * @author Jose Antonio Hurtado Morón
     */

    public Mueve(ComecocosFrame fr){
        frame=fr;
        delay= 175;
        continuar=true;
        suspendFlag=false;

        //Algoritmo implementa la clase Fantasmas, para el movimiento de los
        //fantasmas.
        algoritmo = new Fantasmas(fr,fr.getFantasmas());
    }

    /**
     * Código que constituye las sentencias de la tarea. Se encarga
     * de hacer que se mueva continuamente tanto el comecocos como
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

                // Al igual que a la hora de dibujar los graficos, en la ejecución también usaremos dos fases,
                // para crear una ejecucion mas fluida.

                if(!fase){

                //Esta fase se ejecuta cuando el comecocos / fantasmas se mueven a una fase intermedia, en la que se dibujaran
                //las figuras entre las dos celdas. En esta fase comprobaremos si el comecocos se come un punto
                //en la siguiente casilla, y decidimos la dirección de los fantasmas para el siguiente movimiento.
                //
                //También comprobamos si existe colisión entre algun fantasma y el comecocos, y en el caso afirmativo,
                //decidimos en función de el modo 'fantasmas comestibles'.

                 if(!frame.getRejilla().colision(frame.getComecocos(),frame.getComecocos().getDireccion())){
                    frame.getRejilla().comePunto(frame.getComecocos(), frame.getComecocos().getDireccion());
                 }

                 for(int i=0;i<frame.getFantasmas().length;i++){

                    //Los fantasmas parase se moveran en funcion de la distancia euclidea, y los otros dos de forma
                    //aleatoria.

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

                 //Refrescamos los graficos
                 frame.repaint();
                 //Alternamos fase
                 fase = true;
                }
                else{

                //Esta será la fase en la que el comecocos y fantasmas se situarán en el centro de la celda. Aqui moveremos
                //la figura a su siguiente posición.

                 if(!frame.getRejilla().colision(frame.getComecocos(),frame.getComecocos().getDireccion())){
                    frame.getComecocos().mueve(frame.getComecocos().getDireccion());
                 }


                 for(int i=0;i<frame.getFantasmas().length;i++){
                    frame.getFantasmas()[i].mueve(frame.getFantasmas()[i].getDireccion());
                 }

                 //Alternamos fase
                 fase = false;
                 //Refrescamos los graficos
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
