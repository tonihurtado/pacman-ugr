/*
////////////////////////////////////////////////////////////////////////////////
/////           PRACTICA 8 - COMECOCOS - CLASE FIGURA                      /////
////////////////////////////////////////////////////////////////////////////////
 */

package data;

/**
*Esta clase define una unidad elemental de nuestro juego, que serán las figuras.
*Cada figura contará con dos enteros i e j que definirán la posición de la
*figura, y un entero que definirá la dirección, donde usaremos:
*
* 0 -> arriba
* 1 -> derecha
* 2 -> abajo
* 3 -> izquierda
*
* @author Jose Antonio Hurtado Morón
*/
public class Figura {


    private int direccion;
    private int i;
    private int j;

    public Figura(int i, int j, int direccion){
        this.i = i;
        this.j = j;
        this.direccion=direccion;
    }

//___________________________GETTER & SETTER____________________________________

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion){
        this.direccion = direccion;
    }

    public int getI() {
        return i;
    }

    public void setI(int i){
        this.i=i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j){
        this.j=j;
    }

//______________________________MUEVE___________________________________________

  //El metodo mueve desplazará la posición de la figura en función del parametro
  //dirección que se le pase.

    public void mueve(int direccion){

        if (j==14 && i==1 && direccion == 3){
            this.setI(27);
        }else if (j == 14 && i == 26 && direccion == 1){
            this.setI(0);
        }

        switch (direccion) {

            case 0: //ARRIBA
                this.j--;
                break;
            case 1:  //DERECHA
                this.i++;
                break;
            case 2:  //ABAJO
                this.j++;
                break;
            case 3:  //IZQUIERDA
                this.i--;
                break;
            default:
                break;
        }
    }
}
