package GameStart;

import java.awt.BorderLayout;

public class FrameMain extends javax.swing.JFrame {

    public FrameMain() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(245, 246, 250));
        setPreferredSize(new java.awt.Dimension(1200, 600));
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(220, 221, 225));
        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(47, 54, 64));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Destroy Your Enemey");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(0, 15, 630, 56);

        jButton1.setBackground(new java.awt.Color(47, 53, 66));
        jButton1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Masuk Game");
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(230, 100, 170, 60);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(320, 220, 630, 180);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GameStart/Gemini_Generated_Image_9wupbm9wupbm9wup.png"))); // NOI18N
        jLabel1.setMaximumSize(new java.awt.Dimension(1200, 600));
        jLabel1.setMinimumSize(new java.awt.Dimension(1200, 600));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1200, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        showPilihPesawat();
    }//GEN-LAST:event_jButton1ActionPerformed

    public void jalankanGame(String pes1, String pes2, String n1, String n2, boolean isBot) {
        gameproject.logic.GamePesawat game = new gameproject.logic.GamePesawat(this, pes1, pes2, n1, n2, isBot);

        this.getContentPane().removeAll();
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(game, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
        this.pack();

        game.requestFocusInWindow();
        game.startGameLoop();
    }
    
    public void showPilihPesawat() {
        PilihPesawat menuPilih = new PilihPesawat(this);

        this.getContentPane().removeAll();
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(menuPilih, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
