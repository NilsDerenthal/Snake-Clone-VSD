package my_project.model.menu;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.control.SceneConfig;
import my_project.model.visual_ds.VisualList;

import java.awt.*;

public class Menue extends GraphicalObject {

    private final int height;
    private final int width;
    private final VisualList<MenuePoint> leftList;

    public Menue(ViewController viewController){
        viewController.draw(this, SceneConfig.MENU_SCENE);
        height=Config.WINDOW_HEIGHT;
        width=Config.WINDOW_WIDTH;
        leftList=new VisualList<>(0,50,20,40);
        new MenuePoint(100,viewController,leftList);
    }

    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(Color.darkGray);
        drawTool.drawFilledRectangle(0,0,width,height);
        drawTool.setCurrentColor(Color.gray);
        drawTool.drawFilledPolygon(30,10,10,30,10,height-70,30,height-50,80,height-50,100,height-70,100,30,80,10);
    }

    public void update(double dt){

    }
}
