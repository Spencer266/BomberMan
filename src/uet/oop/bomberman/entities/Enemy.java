package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.utilities.Animator;

public abstract class Enemy extends Entity {

    int moving;
    int limiter;
    protected Animator animator;
    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        init();
    }

    abstract void init();

    void animate() {
        img = animator.nextFrame(moving);
    }

    public abstract void attack();
}
