package my_project.model.menu;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.VisualList;

import java.awt.*;

public class MenuSubOption extends GraphicalObject implements VisualList.AnimableList {

    public interface OnClickExecutor {
        void execute();
    }

    private final Color color;
    private final String text;
    private final OnClickExecutor onClickExecutor;

    public boolean isSelected;

    public MenuSubOption (double height, double width, OnClickExecutor onClickExecutor, Color color, String text){
        this.onClickExecutor = onClickExecutor;
        this.color = color;
        this.text = text;
        this.height = height;
        this.width = width;
    }

    public void draw(DrawTool drawTool){
        if (isSelected){
            drawTool.setCurrentColor(Color.YELLOW);
            drawTool.drawFilledRectangle(x - 10,y - 10,width + 20,height + 20);
        }

        drawTool.setCurrentColor(color);
        drawTool.drawFilledRectangle(x, y, width, height);

        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawRectangle(x, y, width, height);
        drawTool.drawText(x,y + height - 2, text);
    }

    @Override
    public void update(double dt){

    }

    @Override
    public boolean tryToDelete() {
        return false;
    }

    public void clickOn(){
        onClickExecutor.execute();
    }

    public void setSelected (boolean selected) {
        isSelected = selected;
    }
}
