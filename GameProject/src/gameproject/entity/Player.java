package gameproject.entity;

public class Player extends Pesawat {
    public int speed = 5;

    public Player(int panelHeight) {
        this.width = 250;
        this.height = 180;
        this.x = 50; // tetap kiri
        this.y = panelHeight / 2 - this.height / 2; // tengah vertikal
        this.hp = 100;
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveDown() {
        y += speed;
    }
}