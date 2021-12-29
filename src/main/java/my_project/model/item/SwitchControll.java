package my_project.model.item;

import KAGO_framework.view.DrawTool;
import my_project.model.game.Player;

import java.awt.*;

public class SwitchControll extends GameItem{

    public SwitchControll(double alphaChangeRate, Player player) {
        super(alphaChangeRate, player);
        width = 30;
        height = 20;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.ORANGE);
        drawTool.drawFilledRectangle(x,y,width,height);
    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void effect() {
        if(player.isShielded()){
            player.setShielded(false);
        }else if(player.isSwitchControll()){
            player.setSwitchControll(false);
        }else{
            player.setSwitchControll(true);
        }
        spawned = false;
    }
}
