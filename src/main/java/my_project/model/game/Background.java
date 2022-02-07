package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.control.SceneConfig;

import java.awt.*;

public class Background extends GraphicalObject {

    public Background(ViewController v){
        v.draw(this, SceneConfig.GAME_SCENE);
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.GRAY);
        drawTool.drawFilledRectangle(0,0, Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
    }
}
