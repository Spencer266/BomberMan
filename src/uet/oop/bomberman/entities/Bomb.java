package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Animator;
import uet.oop.bomberman.utilities.Manager;
import uet.oop.bomberman.utilities.Physics;

public class Bomb extends Entity {
    private Animator animator;
    private int timer;
    private int limiter;
    private boolean exploded;
    private boolean planted;
    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        init();
    }

    private void init() {

        animator = new Animator();
        animator.addAnimateRight(Sprite.bomb.getFxImage());
        animator.addAnimateRight(Sprite.bomb_1.getFxImage());
        animator.addAnimateRight(Sprite.bomb_2.getFxImage());

        timer = 0;
        limiter = 0;
        exploded = false;
        planted = false;
    }

    private void animate() {
        img = animator.nextFrame(4);
    }
    @Override
    public void update() {
        if (!planted) {
            if (Physics.detectCollision(this, 4, 0) == null) {
                planted = true;
                Manager.plantBomb(this);
            }
        }
        if (timer > 400) {
            Manager.bombExploded(this);
        }
        if (limiter > 10) {
            animate();
            limiter = 0;
        }
        limiter++;
        timer++;
    }
}
