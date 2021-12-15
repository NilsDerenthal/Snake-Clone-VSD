package my_project.model.menu;

import KAGO_framework.model.GraphicalObject;
import my_project.model.visual_ds.VisualList;

public abstract class MenueUnderPoint extends GraphicalObject implements VisualList.AnimableList {

    protected double sY;
    @Override
    public boolean tryToDelete() {
        return false;
    }

    public abstract void clickOn();

    public void changeY(double newY){
        sY=newY;
    }

    public void update(double dt){
        if(y!=sY){
            if(y<sY-1){
                y+=10*dt;
            }else if(y>sY+1){
                y-=10*dt;
            }
        }
    }
}