package gameproject.entity;

import java.util.ArrayList;

public class Bullet {
    public int x, y;
    public int velocityX;
    public boolean fromPlayer;

    public Bullet(int x, int y, int velocityX, boolean fromPlayer) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.fromPlayer = fromPlayer;
    }

    public void move() {
        x += this.velocityX;
    }
}