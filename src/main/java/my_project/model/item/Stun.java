package my_project.model.item;

import my_project.model.game.Player;

import java.awt.*;

public class Stun extends GameItem{

    public Stun(Player player, Color color) {
        super(player, color);
        duration = 100;
        timer = 0;
    }

    @Override
    public void update(double dt) {
        if(active) {
            if (timer > duration) {
                active = false;
                player.setStunned(false);
            }
            timer += 5;
        }
    }

    @Override
    public void effect() {
        if(player.isShielded()){
            player.setShielded(false);
        }else{
            player.setStunned(true);
            active = true;
        }
        spawned = false;
    }
}
