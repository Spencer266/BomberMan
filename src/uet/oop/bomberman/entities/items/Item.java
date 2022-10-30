package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Disposable;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.utilities.Manager;

public abstract class Item extends Entity implements Disposable {
    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void unlock() {
        Manager.removeEffects(this);
        Manager.addEntity(this);
    }

    public abstract void getBuff(Bomber bomber);

    @Override
    public void touchedFlame() {
        destroy();
    }

    @Override
    public void update() {}

    @Override
    public void destroy() {
        Manager.removeEntity(this);
    }
}
