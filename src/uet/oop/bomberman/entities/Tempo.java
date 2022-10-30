package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Physics;

public class Tempo extends Entity {

    private final int moving;
    private final int speed;
//    public Tempo(int xUnit, int yUnit, Image img) {
//        super(xUnit, yUnit, img);
//    }

    public Tempo(int xUnit, int yUnit, int moving) {
        super(xUnit, yUnit, Sprite.powerup_speed.getFxImage());
        this.moving = moving;
        speed = Sprite.SCALED_SIZE / 24;
        switch (moving) {
            case 1 -> y -= Sprite.SCALED_SIZE;
            case 2 -> y += Sprite.SCALED_SIZE;
            case 3 -> x -= Sprite.SCALED_SIZE;
            case 4 -> x += Sprite.SCALED_SIZE;
        }
    }
    @Override
    public void update() {
        switch (moving) {
            case 1 -> y -= speed;
            case 2 -> y += speed;
            case 3 -> x -= speed;
            case 4 -> x += speed;
        }
    }

    public boolean canMove() {
        Entity tmp;
        while ((tmp = Physics.detectCollision(this, moving, speed)) == null) {
            update();
        }
        return (tmp instanceof Bomber);
    }
}
