package my_project.model.menu;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.control.SceneConfig;
import my_project.model.visual_ds.VisualList;
import my_project.Config;

import java.awt.*;

public class MenuOption extends GraphicalObject implements VisualList.AnimableList {

    private final VisualList<MenuSubOption> list;
    private final VisualList<MenuOption> inList;
    private final ViewController viewController;
    private final String text;
    private double sY;
    private double yS;
    private boolean up = false;

    public MenuOption (double y, double yS, ViewController viewController, VisualList<MenuOption> inList, String text){
        this.x = 30;
        this.y = y;
        this.yS = yS;
        this.sY = yS;
        this.inList = inList;
        this.viewController = viewController;
        this.text = text;
        viewController.draw(this,SceneConfig.MENU_SCENE);

        list = new VisualList<>(140, 0, 170, y + 30);
    }

    @Override
    public void draw(DrawTool drawTool) {
        if(inList.getCurrent()==this){
            drawTool.setCurrentColor(Color.YELLOW);
            drawTool.drawFilledRectangle(30,y,60,20);
        }
        drawTool.setCurrentColor(Color.GRAY);
        // not better-looking, but easier to read
        drawTool.drawFilledPolygon(
                140, yS+20,
                160, yS,
                Config.WINDOW_WIDTH - 45, yS,
                Config.WINDOW_WIDTH - 25, yS + 20,
                Config.WINDOW_WIDTH - 25, yS + 150,
                Config.WINDOW_WIDTH - 45, yS + 170,
                160, yS + 170,
                140, yS + 150
        );
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawPolygon(
                140, yS+20,
                160, yS,
                Config.WINDOW_WIDTH - 45, yS,
                Config.WINDOW_WIDTH - 25, yS + 20,
                Config.WINDOW_WIDTH - 25, yS + 150,
                Config.WINDOW_WIDTH - 45, yS + 170,
                160, yS + 170,
                140, yS + 150
        );
        drawTool.drawText(35,y+20,text);
        drawTool.drawRectangle(30,y,60,20);
    }

    @Override
    public void update(double dt){
        if (up) {
            if (yS > sY - 1) {
                yS = Math.max(Config.WINDOW_HEIGHT / 2f - 150, yS - 1000 * dt);
            }
        } else {
            if(yS < sY - 1){
                yS = Math.min(Config.WINDOW_HEIGHT + 300, yS + 1000 * dt);
            }
        }
        updateList();
    }

    private void updateList(){
        MenuSubOption current=list.getCurrent();
        list.toFirst();
        while(list.getCurrent()!=null){
            list.getCurrent().setY(yS+60);
            list.next();
        }
        list.toFirst();
        while(list.getCurrent()!=current) list.next();
    }

    @Override
    public boolean tryToDelete() {
        return false;
    }

    public void changeUp(boolean to){
        up=to;
        if(up){
            list.toFirst();
            sY=y;
        }else{
            sY=Config.WINDOW_HEIGHT+200;
        }
    }

    public void append(MenuSubOption m){
        list.append(m);
        m.setY(yS);
        viewController.draw(m,SceneConfig.MENU_SCENE);
    }

    public VisualList<MenuSubOption> getList(){
        return list;
    }
}
