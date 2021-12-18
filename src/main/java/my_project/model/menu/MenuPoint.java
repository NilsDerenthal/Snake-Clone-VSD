package my_project.model.menu;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.control.SceneConfig;
import my_project.model.visual_ds.VisualList;
import my_project.Config;

import java.awt.*;

public  class MenuPoint extends GraphicalObject implements VisualList.AnimableList {

    private final VisualList<MenuUnderPoint> list;
    private final VisualList<MenuPoint> inList;
    private final ViewController viewController;
    private final String text;
    private double yS;
    private boolean up=false;

    public MenuPoint(double y,double yS, ViewController viewController, VisualList<MenuPoint> inList,String text){
        x=30;
        this.y=y;
        this.yS = yS;
        this.inList = inList;
        this.viewController=viewController;
        this.text=text;
        viewController.draw(this);
        list = new VisualList<>(120, 0, 190, yS+50);
    }

    @Override
    public void draw(DrawTool drawTool) {
        if(inList.getCurrent()==this){
            drawTool.setCurrentColor(Color.YELLOW);
            drawTool.drawFilledRectangle(30,y,50,30);
        }
        drawTool.setCurrentColor(Color.GRAY);
        // not better-looking, but easier to read
        drawTool.drawFilledPolygon(
                160, yS+20,
                180, yS,
                Config.WINDOW_WIDTH - 45, yS,
                Config.WINDOW_WIDTH - 25, yS + 20,
                Config.WINDOW_WIDTH - 25, yS + 150,
                Config.WINDOW_WIDTH - 45, yS + 170,
                180, yS + 170,
                160, yS + 150
        );
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawPolygon(
                160, yS+20,
                180, yS,
                Config.WINDOW_WIDTH - 45, yS,
                Config.WINDOW_WIDTH - 25, yS + 20,
                Config.WINDOW_WIDTH - 25, yS + 150,
                Config.WINDOW_WIDTH - 45, yS + 170,
                180, yS + 170,
                160, yS + 150
        );
        drawTool.drawText(35,y+20,text);
        drawTool.drawRectangle(30,y,50,30);
    }

    @Override
    public void update(double dt){
        if(up){
            if(yS>Config.WINDOW_HEIGHT/2-140){
                yS-=1000*dt;
            }else{
                yS=Config.WINDOW_HEIGHT/2-150;
            }
        }else{
            if(yS<Config.WINDOW_HEIGHT+ 300){
                yS+=1000*dt;
            }else{
                yS=Config.WINDOW_HEIGHT+ 300;
            }
        }
        list.updateAllY(yS+50);
    }

    @Override
    public boolean tryToDelete() {
        return false;
    }

    public void changeUp(boolean to){
        up=to;
    }

    public void append(MenuUnderPoint m){
        list.append(m);
        m.setY(yS+50);
        viewController.draw(m,SceneConfig.MENU_SCENE);
    }
}
