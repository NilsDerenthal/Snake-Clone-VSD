package my_project.model.menu;

import KAGO_framework.model.GraphicalObject;
import my_project.model.visual_ds.VisualList;

public abstract class MenuUnderPoint extends GraphicalObject implements VisualList.AnimableList {

    protected double sY;

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

    public abstract void clickOn();
}