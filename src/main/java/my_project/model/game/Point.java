package my_project.model.game;

import KAGO_framework.view.DrawTool;
import my_project.model.game.Entity;
import my_project.model.game.Player;

import java.awt.*;


public class Point extends Entity {

    private int posX, posY;
        public Point(int posX, int posY) {
            super(Integer.MAX_VALUE);
            this.posX = posX;
            this.posY = posY;
            width = 20;
            height = 20;
        }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.pink);
        drawTool.drawFilledRectangle(x,y,width,height);
    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }
}
