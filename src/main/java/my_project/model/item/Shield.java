package my_project.model.item;

import KAGO_framework.view.DrawTool;
import my_project.model.game.Player;

import java.awt.*;

public class Shield extends GameItem{

    public Shield(double alphaChangeRate, Player player) {
        super(alphaChangeRate, player);
        duration = 10000;
        timer = 0;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.BLUE);
        drawTool.drawFilledRectangle(x,y,width,height);
    }

    @Override
    public void update(double dt) {
        if(timer < duration){
            player.setShieldet(false);
            timer += dt;
        }
    }

    @Override
    public void effect() {
        player.setShieldet(true);
    }
}
