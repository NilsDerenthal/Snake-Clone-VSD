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
    private double sY;
    private double yS;

    public MenuPoint(double y,double yS, ViewController viewController, VisualList<MenuPoint> inList,String text){
        x=30;
        this.y=y;
        this.yS = yS;
        this.sY = yS;
        this.inList = inList;
        this.viewController=viewController;
        this.text=text;
        viewController.draw(this);

        list = new VisualList<>(50, 0, 160, y + 30);

        new MenuUnderPoint(1, 1, 1, 1, () -> {

        },Color.WHITE,"");
    }

    @Override
    public void draw(DrawTool drawTool) {
        if(inList.getCurrent()==this){
            drawTool.setCurrentColor(Color.YELLOW);
            drawTool.drawFilledRectangle(30,y,30,20);
        }
        drawTool.setCurrentColor(Color.GRAY);
        // not better-looking, but easier to read
        drawTool.drawFilledPolygon(
                130, yS+20,
                150, yS,
                Config.WINDOW_WIDTH - 45, yS,
                Config.WINDOW_WIDTH - 25, yS + 20,
                Config.WINDOW_WIDTH - 25, yS + 150,
                Config.WINDOW_WIDTH - 45, yS + 170,
                150, yS + 170,
                130, yS + 150
        );
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawPolygon(
                130, yS+20,
                150, yS,
                Config.WINDOW_WIDTH - 45, yS,
                Config.WINDOW_WIDTH - 25, yS + 20,
                Config.WINDOW_WIDTH - 25, yS + 150,
                Config.WINDOW_WIDTH - 45, yS + 170,
                150, yS + 170,
                130, yS + 150
        );
        drawTool.drawText(30,y+25,text);
        drawTool.drawRectangle(30,y,30,20);
    }

    @Override
    public void update(double dt){
        if(yS != sY){
            if(yS < sY - 1){
                yS += 10 * dt;
            } else if(yS > sY + 1){
                yS -= 10 * dt;
            }
        }
    }

    @Override
    public boolean tryToDelete() {
        return false;
    }

    public void changeY(double newY) {
        sY = newY;
        if(!list.isEmpty()){
            list.toFirst();
            while(list.getCurrent() != null) {
                list.getCurrent().changeY(newY);
                list.next();
            }
        }
    }

    public void append(MenuUnderPoint m){
        list.append(m);
        m.setY(yS);
        viewController.draw(m,SceneConfig.MENU_SCENE);
    }
}
