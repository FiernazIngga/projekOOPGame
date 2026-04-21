package GameStart;

import javax.swing.*;
import java.awt.*;

public class PilihPesawat extends JPanel {
    private FrameMain mainFrame;
    
    // Komponen UI
    private JLabel labelJudul, labelPreview, lblNamaHint, lblPesawat;
    private JTextField txtNama;
    private JComboBox<String> comboPesawat;
    private JButton btnNext;
    private JRadioButton rdBot, rdHuman;
    private ButtonGroup modeGroup;

    // Variabel Penyimpan Data Sementara
    private String namaP1, namaP2, pesawatP1, pesawatP2;
    private boolean isLawanBot;
    private int step = 1; // 1: P1, 2: Pilih Mode, 3: P2

    public PilihPesawat(FrameMain frame) {
        this.mainFrame = frame;
        
        // Setup Panel Utama
        setPreferredSize(new Dimension(1200, 600));
        setBackground(new Color(47, 54, 64));
        setLayout(null);

        // 1. Judul Dinamis
        labelJudul = new JLabel("REGISTRASI PLAYER 1");
        labelJudul.setFont(new Font("Arial", Font.BOLD, 32));
        labelJudul.setForeground(Color.WHITE);
        labelJudul.setBounds(400, 30, 600, 50);
        add(labelJudul);

        // 2. Preview Gambar (Kiri)
        labelPreview = new JLabel();
        labelPreview.setBounds(150, 150, 400, 300);
        labelPreview.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        labelPreview.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelPreview);

        // 3. Input Nama & Dropdown (Kanan)
        lblNamaHint = new JLabel("Nama Pilot:");
        lblNamaHint.setForeground(Color.WHITE);
        lblNamaHint.setBounds(700, 150, 200, 30);
        add(lblNamaHint);

        txtNama = new JTextField("Pilot User 1");
        txtNama.setBounds(700, 180, 300, 40);
        txtNama.setFont(new Font("Arial", Font.PLAIN, 18));
        add(txtNama);
        
        lblPesawat = new JLabel("Pilih Pesawat:");
        lblPesawat.setForeground(Color.WHITE);
        lblPesawat.setBounds(700, 230, 200, 30);
        add(lblPesawat);

        comboPesawat = new JComboBox<>(new String[]{"Pesawat Amerika", "Pesawat Jepang", "Pesawat Indonesia"});
        comboPesawat.setBounds(700, 260, 300, 40);
        comboPesawat.setFont(new Font("Arial", Font.PLAIN, 18));
        comboPesawat.addActionListener(e -> updatePreview((String) comboPesawat.getSelectedItem()));
        add(comboPesawat);

        // 4. Komponen Pilihan Mode (Awalnya Sembunyi)
        rdBot = new JRadioButton("LAWAN BOT (Single Player)", true);
        rdHuman = new JRadioButton("LAWAN TEMAN (Multiplayer 2P)");
        rdBot.setBounds(250, 230, 300, 40);
        rdHuman.setBounds(700, 230, 300, 40);
        rdBot.setForeground(Color.WHITE); rdHuman.setForeground(Color.WHITE);
        rdBot.setOpaque(false); rdHuman.setOpaque(false);
        rdBot.setFont(new Font("Arial", Font.BOLD, 16));
        rdHuman.setFont(new Font("Arial", Font.BOLD, 16));
        
        modeGroup = new ButtonGroup();
        modeGroup.add(rdBot); modeGroup.add(rdHuman);
        
        add(rdBot); add(rdHuman);
        rdBot.setVisible(false); rdHuman.setVisible(false);

        // 5. Tombol Navigasi
        btnNext = new JButton("LANJUTKAN");
        btnNext.setBounds(700, 380, 300, 60);
        btnNext.setBackground(new Color(44, 190, 80));
        btnNext.setForeground(Color.WHITE);
        btnNext.setFont(new Font("Arial", Font.BOLD, 18));
        btnNext.setFocusPainted(false);
        btnNext.addActionListener(e -> handleNextStep());
        add(btnNext);

