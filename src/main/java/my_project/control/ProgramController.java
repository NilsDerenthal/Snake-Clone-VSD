package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.game.GameField;
import my_project.model.game.Player;
import my_project.model.item.*;
import my_project.model.menu.Menu;
import my_project.view.InputManager;

import java.awt.event.KeyEvent;

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
    private GameItem[] gameItems;
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
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     * Sie erstellt die leeren Datenstrukturen, zu Beginn nur eine Queue
     */
    public void startProgram() {
        viewController.showScene(SceneConfig.MENU_SCENE);
        menue = new Menu(viewController);
        gameField = new GameField(viewController, 10, 10, 10, 10);
        new InputManager(this, viewController);
        player = new Player(viewController, 200, 200);
        playerPosX = playerPosY = 4;
        player.addBodyPart();
        gameItems[0] = new AddBodypart(255,player);
        gameItems[1] = new DeleteBodypart(255,player);
        gameItems[2] = new Stun(255,player);
        gameItems[3] = new SwitchControll(255,player);
        gameItems[4] = new Shield(255,player);
    }

    public void spawnRandomItem(){
        int i = (int) (Math.random()*4);
        int x = (int) ((Math.random()*9));
        int y = (int) ((Math.random()*9));
        if(!gameItems[i].isSpawned()){
            gameItems[i].spawn();
            gameField.set(gameItems[i],x,y);
            gameItems[i].setPosX(x);
            gameItems[i].setPosY(y);
        }

    }

    public void doPlayerAction(int key){
        switch (key) {
            case KeyEvent.VK_W -> {
                if(playerPosY > 0) {
                    if(player.movePlayer(0, -40)) playerPosY--;
                }
            }
            case KeyEvent.VK_S -> {
                if(playerPosY < 9) {
                    if(player.movePlayer(0, 40)) playerPosY++;
                }
            }
            case KeyEvent.VK_D -> {
                if(playerPosX < 9) {
                    if(player.movePlayer(40, 0)) playerPosX++;
                }
            }
            case KeyEvent.VK_A -> {
                if(playerPosX > 0) {
                    if(player.movePlayer(-40, 0)) playerPosX--;
                }
            }
            case KeyEvent.VK_G -> spawnRandomItem();
        }
        switch(key){
            case KeyEvent.VK_1 -> menue.previous();
            case KeyEvent.VK_2 -> menue.next();
        }

        //überprüft, ob man ein item einsammelt und aktiviert es falls es der fall ist
        for(int i = 0; i < gameItems.length; i++){
            if(gameItems[i].isSpawned() & gameItems[i].getPosX() == playerPosX & gameItems[i].getPosY() == playerPosY){
                gameItems[i].effect();
                gameField.set(null, gameItems[i].getPosX(), gameItems[i].getPosY());
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
