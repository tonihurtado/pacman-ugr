package sound;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Cristian Almohalla - Jose Carreto
 * Esta clase nos permite implementar un añadido adicional que queriamos incluir
 * que es el sonido. Con ella podremos construir objetos en determinados puntos
 * del codigo para añadir sonido al juego, ya sea por el inicio del juego,
 * muerte del jugador, comida de puntos o paso de nivel. La forma de logralo la
 * hemos encontrado en tutoriales de internet que explicaban el funcionamiento
 * de metodos como getClip o getAudioInputStream, en sí es muy parecido a lo que
 * hicimos en practicas anteriores que requerian lecturas de flujos de bytes
 */
public class Sonido extends Thread{
    //_______________________________VARIABLES________________________________//
    /**
     * Objeto de la clase clip que contendra el sonido
     */
    private Clip sonido; 
    /**
     * Entero usado para indicar el delay o tiempo durante el cual estará sonando
     * el sonido
     */
    private int delay;
    /**
     * Lcalizacion del archivo de entrada
     */
    private URL in;
    //private InputStream in = this.getClass().getResourceAsStream("pacman_chomp_short.wav");
    //private File in = new File("pacman_chomp_short.wav");
    //_____________________________CONSTRUCTORES______________________________//
    /**
     * Este constructor permite operar con un sonido previamente "cargado" 
     * llamandolo mediante su nombre
     * @param tiempo: Entero que representa el delay a usar
     * @param nombreSonido: Indicativo del sonido a usar
     */
    public Sonido(int tiempo, String nombreSonido){
        this.delay=tiempo;
        in= Sonido.class.getResource(nombreSonido);
        try {
            sonido = AudioSystem.getClip();
            sonido.open(AudioSystem.getAudioInputStream(in));
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }
    //________________________________METODOS_________________________________//
    @Override
    public void run()
    {
        sonido.stop();
        sonido.start();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
        }
        sonido.stop();
    }
        
}
