package my_project.model.item;

import KAGO_framework.view.DrawTool;
import my_project.model.game.Player;

import java.awt.*;

public class SwitchControll extends GameItem{

    public SwitchControll(double alphaChangeRate, Player player) {
        super(alphaChangeRate, player);
        duration = 100;
        timer = 0;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.GRAY);
        drawTool.drawFilledRectangle(x,y,width,height);
    }

    @Override
    public void update(double dt) {
        if(active) {
            if (timer > duration) {
                player.setShielded(false);
                active = false;
            }
            timer += dt;
        }
    }

    @Override
    public void effect() {
        player.setSwitchControll(true);
        active = true;
        spawned = false;
    }
}
