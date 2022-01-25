package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.view.DrawTool;
import my_project.control.SceneConfig;

import java.awt.*;

public class Enemy extends Entity {

    private boolean up;
    private boolean left;
    private int xPosIngameField;
    private int yPosInGameField;
    private int gameFieldHeight;
    private int gameFieldXPos;
    private int gameFieldYPos;
    private int cellHeight;
    private double t=1;

    public Enemy(ViewController viewController,int xPosInGameField,int yPosInGameField,int gameFieldHeight,int gameFieldXPos,int gameFieldYPos, int cellHeight) {
        super(0);
        this.cellHeight=cellHeight;
        this.gameFieldHeight=gameFieldHeight;
        this.gameFieldXPos=gameFieldXPos;
        this.gameFieldYPos=gameFieldYPos;
        this.xPosIngameField=xPosInGameField;
        this.yPosInGameField=yPosInGameField;
        changeUpLeft();
        viewController.draw(this, SceneConfig.GAME_SCENE);
        xPosToX();
        yPosToY();
    }

    public Enemy(ViewController viewController,int gameFieldHeight,int gameFieldXPos,int gameFieldYPos, int cellHeight) {
        super(0);
        xPosIngameField=1;
        yPosInGameField=1;
        this.cellHeight=cellHeight;
        this.gameFieldHeight=gameFieldHeight;
        this.gameFieldXPos=gameFieldXPos;
        this.gameFieldYPos=gameFieldYPos;
        changeUpLeft();
        viewController.draw(this, SceneConfig.GAME_SCENE);
        xPosToX();
        yPosToY();
    }

    private void changeUpLeft(){
        changeLeft();
        changeUp();
    }

    private void changeUp(){
        double random=Math.random()*10-5;
        left=random>0;
    }

    private void changeLeft(){
        double random=Math.random()*10-5;
        up=random>0;
    }

    @Override
    public void draw(DrawTool d) {
        d.setCurrentColor(Color.DARK_GRAY);
        d.drawFilledCircle(x,y,cellHeight/2);
    }

    private void xPosToX(){
        x=xPosIngameField*cellHeight+gameFieldXPos;
    }
    private void yPosToY(){
        y=yPosInGameField*cellHeight+gameFieldYPos;
    }

    @Override
    public void update(double dt) {
        t-=dt;
        if(t<=0){
            double d=Math.random()*2-0.5;
            while(!move(d)) d=Math.random()*2-0.5;
            t=1;
        }
    }

    private boolean move(double d){
        boolean moved=false;
        if(d<0){
            if(d<-0.25){
                changeUp();
            }else{
                changeLeft();
            }
        }
        if(d<0.5){
            if(up){
                if(yPosInGameField<gameFieldHeight){
                    yPosInGameField+=1;
                    yPosToY();
                    moved=true;
                }
            }else{
                if(yPosInGameField>1){
                    yPosInGameField-=1;
                    yPosToY();
                    moved=true;
                }
            }
        }else{
            if(left){
                if(xPosIngameField>1){
                    xPosIngameField-=1;
                    xPosToX();
                    moved=true;
                }
            }else{
                if(xPosIngameField<gameFieldHeight){
                    xPosIngameField+=1;
                    xPosToX();
                    moved=true;
                }
            }
        }
        return moved;
    }
}
