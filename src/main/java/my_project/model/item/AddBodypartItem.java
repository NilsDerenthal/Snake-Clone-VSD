package my_project.model.item;

import my_project.model.game.Player;

import java.awt.*;

public class AddBodypartItem extends GameItem {

    public AddBodypartItem(Player player, Color color) {
        super(player, color);
    }

    @Override
    public void effect() {
        player.addBodyPart();
        spawned = false;
    }
}
