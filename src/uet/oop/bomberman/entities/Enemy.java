package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.utilities.Animator;
import uet.oop.bomberman.utilities.Physics;

public abstract class Enemy extends Entity implements Disposable {
    int speed;
    int moving;
    int limiter;
    int timer;
    protected Animator animator;
    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        init();
    }

    abstract void init();

    public void moveControl() {
        if (Physics.detectCollision(this, moving, speed) != null) {
            return;
        }
        switch (moving) {
            case 1 -> y -= speed;
            case 2 -> y += speed;
            case 3 -> x -= speed;
            case 4 -> x += speed;
        }
    }

    void animate() {
        img = animator.nextFrame(moving);
    }
}
