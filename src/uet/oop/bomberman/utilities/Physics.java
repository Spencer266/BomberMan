package uet.oop.bomberman.utilities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Physics {
    private static final List<Entity> still = new ArrayList<>();
    public static void updateObjects() {
        still.clear();
        still.addAll(BombermanGame.getStillObjects());
        still.addAll(BombermanGame.getEntities());
    }
    public static Entity detectCollision(Entity entity, int direction, int speed) {
        int curX = entity.getX();
        int curY = entity.getY();

        switch (direction) {
            case 1 -> curY -= speed;
            case 2 -> curY += speed;
            case 3 -> curX -= speed;
            case 4 -> curX += speed;
        }
        for (Entity e: still) {
            if (e instanceof Grass || e == entity) {
                continue;
            }
            if (Math.abs(curX - e.getX()) < entity.getImgWidth() && Math.abs(curY - e.getY()) < Sprite.SCALED_SIZE-1) {
                return e;
            }
        }
        return null;
    }
}
