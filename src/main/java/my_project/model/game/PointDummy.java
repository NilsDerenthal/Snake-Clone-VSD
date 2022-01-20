package my_project.model.game;

import KAGO_framework.view.DrawTool;

import java.awt.*;

public class PointDummy extends Entity{

    public PointDummy(){
        super(Integer.MAX_VALUE);
        this.width = 30;
        this.height = 20;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.pink);
        drawTool.drawFilledRectangle(x,y,width,height);
    }
}
