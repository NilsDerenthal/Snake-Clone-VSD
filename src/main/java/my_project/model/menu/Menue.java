package my_project.model.menu;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;

import java.awt.*;

public class Menue extends GraphicalObject {

    private final int height;
    private final int width;

    public Menue(ViewController viewController){
        viewController.draw(this);
        height=Config.WINDOW_HEIGHT;
        width=Config.WINDOW_WIDTH;
    }

    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(Color.gray);
        drawTool.drawFilledRectangle(0,0,width,height);
        System.out.print("a");
    }

    public void update(double dt){

    }
}
