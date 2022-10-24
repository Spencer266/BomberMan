package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Ballon extends Enemy {

    public Ballon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }

    public void attack () {
        System.out.println("BALLOON!");
    }
}
