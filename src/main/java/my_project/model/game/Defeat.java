package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.control.ProgramController;

import javax.swing.text.View;

import static my_project.control.SceneConfig.*;

public class Defeat extends GraphicalObject {

    private ViewController viewController;
    private ProgramController programController;

    private String[] flame;
    private String[] normal;
    private String[] praise;
    private String chosenOne="pagh";

    public Defeat(ViewController viewController, ProgramController programController){
        this.viewController = viewController;
        this.programController = programController;

        flame = new String[4];
        normal = new String[4];
        praise = new String[4];

        flame[0] = "Suicide?";
        flame[1] = "BRUH! ARE YOU REALLY THAT BAD?!?";
        flame[2] = "YOU ARE BAD";
        flame[3] = "error: unmapple character (0x81) for encoding windows-1252";
        normal[0] = "I mean like you tried...";
        normal[1] = "YOU BE DED!";
        normal[2] = "BEGONE!";
        normal[3] = "You lost";
        praise[0] = "I will approve";
        praise[1] = "Actually...not bad";
        praise[2] = "(☞ﾟヮﾟ)☞";
        praise[3] = "A nice score you got there!";

        viewController.draw(this, DEFEAT_SCENE);
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.formatText("", 3, 40);
        //drawTool.drawText(250,200,"YOU BE DED!");
        drawTool.drawText(250,200,chosenOne);
        drawTool.drawText(250,260,"Score: " + programController.getPoint());
    }

    public void setFlame(){
        chosenOne = flame[ (int) Math.random() * 4];
    }

    public void setNormal(){
        chosenOne = normal[ (int) Math.random() * 4];
    }

    public void setPraise(){
        chosenOne = praise[ (int) Math.random() * 4];
    }
}
