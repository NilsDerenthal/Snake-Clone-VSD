package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.control.SceneConfig;

import javax.swing.plaf.ColorUIResource;

public class BarField extends GraphicalObject {

    private int points;

    public BarField(ViewController viewController) {
        this.x = 450;
        this.y = 600;
        this.width = 201;
        this.height = 21;
        viewController.draw(this, SceneConfig.GAME_SCENE);
    }

    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawRectangle(x,y,width,height);
        drawTool.setCurrentColor(ColorUIResource.BLACK);
        drawTool.drawText(750,610,"Points: "+ points);
    }

    public void increasePoints() {
        points++;
    }

    public void resetPoints(){
        points = 0;
    }

    public int getPoints() {
        return points;
    }
}
