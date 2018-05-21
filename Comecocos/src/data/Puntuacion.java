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
public class Puntuacion {
    
    private int puntos;
    
    public Puntuacion(){
        this.puntos = 0;
    }
    
    public void addPuntos(int puntuacion){
        this.puntos += puntuacion;
    }
    
    public int getPuntuacion(){
        return puntos;
    } 
}
