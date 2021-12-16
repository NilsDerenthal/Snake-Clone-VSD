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
    private final ViewController viewController;

    public Menu(ViewController viewController){
        this.viewController=viewController;
        viewController.draw(this, SceneConfig.MENU_SCENE);
        leftList = new VisualList<>(0, 50, 20, 40);
        creatMenue();
        leftList.toFirst();
    }

    public void creatMenue(){
        leftList.append(new MenuPoint(30,Config.WINDOW_HEIGHT/2-150,viewController,leftList,"a"));
        leftList.toFirst();
        leftList.getCurrent().append(new MenuUnderPoint(0,(Config.WINDOW_WIDTH-130)/2+130,30,100,()->{},Color.GREEN,"beginnen"));
        leftList.append(new MenuPoint(30,Config.WINDOW_HEIGHT/2-150,viewController,leftList,"b"));
        leftList.next();
        leftList.getCurrent().append(new MenuUnderPoint(0,(Config.WINDOW_WIDTH-130)/2+130,30,100,()->{},Color.GRAY,"text"));
    }

    @Override
    public void draw(DrawTool drawTool){
        // background
        drawTool.setCurrentColor(Color.DARK_GRAY);
        drawTool.drawFilledRectangle(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);

        drawTool.setCurrentColor(Color.GRAY);
        drawTool.drawFilledPolygon(
                30, 10,
                10, 30,
                10, Config.WINDOW_HEIGHT - 70,
                30, Config.WINDOW_HEIGHT - 50,
                80, Config.WINDOW_HEIGHT - 50,
                100, Config.WINDOW_HEIGHT - 70,
                100, 30,
                80, 10
        );
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawPolygon(
                30, 10,
                10, 30,
                10, Config.WINDOW_HEIGHT - 70,
                30, Config.WINDOW_HEIGHT - 50,
                80, Config.WINDOW_HEIGHT - 50,
                100, Config.WINDOW_HEIGHT - 70,
                100, 30,
                80, 10
        );
    }

    public void next(){
        if(leftList.getCurrent()!=null) {
            leftList.getCurrent().changeY(Config.WINDOW_HEIGHT + 300);
            leftList.next();
            if(leftList.getCurrent()==null){
                leftList.previous();
            }
            if(leftList.getCurrent()!=null) leftList.getCurrent().changeY(Config.WINDOW_HEIGHT / 2 - 150);
        }
    }

    public void previous(){
        if(leftList.getCurrent()!=null) {
            leftList.getCurrent().changeY(Config.WINDOW_HEIGHT + 300);
            leftList.previous();
            if (leftList.getCurrent() != null) leftList.getCurrent().changeY(Config.WINDOW_HEIGHT / 2 - 150);
        }
    }
}
