package my_project.model.menu;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.control.SceneConfig;
import my_project.model.visual_ds.VisualList;

import java.awt.*;

public class Menu extends GraphicalObject {

    private final VisualList<MenuPoint> leftList;

    public Menu(ViewController viewController){
        viewController.draw(this, SceneConfig.MENU_SCENE);
        leftList=new VisualList<>(0,50,20,40);
        new MenuPoint(100,viewController,leftList);
    }

    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(Color.darkGray);
        drawTool.drawFilledRectangle(0,0,Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        drawTool.setCurrentColor(Color.gray);
        drawTool.drawFilledPolygon(30,10,10,30,10,Config.WINDOW_HEIGHT-70,30,Config.WINDOW_HEIGHT-50,80,Config.WINDOW_HEIGHT-50,100,Config.WINDOW_HEIGHT-70,100,30,80,10);
    }

    public void update(double dt){

    }
}
