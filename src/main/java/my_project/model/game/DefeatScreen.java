package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.control.ProgramController;

import java.awt.*;

import static my_project.control.SceneConfig.*;

public class DefeatScreen extends GraphicalObject {

    private ProgramController programController;

    private String[] flame;
    private String[] normal;
    private String[] praise;
    private String chosenOne;

    public DefeatScreen(ViewController viewController, ProgramController programController){
        this.programController = programController;

        flame = new String[4];
        normal = new String[4];
        praise = new String[4];

        flame[0] = "Suicide?";
        flame[1] = "BRUH! ARE YOU REALLY THAT BAD?!?";
        flame[2] = "BAD!BAD!BAD!BAD!BAD!BAD!BAD!";
        flame[3] = "This isn't even hard....";
        normal[0] = "I mean like you tried...";
        normal[1] = "YOU BE DED!";
        normal[2] = "BEGONE!";
        normal[3] = "You lost";
        praise[0] = "I will approve";
        praise[1] = "Actually...not bad";
        praise[2] = "Help, idk wht to say now...";
        praise[3] = "A nice score you got there!";

        viewController.draw(this, DEFEAT_SCENE);
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.gray);
        drawTool.drawFilledRectangle(0,0, Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.formatText("", 3, 40);
        drawTool.drawText(250,200,chosenOne);
        drawTool.drawText(260,260,"Score: " + programController.getPoints());
        drawTool.formatText("", 1, 20);
        drawTool.drawText(400,400,"(click any Button besides space, because i don't like space)");
    }

    public void setFlame(){
        chosenOne = flame[ (int) (Math.random()*4) ];
    }

    public void setNormal(){
        chosenOne = normal[ (int) (Math.random()*4) ];
    }

    public void setPraise(){
        chosenOne = praise[ (int) (Math.random()*4) ];
    }
}
