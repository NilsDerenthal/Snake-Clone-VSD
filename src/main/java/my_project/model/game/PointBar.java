package my_project.model.game;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.VisualStack;

import java.awt.*;

/**
 * 15.12.2021 class created by Fatih, Visualisation of the PointBar
 */
public class PointBar extends GraphicalObject implements VisualStack.Animated {

    private int counter;
    private int startX;
    private int r, g, b;
    private boolean full; //zeigt an ob die Leiste voll ist

    public PointBar(int x, double y, double width, double height, int startX) {
        this.counter = 1;
        this.startX = startX;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.r = 255;
        this.g = 0;
        this.b = 0;
        full = false;
    }

    public void draw(DrawTool drawTool) {
        drawTool.drawRectangle(x, y, width * 40, height);
        drawTool.setCurrentColor(r, g, b, 255);
        drawTool.drawFilledRectangle(x, y, width, height);
    }

    public void update(double dt) {
        //TODO Farbe ändern wenn die Leiste voll ist, damit man sieht dass man mehr Punkte hat
        if (!full) { // Bewegung wenn die Leiste nicht voll ist
            x = x - 20 * dt;
            if (x == width * 40) { // Gucken ob das letzte Rechteck das Ende erreicht hat, wenn ja dann full = true;
                x = startX;
                r= (int) (Math.random()* 255+0);
                g= (int) (Math.random()* 255+0);
                b= (int) (Math.random()* 255+0);
                full = true;
            }
        }
    }

    @Override
    public void comeIn() {
        setX(counter * width);
        setY(y);
        counter++;
        System.out.println(counter);
    }

    @Override
    public void goOut() {

    }
}
