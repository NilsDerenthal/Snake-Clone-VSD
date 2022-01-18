package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

public class BarField extends GraphicalObject {

    private ViewController viewController;

    public BarField(ViewController viewController) {
        this.x = 19;
        this.y = 99;
        this.width = 201;
        this.height = 21;
        this.viewController = viewController;
        viewController.draw(this);
    }

    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawRectangle(x,y,width,height);
    }
}
