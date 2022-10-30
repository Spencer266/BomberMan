package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomber;

public class FlamePassItem extends Item {
    public FlamePassItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void getBuff(Bomber bomber) {
        bomber.flamePassAllowed();
        destroy();
    }
}
