package my_project.model.item;

import my_project.model.game.Player;

import java.awt.*;

public class Stun extends GameItem{

    public int counter;

    public Stun(Player player, Color color) {
        super(player, color);
        duration = 100;
    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void effect() {
        if(player.isShielded()){
            player.setShielded(false);
        }else{
            player.setStunned(true);
            counter = 0;
            active = true;
        }
        spawned = false;
    }

    public boolean StunCounter(){
        if(active){
            if(counter < 5){
                counter++;
                return false;
            }else{
                return true;
            }
        }
        return true;
    }
}
