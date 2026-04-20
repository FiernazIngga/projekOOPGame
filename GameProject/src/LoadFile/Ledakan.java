package LoadFile;

import java.awt.image.BufferedImage;

public class Ledakan {
    public int x, y;
    public int duration = 10; // lama tampil (frame)

    private BufferedImage gambar;

    public Ledakan(int x, int y, BufferedImage gambar) {
        this.x = x;
        this.y = y;
        this.gambar = gambar;
    }

    public void update() {
        duration--;
    }

    public boolean isHabis() {
        return duration <= 0;
    }

    public BufferedImage getGambar() {
        return gambar;
    }
}