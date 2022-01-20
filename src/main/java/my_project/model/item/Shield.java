package my_project.model.item;

import my_project.model.game.Player;

import java.awt.*;

public class Shield extends GameItem {

    public Shield(Player player, String imgPath) {
        super(player, imgPath);
    }

    @Override
    public void effect() {
        if(!player.isShielded()) {
            player.setShielded(true);
        }
    }
}
