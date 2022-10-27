package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;

public interface Disposable {
    public abstract void touchedFlame();

    abstract void destroy();
}
