/**
 * 14.12.2021: This is a abstract class for entities, e.g. our player and the enemies
 */

package my_project.model.game;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.Visual2DArray;

public abstract class Entity extends GraphicalObject implements Visual2DArray.Animatable {

    private boolean fadingIn, fadingOut;
    private double alpha, alphaChangeRate;

    public Entity(double alphaChangeRate) {
        this.alphaChangeRate = alphaChangeRate;
    }

    @Override
    public abstract void draw(DrawTool drawTool);

    @Override
    public void update(double dt) {
        double change = dt * alphaChangeRate;
        if (fadingOut)
            this.alpha = Math.max(0, this.alpha - change);
        if (fadingIn)
            this.alpha = Math.min(255, this.alpha + change);
    }

    @Override
    public void fadeIn() {
        fadingIn = true;
    }

    @Override
    public void fadeOut() {
        fadingOut = true;
    }
}
