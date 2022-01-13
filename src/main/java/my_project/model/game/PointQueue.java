package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.VisualQueue;

import java.awt.*;

public class PointQueue extends Entity {

    private class Point extends Entity implements VisualQueue.Animatable{

        public Point() {
            super(Integer.MAX_VALUE);

            width = height = 20;
        }

        @Override
        public void draw(DrawTool drawTool) {

        }

        @Override
        public void fadeOut(boolean fadeOut) {

        }

        @Override
        public void setTx(double tx) {
            x = tx;
        }

        @Override
        public void setTy(double ty) {
            y = ty;
        }

        @Override
        public double getTx() {
            return x;
        }

        @Override
        public double getTy() {
            return y;
        }

        @Override
        public boolean isArrived() {
            return true;
        }
    }

    private String typ;
    private VisualQueue<Point> pointQueue;

    public PointQueue(ViewController viewController, String typ) {
        super(Integer.MAX_VALUE);

        pointQueue = new VisualQueue<>(viewController, 1000,900,"up");
        this.typ = typ;
        radius = 20;
    }


    @Override
    public void draw(DrawTool drawTool) {
        if(typ.equals("typ 1")){
            drawTool.setCurrentColor(Color.MAGENTA);
        }else if(typ.equals("typ 2")){
            drawTool.setCurrentColor(Color.PINK);
        }else{
            drawTool.setCurrentColor(Color.CYAN);
        }
        drawTool.drawFilledCircle(x,y,radius);
    }

    public String getTyp() {
        return typ;
    }

    public void spawnRandomPoint(){
        int r = (int) (Math.random()*12);
    }
}
