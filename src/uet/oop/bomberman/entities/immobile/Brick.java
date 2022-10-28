package uet.oop.bomberman.entities.immobile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Disposable;
import uet.oop.bomberman.entities.immobile.Immobile;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Animator;
import uet.oop.bomberman.utilities.Manager;

public class Brick extends Immobile implements Disposable {
    private Animator animator;
    private int limiter;
    private boolean isFlamed;
    private Item item;
    public Brick(int x, int y, Image img) {
        super(x, y, img);
        isFlamed = false;
        item = null;
        init();
    }

    public Brick(int x, int y, Image img, Item item) {
        super(x, y, img);
        isFlamed = false;
        this.item = item;
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
    public void update() {
        if (isFlamed) {
            if (limiter > 6) {
                if (animator.isEnd()) {
                    destroy();
                }
                img = animator.nextFrame(5);
                limiter = 0;
            }
            limiter++;
        }
    }

    @Override
    public void touchedFlame() {
        isFlamed = true;
        img = animator.nextFrame(5);
    }

    @Override
    public void destroy() {
        Manager.removeStill(this);
        if (item != null) {
            item.unlock();
        }
    }
}
