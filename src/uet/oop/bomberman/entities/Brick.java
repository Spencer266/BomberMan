package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Animator;
import uet.oop.bomberman.utilities.Manager;
import uet.oop.bomberman.utilities.Physics;

public class Brick extends Entity implements Disposable {
    private Animator animator;
    private int limiter;
    private boolean isFlamed;
    public Brick(int x, int y, Image img) {
        super(x, y, img);
        isFlamed = false;
        init();
    }

    private void init() {
        animator = new Animator();
        animator.addAnimateDestroyed(Sprite.brick_exploded.getFxImage());
        animator.addAnimateDestroyed(Sprite.brick_exploded1.getFxImage());
        animator.addAnimateDestroyed(Sprite.brick_exploded2.getFxImage());
        limiter = 0;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isFlamed) {
            if (limiter > 35) {
                img = animator.nextFrame(5);
                limiter = 0;
            }
            if (animator.isEnd()) {
                destroy();
            }
            limiter++;
        }
        gc.drawImage(img, x, y);
    }

    @Override
    public void update() {

    }

    @Override
    public void touchedFlame() {
        isFlamed = true;
        img = animator.nextFrame(5);
    }

    @Override
    public void destroy() {
        Manager.removeStill(this);
    }
}
