package gameproject.entity;

public class Enemy extends Pesawat {
    public int speed = 5; // Tambahkan variabel speed agar konsisten dengan Player

    public Enemy(int panelWidth, int panelHeight) {
        this.width = 200;
        this.height = 130;
        this.x = panelWidth - 50 - this.width; 
        this.y = panelHeight / 2 - this.height / 2; 
        this.hp = 100;
    }

    // --- FUNGSI UNTUK PLAYER 2 ---
    public void moveUp() {
        y -= speed;
        if (y < 0) y = 0; // Batas atas
    }

    public void moveDown() {
        y += speed;
        // 600 adalah tinggi layar (sesuai FrameMain kamu)
        if (y > 600 - height) y = 600 - height; // Batas bawah
    }

    // --- FUNGSI UNTUK BOT ---
    public void followPlayer(Player player) {
        if (this.y < player.y) this.y += 2;
        else if (this.y > player.y) this.y -= 2;
        
        // Jaga agar AI juga tidak keluar layar
        if (y < 0) y = 0;
        if (y > 600 - height) y = 600 - height;
    }
}