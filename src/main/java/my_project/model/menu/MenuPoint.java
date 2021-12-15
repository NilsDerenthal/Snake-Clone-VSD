package my_project.model.menu;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.VisualList;
import my_project.Config;

import java.awt.*;

public  class MenuPoint extends GraphicalObject implements VisualList.AnimableList {

    private final VisualList<MenuUnderPoint> list;
    private final VisualList<MenuPoint> inList;
    private double sY;

    public MenuPoint(double y, ViewController viewController, VisualList inList){
        this.y=y;
        sY=y;
        viewController.draw(this);
        list=new VisualList<>(50,0,160,y+30);
        this.inList=inList;
        StartMenu m=new StartMenu(y);
        list.append(m);
        viewController.draw(m);
    }

    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(Color.gray);
        drawTool.drawFilledPolygon(130,y+20,150,y,Config.WINDOW_WIDTH-45,y,Config.WINDOW_WIDTH-25,y+20,Config.WINDOW_WIDTH-25,y+150,Config.WINDOW_WIDTH-45,y+170,150,y+170,130,y+150);
    }

    public void update(double dt){
        if(y!=sY){
            if(y<sY-1){
                y+=10*dt;
            }else if(y>sY+1){
                y-=10*dt;
            }
        }
    }

    @Override
    public boolean tryToDelete() {
        return false;
    }

    public void chengeY(double newY) {
        sY=newY;
        if(!list.isEmpty()){
            list.toFirst();
            while(list.getCurrent()!=null) {
                list.getCurrent().changeY(newY);
                list.next();
            }
        }
    }
}
