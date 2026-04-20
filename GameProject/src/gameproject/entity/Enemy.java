package gameproject.entity;

public class Enemy extends Pesawat {

    public Enemy(int panelWidth, int panelHeight) {
        this.width = 250;
        this.height = 180;
        this.x = panelWidth - 50 - this.width; // 50 px dari kanan
        this.y = panelHeight / 2 - this.height / 2; // tengah vertikal
        this.hp = 100;
    }

    // AI sederhana
    public void followPlayer(Player player) {
        if (this.y < player.y) this.y += 2;
        else if (this.y > player.y) this.y -= 2;
    }
}