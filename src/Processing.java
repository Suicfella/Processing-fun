import processing.core.PApplet;
import processing.core.PImage;


class Processor extends PApplet {



    public static void main(String[] args) {
        PApplet.main("Processor");

    }

    @Override
    public void settings() {
        size(750, 500);
    }

    @Override
    public void draw() {
        super.draw();
        background(0, 0, 0);
    }

    @Override
    public void mouseClicked() {
        System.out.println("X: " + mouseX + "\n" + "Y: " + mouseY);
    }
}