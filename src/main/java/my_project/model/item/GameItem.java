package my_project.model.item;

import KAGO_framework.view.DrawTool;
import my_project.model.game.Entity;
import my_project.model.game.Player;

public class GameItem extends Entity {

    protected boolean active, spawned;
    protected Player player;
    protected int timer, posX, posY, duration;

    public GameItem(double alphaChangeRate, Player player) {
        super(alphaChangeRate);
        this.player = player;
    }

    public void effect(){

    }

    public void spawn(){
        timer = 0;
        spawned = true;
    }

    @Override
    public void draw(DrawTool drawTool) {

    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean isSpawned() {
        return spawned;
    }
}
