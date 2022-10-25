package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Animator;
import uet.oop.bomberman.utilities.Physics;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    private Animator animator;
    private int speed = Sprite.SCALED_SIZE/8;
    private int moving;
    private int limiter;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        init();
    }

    private void init() {
        animator = new Animator();
        animator.addAnimateUp(Sprite.player_up.getFxImage());
        animator.addAnimateUp(Sprite.player_up_1.getFxImage());
        animator.addAnimateUp(Sprite.player_up_2.getFxImage());

        animator.addAnimateDown(Sprite.player_down.getFxImage());
        animator.addAnimateDown(Sprite.player_down_1.getFxImage());
        animator.addAnimateDown(Sprite.player_down_2.getFxImage());

        animator.addAnimateLeft(Sprite.player_left.getFxImage());
        animator.addAnimateLeft(Sprite.player_left_1.getFxImage());
        animator.addAnimateLeft(Sprite.player_left_2.getFxImage());

        animator.addAnimateRight(Sprite.player_right.getFxImage());
        animator.addAnimateRight(Sprite.player_right_1.getFxImage());
        animator.addAnimateRight(Sprite.player_right_2.getFxImage());
        moving = 0;
        limiter = 0;
    }
    public void moveControl(KeyEvent key) {
        switch (key.getCode()) {
            case UP -> {
                moving = 1;
                if (!Physics.detectCollision(x, y, moving, speed)) {
                    y -= speed;
                }
            }
            case DOWN -> {
                moving = 2;
                if (!Physics.detectCollision(x, y, moving, speed)) {
                    y += speed;
                }
            }
            case LEFT -> {
                moving = 3;
                if (!Physics.detectCollision(x, y, moving, speed)) {
                    x -= speed;
                }
            }
            case RIGHT -> {
                moving = 4;
                if (!Physics.detectCollision(x, y, moving, speed)) {
                    x += speed;
                }
            }
        }
    }

    public void OnKeyRelease(KeyEvent keyEvent) {
        KeyCode[] keyCodes = {KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT};
        List<KeyCode> keys = new ArrayList<>(List.of(keyCodes));
        if (keys.contains(keyEvent.getCode())) {
            moving = 0;
        }
    }
    private void animate() {
        img = animator.nextFrame(moving);
    }

    @Override
    public void update() {
        if (limiter > 5) {
            animate();
            limiter = 0;
        }
        limiter++;
    }
}
