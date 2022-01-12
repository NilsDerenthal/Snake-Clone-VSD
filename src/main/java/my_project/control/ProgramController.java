package my_project.control;

import KAGO_framework.control.ViewController;
import my_project.model.game.GameField;
import my_project.model.game.Player;
import my_project.model.item.*;
import my_project.model.menu.Menu;
import my_project.view.InputManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute


    // Referenzen
    private final ViewController viewController;  // diese Referenz soll auf ein Objekt der Klasse viewController zeigen. Über dieses Objekt wird das Fenster gesteuert.
    private Player player;
    private Menu menue;
    private GameField gameField;
    private final GameItem[] gameItems;
    private final int[][] itemPosition;
    private int playerPosX;
    private int playerPosY;

    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse viewController. Diese wird als Parameter übergeben.
     * @param viewController das viewController-Objekt des Programms
     */
    public ProgramController(ViewController viewController){
        this.viewController = viewController;
        gameItems = new GameItem[5];
        itemPosition = new int[5][2];
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     * Sie erstellt die leeren Datenstrukturen, zu Beginn nur eine Queue
     */
    public void startProgram() {
        // start scene
        viewController.showScene(SceneConfig.MENU_SCENE);

        menue = new Menu(viewController);
        gameField = new GameField(viewController, 10, 10, 10, 10);
        new InputManager(this, viewController);
        player = new Player(viewController, 200, 200);
        player.addBodyPart();

        playerPosY = playerPosX = 4;

        gameItems[0] = new AddBodypartItem(player, Color.BLUE);
        gameItems[1] = new DeleteBodypartItem(player, Color.RED);
        gameItems[2] = new Stun(player, Color.BLACK);
        gameItems[3] = new InvertControlsItem(player, Color.ORANGE);
        gameItems[4] = new Shield(player, Color.GREEN);
    }

    public void spawnRandomItem(){
        var rand = new Random();
        int i = rand.nextInt(4);
        int x = rand.nextInt(9);
        int y = rand.nextInt(9);

        int numberSpawned = 0;
        boolean allowed = true;

        while(numberSpawned < 5){
            if(!gameItems[i].isSpawned()){
                gameItems[i].spawn();
                for (int[] ints : itemPosition) {
                    if (ints[0] == x && ints[1] == y) {
                        allowed = false;
                    }
                }
                if(allowed){
                    gameField.set(gameItems[i], x, y);
                    gameItems[i].setPosX(x);
                    gameItems[i].setPosY(y);
                    numberSpawned = 5;
                }else{
                    x = rand.nextInt(9);
                    y = rand.nextInt(9);
                    numberSpawned = 0;
                }
            }
            if(++i >= 5) {
                i = 0;
            }
            numberSpawned++;
        }
    }

    public void doPlayerAction(int key){
        if(!player.isStunned()) {

            int effectiveKey = key;
            if (player.isInvertedControls()) {
                effectiveKey = switch (key) {
                    case KeyEvent.VK_W -> KeyEvent.VK_S;
                    case KeyEvent.VK_S -> KeyEvent.VK_W;
                    case KeyEvent.VK_D -> KeyEvent.VK_A;
                    case KeyEvent.VK_A -> KeyEvent.VK_D;
                    default -> key;
                };
            }

            // normal
            switch (effectiveKey) {
                case KeyEvent.VK_W -> {
                    if (playerPosY > 0 && player.movePlayer(0, -40)) {
                        playerPosY--;
                    }
                }
                case KeyEvent.VK_S -> {
                    if (playerPosY < 9 && player.movePlayer(0, 40)) {
                        playerPosY++;
                    }
                }
                case KeyEvent.VK_D -> {
                    if (playerPosX < 9 && player.movePlayer(40, 0)) {
                        playerPosX++;
                    }
                }
                case KeyEvent.VK_A -> {
                    if (playerPosX > 0 && player.movePlayer(-40, 0)) {
                        playerPosX--;
                    }
                }
                case KeyEvent.VK_G -> spawnRandomItem();
            }
        }

        // menü
        switch(key){
            case KeyEvent.VK_1 -> menue.previous();
            case KeyEvent.VK_2 -> menue.next();
            case KeyEvent.VK_F -> player.setStunned(false);
        }

        //überprüft, ob man ein item einsammelt und aktiviert es falls es der fall ist
        for (GameItem gameItem : gameItems) {
            if (gameItem.isSpawned() && gameItem.getPosX() == playerPosX && gameItem.getPosY() == playerPosY) {
                gameItem.effect();
                if (!gameItem.isSpawned()) {
                    gameField.set(null, gameItem.getPosX(), gameItem.getPosY());
                }
            }
        }

    }

    public void showScene(int scene) {
        viewController.showScene(scene);
    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){

    }
}
