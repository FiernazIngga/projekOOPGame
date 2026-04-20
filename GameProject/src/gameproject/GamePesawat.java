package gameproject;

import LoadFile.SoundPlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import gameproject.logic.LogikaGame;
import gameproject.entity.Bullet;
import LoadFile.Ledakan;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class GamePesawat extends JPanel implements KeyListener {

    LogikaGame pesawat = new LogikaGame();

    boolean up, down;
    boolean tembak = false;

    long terakhirTembak = 0;
    final int tembak_cd = 300;

    private BufferedImage backgroundImage;
    private BufferedImage playerImage;
    private BufferedImage enemyImage;
    private BufferedImage explosionImage; // 🔥 TAMBAHAN

    public GamePesawat() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/Resources/Image/backgroundGemini.png"));
            playerImage = ImageIO.read(getClass().getResource("/Resources/Image/PlayerPesawat.png"));
            enemyImage = ImageIO.read(getClass().getResource("/Resources/Image/EnemyPesawat.png"));

            // 🔥 LOAD LEDAKAN
            explosionImage = ImageIO.read(
                getClass().getResource("/Resources/Image/mentahanLedakanFiks.png")
            );

            // 🔥 KIRIM KE LOGIKA
            pesawat.gambarLedakan = explosionImage;

        } catch (Exception e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(1200, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
    }

    public void startGameLoop() {
        SoundPlayer soundPlay = new SoundPlayer("/Resources/Sound/MentahanSound.wav");

        new Thread(() -> {
            while (true) {

                // ===== GERAK PLAYER =====
                if (up) pesawat.player.moveUp();
                if (down) pesawat.player.moveDown();

                long sekarang = System.currentTimeMillis();

                // ===== TEMBAK =====
                if (tembak && sekarang - terakhirTembak >= tembak_cd) {

                    int lebarPeluru = 10;
                    int tinggiPeluru = 5;

                    int peluruX = pesawat.player.x + pesawat.player.width;
                    int peluruY = pesawat.player.y + pesawat.player.height / 2 - tinggiPeluru / 2;

                    pesawat.bullets.add(new Bullet(peluruX, peluruY, 17, true));

                    soundPlay.play();

                    terakhirTembak = sekarang;
                }

                pesawat.update();
                repaint();

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ===== BACKGROUND =====
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }

        // ===== HP =====
        g.setColor(Color.WHITE);
        g.drawString("Player HP: " + pesawat.player.hp, 10, 20);
        g.drawString("Enemy HP: " + pesawat.enemy.hp, 450, 20);

        // ===== PLAYER =====
        if (playerImage != null) {
            g.drawImage(playerImage, pesawat.player.x, pesawat.player.y,
                    pesawat.player.width, pesawat.player.height, null);
        }

        // ===== ENEMY =====
        if (enemyImage != null) {
            g.drawImage(enemyImage, pesawat.enemy.x, pesawat.enemy.y,
                    pesawat.enemy.width, pesawat.enemy.height, null);
        }

        // ===== BULLET =====
        for (Bullet b : pesawat.bullets) {
            if (b.fromPlayer) g.setColor(Color.YELLOW);
            else g.setColor(Color.RED);

            g.fillRect(b.x, b.y, 10, 5);
        }

        // 🔥 ===== LEDAKAN =====
        for (Ledakan l : pesawat.ledakans) {
            g.drawImage(l.getGambar(), l.x, l.y, 60, 60, null);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) up = true;
        if (e.getKeyCode() == KeyEvent.VK_S) down = true;

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            tembak = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) up = false;
        if (e.getKeyCode() == KeyEvent.VK_S) down = false;

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            tembak = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}