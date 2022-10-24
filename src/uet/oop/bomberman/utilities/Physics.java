package uet.oop.bomberman.utilities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Physics {

    public static boolean detectCollision(int curX, int curY, int direction, int speed) {
        boolean check = false;
        List<Entity> still = BombermanGame.getStillObjects();
        List<Entity> mobile = BombermanGame.getEntities();
        switch (direction) {
            case 1 -> {
                curY -= speed;
            }
            case 2 -> {
                curY += speed;
            }
            case 3 -> {
                curX -= speed;
            }
            case 4 -> {
                curX += speed;
            }
        }
        if (loopThrough(curX, curY, still)) return true;
        if (loopThrough(curX, curY, mobile)) return true;
        return check;
    }

    private static boolean loopThrough(int curX, int curY, List<Entity> objects) {
        for (Entity e: objects) {
            if (e instanceof Grass || e instanceof Bomber) {
                continue;
            }
            if (Math.abs(curX - e.getX()) < Sprite.SCALED_SIZE-4 && Math.abs(curY - e.getY()) < Sprite.SCALED_SIZE-1) {
                return true;
            }
        }
        return false;
    }
}
