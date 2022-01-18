package my_project.model.game;

import KAGO_framework.control.ViewController;
import KAGO_framework.view.DrawTool;
import my_project.control.SceneConfig;
import my_project.model.visual_ds.Visual2DArray;

import java.awt.*;

public class GameField extends Visual2DArray<Entity> {

    public GameField(ViewController vc, int x, int y, int width, int height) {
        super(width, height, 0, 0,
                new VisualizationConfig(
                        x, y, 20, 20, 10,
                        true, false, false,
                        null, Color.BLACK, Color.WHITE
                )
        );
        vc.draw(this, SceneConfig.GAME_SCENE);
    }
}
