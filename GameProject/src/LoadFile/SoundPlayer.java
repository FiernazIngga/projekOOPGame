package LoadFile;

import javax.sound.sampled.*;
import java.net.URL;

public class SoundPlayer {
    private Clip[] clip;
    private int indexSekarang = 0;
    
    public SoundPlayer(String path) {
        this(path, 5); 
    }
    
    public SoundPlayer(String path, int size) {
        try {
            
            URL url = getClass().getResource(path);

            if (url == null) {
                System.out.println("ERROR: file tidak ditemukan -> " + path);
                return;
            }
            
            clip = new Clip[size];
            
            for (int i = 0; i < size; i++) {
                AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(path));
                clip[i] = AudioSystem.getClip();
                clip[i].open(audio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void play() {
        Clip klipSuara = clip[indexSekarang];
        
        if (klipSuara.isRunning()) {
            klipSuara.stop();
        }

        klipSuara.setFramePosition(0);
        klipSuara.start();

        indexSekarang = (indexSekarang + 1) % clip.length;
    }
}
