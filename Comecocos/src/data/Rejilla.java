/*
////////////////////////////////////////////////////////////////////////////////
/////           PRACTICA 8 - COMECOCOS - CLASE REJILLA                     /////
////////////////////////////////////////////////////////////////////////////////
 */
package data;

import comecocos.ComecocosFrame;
import sound.Sonido;



 /**
 * Clase que contiene el mapa. (Rejilla inicial en forma de array y la matriz
 * de caracteres). Tambien implementa ciertas clases que realizan comprobaciones
 * o modificaciones en la matriz de la rejilla.

 * @author Jose Antonio Hurtado Morón
 */

public class Rejilla {

    //Array de Strings que contienen el mapa inicial.
    /*
    *B -> BLOCK (PARED)
    *. -> PUNTO (LABERINTO)
    *o -> PUNTO GORDO
    *P -> PUERTA (SOLO SE PUEDE ATRAVESAR HACIA ARRIBA)
    */
    private final static String rejillaInicial[] =
       {"BBBBBBBBBBBBBBBBBBBBBBBBBBBB",
        "B............BB............B",
        "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
        "BoBBBB.BBBBB.BB.BBBBB.BBBBoB",
        "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
        "B..........................B",
        "B.BBBB.BB.BBBBBBBB.BB.BBBB.B",
        "B.BBBB.BB.BBBBBBBB.BB.BBBB.B",
        "B......BB....BB....BB......B",
        "BBBBBB.BBBBB BB BBBBB.BBBBBB",
        "BBBBBB.BBBBB BB BBBBB.BBBBBB",
        "BB.....BB          BB.....BB",
        "BB.BBB.BB BBBPPBBB BB.BBB.BB",
        "BB.BBB.BB B      B BB.BBB.BB",
        "  .....   B      B   .....  ",
        "BBBBBB.BB B      B BB.BBBBBB",
        "BB.....BB BBBBBBBB BB.....BB",
        "BB.BBB.BB          BB.BBB.BB",
        "BB.....BB BBBBBBBB BB.....BB",
        "BBBBBB.BB BBBBBBBB BB.BBBBBB",
        "B............BB............B",
        "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
        "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
        "Bo..BB................BB..oB",
        "BBB.BB.BB.BBBBBBBB.BB.BB.BBB",
        "BBB.BB.BB.BBBBBBBB.BB.BB.BBB",
        "B......BB....BB....BB......B",
        "B.BBBBBBBBBB.BB.BBBBBBBBBB.B",
        "B.BBBBBBBBBB.BB.BBBBBBBBBB.B",
        "B..........................B",
        "BBBBBBBBBBBBBBBBBBBBBBBBBBBB"};


    /**Anchura del laberinto.*/
    private final int anchura;
    /*Altura del laberinto.*/
    private final int altura;
    /**Matriz que almacena el laberinto.*/
    private char[][] map;
    /**Frame principal.*/
    ComecocosFrame frame;

    /**Constructor Principal*/

    public Rejilla(ComecocosFrame fr){

        this.frame = fr;
        this.anchura = 28;
        this.altura = 31;

        this.map = new char[altura][anchura];
        map = getInitalMap();
    }

     /**Devuelve la cantidad de celdas de ancho que posee el laberinto.
     *      @return anchura
     */
    public int getAnchura(){
        return anchura;
    }
     /**Devuelve la cantidad de celdas de alto que posee el laberinto.
     *      @return alto
     */
    public int getAltura(){
        return altura;
    }

     /**Devuelve el tipo de la celda demarcada por i,j.
     *      @return char
     */
    public char getCelda(int i, int j){
        return map[i][j];
    }

     /**Modifica el tipo de la celda i,j con el valor C.
     */
    public void setCelda(int i, int j,char type){
        map[i][j] = type;
    }

    /**Clase que comprueba si una figura colisiona en su siguiente movimiento.
    *(Definido por su direccion)

    *     @param f figura
    *     @param direccion dirección que lleva la figura

    *     @return boolean
    */

    public boolean colision(Figura f, int direccion){

        char nextCelda;
        boolean colision = true;

            switch (direccion) {

            case 0:
                nextCelda = map[f.getJ()-1][f.getI()];
                if(nextCelda == '.' || nextCelda == ' ' || nextCelda == 'o' || nextCelda == 'P'){
                    colision = false;
                }
                break;
            case 1:
                nextCelda = map[f.getJ()][f.getI()+1];
                if(nextCelda == '.' || nextCelda == ' ' || nextCelda == 'o'){
                    colision = false;
                }
                break;
            case 2:
                nextCelda = map[f.getJ()+1][f.getI()];
                if(nextCelda == '.' || nextCelda == ' ' || nextCelda == 'o'){
                    colision = false;
                }
                break;

             case 3:
                nextCelda = map[f.getJ()][f.getI()-1];
                if(nextCelda == '.' || nextCelda == ' ' || nextCelda == 'o'){
                    colision = false;
                }
                break;
            default:
                break;
        }

         return colision;
    }

    /**Clase que comprueba si el comecocos se comerá un punto en su siguiente
    *movimiento

    *     @param f figura
    *     @param direccion dirección del comecocos
    */

    public void comePunto(Figura f, int direccion){
        char nextCelda;
        boolean colision = true;

            switch (direccion) {

            case 0:
                nextCelda = map[f.getJ()-1][f.getI()];
                if(nextCelda == '.'){
                    setCelda(f.getJ()-1,f.getI(),' ');
                    frame.addPuntos(10);
                }
                else if(nextCelda == 'o'){
                    setCelda(f.getJ()-1,f.getI(),' ');
                    frame.setAzules();
                }
                break;
            case 1:
                nextCelda = map[f.getJ()][f.getI()+1];
                if(nextCelda == '.'){
                    setCelda(f.getJ(),f.getI()+1,' ');
                    frame.addPuntos(10);
                }
                else if(nextCelda == 'o'){
                    setCelda(f.getJ(),f.getI()+1,' ');
                    frame.setAzules();
                }
                break;
            case 2:
                nextCelda = map[f.getJ()+1][f.getI()];
                if(nextCelda == '.'){
                    setCelda(f.getJ()+1,f.getI(),' ');
                    frame.addPuntos(10);
                }
                else if(nextCelda == 'o'){
                    setCelda(f.getJ()+1,f.getI(),' ');
                    frame.setAzules();
                }
                break;

             case 3:
                nextCelda = map[f.getJ()][f.getI()-1];
                if(nextCelda == '.'){
                    setCelda(f.getJ(),f.getI()-1,' ');
                    frame.addPuntos(10);
                }
                else if(nextCelda == 'o'){
                    setCelda(f.getJ(),f.getI()-1,' ');
                    frame.setAzules();
                }
                break;
            default:
                break;
        }

        if(nivelCompleto()){

            reiniciar();
            frame.nextLevel();
        }
    }

    /**Clase que comprueba si ya se ha comido todos los puntos (nivel completado)
    */

    public boolean nivelCompleto(){

        boolean completo = true;
        for (int i=0;i<getAnchura();i++){
            for(int j=0;j<getAltura(); j++){
                if(map[j][i] == '.' || map[j][i] == 'o'){
                    completo = false;
                }
            }
        }
        return completo;
    }

    public void reiniciar(){
        this.getInitalMap();
    }

    /**Convierte el string inicial a una matriz de chars que delimita
     la rejilla de juego
     */
    private char[][] getInitalMap() {

        for(int i=0; i<altura; i++){
            for(int j=0;j<anchura;j++){
                map[i][j]=rejillaInicial[i].charAt(j);
            }
        }

        return map;
    }
}
