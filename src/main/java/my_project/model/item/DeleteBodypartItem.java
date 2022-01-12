package my_project.model.item;

import KAGO_framework.view.DrawTool;
import my_project.model.game.Player;

import java.awt.*;

public class DeleteBodypartItem extends GameItem {

    public DeleteBodypartItem(Player player, Color color) {
        super(player, color);
    }

    @Override
    public void effect() {
        if (player.deletable()) {
            player.deleteBodyPart();
            spawned = false;
        }
    }
}
