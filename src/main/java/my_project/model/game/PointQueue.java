package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.VisualQueue;

import java.awt.*;

public class PointQueue extends Entity {

    private class Point extends Entity implements VisualQueue.Animatable{

        private String typ;

        public Point(String typ) {
            super(Integer.MAX_VALUE);

            this.typ = typ;
            width = 20;
            height = 20;
        }

        @Override
        public void draw(DrawTool drawTool) {
            switch (typ) {
                case "typ 1" -> drawTool.setCurrentColor(Color.cyan);
                case "typ 2" -> drawTool.setCurrentColor(Color.magenta);
                case "typ 3" -> drawTool.setCurrentColor(Color.pink);
                default -> drawTool.setCurrentColor(Color.black);
            }
            drawTool.drawFilledRectangle(x, y, width, height);
        }

        public String getTyp() {
            return typ;
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

        @Override
        public void fadeOut(boolean fadeOut) {

        }

        @Override
        public void fadeIn() {
            super.fadeIn();
        }
    }

    private VisualQueue<Point> pointQueue;
    private int pointQueueLength;

    public PointQueue(ViewController viewController, int startX, int startY) {
        super(Integer.MAX_VALUE);
        pointQueue = new VisualQueue<>(viewController, startX,startY,"up");
        Point base = new Point("base");
        pointQueue.enqueue(base);

        pointQueueLength = 0;
    }


    @Override
    public void draw(DrawTool drawTool) {

    }


    public String frontType(){
        return pointQueue.getFront().getTyp();
    }

    public void spawnRandomPoint(){

        int r = (int) (Math.random()*3)+1;
        switch (r){
            case 1 -> {
                Point point = new Point("typ 1");
                pointQueue.enqueue(point);
                pointQueueLength++;
            }
            case 2 -> {
                Point point = new Point("typ 2");
                pointQueue.enqueue(point);
                pointQueueLength++;
            }
            case  3 -> {
                Point point = new Point("typ 3");
                pointQueue.enqueue(point);
                pointQueueLength++;
            }
        }
    }
}
