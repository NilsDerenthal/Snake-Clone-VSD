package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.VisualQueue;

import java.awt.*;

public class Player {

    //Anfang innerer Klasse

    class Body extends GraphicalObject implements VisualQueue.Animatible {

        private boolean head;

        public Body(double radius){
            this.radius = radius;
            head = false;
        }

        @Override
        public void draw(DrawTool drawTool) {
            drawTool.drawFilledCircle(x,y,radius);
            drawTool.setCurrentColor(Color.red);
            if(head) drawTool.drawCircle(x,y,radius);
            drawTool.setCurrentColor(Color.black);
        }

        public void setHead(boolean head) {
            this.head = head;
        }

        @Override
        public void fadeIn() {

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

    //Ende innerer Klasse

    private final VisualQueue<Body> bodyPart;

    public Player(ViewController viewcontroller, double startX, double startY){

        bodyPart = new VisualQueue<>(viewcontroller, startX, startY, "movable");
        Body firstPart = new Body(20);
        bodyPart.enqueue(firstPart);
        bodyPart.getFront().setHead(true);
    }

    public void movePlayer(double moveX, double moveY){
        if(moveX != 0 || moveY != 0) bodyPart.moveQueue(moveX,moveY);
    }

    public void addBodyPart(){
        Body body = new Body(20);
        bodyPart.enqueue(body);
        bodyPart.getFront().setHead(true);
    }

    public void deleteBodyPart(){
        bodyPart.dequeue();
        bodyPart.getFront().setHead(true);
    }

    public double PositionX(){
        return bodyPart.getFront().getX();
    }

    public double PositionY(){
        return bodyPart.getFront().getY();
    }

    public boolean alive(){
        if(bodyPart.getFront() != null){
            return true;
        }else{
            return false;
        }
    }
}
