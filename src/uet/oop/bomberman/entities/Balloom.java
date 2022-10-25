package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Animator;

public class Balloom extends Enemy {

    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    void init() {
        animator = new Animator();
        animator.setSimple(true);

        animator.addAnimateLeft(Sprite.balloom_left1.getFxImage());
        animator.addAnimateLeft(Sprite.balloom_left2.getFxImage());
        animator.addAnimateLeft(Sprite.balloom_left3.getFxImage());

        animator.addAnimateRight(Sprite.balloom_right1.getFxImage());
        animator.addAnimateRight(Sprite.balloom_right2.getFxImage());
        animator.addAnimateRight(Sprite.balloom_right3.getFxImage());

        moving = 4;
        limiter = 0;
    }

    public void attack () {
        System.out.println("BALLOOM!");
    }

    @Override
    public void update() {
        if (limiter > 8) {
            animate();
            limiter = 0;
        }
        limiter++;
    }
}
