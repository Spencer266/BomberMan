package uet.oop.bomberman.utilities;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.enemies.Balloom;
import uet.oop.bomberman.entities.immobile.Brick;
import uet.oop.bomberman.entities.immobile.Wall;
import uet.oop.bomberman.entities.items.FlameItem;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mapper {
    private static int width;
    private static int height;
    private static int level = 2;

    public static final List<Entity> effects = new ArrayList<>();
    public static final List<Entity> mobile = new ArrayList<>();
    public static final List<Entity> immobile = new ArrayList<>();
    public static void readMap() throws IOException {
        mobile.clear();
        immobile.clear();
        String filePath = System.getProperty("user.dir") + "\\res\\levels\\" + String.format("map%d.txt", level);
        FileReader file = new FileReader(filePath);
        BufferedReader buffer = new BufferedReader(file);

        String line = buffer.readLine();
        String[] info = line.split(" ");
        level = Integer.parseInt(info[0]);
        height = Integer.parseInt(info[1]);
        width = Integer.parseInt(info[2]);

        int row = 0;
        while ((line = buffer.readLine()) != null) {
            for (int i = 0; i < width; i++) {
                Entity object;
                switch (line.charAt(i)) {
                    case '#' -> object = new Wall(i, row, Sprite.wall.getFxImage());
                    case '*' -> {
                        immobile.add(new Grass(i, row, Sprite.grass.getFxImage()));
                        object = new Brick(i, row, Sprite.brick.getFxImage());
                    }
                    case 'p' -> {
                        object = new Grass(i, row, Sprite.grass.getFxImage());
                        mobile.add(new Bomber(i, row, Sprite.player_right.getFxImage()));
                    }
                    case '1' -> {
                        object = new Grass(i, row, Sprite.grass.getFxImage());
                        mobile.add(new Balloom(i, row, Sprite.balloom_right1.getFxImage()));
                    }
                    case 'f' -> {
                        FlameItem flameItem = new FlameItem(i, row, Sprite.powerup_flames.getFxImage());
                        immobile.add(new Grass(i, row, Sprite.grass.getFxImage()));
                        effects.add(flameItem);
                        object = new Brick(i, row, Sprite.brick.getFxImage(), flameItem);
                    }
                    default -> object = new Grass(i, row, Sprite.grass.getFxImage());
                }
                immobile.add(object);
            }
            row++;
        }
        level++;
    }
}
