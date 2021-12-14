package my_project.model.game;

import my_project.model.visual_ds.Visual2DArray;

public class GameField extends Visual2DArray<Entity> {

    public GameField(int width, int height) {
        super(width, height);
        //super(width, height, 0, 0, new VisualizationConfig(0, 0, 20, 20));a
    }


}
