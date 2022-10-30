package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.immobile.Bomb;
import uet.oop.bomberman.entities.immobile.Immobile;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.entities.items.Portal;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Animator;
import uet.oop.bomberman.utilities.Manager;
import uet.oop.bomberman.utilities.Physics;
import uet.oop.bomberman.utilities.Sound;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity implements Disposable {
    private Animator animator;
    private static int countBombers = 0;
    private static int score = 0;
    private double speed;
    private int moving;
    private int limiter;
    private int f_switch;
    private int bombAmount;
    private int bombSize;
    private boolean flamePass;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        init();
        countBombers++;
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

        animator.addAnimateDestroyed(Sprite.player_dead1.getFxImage());
        animator.addAnimateDestroyed(Sprite.player_dead2.getFxImage());
        animator.addAnimateDestroyed(Sprite.player_dead3.getFxImage());

        f_switch = 6;
        moving = 0;
        limiter = 0;
        bombAmount = 1;
        bombSize = 1;
        speed = 3;
        flamePass = false;
    }
    public static int getCountBombers() {
        return countBombers;
    }
    public static void resetCountBombers() { countBombers = 0; }
    public static int getScore() {
        return score;
    }
    public static void addScore(int points) {
        Bomber.score += points;
    }

    public void increaseSpeed() {
        speed++;
    }
    public synchronized void increaseBomb() {
        bombAmount++;
    }
    public int getBombSize() {
        return bombSize;
    }
    public void increaseBombSize() {
        bombSize++;
    }
    public void flamePassAllowed() {
        flamePass = true;
    }

    public void moveControl(KeyEvent key) {
        if (moving == 5) {
            return;
        }
        switch (key.getCode()) {
            case UP -> moving = 1;
            case DOWN -> moving = 2;
            case LEFT -> moving = 3;
            case RIGHT -> moving = 4;
            case SPACE -> {
                if (bombAmount > 0) {
                    Manager.addEffects(new Bomb((x + 8) / Sprite.SCALED_SIZE, (y + 8) / Sprite.SCALED_SIZE, Sprite.bomb.getFxImage(), this));
                    bombAmount--;
                    Sound.play("plantBomb");
                }
            }
        }
    }

    public void OnKeyRelease(KeyEvent keyEvent) {
        KeyCode[] keyCodes = {KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT};
        List<KeyCode> keys = new ArrayList<>(List.of(keyCodes));
        if (keys.contains(keyEvent.getCode())) {
            if (moving == 5) {
                return;
            }
            moving = 0;
        }
    }
    private void animate() {
        img = animator.nextFrame(moving);
    }

    private void animateDeath() {
        Sound.play("oof");
        moving = 5;
        img = animator.nextFrame(moving);
        f_switch = 30;
        limiter = 0;
    }

    @Override
    public void update() {
        if (moving == 5) {
            if (animator.isEnd()) {
                destroy();
            }
        } else if (limiter % 2 == 0) {
            Entity tmp = Physics.detectCollision(this, moving, speed);
            if (tmp != null) {
                if (tmp instanceof Immobile) {
                    return;
                }
                if (tmp instanceof Enemy) {
                    animateDeath();
                    return;
                }
                if (tmp instanceof Item) {
                    ((Item) tmp).getBuff(this);
                    if (!(tmp instanceof Portal)) {
                        Sound.play("getBuff");
                    }
                }
            }
            switch (moving) {
                case 1 -> y -= speed;
                case 2 -> y += speed;
                case 3 -> x -= speed;
                case 4 -> x += speed;
            }
            if (limiter > f_switch && moving != 0) {
                Sound.play("moving");
            }
        }
        if (limiter > f_switch) {
            animate();
            limiter = 0;
        }
        limiter++;
    }

    @Override
    public void touchedFlame() {
        if (!flamePass) {
            animateDeath();
        }
    }

    @Override
    public void destroy() {
        Manager.removeEntity(this);
        countBombers--;
        if (countBombers <= 0) {
            BombermanGame.endGame();
        }
    }
}
