package lab2;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Musica {

    private static Clip clipActual;
    private static FloatControl controlVolumen;

    private static void reproducirLoop(String rutaRecurso) {

        detener(); 

        try {
      
            URL urlAudio = Musica.class.getResource(rutaRecurso);

            if (urlAudio == null) {
                System.out.println("⚠ No se encontró la pista: " + rutaRecurso);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(urlAudio);
            clipActual = AudioSystem.getClip();
            clipActual.open(audioStream);

         
            if (clipActual.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                controlVolumen = (FloatControl) clipActual.getControl(FloatControl.Type.MASTER_GAIN);
                controlVolumen.setValue(-8f); 
                controlVolumen.setValue(-8f); 
            }

            clipActual.loop(Clip.LOOP_CONTINUOUSLY);
            clipActual.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("❌ Error al reproducir WAV: " + e.getMessage());
        }
    }

    
    public static void reproducirMenu() {
        reproducirLoop("/lab2/EOL.wav");
    }

 
    public static void reproducirJuego() {
        reproducirLoop("/lab2/F.wav");
    }

   
    public static void detener() {
        if (clipActual != null) {
            clipActual.stop();
            clipActual.close();
            clipActual = null;
            controlVolumen = null;
        }
    }

   
    public static void setVolumenDb(float db) {
        if (controlVolumen != null) {
            controlVolumen.setValue(db);
        }
    }
}
