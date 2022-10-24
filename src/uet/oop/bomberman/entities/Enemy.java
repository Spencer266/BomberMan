package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class Enemy extends Entity {

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public abstract void attack();
}
