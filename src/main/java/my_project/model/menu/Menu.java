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
    }

    public void creatMenue(){
        leftList.append(new MenuPoint(30,Config.WINDOW_HEIGHT/2-150,viewController,leftList,"Start"));
        leftList.toFirst();
        leftList.getCurrent().append(new MenuUnderPoint(30,100,/*()->{},*/Color.GREEN,"beginnen",leftList.getCurrent().getList()));
        leftList.getCurrent().append(new MenuUnderPoint(30,100,/*()->{},*/Color.RED,"Spiel beenden",leftList.getCurrent().getList()));
        leftList.append(new MenuPoint(800,Config.WINDOW_HEIGHT/2-150,viewController,leftList,"farben"));
        leftList.next();
        leftList.getCurrent().append(new MenuUnderPoint(30,100,/*()->{},*/Color.BLUE,"",leftList.getCurrent().getList()));
        leftList.getCurrent().append(new MenuUnderPoint(30,100,/*()->{},*/Color.RED,"",leftList.getCurrent().getList()));
        leftList.getCurrent().append(new MenuUnderPoint(30,100,/*()->{},*/Color.GREEN,"",leftList.getCurrent().getList()));


        leftList.toFirst();
        leftList.getCurrent().changeUp(true);
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
                110, Config.WINDOW_HEIGHT - 50,
                130, Config.WINDOW_HEIGHT - 70,
                130, 30,
                110, 10
        );
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawPolygon(
                30, 10,
                10, 30,
                10, Config.WINDOW_HEIGHT - 70,
                30, Config.WINDOW_HEIGHT - 50,
                110, Config.WINDOW_HEIGHT - 50,
                130, Config.WINDOW_HEIGHT - 70,
                130, 30,
                110, 10
        );
    }

    public void next(){
        if(leftList.getCurrent()!=null) {
            leftList.getCurrent().changeUp(false);
            leftList.next();
            if(leftList.getCurrent()==null) leftList.previous();
            if(leftList.getCurrent()!=null) leftList.getCurrent().changeUp(true);
        }
    }

    public void previous(){
        if(leftList.getCurrent()!=null) leftList.getCurrent().changeUp(false);
        leftList.previous();
        if (leftList.getCurrent() != null) leftList.getCurrent().changeUp(true);
    }

    public void left(){
        leftList.getCurrent().getList().previous();
    }

    public void right(){
        leftList.getCurrent().getList().next();
    }

    public void clickOn(){
        leftList.getCurrent().getList().getCurrent().clickOn();
    }
}
