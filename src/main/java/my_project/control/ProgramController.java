package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.game.PointBar;
import my_project.model.visual_ds.*;
import my_project.model.game.GameField;
import my_project.model.game.*;
import my_project.model.item.*;
import my_project.model.menu.Menu;
import my_project.view.GameInputManager;
import my_project.view.MenuInputManager;

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
    private Menu menu;
    private GameField gameField;
    private PointQueue pointQueue;

    private List<GameItem> spawnable, spawned;

    private int playerPosX;
    private int playerPosY;

    private VisualStack<PointBar> pointBarStack;
    private BarField field;
    private PointBar pointBarOrig;

    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse viewController. Diese wird als Parameter übergeben.
     * @param viewController das viewController-Objekt des Programms
     */
    public ProgramController(ViewController viewController){
        this.viewController = viewController;
        pointBarStack = new VisualStack<>(viewController);
        pointBarOrig = new PointBar(20,255,0,0);
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     * Sie erstellt die leeren Datenstrukturen, zu Beginn nur eine Queue
     */
    public void startProgram() {
        viewController.createScene();
        viewController.showScene(SceneConfig.MENU_SCENE);

        new MenuInputManager(this,viewController);
        new GameInputManager(this, viewController);
        menu = new Menu(viewController, this);
        gameField = new GameField(viewController, 10, 10, 10, 10);
        player = new Player(viewController, 200, 200);
        pointQueue = new PointQueue(viewController, 600, 600);
        pointQueue.spawnRandomPoint();
        player.addBodyPart();
        playerPosY = playerPosX = 4;

        spawnable = new List<>();
        spawned = new List<>();

        // add items to list
        for (var item : new GameItem[]{
                new Shield(player, Color.BLUE),
                new InvertControlsItem(player, Color.BLACK),
                new Stun(player, Color.WHITE),
                new AddBodypartItem(player, Color.CYAN),
                new DeleteBodypartItem(player, Color.RED)
        }) {
            spawnable.append(item);
        }
    }

    public void spawnRandomItem(){
        Random rand = new Random();
        int index = rand.nextInt(5);

        spawnable.toFirst();
        for (int i = 0; i < index; i++) {
            spawnable.next();
            if (!spawnable.hasAccess())
                spawnable.toFirst();
        }

        GameItem toSpawn = spawnable.getContent();
        spawnable.remove();
        spawned.append(toSpawn);

        if (toSpawn != null) {
            int x = -1, y = -1;
            while (!gameField.isValidIndex(x, y) || gameField.get(x, y) != null) {
                x = rand.nextInt(10);
                y = rand.nextInt(10);
            }

            gameField.set(toSpawn, x, y);
            toSpawn.setPosX(x);
            toSpawn.setPosY(y);
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
                case KeyEvent.VK_H -> pointQueue.spawnRandomPoint();
            }
        }

        // check items
        spawned.toFirst();
        while (spawned.hasAccess()) {
            var item = spawned.getContent();
            if (item.getPosX() == playerPosX && item.getPosY() == playerPosY) {
                item.effect();
                gameField.set(null, item.getPosX(), item.getPosY());
                spawned.remove();
                spawnable.append(item);
            }
            spawned.next();
        }

    }

    public void doMenuAction(int key){
        switch(key){
            case KeyEvent.VK_W -> menu.previous();
            case KeyEvent.VK_S -> menu.next();
            case KeyEvent.VK_A -> menu.left();
            case KeyEvent.VK_D -> menu.right();
            case KeyEvent.VK_SPACE -> menu.clickOn();
        }
    }

    public void addPoints(){
        if(pointBarStack.getCounter() == 11){
            pointBarStack.setCounter(1);
            pointBarOrig.setR((int) (Math.random()*255));
            pointBarOrig.setG((int) (Math.random()*255));
            pointBarOrig.setB((int) (Math.random()*255));
            PointBar newRec = new PointBar(20,255,0,0);
            newRec.setR(pointBarOrig.getR());
            newRec.setG(pointBarOrig.getG());
            newRec.setB(pointBarOrig.getB());
            pointBarStack.pushInVisual(newRec);
        }else{
            PointBar newRec = new PointBar(20,255,0,0);
            newRec.setR(pointBarOrig.getR());
            newRec.setG(pointBarOrig.getG());
            newRec.setB(pointBarOrig.getB());
            pointBarStack.pushInVisual(newRec);
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

    public Player getPlayer(){ return player; }

    public ViewController getViewController(){ return viewController; }
}
