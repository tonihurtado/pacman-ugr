
/*
////////////////////////////////////////////////////////////////////////////////
/////           PRACTICA 8 - COMECOCOS - CLASE FANTASMAS                   /////
////////////////////////////////////////////////////////////////////////////////
 */

 package data;


 import comecocos.ComecocosFrame;
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.Random;

/**
*   En esta clase vamos a implementar diferentes metodos relacionados con el
*   movimiento de los fantasmas. Nos serán de utilidad tanto para programar el
*   algoritmo que decidira el movimiento de los fantasmas, como para definir que
*   ocurre cuando entramos en el modo "fantasmas comestibles".

*   @author Jose Antonio Hurtado Morón
*/

public class Fantasmas {

    private ComecocosFrame frame;
    private Random r = new Random();

    private final Figura[] fantasmas;

    // fantasmaColision especifica en caso de que un fantasma se coma al come-
    //  cocos que fantasma ha sido.

    private int fantasmaColision;

////////////////////////////////////////////////////////////////////////////////
//                              CONSTRUCTOR ()                                //
////////////////////////////////////////////////////////////////////////////////

    public Fantasmas(ComecocosFrame fr, Figura[] fantasmas){

        this.fantasmas = fantasmas;
        this.frame = fr;
        this.fantasmaColision = 0;
    }

////////////////////////////////////////////////////////////////////////////////
//                                METODOS ()                                  //
////////////////////////////////////////////////////////////////////////////////

//___________________________ALGORITMOS MOVIMIENTO______________________________

/**
*   Para los algoritmos que definirán el movimiento de los fantasmas vamos a
*   implementar dos métodos:

*     1. Movimiento movimiento Aleatorio
*     2. Movimiento en función de la distancia euclidea

*    Dos de los fantasmas se guiaran por el algoritmo aleatorio, y otros dos
*    por el de la distancia (esto se implementa en Mueve a la hora de pasar
*    {index} a esta clase).

*   Los implementaremos de forma que no les esta permitido moverse en la
*   direccion opuesta a la que lleva.
*   Para ello utilizamos un array `posibles` que vamos sumando a la dirección
*   del fantasma {dirección + 0 : dirección actual}, {dirección + 1: siguiente
*   dirección en el sentido de las agujas del reloj}, {dirección + 3: siguiente
*   dirección en el sentido antihorario (siempre trabajando en módulo 4)}
*/

/****************** movimientoFantasmaAleatorio *******************************

*   Algoritmo que de forma aleatoria, utilizando la clase Random, devuelve la
*   dirección de movimiento de el fantasma {index}.
*/
    public int movimientoFantasmaAleatorio(int index){


            Figura fantasma = fantasmas[index];
            int[] posibles = {0,3,1};
            int direccion = fantasma.getDireccion();
            ArrayList<Integer> puede = new ArrayList<Integer>();

            //Calculamos las posibles direcciones a las que se puede mover:

            for(int i=0;i<3;i++){
                if(!frame.getRejilla().colision(
                  fantasma,(fantasma.getDireccion()+posibles[i])%4)
                ){
                    puede.add((fantasma.getDireccion()+posibles[i])%4);
                }
            }

            //Elegimos una de ellas aleatoriamente (r = new Random())

            if(!puede.isEmpty()){
               direccion = r.nextInt(puede.size());
            }
            System.out.println(puede.get(direccion));

            return puede.get(direccion);
    }

/****************** movimientoFantasmaAleatorio *******************************

*   Algoritmo que calcula la distancia euclidea desde cada una de las siguientes
*   celdas a las que se puede mover el fantasma {index} hasta el comecocos, y
*   elige aquella que:

      - Sea mas cercana al comecocos (modo normal)
      - Sea mas lejana al comecocos (caso fantasmas azules)
*/