        updatePreview((String) comboPesawat.getSelectedItem());
    }

    private void handleNextStep() {
        if (step == 1) {
            // SIMPAN DATA P1
            namaP1 = txtNama.getText().trim();
            pesawatP1 = (String) comboPesawat.getSelectedItem();
            
            if (namaP1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!");
                return;
            }

            // PINDAH KE PILIHAN MODE
            step = 2;
            labelJudul.setText("PILIH MODE PERMAINAN");
            lblNamaHint.setVisible(false);
            lblPesawat.setVisible(false);
            txtNama.setVisible(false);
            comboPesawat.setVisible(false);
            labelPreview.setVisible(false);
            
            rdBot.setVisible(true);
            rdHuman.setVisible(true);
            btnNext.setBounds(440, 380, 300, 60);
            btnNext.setText("KONFIRMASI MODE");

        } else if (step == 2) {
            isLawanBot = rdBot.isSelected();
            
            if (isLawanBot) {
                // Jika pilih BOT, langsung jalankan game
                namaP2 = "BOT AI";
                pesawatP2 = "Pesawat Musuh"; // Default pesawat bot
                mainFrame.jalankanGame(pesawatP1, pesawatP2, namaP1, namaP2, true);
            } else {
                rdBot.setVisible(false); rdHuman.setVisible(false);
                // Jika pilih TEMAN, masuk ke input Player 2
                step = 3;
                labelJudul.setText("REGISTRASI PLAYER 2");

                // --- PERUBAHAN DI SINI ---
                // 1. Kosongkan pilihan lama
                comboPesawat.removeAllItems(); 

                // 2. Masukkan pilihan baru khusus Player 2 (Misal: Pesawat versi Dark/Enemy)
                comboPesawat.addItem("Pesawat Alien");
                comboPesawat.addItem("Pesawat Phantom");
                comboPesawat.addItem("Pesawat Rusia");

                // Munculkan kembali input
                lblNamaHint.setText("Nama Pilot Pesawat 2:");
                lblPesawat.setText("Pilih Pesawat Pilot 2:");
                lblNamaHint.setVisible(true);
                lblPesawat.setVisible(true);
                txtNama.setVisible(true);
                txtNama.setText("Pilot_2");
                comboPesawat.setVisible(true);
                labelPreview.setVisible(true);
                btnNext.setBounds(700, 380, 300, 60);
                btnNext.setText("MULAI PERTEMPURAN!");
                updatePreview((String) comboPesawat.getSelectedItem());
            }
        } else if (step == 3) {
            // SIMPAN DATA P2 & START
            namaP2 = txtNama.getText().trim();
            pesawatP2 = (String) comboPesawat.getSelectedItem();
            
            if (namaP2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama Player 2 tidak boleh kosong!");
                return;
            }
            
            mainFrame.jalankanGame(pesawatP1, pesawatP2, namaP1, namaP2, false);
        }
    }

    private void updatePreview(String jenisPesawat) {
        if (jenisPesawat == null) return;

        String path;

        // --- LOGIKA IF-ELSE UNTUK RESOURCE ---
        if (jenisPesawat.equals("Pesawat Alien")) {
            path = "/Resources/Image/musuhAlien.png";
        } else if (jenisPesawat.equals("Pesawat Phantom")) {
            path = "/Resources/Image/musuhPhantom.png";
        } else if (jenisPesawat.equals("Pesawat Rusia")) {
            path = "/Resources/Image/musuhDarker.png";
        } else {
            // Default untuk pesawat player 1 (Amerika, Jepang, Indonesia)
            path = "/Resources/Image/" + jenisPesawat.replace(" ", "") + ".png";
        }

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image img = icon.getImage().getScaledInstance(350, 250, Image.SCALE_SMOOTH);
            labelPreview.setIcon(new ImageIcon(img));
            labelPreview.setText("");
        } catch (Exception e) {
            labelPreview.setIcon(null);
            labelPreview.setText("Gambar: " + jenisPesawat + " Tidak Ditemukan");
            System.out.println("Gagal load: " + path); // Cek di console path mana yang salah
        }
    }
}