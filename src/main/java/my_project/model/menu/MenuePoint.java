package my_project.model.menu;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.VisualList;
import my_project.Config;

import java.awt.*;

public  class MenuePoint extends GraphicalObject implements VisualList.AnimableList {

    public MenuePoint(double y, ViewController viewController){
        this.y=y;
        viewController.draw(this);
    }

    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(Color.gray);
        drawTool.drawFilledPolygon(130,y+20,150,y,Config.WINDOW_WIDTH-45,y,Config.WINDOW_WIDTH-25,y+20,130,y+Config.WINDOW_HEIGHT-89,150,y+20+Config.WINDOW_HEIGHT-89,Config.WINDOW_WIDTH-45,y+20+Config.WINDOW_HEIGHT-89,Config.WINDOW_WIDTH-25,y+Config.WINDOW_HEIGHT-89);
        //drawTool.drawFilledPolygon(130,y+Config.WINDOW_HEIGHT-89,150,y+20+Config.WINDOW_HEIGHT-89,Config.WINDOW_WIDTH-45,y+20+Config.WINDOW_HEIGHT-89,Config.WINDOW_WIDTH-25,y+Config.WINDOW_HEIGHT-89);
    }

    public void update(double dt){

    }

    @Override
    public boolean tryToDelete() {
        return false;
    }
}
