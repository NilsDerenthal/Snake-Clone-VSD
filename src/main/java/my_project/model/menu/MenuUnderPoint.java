package my_project.model.menu;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.VisualList;

import java.awt.*;

public class MenuUnderPoint extends GraphicalObject implements VisualList.AnimableList {

    public interface Command {
        void execute();
    }

    private final VisualList<MenuUnderPoint> list;
    private final Color color;
    private final String text;
    private final Command command;

    public MenuUnderPoint(double heigth, double widht, Command command, Color color,String text,VisualList<MenuUnderPoint> list){
        this.command = command;
        this.color=color;
        this.text=text;
        this.height=heigth;
        this.width=widht;
        this.list=list;
    }

    public void draw(DrawTool drawTool){
        if(list.getCurrent()==this){
            drawTool.setCurrentColor(Color.YELLOW);
            drawTool.drawFilledRectangle(x-10,y-10,width+20,height+20);
        }
        drawTool.setCurrentColor(color);
        drawTool.drawFilledRectangle(x,y,width,height);
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawRectangle(x,y,width,height);
        drawTool.drawText(x,y+height-2,text);
    }

    @Override
    public void update(double dt){

    }

    @Override
    public boolean tryToDelete() {
        return false;
    }

    public void clickOn(){
        command.execute();
    }
}