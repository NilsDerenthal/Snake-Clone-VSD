package my_project.model.game;

import KAGO_framework.view.DrawTool;

import java.awt.*;

public class Point extends Entity{

    private String typ;

    public Point(double alphaChangeRate, String typ) {
        super(alphaChangeRate);
        this.typ = typ;
    }

    @Override
    public void draw(DrawTool drawTool) {
        if(typ.equals("typ 1")){
            drawTool.setCurrentColor(Color.MAGENTA);
        }else if(typ.equals("typ 2")){
            drawTool.setCurrentColor(Color.PINK);
        }else{
            drawTool.setCurrentColor(Color.CYAN);
        }
        drawTool.drawFilledRectangle(x,y,width,height);
    }

    public String getTyp() {
        return typ;
    }
}
