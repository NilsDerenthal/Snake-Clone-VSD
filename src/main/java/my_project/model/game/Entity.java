package my_project.model.game;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.Visual2DArray;
import my_project.model.visual_ds.VisualQueue;

/**
 * 14.12.2021: This is a abstract class for entities, e.g. our player and the enemies
 */
public abstract class Entity extends GraphicalObject implements Visual2DArray.Animatable {

    @Override
    public abstract void draw(DrawTool d);
}
