package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.VisualQueue;

import java.awt.*;

public class PointQueue extends Entity {

    private class Point extends Entity implements VisualQueue.Animatable{

        private String typ;
        private int posX, posY;

        public Point(String typ, int posX, int posY) {
            super(Integer.MAX_VALUE);

            this.posX = posX;
            this.posY = posY;
            this.typ = typ;
        }

        @Override
        public void draw(DrawTool drawTool) {
            switch (typ) {
                case "typ 1" -> drawTool.setCurrentColor(Color.cyan);
                case "typ 2" -> drawTool.setCurrentColor(Color.magenta);
                case "typ 3" -> drawTool.setCurrentColor(Color.pink);
                case "typ 4" -> drawTool.setCurrentColor(Color.yellow);
                default -> drawTool.setCurrentColor(Color.black);
            }
            drawTool.drawFilledRectangle(x, y, width,height);
        }

        public String getTyp() {
            return typ;
        }

        public int getPosX() {
            return posX;
        }

        public int getPosY() {
            return posY;
        }

        //VisualQueue Zeugs.

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

        pointQueueLength = 0;
    }


    @Override
    public void draw(DrawTool drawTool) {

    }


    public String frontType(){
        return pointQueue.getFront().getTyp();
    }

    public void spawnRandomPoint(int x,int y){

        int r = (int) (Math.random()*4)+1;
        switch (r){
            case 1 -> {
                Point point = new Point("typ 1", x, y);
                pointQueue.enqueue(point);
                pointQueueLength++;
            }
            case 2 -> {
                Point point = new Point("typ 2", x, y);
                pointQueue.enqueue(point);
                pointQueueLength++;
            }
            case  3 -> {
                Point point = new Point("typ 3", x, y);
                pointQueue.enqueue(point);
                pointQueueLength++;
            }
            case 4 -> {
                Point point = new Point("typ 4", x, y);
                pointQueue.enqueue(point);
                pointQueueLength++;
            }
        }
    }

    public boolean pickPointUP(int x, int y){
        if(front() != null) {
            if (front().getPosX() == x && front().getPosY() == y) {
                removeFront();
                return true;
            }
        }
        return false;
    }

    public Point front(){
        return  pointQueue.getFront();
    }

    public void removeFront(){
        pointQueue.dequeue();
    }

    public int getPointQueueLength() {
        return pointQueueLength;
    }
}
