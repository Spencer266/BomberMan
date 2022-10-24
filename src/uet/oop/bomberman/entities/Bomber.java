package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Physics;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    private ArrayList<Image> animateUp = new ArrayList<>();
    private ArrayList<Image> animateDown = new ArrayList<>();
    private ArrayList<Image> animateLeft = new ArrayList<>();
    private ArrayList<Image> animateRight = new ArrayList<>();
    private ArrayList<Image> currentAnimation;
    private int speed = Sprite.SCALED_SIZE/8;
    private int moving;
    private int f_index;
    private int limiter;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        init();
    }

    public void init() {
        animateUp.add(Sprite.player_up.getFxImage());
        animateUp.add(Sprite.player_up_1.getFxImage());
        animateUp.add(Sprite.player_up_2.getFxImage());

        animateDown.add(Sprite.player_down.getFxImage());
        animateDown.add(Sprite.player_down_1.getFxImage());
        animateDown.add(Sprite.player_down_2.getFxImage());

        animateLeft.add(Sprite.player_left.getFxImage());
        animateLeft.add(Sprite.player_left_1.getFxImage());
        animateLeft.add(Sprite.player_left_2.getFxImage());

        animateRight.add(Sprite.player_right.getFxImage());
        animateRight.add(Sprite.player_right_1.getFxImage());
        animateRight.add(Sprite.player_right_2.getFxImage());

        currentAnimation = animateRight;
        moving = 0;
        f_index = 0;
        limiter = 0;
    }
    public void moveControl(KeyEvent key) {
        switch (key.getCode()) {
            case UP -> {
                moving = 1;
                if (!Physics.detectCollision(x, y, moving, speed)) {
                    y -= speed;
                }
                currentAnimation = animateUp;
            }
            case DOWN -> {
                moving = 2;
                if (!Physics.detectCollision(x, y, moving, speed)) {
                    y += speed;
                }
                currentAnimation = animateDown;
            }
            case LEFT -> {
                moving = 3;
                if (!Physics.detectCollision(x, y, moving, speed)) {
                    x -= speed;
                }
                currentAnimation = animateLeft;
            }
            case RIGHT -> {
                moving = 4;
                if (!Physics.detectCollision(x, y, moving, speed)) {
                    x += speed;
                }
                currentAnimation = animateRight;
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
        if (moving == 0) {
            f_index = 0;
        }
        img = currentAnimation.get(f_index);
        f_index = (f_index + 1) % currentAnimation.size();
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
