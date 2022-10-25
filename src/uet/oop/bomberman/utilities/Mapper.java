package uet.oop.bomberman.utilities;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    private char[][] map = new char[50][50];
    private static int width;
    private static int height;
    private static int level;
    private static String filePath = "F:\\Workstorage\\IntelliJ Projects\\BomberMan\\res\\levels\\map1.txt";

    public static List<Entity> getMobile() {
        return mobile;
    }

    public static List<Entity> getImmobile() {
        return immobile;
    }

    private static List<Entity> mobile = new ArrayList<>();
    private static List<Entity> immobile = new ArrayList<>();
    public static void readMap() throws IOException {

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
                    case '#':
                        object = new Wall(i, row, Sprite.wall.getFxImage());
                        break;
                    case '*':
                        object = new Brick(i, row, Sprite.brick.getFxImage());
                        break;
                    case 'p':
                        object = new Grass(i, row, Sprite.grass.getFxImage());
                        mobile.add(new Bomber(i, row, Sprite.player_right.getFxImage()));
                        break;
                    case '1':
                        object = new Grass(i, row, Sprite.grass.getFxImage());
                        mobile.add(new Balloom(i, row, Sprite.balloom_right1.getFxImage()));
                        break;
                    default:
                        object = new Grass(i, row, Sprite.grass.getFxImage());
                        break;
                }
                immobile.add(object);
            }
            row++;
        }
    }
}
