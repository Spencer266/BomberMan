package uet.oop.bomberman.entities.immobile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Disposable;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Manager;
import uet.oop.bomberman.utilities.Physics;

import java.util.ArrayList;

public class Flame extends Immobile implements Disposable {
    private final int size;
    private int f_index;
    private int limiter;
    private final int[] directions;
    private final ArrayList<Image> flameHorizontal = new ArrayList<>();
    private final ArrayList<Image> flameVertical = new ArrayList<>();
    private final ArrayList<Image> flameTop = new ArrayList<>();
    private final ArrayList<Image> flameBottom = new ArrayList<>();
    private final ArrayList<Image> flameLeft = new ArrayList<>();
    private final ArrayList<Image> flameRight = new ArrayList<>();
    private final ArrayList<Image> flameCenter = new ArrayList<>();

    public Flame(int xUnit, int yUnit, Image img, int size) {
        super(xUnit, yUnit, img);
        this.size = size;
        directions = new int[4];
        addMiddle();
        addTop();
        addBottom();
        addLeft();
        addRight();
        explode();
    }

    private void addMiddle() {
        flameHorizontal.add(Sprite.explosion_horizontal.getFxImage());
        flameHorizontal.add(Sprite.explosion_horizontal1.getFxImage());
        flameHorizontal.add(Sprite.explosion_horizontal2.getFxImage());

        flameVertical.add(Sprite.explosion_vertical.getFxImage());
        flameVertical.add(Sprite.explosion_vertical1.getFxImage());
        flameVertical.add(Sprite.explosion_vertical2.getFxImage());

        flameCenter.add(Sprite.bomb_exploded.getFxImage());
        flameCenter.add(Sprite.bomb_exploded1.getFxImage());
        flameCenter.add(Sprite.bomb_exploded2.getFxImage());

        f_index = 0;
        limiter = 0;
    }

    private void addTop() {
        flameTop.add(Sprite.explosion_vertical_top_last.getFxImage());
        flameTop.add(Sprite.explosion_vertical_top_last1.getFxImage());
        flameTop.add(Sprite.explosion_vertical_top_last2.getFxImage());
    }

    private void addBottom() {
        flameBottom.add(Sprite.explosion_vertical_down_last.getFxImage());
        flameBottom.add(Sprite.explosion_vertical_down_last1.getFxImage());
        flameBottom.add(Sprite.explosion_vertical_down_last2.getFxImage());
    }

    private void addLeft() {
        flameLeft.add(Sprite.explosion_horizontal_left_last.getFxImage());
        flameLeft.add(Sprite.explosion_horizontal_left_last1.getFxImage());
        flameLeft.add(Sprite.explosion_horizontal_left_last2.getFxImage());
    }

    private void addRight() {
        flameRight.add(Sprite.explosion_horizontal_right_last.getFxImage());
        flameRight.add(Sprite.explosion_horizontal_right_last1.getFxImage());
        flameRight.add(Sprite.explosion_horizontal_right_last2.getFxImage());
    }

    private void explode() {
        Entity tmp;
        tmp = Physics.detectCollision(this, 1, 0);
        if (tmp instanceof Bomber) {
            ((Bomber) tmp).touchedFlame();
        }
        for (int d = 0; d < 4; d++) {
            for (int i = 1; i <= size; i++) {
                tmp = Physics.detectCollision(this, d + 1, (int) getImgWidth() * i);
                if (tmp instanceof Wall) {
                    break;
                }
                if (tmp instanceof Brick) {
                    ((Brick) tmp).touchedFlame();
                    break;
                }
                if (tmp instanceof Enemy || tmp instanceof Bomber) {
                    ((Disposable) tmp).touchedFlame();
                }
                directions[d]++;
            }
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        int tmpX, tmpY, increX, increY;

        gc.drawImage(flameCenter.get(f_index), x, y);

        for (int d = 0; d < 4; d++) {
            tmpX = x;
            tmpY = y;
            increX = d % 2 == 1 ? 1 : -1;
            increY = d % 2 == 1 ? 1 : -1;
            if (d < 2) {
                img = flameVertical.get(f_index);
                increX = 0;
            } else {
                img = flameHorizontal.get(f_index);
                increY = 0;
            }
            for (int i = 1; i <= directions[d]; i++) {
                if (i == size) {
                    switch (d) {
                        case 0 -> img = flameTop.get(f_index);
                        case 1 -> img = flameBottom.get(f_index);
                        case 2 -> img = flameLeft.get(f_index);
                        case 3 -> img = flameRight.get(f_index);
                    }
                }
                tmpX += Sprite.SCALED_SIZE * increX;
                tmpY += Sprite.SCALED_SIZE * increY;
                gc.drawImage(img, tmpX, tmpY);
            }
        }
    }

    @Override
    public void update() {
        if (limiter > 5) {
            f_index++;
            if (f_index > 2) {
                destroy();
            }
            limiter = 0;
        }
        limiter++;
    }

    @Override
    public void touchedFlame() {}

    @Override
    public void destroy() {
        Manager.removeEntity(this);
    }
}
