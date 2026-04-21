package gameproject.logic;

import GameStart.FrameMain;
import LoadFile.SoundPlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import gameproject.entity.Bullet;
import LoadFile.Ledakan;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;

public class GamePesawat extends JPanel implements KeyListener {
    LogikaGame pesawat = new LogikaGame();
    String namaP1, namaP2, path;
    boolean isBotMode;
    
    // Kontrol Player 1
    boolean up, down, tembak = false;
    // Kontrol Player 2 (Tambahan)
    boolean up2, down2, tembak2 = false;
    
    long terakhirTembak = 0;
    long terakhirTembak2 = 0; // CD Tembak P2
    final int tembak_cd = 300;
    
    private BufferedImage backgroundImage, playerImage, enemyImage, explosionImage;
    FrameMain mainFrame;

    public GamePesawat(FrameMain frame, String pes1, String pes2, String n1, String n2, boolean isBot) {
        this.mainFrame = frame;
        this.namaP1 = n1;
        this.namaP2 = n2;
        this.isBotMode = isBot;

        try {
            backgroundImage = ImageIO.read(getClass().getResource(pilihBackground()));
            explosionImage = ImageIO.read(getClass().getResource("/Resources/Image/mentahanLedakanFiks.png"));
            
            // LOAD GAMBAR PLAYER 1
            playerImage = ImageIO.read(getClass().getResource("/Resources/Image/" + pes1.replace(" ", "") + ".png"));

            // LOAD GAMBAR PLAYER 2 / MUSUH
            if (isBotMode) {
                enemyImage = ImageIO.read(getClass().getResource("/Resources/Image/pesawatMusuh.png"));
            } else {
                if (pes2.equals("Pesawat Alien")) {
                    path = "/Resources/Image/musuhAlien.png";
                } else if (pes2.equals("Pesawat Phantom")) {
                    path = "/Resources/Image/musuhPhantom.png";
                } else if (pes2.equals("Pesawat Rusia")) {
                    path = "/Resources/Image/musuhDarker.png";
                } else {
                    // Default untuk pesawat player 1 (Amerika, Jepang, Indonesia)
                    path = "/Resources/Image/" + pes2.replace(" ", "") + ".png";
                }
                enemyImage = ImageIO.read(getClass().getResource(path));
            }

            pesawat.gambarLedakan = explosionImage;
        } catch (Exception e) {
            System.out.println("Error: Gambar tidak ditemukan!");
            e.printStackTrace();
        }
        
        setPreferredSize(new Dimension(1200, 600));
        setFocusable(true);
        addKeyListener(this);
    }

    // Cari bagian startGameLoop() dan ganti isinya dengan ini:
    public void startGameLoop() {
        SoundPlayer soundPlay = new SoundPlayer("/Resources/Sound/MentahanSound.wav");
        new Thread(() -> {
            while (!pesawat.gameOver) { // Loop berjalan selama belum game over
                // LOGIKA GERAK PLAYER 1
                if (up) pesawat.player.moveUp();
                if (down) pesawat.player.moveDown();

                // LOGIKA GERAK PLAYER 2 / BOT (SINKRONISASI)
                if (isBotMode) {
                    pesawat.updateAI(); // Panggil fungsi AI yang kita buat tadi
                } else {
                    if (up2) pesawat.enemy.moveUp();
                    if (down2) pesawat.enemy.moveDown();
                }

                long sekarang = System.currentTimeMillis();

                // TEMBAK PLAYER 1
                if (tembak && sekarang - terakhirTembak >= tembak_cd) {
                    pesawat.bullets.add(new Bullet(pesawat.player.x + 60, pesawat.player.y + 30, 17, true));
                    soundPlay.play();
                    terakhirTembak = sekarang;
                }

                // TEMBAK PLAYER 2 (Hanya manual jika mode PvP)
                if (!isBotMode && tembak2 && sekarang - terakhirTembak2 >= tembak_cd) {
                    pesawat.bullets.add(new Bullet(pesawat.enemy.x, pesawat.enemy.y + 30, -17, false));
                    soundPlay.play();
                    terakhirTembak2 = sekarang;
                }

                pesawat.update();
                repaint();
                try { Thread.sleep(16); } catch (InterruptedException e) { }
            }

            // Opsional: Tampilkan Game Over jika loop berhenti
            int pilihan = JOptionPane.showConfirmDialog(
                    this,
                    "GAME OVER!\nMain lagi?",
                    "Game Over",
                    JOptionPane.YES_NO_OPTION
            );

            if (pilihan == JOptionPane.YES_OPTION) {
                mainFrame.showPilihPesawat(); 
            } else {
                System.exit(0); // atau diam saja
            }
        }).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (backgroundImage != null) g2.drawImage(backgroundImage, 0, 0, 1200, 600, null);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        
        // HUD Player 1 (Kiri)
        g2.drawString(namaP1 + " HP: " + pesawat.player.hp, 20, 30);

        // HUD Player 2 (Kanan)
        String txtP2 = namaP2 + " HP: " + pesawat.enemy.hp;
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(txtP2, getWidth() - fm.stringWidth(txtP2) - 20, 30);

        if (playerImage != null) g2.drawImage(playerImage, pesawat.player.x, pesawat.player.y, 80, 60, null);
        if (enemyImage != null) g2.drawImage(enemyImage, pesawat.enemy.x, pesawat.enemy.y, 80, 60, null);

        for (Bullet b : pesawat.bullets) {
            g2.setColor(b.fromPlayer ? Color.YELLOW : Color.RED);
            g2.fillRect(b.x, b.y, 10, 5);
        }
        for (Ledakan l : pesawat.ledakans) {
            g2.drawImage(l.getGambar(), l.x, l.y, 60, 60, null);
        }
    }

    @Override 
    public void keyPressed(KeyEvent e) {
        // Kontrol P1
        if (e.getKeyCode() == KeyEvent.VK_W) up = true;
        if (e.getKeyCode() == KeyEvent.VK_S) down = true;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) tembak = true;

        // Kontrol P2 (Hanya aktif jika bukan Bot)
        if (!isBotMode) {
            if (e.getKeyCode() == KeyEvent.VK_UP) up2 = true;
            if (e.getKeyCode() == KeyEvent.VK_DOWN) down2 = true;
            if (e.getKeyCode() == KeyEvent.VK_ENTER) tembak2 = true;
        }
    }

    @Override 
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) up = false;
        if (e.getKeyCode() == KeyEvent.VK_S) down = false;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) tembak = false;

        if (!isBotMode) {
            if (e.getKeyCode() == KeyEvent.VK_UP) up2 = false;
            if (e.getKeyCode() == KeyEvent.VK_DOWN) down2 = false;
            if (e.getKeyCode() == KeyEvent.VK_ENTER) tembak2 = false;
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    
    public static String pilihBackground() {
        String[] backgrounds = {
            "/Resources/BackgroundImage/background1.png",
            "/Resources/BackgroundImage/background2.png",
            "/Resources/BackgroundImage/background3.png"
        };

        int index = new java.util.Random().nextInt(backgrounds.length);
        return backgrounds[index];
    }
}