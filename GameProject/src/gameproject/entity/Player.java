package gameproject.entity;

public class Player extends Pesawat {
    public int speed = 8; // Saya naikkan dikit biar lebih gesit

    public Player(int panelHeight) {
        this.width = 200;
        this.height = 130;
        this.x = 50; 
        this.y = panelHeight / 2 - this.height / 2; 
        this.hp = 100;
    }

    public void moveUp() {
        y -= speed;
        if (y < 0) y = 0; // Berhenti di mentok atas
    }

    public void moveDown() {
        y += speed;
        // 600 adalah tinggi layar
        if (y > 600 - height) y = 600 - height; // Berhenti di mentok bawah
    }
}