/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Toni
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
