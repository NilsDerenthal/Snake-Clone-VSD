package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.view.DrawTool;
import my_project.control.ProgramController;
import my_project.control.SceneConfig;

import java.awt.*;

public class Enemy extends Entity {

    private boolean up,left,hardDifficuld=false,twoEnemys=false;
    private int xPosInGameField,yPosInGameField;
    private final int gameFieldHeight,gameFieldXPos,gameFieldYPos,cellHeight;
    private double t=1;
    private final Player player;
    private final ProgramController programController;
    private Enemy otherEnemy;



    public Enemy(ViewController viewController,int xPosInGameField,int yPosInGameField,int gameFieldHeight,int gameFieldXPos,int gameFieldYPos, int cellHeight,ProgramController programController) {
        super(0);
        this.cellHeight=cellHeight;
        this.gameFieldHeight=gameFieldHeight;
        this.gameFieldXPos=gameFieldXPos;
        this.gameFieldYPos=gameFieldYPos;
        this.xPosInGameField =xPosInGameField;
        this.yPosInGameField=yPosInGameField;
        this.programController=programController;
        this.player= programController.getPlayer();
        changeUpLeft();
        viewController.draw(this, SceneConfig.GAME_SCENE);
        xPosToX();
        yPosToY();
    }

    public Enemy(ViewController viewController,int gameFieldHeight,int gameFieldXPos,int gameFieldYPos, int cellHeight,ProgramController programController) {
        super(0);
        xPosInGameField =1;
        yPosInGameField=1;
        this.cellHeight=cellHeight;
        this.gameFieldHeight=gameFieldHeight;
        this.gameFieldXPos=gameFieldXPos;
        this.gameFieldYPos=gameFieldYPos;
        this.programController=programController;
        this.player= programController.getPlayer();
        changeUpLeft();
        viewController.draw(this, SceneConfig.GAME_SCENE);
        xPosToX();
        yPosToY();
    }

    /**
     * ändert left und up zufällig
     */
    private void changeUpLeft(){
        changeLeft();
        changeUp();
    }

    /**
     * ändert up zufällig
     */
    private void changeUp(){
        double random=Math.random()*10-5;
        left=random>0;
    }

    /**
     * ändert left zufällig
     */
    private void changeLeft(){
        double random=Math.random()*10-5;
        up=random>0;
    }

    @Override
    public void draw(DrawTool d) {
        d.setCurrentColor(Color.DARK_GRAY);
        d.drawFilledCircle(x,y,cellHeight/2);
    }

    /**
     * setzt den X wert entsprechend der position im Gamefield
     */
    private void xPosToX(){
        x= xPosInGameField *cellHeight+gameFieldXPos;
    }

    /**
     * setzt den Y wert entsprechend der position im Gamefield
     */
    private void yPosToY(){
        y=yPosInGameField*cellHeight+gameFieldYPos;
    }

    @Override
    public void update(double dt) {
        if(programController.getIsRunning()) {
            t -= dt;
            if (t <= 0) {
                if (hardDifficuld) {
                    moveToPlayer();
                } else {
                    boolean moved = false;
                    int timesTried = 0;
                    while (!moved) {
                        timesTried++;
                        moved = move();
                        if (timesTried > 10) changeUpLeft();
                    }
                }
                t = 1;
            }
        }
    }

