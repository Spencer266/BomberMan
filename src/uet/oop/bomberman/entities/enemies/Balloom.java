package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
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
        animator = new Animator(true);

        animator.addAnimateLeft(Sprite.balloom_left1.getFxImage());
        animator.addAnimateLeft(Sprite.balloom_left2.getFxImage());
        animator.addAnimateLeft(Sprite.balloom_left3.getFxImage());

        animator.addAnimateRight(Sprite.balloom_right1.getFxImage());
        animator.addAnimateRight(Sprite.balloom_right2.getFxImage());
        animator.addAnimateRight(Sprite.balloom_right3.getFxImage());

        animator.addAnimateDestroyed(Sprite.balloom_dead.getFxImage());
        animator.addAnimateDestroyed(Sprite.mob_dead1.getFxImage());
        animator.addAnimateDestroyed(Sprite.mob_dead2.getFxImage());
        animator.addAnimateDestroyed(Sprite.mob_dead3.getFxImage());

        moving = 4;
        limiter = 0;
        speed = Sprite.SCALED_SIZE / 32;
        timer = randomGenerator.nextInt(200);
    }

    @Override
    public void update() {
        if (moving == 5) {
            if (animator.isEnd()) {
                destroy();
            }
        } else {
            if (timer > 100) {
                Entity tmp;
                while ((tmp = Physics.detectCollision(this, moving, speed)) != null) {
                    if (tmp instanceof Bomber) {
                        continue;
                    }
                    moving = this.randomGenerator.nextInt(4) + 1;
                }
                timer = this.randomGenerator.nextInt(200);
            }
            moveControl();
        }
        if (limiter > f_switch) {
            animate();
            limiter = 0;
        }
        limiter++;
        timer++;
    }

    @Override
    public void touchedFlame() {
        moving = 5;
        animateDeath();
    }
}
