package my_project.model;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.control.ProgramController;
import my_project.model.game.BarField;
import my_project.model.game.Player;

import java.awt.*;

import static my_project.control.SceneConfig.LEADERBOARD_SCENE;

public class LeaderBoard extends GraphicalObject {

    private ViewController vc;
    private ProgramController p;

    public LeaderBoard(ViewController vc, ProgramController p){
        this.p = p;
        vc.draw(this, LEADERBOARD_SCENE);
    }

    public void draw(DrawTool d){
        d.setCurrentColor(Color.gray);
        d.drawFilledRectangle(0,0, Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        d.setCurrentColor(0,0,0,255);
        d.drawText(30,30,"The Player "+ p.getPlayerName()+" has " +p.getPoints()+ " Points");
    }
}
