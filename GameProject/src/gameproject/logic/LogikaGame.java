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

    // 🔥 gambar ledakan (dikirim dari GamePesawat)
    public BufferedImage gambarLedakan;

    public void update() {

        // ===== ENEMY AI =====
        enemy.followPlayer(player);

        // ===== ENEMY SHOOT =====
        if (Math.random() < 0.03) {

            // ⚠️ sound kamu tetap (tidak diubah)
            SoundPlayer soundPlay = new SoundPlayer("/Resources/Sound/MentahanSound.wav");

            int bulletWidth = 10;
            int bulletHeight = 5;

            int bulletX = enemy.x - (bulletWidth - 60);
            int bulletY = enemy.y + enemy.height / 2 - bulletHeight / 2;

            soundPlay.play();

            bullets.add(new Bullet(bulletX, bulletY, -17, false));
        }

        // ===== UPDATE BULLETS =====
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);

            b.move();

            boolean kena = false;

            // ===== PLAYER → ENEMY =====
            if (b.fromPlayer && Collision.check(
                    b.x, b.y, 10, 5,
                    enemy.x + 50, enemy.y + 20,
                    enemy.width - 100, enemy.height - 40)) {

                enemy.hp -= 10;

                // 🔥 TAMBAH LEDAKAN (pakai gambar, bukan path)
                if (gambarLedakan != null) {
                    ledakans.add(new Ledakan(b.x-25, b.y-25, gambarLedakan));
                }

                kena = true;
            }

            // ===== ENEMY → PLAYER =====
            else if (!b.fromPlayer && Collision.check(
                    b.x, b.y, 10, 5,
                    player.x, player.y,
                    player.width - 50, player.height - 40)) {

                player.hp -= 10;
                kena = true;
            }

            // ===== HAPUS BULLET =====
            if (kena) {
                bullets.remove(i);
                i--;
            } else if (b.x > 1200 || b.x < 0) {
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

        // ===== GAME OVER =====
        if (player.hp <= 0 || enemy.hp <= 0) {
            gameOver = true;
        }
    }
}