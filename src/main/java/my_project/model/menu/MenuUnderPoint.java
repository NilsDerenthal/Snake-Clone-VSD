package my_project.model.menu;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.VisualList;

import java.awt.*;

public class MenuUnderPoint extends GraphicalObject implements VisualList.AnimableList {

    public interface Command {
        void execute();
    }

    private final Color color;
    private final String text;
    private double sY;
    private final Command command;

    public MenuUnderPoint(double y, double x, double heigth, double widht, Command command, Color color,String text){
        this.y = y;
        sY = this.y;
        //TODO fix sY
        this.command = command;
        this.color=color;
        this.text=text;
        this.x=x;
        this.height=heigth;
        this.width=widht;
    }

    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(color);
        drawTool.drawFilledRectangle(x,y,width,height);
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawRectangle(x,y,width,height);
        drawTool.drawText(x,y+height-2,text);
    }

    @Override
    public void update(double dt){
        // TODO duplicate code with MenuPoint
        if(y != sY){
            if(y < sY - 1){
                y += 10 * dt;
            }else if(y > sY + 1){
                y -= 10 * dt;
            }
        }
    }

    @Override
    public boolean tryToDelete() {
        return false;
    }

    public void changeY(double newY){
        sY=newY;
    }

    public void clickOn(){
        command.execute();
    }
}