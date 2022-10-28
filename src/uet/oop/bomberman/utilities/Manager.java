package uet.oop.bomberman.utilities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private static final List<Entity> entityToRemove = new ArrayList<>();
    private static final List<Entity> stillToRemove = new ArrayList<>();
    private static final List<Entity> entityToAdd = new ArrayList<>();
    private static final List<Entity> effectToRemove = new ArrayList<>();
    private static final List<Entity> effectToAdd = new ArrayList<>();

    public static void removeEntity(Entity entity) {
        entityToRemove.add(entity);
    }

    public static void removeStill(Entity entity) {
        stillToRemove.add(entity);
    }

    public static void addEntity(Entity entity) {
        entityToAdd.add(entity);
    }

    public static void removeEffects(Entity entity) {
        effectToRemove.add(entity);
    }

    public static void addEffects(Entity entity) {
        effectToAdd.add(entity);
    }
    public static void cycle() {
        if (!entityToRemove.isEmpty()) {
            BombermanGame.removeEntities(entityToRemove);
            entityToRemove.clear();
        }

        if (!stillToRemove.isEmpty()) {
            BombermanGame.removeStillObjects(stillToRemove);
            stillToRemove.clear();
        }

        if (!entityToAdd.isEmpty()) {
            BombermanGame.addEntities(entityToAdd);
            entityToAdd.clear();
        }

        if (!effectToRemove.isEmpty()) {
            BombermanGame.removeEffects(effectToRemove);
            effectToRemove.clear();
        }

        if (!effectToAdd.isEmpty()) {
            BombermanGame.addEffects(effectToAdd);
            effectToAdd.clear();
        }

        Physics.updateObjects();
    }
}
