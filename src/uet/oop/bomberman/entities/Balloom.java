package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Animator;
import uet.oop.bomberman.utilities.Physics;

import java.util.Random;

public class Balloom extends Enemy {

    private Random randomGenerator;
    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    void init() {
        this.randomGenerator = new Random();
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
        speed = Sprite.SCALED_SIZE/24;
        timer = randomGenerator.nextInt(200);
    }

    @Override
    public void update() {
        if (timer > 100) {
            while (Physics.detectCollision(this, moving, speed) != null) {
                moving = this.randomGenerator.nextInt(4) + 1;
            }
            timer = this.randomGenerator.nextInt(200);
        }
        moveControl();
        if (limiter > 15) {
            animate();
            limiter = 0;
        }
        limiter++;
        timer++;
    }
}
