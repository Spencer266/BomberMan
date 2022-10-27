package uet.oop.bomberman.utilities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private static final List<Entity> entityToRemove = new ArrayList<>();
    private static final List<Entity> stillToRemove = new ArrayList<>();
    private static final List<Entity> entityToAdd = new ArrayList<>();
    public static void removeEntity(Entity entity) {
        entityToRemove.add(entity);
    }

    public static void removeStill(Entity entity) {
        stillToRemove.add(entity);
    }

    public static void addEntity(Entity entity) {
        entityToAdd.add(entity);
    }

    public static void cycle() {
        if (!entityToRemove.isEmpty()) {
            for (Entity e: entityToRemove) {
                BombermanGame.removeEntity(e);
            }
            entityToRemove.clear();
        }

        if (!stillToRemove.isEmpty()) {
            for (Entity e : stillToRemove) {
                BombermanGame.removeStillObject(e);
            }
            stillToRemove.clear();
        }

        if (!entityToAdd.isEmpty()) {
            for (Entity e: entityToAdd) {
                BombermanGame.addEntity(e);
            }
            entityToAdd.clear();
        }

        Physics.updateObjects();
    }
}
