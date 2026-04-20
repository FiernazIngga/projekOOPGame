package gameproject.logic;

import gameproject.entity.Bullet;
import gameproject.entity.Enemy;
import gameproject.entity.Player;
import LoadFile.SoundPlayer;
import LoadFile.Ledakan;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class LogikaGame {
    public Player player = new Player(600);
    public Enemy enemy = new Enemy(1200, 600);
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Ledakan> ledakans = new ArrayList<>();
    public boolean gameOver = false;
    public BufferedImage gambarLedakan;

    // --- FUNGSI KHUSUS UNTUK BOT ---
    public void updateAI() {
        // Gerakan otomatis mengikuti player
        enemy.followPlayer(player);

        // Tembak otomatis (Random)
        if (Math.random() < 0.03) {
            SoundPlayer soundPlay = new SoundPlayer("/Resources/Sound/MentahanSound.wav");
            int bulletX = enemy.x - 10;
            int bulletY = enemy.y + enemy.height / 2;
            soundPlay.play();
            bullets.add(new Bullet(bulletX, bulletY, -17, false));
        }
    }

    public void update() {
        // HAPUS bagian enemy.followPlayer dan tembak otomatis dari sini!
        // Biarkan GamePesawat yang menentukan kapan memanggil updateAI()

        // ===== UPDATE BULLETS =====
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.move();
            boolean kena = false;

            // PLAYER -> ENEMY
            if (b.fromPlayer && Collision.check(b.x, b.y, 10, 5, enemy.x + 50, enemy.y + 20, enemy.width - 100, enemy.height - 40)) {
                enemy.hp -= 10;
                if (gambarLedakan != null) ledakans.add(new Ledakan(b.x - 25, b.y - 25, gambarLedakan));
                kena = true;
            }
            // ENEMY -> PLAYER
            else if (!b.fromPlayer && Collision.check(b.x, b.y, 10, 5, player.x, player.y, player.width - 50, player.height - 40)) {
                player.hp -= 10;
                if (gambarLedakan != null) ledakans.add(new Ledakan(b.x - 25, b.y - 25, gambarLedakan)); // Tambah ledakan di player juga
                kena = true;
            }

            if (kena || b.x > 1200 || b.x < 0) {
                bullets.remove(i);
                i--;
            }
        }

        // ===== UPDATE LEDAKAN =====
        for (int i = 0; i < ledakans.size(); i++) {
            Ledakan l = ledakans.get(i);
            l.update();
            if (l.isHabis()) {
                ledakans.remove(i);
                i--;
            }
        }

        if (player.hp <= 0 || enemy.hp <= 0) gameOver = true;
    }
}