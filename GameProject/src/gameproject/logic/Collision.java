package gameproject.logic;

public class Collision {

    public static boolean check(
        int projectileX, int projectileY, int projectileWidth, int projectileHeight,
        int targetX, int targetY, int targetWidth, int targetHeight
    ) {
        return projectileX < targetX + targetWidth &&
               projectileX + projectileWidth > targetX &&
               projectileY < targetY + targetHeight &&
               projectileY + projectileHeight > targetY;
    }
}