        public int movimientoFantasmaEuclideo(int index){
            Figura fantasma = fantasmas[index];
            int[] posibles = {0,3,1};
            int[] next;
            int direccion = fantasma.getDireccion();
            ArrayList<Integer> puede = new ArrayList<Integer>();
            ArrayList<Integer> distancia = new ArrayList<Integer>();

            //Calculamos las posibles direcciones a las que se puede mover:

            for(int i=0;i<3;i++){
                if(!frame.getRejilla().colision(
                  fantasma,(fantasma.getDireccion()+posibles[i])%4)
                ){
                    puede.add((fantasma.getDireccion()+posibles[i])%4);

                    //Calculamos la distancia euclidea desde la siguiente
                    //celda posible y la guardamos en 'distancia':

                    next = getNextPosition(fantasma.getI(),fantasma.getJ(),
                    (fantasma.getDireccion()+posibles[i])%4);

                    distancia.add((int)distanciaEuclidea(
                      frame.getComecocos(),next[0],next[1])
                    );
                }
            }

            // Diferenciamos el caso de los fantasmas azules del nomal, y para
            // cada caso elegimos la dirección en función de la distancia idonea.

            // Como apunte, decir que he implementado una funcion para que en el
            // caso de que la distancia sea mayor de cierto valor, el fantasma
            // se movera de forma aleatoria en vez de guiarse por la distancia,
            // para darle algo de aletoriedad a sus movimientos.

            if(frame.azules()){
                if(!puede.isEmpty()){
                    if (Collections.max(distancia)>5){
                        direccion = puede.get(distancia.indexOf(Collections.max(distancia)));
                    }else{
                        direccion = puede.get(r.nextInt(puede.size()));
                    }
                }
                return direccion;
            }else{

                if(!puede.isEmpty()){
                    if (Collections.min(distancia)<10){
                        direccion = puede.get(distancia.indexOf(Collections.min(distancia)));
                    }else{
                        direccion = puede.get(r.nextInt(puede.size()));
                    }
                }
                return direccion;
            }
    }

//________________________METODO COLISIÓN COMECOCOS_____________________________

/**
*  Metodo que comprueba si algun fantasma se come al comecocos. Utilizaremos
*  un metodo secundario (getNextPosition) para en funcion de la posicion de la
*  figura obtener su siguiente posición, y comprobaremos las posibles combina-
*  ciones para cada fantasma y el comecocos.
*/

    public boolean colisionComecocos(Figura comecocos){

       boolean colision = false;

       if(!frame.getPanel().getParpadeoComecocos()){

        int[] posicionComecocos = {comecocos.getI(),comecocos.getJ()};
        int[] nextComecocos = getNextPosition(comecocos.getI(),comecocos.getJ(),comecocos.getDireccion());

        for(int i=0; i<fantasmas.length; i++){

            int[] posicionFantasma = {fantasmas[i].getI(),fantasmas[i].getJ()};
            int[] nextFantasma = getNextPosition(fantasmas[i].getI(),fantasmas[i].getJ(),fantasmas[i].getDireccion());

            if((posicionFantasma[0] == posicionComecocos[0] && posicionFantasma[1] == posicionComecocos[1]) ||
                    (nextFantasma[0] == nextComecocos[0] && nextFantasma[1] == nextComecocos[1]) ||
                    (nextComecocos[0] == posicionFantasma[0] && nextComecocos[1] == posicionFantasma[1]) ||
                    (nextFantasma[0] == posicionComecocos[0] && nextFantasma[1] == posicionComecocos[1])){
                colision = true;
                fantasmaColision = i;
            }
        }
       }
       return colision;
    }


//_______________________________HELPERS________________________________________

  //Devuelve la siguiente posicion en función de la posicion actual y la direccion

    private int[] getNextPosition(int x,int y, int direccion){
        switch(direccion){
            case 0:
                y--;
                break;
            case 1:
                x++;
                break;
            case 2:
                y++;
                break;
            case 3:
                x--;
                break;
            default:
                break;
        }

        int[] par = {x,y};
        return par;
    }

    //devuelve el fantasma que se ha comido el comecocos (en el caso Fantasmas
    //Azules)

    public int getFantasmaPosicion(int i, int j){
        return fantasmaColision;
    }

    //Distancia Euclidea

    private static double distanciaEuclidea(Figura comecocos, int x, int y){
        return Math.sqrt(Math.pow((comecocos.getI()-x),2) + Math.pow((comecocos.getJ()-y),2));
    }
}
