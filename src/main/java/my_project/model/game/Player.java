package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.VisualQueue;
import static my_project.control.SceneConfig.GAME_SCENE;

import java.awt.*;

public class Player extends Entity {

    private static class BodyPart extends Entity implements VisualQueue.Animatable {

        private boolean head;
        private boolean shieldActive;
        private Color color;

        public BodyPart(double radius,Color color){
            super(Integer.MAX_VALUE);

            this.radius = radius;
            this.head = false;
            shieldActive = false;
            this.color=color;
        }

        @Override
        public void draw(DrawTool drawTool) {
            if(head) {
                drawTool.setCurrentColor(Color.BLACK);
                drawTool.drawFilledCircle(x, y, radius);
                drawTool.setCurrentColor(
                        shieldActive ? Color.GREEN : Color.RED
                );
                drawTool.drawCircle(x, y, radius);
            }else{
                drawTool.setCurrentColor(color);
                drawTool.drawFilledCircle(x, y, radius);
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

    private String name;
    private int points;
    private final VisualQueue<BodyPart> body;
    private boolean shielded, invertedControls, stunned;
    private int length;
    private Color color;

    public Player(ViewController viewcontroller, double startX, double startY, Color color){
        // infinitely fast fading -> no fading
        super(Integer.MAX_VALUE);

        body = new VisualQueue<>(viewcontroller, startX, startY, "movable");
        BodyPart firstPart = new BodyPart(20,color);

        body.enqueue(firstPart);
        body.getFront().setHead(true);

        this.points = points;
        this.color=color;
        System.out.println(color);
        viewcontroller.draw(this, GAME_SCENE);
    }

    public String getInfos(){
        return getName()+": ";
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
        BodyPart body = new BodyPart(20,color);
        this.body.enqueue(body);
        this.body.getFront().setHead(true);
        length++;
    }

    public void deleteBodyPart(){
        boolean shielded = body.getFront().isShieldActive();
        body.dequeue();
        body.getFront().setHead(true);
        if(shielded){
            body.getFront().setShield(true);
        }
        length--;
    }

    public boolean gotHit(double x, double y){
        return body.collidesWithSnake(x,y);
    }

    @Override
    public void draw(DrawTool drawTool) {

    }

    public void setColor(Color color) {
        this.color=color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlive(){
        return body.getFront() != null;
    }


    public boolean isInvertedControls() {
        return invertedControls;
    }


    public void setShielded(boolean shielded) {
        this.shielded = shielded;
        body.getFront().setShield(shielded);
    }

    public int getLength() {
        return length;
    }

    public int getPoints(){
        return points;
    }

    public void setPoints(int points){
        this.points = points;
    }

    public void setInvertedControls(boolean invertedControls) {
        this.invertedControls = invertedControls;
    }

    public boolean isShielded() {
        return shielded;
    }

    public boolean isStunned() {
        return stunned;
    }

    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    public boolean deletable(){
        return length > 1;
    }

    public double getHeadX(){ return body.getFront().getTx(); }
    public double getHeadY(){ return body.getFront().getTy(); }
}
