package my_project.model.game;

import KAGO_framework.view.DrawTool;

public class Point extends Entity {

    private final int posX, posY;
    private double yRoation=0,zRotation=0;

    public Point(int posX, int posY) {
        super(Integer.MAX_VALUE);
        this.posX = posX;
        this.posY = posY;
        width = 20;
        height = 20;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.drawCube(x+10,y+10,20,0,yRoation,zRotation);
        /*
        drawTool.setCurrentColor(Color.yellow);
        drawTool.drawFilledRectangle(x,y,width,height);
         */
    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        yRoation+=20*dt;
        zRotation+=20*dt;
    }
}
