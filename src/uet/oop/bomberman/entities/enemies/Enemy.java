package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Disposable;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.utilities.Animator;
import uet.oop.bomberman.utilities.Manager;
import uet.oop.bomberman.utilities.Physics;

public abstract class Enemy extends Entity implements Disposable {
    int speed;
    int moving;
    int f_switch;
    int limiter;
    int timer;
    protected Animator animator;
    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        init();
        f_switch = 15;
    }

    abstract void init();

    public void moveControl() {
        Entity tmp = Physics.detectCollision(this, moving, speed);
        if (tmp != null && !(tmp instanceof Bomber)) {
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
        f_switch = 15;
    }

    void animateDeath() {
        img = animator.nextFrame(moving);
        f_switch = 90;
    }

    @Override
    public void destroy() {
        Manager.removeEntity(this);
    }
}
