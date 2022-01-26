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
    public Defeat(ViewController viewController, ProgramController programController){
        this.viewController = viewController;
        this.programController = programController;
        viewController.draw(this, DEFEAT_SCENE);
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.formatText("", 3, 40);
        drawTool.drawText(250,200,"YOU BE DED!");
        drawTool.drawText(260,260,"Score: ");
    }
}
