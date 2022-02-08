package my_project.model;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.control.LeaderboardController;
import my_project.control.ProgramController;

import java.awt.*;

import static my_project.control.SceneConfig.LEADERBOARD_SCENE;

public class LeaderBoard extends GraphicalObject {

    private final LeaderboardController l;

    public LeaderBoard(ViewController vc, ProgramController p, LeaderboardController l){
        this.l=l;
        width = 20;
        height = 20;
        x = 800;
        y = 500;
        vc.draw(this, LEADERBOARD_SCENE);
    }

    public void draw(DrawTool d){
        d.setCurrentColor(Color.gray);
        d.drawFilledRectangle(0,0, Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        d.setCurrentColor(0,0,0,255);
        int yText=110;
        String[] names=l.getNames();
        String[] difficults=l.getDifficults();
        int[] scores=l.getScores();
        for(int i=0;i<names.length&&i<20;i++) {
            d.drawText(30, yText, "The Player " + names[i] + " has " + scores[i] + " Points with difficuly "+difficults[i]);
            yText+=20;
        }

        d.formatText("",3,70);
        d.drawText(290,80,"LEADERBOARD");

        //POKAL
        d.setCurrentColor(0,0,0,255);
        d.drawFilledRectangle(x,y,width*7,height);
        d.drawFilledRectangle(x,y-width,width,height);
        d.drawFilledRectangle(x+width*6,y-width,width,height);
        d.drawFilledRectangle(x+width,y-width*2,width,height);
        d.drawFilledRectangle(x+width*5,y-width*2,width,height);
        d.drawFilledRectangle(x+width*2,y-width*5,width,height+width*3);
        d.drawFilledRectangle(x+width*4,y-width*5,width,height+width*3);
        d.drawFilledRectangle(x+width,y-width*5,width,height);
        d.drawFilledRectangle(x+width*5,y-width*5,width,height);
        d.drawFilledRectangle(x,y-width*6,width*2,height);
        d.drawFilledRectangle(x+width*5,y-width*6,width*2,height);
        d.drawFilledRectangle(x-width,y-width*7,width*2,height);
        d.drawFilledRectangle(x+width*6,y-width*7,width*2,height);
        d.drawFilledRectangle(x-width*2,y-width*16,width*11,height+width*8);

        //Dinge zum halten links
        d.drawFilledRectangle(x-width*5,y-width*14,width*3,height);
        d.drawFilledRectangle(x-width*5,y-width*14,width,height*4);
        d.drawFilledRectangle(x-width*4,y-width*11,width,height*2);
        d.drawFilledRectangle(x-width*3,y-width*10,width,height);
        //rechts
        d.drawFilledRectangle(x+width*8,y-width*14,width*3,height);
        d.drawFilledRectangle(x+width*11,y-width*14,width,height*4);
        d.drawFilledRectangle(x+width*10,y-width*11,width,height*2);
        d.drawFilledRectangle(x+width*9,y-width*10,width,height);


        d.setCurrentColor(255,215,0,255);
        d.drawFilledRectangle(x-width, y-width*15,width*9,height+width*7);
        d.drawFilledRectangle(x+width,y-width,width*5,height);
        d.drawFilledRectangle(x+width*3,y-width*5,width,height+width*3);
        d.drawFilledRectangle(x+width*2,y-width*5,width*3,height);
        d.drawFilledRectangle(x+width,y-width*6,width*5,height);
        d.drawFilledRectangle(x,y-width*7,width*7,height);

        d.setCurrentColor(255,255,255,255);
        d.drawFilledRectangle(x, y-width*14,width,height*6);
    }
}
