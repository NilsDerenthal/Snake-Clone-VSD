package my_project.model;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;

import java.awt.*;

import static my_project.control.SceneConfig.NAME_SCENE;

public class NameField extends GraphicalObject {

    private String textToDraw;
    private boolean nameTaken;

    public NameField(ViewController viewController){
        viewController.draw(this, NAME_SCENE);
        textToDraw = "";
    }

    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawFilledRectangle(0,0, Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        drawTool.setCurrentColor(Color.WHITE);
        drawTool.drawText(400,290,"Enter Name:");
        drawTool.drawFilledRectangle(400,300,150,30);
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawText(400,320,textToDraw);
        if(nameTaken){
            drawTool.setCurrentColor(255,0,0,255);
            drawTool.drawText(400,345,"This name is already taken, try another one");
        }
    }

    public void setTextToDraw(String content){
        textToDraw = textToDraw + content;
    }

    public String getTextToDraw(){
        return textToDraw;
    }

    public boolean isNameTaken() {
        return nameTaken;
    }

    public void setNameTaken(boolean nameTaken) {
        this.nameTaken = nameTaken;
    }

    public void resetTextToDraw(){
        textToDraw = "";
    }
}
