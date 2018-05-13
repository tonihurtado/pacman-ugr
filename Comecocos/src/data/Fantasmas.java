/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import comecocos.ComecocosFrame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Toni
 */
public class Fantasmas {
    
    private Random r = new Random();
    private final Figura[] fantasmas;
    ComecocosFrame frame;
    
    public Fantasmas(ComecocosFrame fr, Figura[] fantasmas){
        
        this.fantasmas = fantasmas;
        this.frame = fr;
    }
    
    public int movimientoFantasmaAleatorio(int index){
        
            
            Figura fantasma = fantasmas[index];
            int[] posibles = {0,3,1}; 
            int direccion = fantasma.getDireccion();
            ArrayList<Integer> puede = new ArrayList<Integer>();
            
            for(int i=0;i<3;i++){
                if(!frame.getRejilla().colision(fantasma,(fantasma.getDireccion()+posibles[i])%4)){
                    puede.add((fantasma.getDireccion()+posibles[i])%4);
                }
            }  
            System.out.println(puede);
                
            if(!puede.isEmpty()){
               direccion = r.nextInt(puede.size()); 
            }
            System.out.println(puede.get(direccion));
               
            return puede.get(direccion);
    }
    
        public int movimientoFantasmaEuclideo(int index){
            Figura fantasma = fantasmas[index];
            int[] posibles = {0,3,1}; 
            int[] next;
            int direccion = fantasma.getDireccion();
            ArrayList<Integer> puede = new ArrayList<Integer>();
            ArrayList<Integer> distancia = new ArrayList<Integer>();
            
            for(int i=0;i<3;i++){
                if(!frame.getRejilla().colision(fantasma,(fantasma.getDireccion()+posibles[i])%4)){

                    puede.add((fantasma.getDireccion()+posibles[i])%4);

                    next = getNextPosition(fantasma.getI(),fantasma.getJ(),(fantasma.getDireccion()+posibles[i])%4);
                    distancia.add((int)distanciaEuclidea(frame.getComecocos(),next[0],next[1]));
                }
            }  
            
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
        
    public boolean colisionComecocos(Figura comecocos){
        
        boolean colision = false;
        int[] posicionComecocos = {comecocos.getI(),comecocos.getJ()};
        int[] nextComecocos = getNextPosition(comecocos.getI(),comecocos.getJ(),comecocos.getDireccion());
        
        
        for(int i=0; i<fantasmas.length; i++){
          
            int[] posicionFantasma = {fantasmas[i].getI(),fantasmas[i].getJ()};
            int[] nextFantasma = getNextPosition(fantasmas[i].getI(),fantasmas[i].getJ(),fantasmas[i].getDireccion());
            
            if((posicionFantasma[0] == posicionComecocos[0] && posicionFantasma[1] == posicionComecocos[1]) ||
                    (nextFantasma[0] == nextComecocos[0] && nextFantasma[1] == nextComecocos[1])){
                colision = true;
            }
        }
        
        return colision;
    }
   
        
    //HELPERS
        
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
        
    private static double distanciaEuclidea(Figura comecocos, int x, int y){
        return Math.sqrt(Math.pow((comecocos.getI()-x),2) + Math.pow((comecocos.getJ()-y),2));
    }
}
