package my_project.model.item;

import KAGO_framework.view.DrawTool;
import my_project.model.game.Entity;
import my_project.model.game.Player;

import java.awt.*;

public abstract class GameItem extends Entity {

    protected boolean active;
    protected Player player;
    protected int posX, posY;

    private final Color color;

    public GameItem(Player player, Color color) {
        super(255);

        this.player = player;
        this.color = color;
        this.width = 30;
        this.height = 20;
    }

    public abstract void effect();


    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(color);
        drawTool.drawFilledRectangle(x, y, width, height);
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
}
