package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Mapper;
import uet.oop.bomberman.utilities.Physics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    
    private GraphicsContext gc;
    private Canvas canvas;

    private static List<Entity> entities = new ArrayList<>();
    public static List<Entity> getEntities() {
        return entities;
    }
    public static void addEntity(Entity entity) {
        entities.add(entity);
    }
    public static void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    private static List<Entity> stillObjects = new ArrayList<>();
    public static List<Entity> getStillObjects() {
        return stillObjects;
    }
    public static void removeStillObject(Entity object) {
        stillObjects.remove(object);
    }

    private static List<Entity> effects = new ArrayList<>();
    public static List<Entity> getEffects() {
        return effects;
    }
    public static void addEffect(Entity effect) {
        effects.add(effect);
    }
    public static void removeEffect(Entity effect) {
        effects.remove(effect);
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long l) {
                update();
                render();
            }
        };
        timer.start();

        createMap();

        Bomber bomberman = (Bomber) entities.stream().filter(e -> e instanceof Bomber).findFirst().get();
        scene.setOnKeyPressed(bomberman::moveControl);
        scene.setOnKeyReleased(bomberman::OnKeyRelease);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
    }

    public void createMap() throws IOException {
        Mapper.readMap();
        entities = Mapper.getMobile();
        stillObjects = Mapper.getImmobile();
        Physics.updateObjects();
    }

    public void update() {
        try {
            entities.forEach(Entity::update);
            effects.forEach(Entity::update);
        } catch (ConcurrentModificationException c) {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        effects.forEach(e -> e.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
