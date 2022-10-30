package uet.oop.bomberman.entities.immobile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Animator;
import uet.oop.bomberman.utilities.Manager;
import uet.oop.bomberman.utilities.Physics;

import static uet.oop.bomberman.entities.Bomber.*;

public class Bomb extends Immobile {
    private final Bomber planter;
    private Animator animator;
    private int timer;
    private int limiter;
    private boolean planted;
    public Bomb(int xUnit, int yUnit, Image img, Bomber planter) {
        super(xUnit, yUnit, img);
        init();
        this.planter = planter;
    }
    private void init() {

        animator = new Animator();
        animator.addAnimateRight(Sprite.bomb.getFxImage());
        animator.addAnimateRight(Sprite.bomb_1.getFxImage());
        animator.addAnimateRight(Sprite.bomb_2.getFxImage());

        timer = 0;
        limiter = 0;
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
                Manager.removeEffects(this);
                Manager.addEntity(this);
            }
        }
        if (timer > 300) {
            Bomber.screensound.play();
            Manager.addEntity(new Flame(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Sprite.bomb_exploded.getFxImage(), planter.getBombSize()));
            planter.increaseBomb();
            Manager.removeEntity(this);
        }
        if (limiter > 10) {
            animate();
            limiter = 0;
        }
        limiter++;
        timer++;
    }
}


