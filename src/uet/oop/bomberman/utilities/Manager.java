package uet.oop.bomberman.utilities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb;

public class Manager {
    public static void plantBomb(Bomb bomb) {
        BombermanGame.removeEffect(bomb);
        BombermanGame.addEntity(bomb);
        Physics.updateObjects();
    }

    public static void bombExploded(Bomb bomb) {
        BombermanGame.removeEntity(bomb);
        Physics.updateObjects();
    }
}
