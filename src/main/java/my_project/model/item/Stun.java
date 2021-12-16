package my_project.model.item;

import KAGO_framework.view.DrawTool;
import my_project.model.game.Player;

import java.awt.*;

public class Stun extends GameItem{


    public Stun(double alphaChangeRate, Player player) {
        super(alphaChangeRate, player);
        duration = 10000;
        timer = 0;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.GRAY);
        drawTool.drawFilledRectangle(x,y,width,height);
    }

    @Override
    public void update(double dt) {
        if(timer < duration){
            player.setStunned(false);
            timer += dt;
        }
    }

    @Override
    public void effect() {
        player.setStunned(true);
    }
}
