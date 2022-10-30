package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Tempo;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Animator;
import uet.oop.bomberman.utilities.Physics;

import java.util.Random;

public class Oneal extends Enemy {
    private Random randomGenerator;
    private boolean trigger;
    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    void init() {
        this.randomGenerator = new Random();
        animator = new Animator(true);

        animator.addAnimateLeft(Sprite.oneal_left1.getFxImage());
        animator.addAnimateLeft(Sprite.oneal_left2.getFxImage());
        animator.addAnimateLeft(Sprite.oneal_left3.getFxImage());

        animator.addAnimateRight(Sprite.oneal_right1.getFxImage());
        animator.addAnimateRight(Sprite.oneal_right2.getFxImage());
        animator.addAnimateRight(Sprite.oneal_right3.getFxImage());

        animator.addAnimateDestroyed(Sprite.oneal_dead.getFxImage());
        animator.addAnimateDestroyed(Sprite.mob_dead1.getFxImage());
        animator.addAnimateDestroyed(Sprite.mob_dead2.getFxImage());
        animator.addAnimateDestroyed(Sprite.mob_dead3.getFxImage());

        trigger = false;
        moving = 4;
        limiter = 0;
        speed = randomGenerator.nextInt(1, 2);
        timer = randomGenerator.nextInt(20);
        killPoints = 700;
    }

    @Override
    public void update() {
        if (moving == 5) {
            if (animator.isEnd()) {
                destroy();
            }
        } else {
            Entity tmp = Physics.detectCollision(this, moving, speed);
            if (tmp == null || tmp instanceof Bomber) {
                moveControl();
            }
            float bomberX = BombermanGame.getBomberman().getX();
            float bomberY = BombermanGame.getBomberman().getY();
            if (Math.abs(bomberX - x) == 0) {
                int direction = bomberY < y ? 1 : 2;
                Tempo shadow = new Tempo(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, direction);
                if (shadow.canMove()) {
                    moving = direction;
                }
                if (!trigger) {
                    fixPosition();
                    trigger = true;
                }
            } else if (Math.abs(bomberY - y) == 0) {
                int direction = bomberX < x ? 3 : 4;
                Tempo shadow = new Tempo(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, direction);
                if (shadow.canMove()) {
                    moving = direction;
                }
                if (!trigger) {
                    fixPosition();
                    trigger = true;
                }
            } else if (timer > 100) {
                trigger = false;
                while ((tmp = Physics.detectCollision(this, moving, speed)) != null) {
                    if (tmp instanceof Bomber) {
                        break;
                    }
                    moving = this.randomGenerator.nextInt(4) + 1;
                }
                timer = this.randomGenerator.nextInt(20);
                speed = this.randomGenerator.nextInt(1, 2);
            } else {
                trigger = false;
            }
        }
        if (limiter > f_switch) {
            animate();
            limiter = 0;
        }
        limiter++;
        timer++;
    }

    private void fixPosition() {
        x = ((x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE;
        y = ((y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE;
    }
}
