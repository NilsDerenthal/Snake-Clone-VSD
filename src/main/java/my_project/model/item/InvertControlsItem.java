package my_project.model.item;

import my_project.model.game.Player;

import java.awt.*;

public class InvertControlsItem extends GameItem{

    public InvertControlsItem(Player player, Color color) {
        super(player, color);
    }

    @Override
    public void effect() {
        if(player.isShielded()){
            player.setShielded(false);
        } else {
            player.setInvertedControls(!player.isInvertedControls());
        }
        spawned = false;
    }
}
