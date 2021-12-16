package my_project.model.item;

import KAGO_framework.view.DrawTool;
import my_project.model.game.Player;

public class DeleteBodypart extends GameItem{

    public DeleteBodypart(double alphaChangeRate, Player player) {
        super(alphaChangeRate, player);
        width = 30;
        height = 20;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.drawFilledRectangle(x,y,width,height);
    }

    @Override
    public void effect() {
        player.deleteBodyPart();
    }
}
