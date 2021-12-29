package my_project.model.item;

import KAGO_framework.view.DrawTool;
import my_project.model.game.Player;

import java.awt.*;

public class Stun extends GameItem{


    public Stun(double alphaChangeRate, Player player) {
        super(alphaChangeRate, player);
        width = 30;
        height = 20;
        duration = 100;
        timer = 0;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawFilledRectangle(x,y,width,height);
    }

    @Override
    public void update(double dt) {
            if(active) {
                if (timer > duration) {
                    active = false;
                    player.setStunned(false);
            }
             timer += dt;
            }
    }

    @Override
    public void effect() {
        if(player.isShielded()){
            player.setShielded(false);
        }else{
            player.setStunned(true);
            active = true;
        }
        spawned = false;
    }
}
