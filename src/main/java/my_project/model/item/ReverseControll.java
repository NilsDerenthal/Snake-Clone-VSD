package my_project.model.item;

import KAGO_framework.view.DrawTool;
import my_project.model.game.Player;

import java.awt.*;

public class ReverseControll extends GameItem{

    public ReverseControll(double alphaChangeRate, Player player) {
        super(alphaChangeRate, player);
        duration = 10000;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.GRAY);
        drawTool.drawFilledRectangle(x,y,width,height);
    }

    @Override
    public void update(double dt) {
        if(duration > dt){
            player.setStunned(false);
            duration += dt;
        }
    }

    @Override
    public void effect() {
        player.setReversed(true);
    }
}
