package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.VisualQueue;

import java.awt.*;

public class Player extends Entity {

    private static class BodyPart extends Entity implements VisualQueue.Animatable {

        private boolean head;
        private boolean shieldActive;

        public BodyPart(double radius){
            super(Integer.MAX_VALUE);

            this.radius = radius;
            this.head = false;
            shieldActive = false;
        }

        @Override
        public void draw(DrawTool drawTool) {
            drawTool.setCurrentColor(Color.BLACK);
            drawTool.drawFilledCircle(x, y, radius);
            if(head) {
                if(shieldActive){
                    drawTool.setCurrentColor(Color.GREEN);
                }else {
                    drawTool.setCurrentColor(Color.RED);
                }
                drawTool.drawCircle(x, y, radius);
            }



        }

        public void setHead(boolean head) {
            this.head = head;
        }

        public void setShield(boolean shield) {
            this.shieldActive = shield;
        }

        public boolean isShieldActive() {
            return shieldActive;
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
    private boolean stunned, shielded, invertedControls;
    private int length;

    public Player(ViewController viewcontroller, double startX, double startY){
        // infinitely fast fading -> no fading
        super(Integer.MAX_VALUE);

        body = new VisualQueue<>(viewcontroller, startX, startY, "movable");
        BodyPart firstPart = new BodyPart(20);

        body.enqueue(firstPart);
        body.getFront().setHead(true);
    }

    public boolean movePlayer(double moveX, double moveY) {
            if (moveX != 0 || moveY != 0) {
                if (body.isPlaceFree(moveX, moveY)) {
                    body.moveQueue(moveX, moveY);
                    return true;
                }
            }

        return false;
    }

    public void addBodyPart(){
        BodyPart body = new BodyPart(20);
        this.body.enqueue(body);
        this.body.getFront().setHead(true);
        length++;
    }

    public void deleteBodyPart(){
        boolean shielded = false;
        if(body.getFront().isShieldActive()){
            shielded = true;
        }
        body.dequeue();
        body.getFront().setHead(true);
        if(shielded){
            body.getFront().setShield(true);
        }
        length--;
    }

    @Override
    public void draw(DrawTool drawTool) {

    }

    public boolean isAlive(){
        return body.getFront() != null;
    }


    public boolean isInvertedControls() {
        return invertedControls;
    }

    public boolean isStunned() {
        return stunned;
    }

    public void setStunned(boolean stunned) {
        if(!shielded)
            this.stunned = stunned;
    }

    public void setShielded(boolean shielded) {
        this.shielded = shielded;
        body.getFront().setShield(shielded);
    }

    public void setInvertedControls(boolean invertedControls) {
        this.invertedControls = invertedControls;
    }

    public boolean isShielded() {
        return shielded;
    }

    public boolean deletable(){
        return length > 1;
    }

}
