package my_project.model.game;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.model.visual_ds.VisualStack;

public class PointBar extends GraphicalObject implements VisualStack.Animated{

    private int r,g,b;

    public PointBar(double width, int r, int g, int b) {
        this.width = width;
        this.r = r;
        this.g = g;
        this.b = b;

    }

    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(r,g,b,255);
        drawTool.drawFilledRectangle(x,y,width,20);
    }

    public void update(double dt){

    }

    public void comeIn(){
        y = 600;
    }

    public void goOut(){

    }

    public void setG(int g){
        this.g = g;
    }

    public int getG(){
        return g;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
