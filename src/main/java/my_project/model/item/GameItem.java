package my_project.model.item;

import KAGO_framework.view.DrawTool;
import my_project.model.game.Entity;
import my_project.model.game.Player;

public class GameItem extends Entity {

    protected boolean active;
    protected Player player;
    protected int duration;

    public GameItem(double alphaChangeRate, Player player) {
        super(alphaChangeRate);
        this.player = player;
    }

    public boolean itemPicked(){
        if(player.getPosX() == getX() & player.getPosY() == getY()){
            active = true;
            return true;
        }
        return false;
    }

    public void effect(){

    }

    @Override
    public void draw(DrawTool drawTool) {

    }
}
