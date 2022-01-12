package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.VisualQueue;

import java.awt.*;

public class Player extends Entity {

    private static class BodyPart extends Entity implements VisualQueue.Animatable {

        private boolean head;

        public BodyPart(double radius){
            super(Integer.MAX_VALUE);

            this.radius = radius;
            this.head = false;
        }

        @Override
        public void draw(DrawTool drawTool) {
            drawTool.setCurrentColor(Color.BLACK);
            drawTool.drawFilledCircle(x, y, radius);

            if(head) {
                drawTool.setCurrentColor(Color.RED);
                drawTool.drawCircle(x, y, radius);
            }
        }

        public void setHead(boolean head) {
            this.head = head;
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

        // ignore
        @Override
        public void fadeIn() {}

        @Override
        public void fadeOut(boolean fadeOut) {}
    }


    private final VisualQueue<BodyPart> body;
    private boolean stunned;
    private boolean shieldet;
    private boolean switchControll;

    public Player(ViewController viewcontroller, double startX, double startY){
        // infinitely fast fading -> no fading
        super(Integer.MAX_VALUE);

        body = new VisualQueue<>(viewcontroller, startX, startY, "movable");
        BodyPart firstPart = new BodyPart(20);

        body.enqueue(firstPart);
        body.getFront().setHead(true);
    }

    public void movePlayer(double moveX, double moveY) {
        if (moveX != 0 || moveY != 0 && !stunned) {
            if(switchControll){
                moveX *= -1;
                moveY *= -1;
            }
            if(body.isPlaceFree(moveX, moveY)) body.moveQueue(moveX, moveY);
        }
    }

    public void addBodyPart(){
        BodyPart body = new BodyPart(20);
        this.body.enqueue(body);
        this.body.getFront().setHead(true);
    }

    public void deleteBodyPart(){
        body.dequeue();
        body.getFront().setHead(true);
    }

    @Override
    public void draw(DrawTool drawTool) {

    }

    public boolean isAlive(){
        return body.getFront() != null;
    }

    public double getPosX(){
        return body.getFront().getX();
    }

    public double getPosY(){
        return body.getFront().getY();
    }

    public void setStunned(boolean stunned) {

        if(!shieldet) this.stunned = stunned;
    }

    public void setShieldet(boolean shieldet) {
        this.shieldet = shieldet;
    }

    public void setSwitchControll(boolean switchControll) {
        this.switchControll = switchControll;
    }
}
