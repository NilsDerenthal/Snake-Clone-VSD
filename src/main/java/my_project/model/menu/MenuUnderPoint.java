package my_project.model.menu;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.model.visual_ds.VisualList;

import java.awt.*;

public class MenuUnderPoint extends GraphicalObject implements VisualList.AnimableList {

    public interface Command {
        void execute();
    }

    private final Color color;
    private final String text;
    private final Command command;
    private VisualList inList;

    public MenuUnderPoint(double heigth, double widht, Command command, Color color,String text){
        this.command = command;
        this.color=color;
        this.text=text;
        this.height=heigth;
        this.width=widht;
    }

    public void draw(DrawTool drawTool){
        if(inList.getCurrent()==this){
            drawTool.setCurrentColor(Color.YELLOW);
            drawTool.drawRectangle(x-10,y-10,width+20,height+20);
        }
        drawTool.setCurrentColor(color);
        drawTool.drawFilledRectangle(x,y,width,height);
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawRectangle(x,y,width,height);
        drawTool.drawText(x+5,y+height-5,text);
    }

    @Override
    public void update(double dt){
    }

    @Override
    public boolean tryToDelete() {
        return false;
    }

    public void setInList(VisualList list){ inList=list; }

    public void clickOn(){
        command.execute();
    }
}