    /**
     * bewegt den Player in eine zufällige Richtung
     * @return ob der enemy sich bewegt hat
     */
    private boolean move(){
        //Todo fix
        double d = Math.random()*2-0.5;
        boolean moved=false;
        if(d<0.5){
            if(up){
                if(yPosInGameField<gameFieldHeight){
                    if(twoEnemys&&otherEnemy.xPosInGameField == xPosInGameField){
                        if(otherEnemy.yPosInGameField>yPosInGameField+1) return false;
                    }
                    yPosInGameField+=1;
                    yPosToY();
                    moved=true;
                }
            }else{
                if(yPosInGameField>1){
                    if(twoEnemys&&otherEnemy.xPosInGameField == xPosInGameField){
                        if(otherEnemy.yPosInGameField<yPosInGameField-1) return false;
                    }
                    yPosInGameField-=1;
                    yPosToY();
                    moved=true;
                }
            }
        }else{
            if(left){
                if(xPosInGameField >1){
                    if(twoEnemys&&otherEnemy.yPosInGameField==yPosInGameField){
                        if(otherEnemy.xPosInGameField < xPosInGameField -1) return false;
                    }
                    xPosInGameField -=1;
                    xPosToX();
                    moved=true;
                }
            }else{
                if(xPosInGameField <gameFieldHeight){
                    if(twoEnemys&&otherEnemy.yPosInGameField==yPosInGameField){
                        if(otherEnemy.xPosInGameField > xPosInGameField +1) return false;
                    }
                    xPosInGameField +=1;
                    xPosToX();
                    moved=true;
                }
            }
        }
        if(d%0.5==0){
            if(d<0.5){
                changeUp();
            }else{
                changeLeft();
            }
        }
        return moved;
    }

    /**
     * enemy bewegt sich ein Feld in richtung des Players
     */
    private void moveToPlayer(){
        //TODO fix
        double playerX=player.getHeadX();
        double playerY=player.getHeadY();
        double[] d = getDxDy(playerX,playerY);
        boolean moved=false;
        while(!moved) {
            if (d[0] >= d[1]) {
                if (playerX > x) {
                    if (xPosInGameField < gameFieldHeight){
                        if(twoEnemys&&otherEnemy.yPosInGameField==yPosInGameField){
                            if(otherEnemy.xPosInGameField > xPosInGameField +1){
                                xPosInGameField += 1;
                                moved=true;
                            }else{
                                d[0]-=1;
                            }
                        }else{
                            xPosInGameField += 1;
                            moved=true;
                        }
                    }
                } else {
                    if (xPosInGameField > 0){
                        if(twoEnemys&&otherEnemy.yPosInGameField==yPosInGameField){
                            if(otherEnemy.xPosInGameField < xPosInGameField -1) {
                                xPosInGameField -= 1;
                                moved=true;
                            }else{
                                d[0]-=1;
                            }
                        }else{
                            xPosInGameField -= 1;
                            moved=true;
                        }
                    }
                }
                xPosToX();
            } else {
                if (playerY < y) {
                    if (yPosInGameField > 0){
                        if(twoEnemys&&otherEnemy.xPosInGameField == xPosInGameField){
                            if(otherEnemy.yPosInGameField<yPosInGameField-1){
                                moved=true;
                                yPosInGameField -= 1;
                            }else{
                                d[1]-=1;
                            }
                        }else{
                            yPosInGameField -= 1;
                            moved=true;
                        }
                    }
                } else {
                    if (yPosInGameField < gameFieldHeight){
                        if(twoEnemys&&otherEnemy.xPosInGameField == xPosInGameField){
                            if(otherEnemy.yPosInGameField>yPosInGameField+1){
                                yPosInGameField += 1;
                                moved=true;
                            }else{
                                d[1]-=1;
                            }
                        }else{
                            yPosInGameField += 1;
                            moved=true;
                        }
                    }
                }
                yPosToY();
            }
        }
    }

    public double[] getDxDy(double playerX,double playerY){
        double[] d=new double[2];
        double dx=playerX-x;
        double dy=playerY-y;
        if(dx<0) dx=-dx;
        if(dy<0) dy=-dy;
        d[0]=dx;
        d[1]=dy;
        return d;
    }

    /**
     * ändert die schwierigkeit
     */
    public void setDifficulty (boolean hard){ hardDifficuld=hard; }

    public void setTwoEnemys(boolean to){ twoEnemys=to; }

    public void setOtherEnemy(Enemy e){ otherEnemy=e; }
}
