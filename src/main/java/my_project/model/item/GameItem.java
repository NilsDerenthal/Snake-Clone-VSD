package my_project.model.item;

import KAGO_framework.view.DrawTool;
import com.sun.tools.javac.Main;
import my_project.model.game.Entity;
import my_project.model.game.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameItem extends Entity {

    protected boolean active;
    protected Player player;
    protected int posX, posY;

    private final BufferedImage img;

    public GameItem(Player player, String imgPath) {
        super(255);

        this.player = player;
        this.img = createImage("src/main/resources/graphic/" + imgPath);
        this.width = 30;
        this.height = 20;
    }

    public abstract void effect();


    @Override
    public void draw(DrawTool drawTool) {
        double max = Math.min(img.getHeight(), img.getWidth());

        drawTool.drawTransformedImage(img, x - 30, y - 30, 0, 30 / max);
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
