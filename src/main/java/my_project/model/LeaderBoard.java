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

    private final ProgramController p;
    private final LeaderboardController l;


    public LeaderBoard(ViewController vc, ProgramController p, LeaderboardController l){
        this.p = p;
        this.l=l;
        vc.draw(this, LEADERBOARD_SCENE);
    }

    public void draw(DrawTool d){
        d.setCurrentColor(Color.gray);
        d.drawFilledRectangle(0,0, Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        d.setCurrentColor(0,0,0,255);
        d.drawText(30,30,"The Player "+ p.getPlayerName()+" has " +p.getPoints()+ " Points");
    }
}
