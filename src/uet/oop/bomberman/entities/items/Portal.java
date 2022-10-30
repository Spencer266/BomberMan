package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Sound;

public class Portal extends Item {

    public Portal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void getBuff(Bomber bomber) {
        if (Math.abs(x - bomber.getX()) < Sprite.SCALED_SIZE / 4 && Math.abs(y - bomber.getY()) < 2) {
            Sound.play("clear");
            BombermanGame.newLevel();
        }
    }
}
