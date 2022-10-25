package uet.oop.bomberman.utilities;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Animator {
    private ArrayList<Image> animateUp = new ArrayList<>();
    private ArrayList<Image> animateDown = new ArrayList<>();
    private ArrayList<Image> animateLeft = new ArrayList<>();
    private ArrayList<Image> animateRight = new ArrayList<>();
    private ArrayList<Image> destroyed = new ArrayList<>();
    private ArrayList<Image> currentAnimation;
    private boolean simple;
    private int option;
    private int f_index;

    public Animator() {
        option = 4;
        f_index = 0;
        simple = false;
        currentAnimation = animateRight;
    }
    public void addAnimateUp(Image frame) {
        animateUp.add(frame);
    }

    public void addAnimateDown(Image frame) {
        animateDown.add(frame);
    }

    public void addAnimateLeft(Image frame) {
        animateLeft.add(frame);
    }

    public void addAnimateRight(Image frame) {
        animateRight.add(frame);
    }

    public void addAnimateDestroyed(Image frame) {
        destroyed.add(frame);
    }

    public void setSimple(boolean simple) {
        this.simple = simple;
    }
    public Image nextFrame(int inOption) {
        Image next;
        if (inOption != option || inOption == 0) {
            switch (inOption) {
                case 1 -> {
                    if (simple) {
                        currentAnimation = animateRight;
                    } else {
                        currentAnimation = animateUp;
                    }
                }
                case 2 -> {
                    if (simple) {
                        currentAnimation = animateLeft;
                    } else {
                        currentAnimation = animateDown;
                    }
                }
                case 3 -> {
                    currentAnimation = animateLeft;
                }
                case 4 -> {
                    currentAnimation = animateRight;
                }
                case 0 -> {
                    f_index = 0;
                }
            }
            option = inOption;
        }
        next = currentAnimation.get(f_index);
        f_index = (f_index + 1) % currentAnimation.size();
        return next;
    }
}
