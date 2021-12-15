package my_project.model.menu;

import my_project.Config;
import KAGO_framework.view.DrawTool;

import java.awt.*;

public class StartMenu extends MenuUnderPoint {

    public StartMenu(double y){
        this.y = y;
        sY = y;
        //Todo fix sY
    }

    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(Color.GREEN);
        drawTool.drawFilledRectangle(((Config.WINDOW_WIDTH - 130)/2f + 130) - 110, y + 30, 220, 50);

        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawRectangle(((Config.WINDOW_WIDTH - 130)/2f + 130) - 110, y + 30, 220, 50);

        drawTool.formatText("", 1, 20);
        drawTool.drawText(((Config.WINDOW_WIDTH - 130)/2f + 130) - 100, y + 65, "start game");
    }

    @Override
    public void clickOn() {
        // TODO?
    }
}
