package my_project.model.menu;

import KAGO_framework.model.GraphicalObject;
import my_project.model.visual_ds.VisualList;

public abstract class MenueUnderPoint extends GraphicalObject implements VisualList.AnimableList {
    @Override
    public boolean tryToDelete() {
        return false;
    }
}
