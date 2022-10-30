package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Manager;
import uet.oop.bomberman.utilities.Mapper;
import uet.oop.bomberman.utilities.Physics;
import uet.oop.bomberman.utilities.Sound;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    public static volatile boolean bgmPlaying = false;
    private static boolean gameOver;
    private static int level = 1;

    private Stage currentStage;
    private static GraphicsContext gc;
    private static Canvas canvas;
    private static AnimationTimer timer;
    private static GraphicsContext infoGc;
    private static Canvas info;

    private static Bomber bomberman;
    public static Bomber getBomberman() {
        return bomberman;
    }

    private static List<Entity> entities = new ArrayList<>();
    public static List<Entity> getEntities() {
        return entities;
    }
    public static void addEntities(List<Entity> le) {
        entities.addAll(0, le);
    }
    public static void removeEntities(List<Entity> le) {
        entities.removeAll(le);
    }

    private static List<Entity> stillObjects = new ArrayList<>();
    public static List<Entity> getStillObjects() {
        return stillObjects;
    }
    public static void removeStillObjects(List<Entity> lo) {
        stillObjects.removeAll(lo);
    }

    private static final List<Entity> effects = new ArrayList<>();
    public static void addEffects(List<Entity> le) {
        effects.addAll(le);
    }
    public static void removeEffects(List<Entity> le) {
        effects.removeAll(le);
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        info = new Canvas(Sprite.SCALED_SIZE * WIDTH, 30);
        infoGc = info.getGraphicsContext2D();
        infoGc.setTextAlign(TextAlignment.CENTER);
        infoGc.setTextBaseline(VPos.CENTER);
        infoGc.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        canvas.setLayoutY(30);
        gc = canvas.getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

        // Tao root container
        Group root = new Group();
        root.getChildren().add(info);
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        timer = new AnimationTimer() {

            @Override
            public void handle(long l) {
                update();
                Manager.cycle();
                render();
            }
        };
        timer.start();

        createMap();

        bomberman = (Bomber) entities.stream().filter(e -> e instanceof Bomber).findFirst().get();
        scene.setOnKeyPressed(bomberman::moveControl);
        scene.setOnKeyReleased(bomberman::OnKeyRelease);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        currentStage = stage;

        bgmPlaying = true;
        Sound.play("bgm");
    }

    private static void createMap() {
        Mapper.readMap(level);
        entities = Mapper.mobile;
        stillObjects = Mapper.immobile;
        Physics.updateObjects();
    }

    public void update() {
        try {
            stillObjects.forEach(Entity::update);
            effects.forEach(Entity::update);
            entities.forEach(Entity::update);
        } catch (ConcurrentModificationException c) {
            bomberman = (Bomber) entities.stream().filter(e -> e instanceof Bomber).findFirst().get();
            currentStage.getScene().setOnKeyPressed(bomberman::moveControl);
            currentStage.getScene().setOnKeyReleased(bomberman::OnKeyRelease);
        }
    }

    public void render() {
        infoGc.clearRect(0, 0, info.getWidth(), info.getHeight());
        infoGc.setFill(Color.BLACK);
        infoGc.fillRect(0, 0, info.getWidth(), info.getHeight());
        infoGc.setFill(Color.WHITE);
        infoGc.fillText("Score: " + Bomber.getScore(), info.getWidth() / 2, info.getHeight() / 2);
        if (!gameOver) {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            stillObjects.forEach(g -> g.render(gc));
            effects.forEach(e -> e.render(gc));
            entities.forEach(g -> g.render(gc));
        } else {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.setFill(Color.WHITE);
            gc.fillText("Game Over", canvas.getWidth() / 2, canvas.getHeight() / 2 + 10);
        }
    }

    public static void endGame() {
        //timer.stop();
        gameOver = true;
        bgmPlaying = false;
        Sound.play("gameOver");
    }

    public static void newLevel() {
        level++;
        bgmPlaying = false;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Bomber.resetCountBombers();
        createMap();
        timer.start();
        bgmPlaying = true;
        Sound.play("bgm");
    }
}
