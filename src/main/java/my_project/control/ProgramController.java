package my_project.control;

import KAGO_framework.control.SoundController;
import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.model.abitur.datenstrukturen.Queue;
import my_project.Config;
import my_project.model.game.PointBar;
import my_project.model.game.Point;
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

    private double timer,
            gameTimer,
            pointsToSpawn,
            pointsSpawned;

    private boolean dead,
            isRunning,
            hardDifficulty;

    private int playerPosX,
            playerPosY;


    private final ViewController viewController;

    private Queue<Point> pointQueue;
    private GameItem[] items;
    private List<GameItem> spawnable, spawned;
    private VisualStack<PointBar> pointBarStack;

    private Player player;
    private Color playerColor;
    private Menu menu;
    private Enemy enemy;
    private GameField gameField;
    private BarField field;
    private PointBar pointBarOrig;
    private DefeatScreen defeat;

    private Random rand;

    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse viewController. Diese wird als Parameter übergeben.
     * @param viewController das viewController-Objekt des Programms
     */
    public ProgramController(ViewController viewController){
        this.viewController = viewController;
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     * Sie erstellt die leeren Datenstrukturen, zu Beginn nur eine Queue
     */
    public void startProgram() {
        this.playerColor = Color.BLUE;
        this.rand = new Random();

        // prepare two scenes
        viewController.createScene();
        viewController.createScene();

        // default starting scene
        viewController.showScene(SceneConfig.MENU_SCENE);

        // input managers
        new MenuInputManager(this,viewController);
        new GameInputManager(this, viewController);

        menu = new Menu(viewController, this);

        items = new GameItem[]{
                new Shield(player, "shield.png"),
                new InvertControlsItem(player, "invert.png"),
                new Stun(player, "chain.png"),
                new AddBodypartItem(player, "plus.png"),
                new DeleteBodypartItem(player, "minus.png")
        };

        loadSounds();
        SoundController.playSound("menu_sound");
    }

    private void loadSounds() {
        var soundController = viewController.getSoundController();
        String filePrefix = "src/main/resources/sound/";

        soundController.loadSound(filePrefix + "game_song.mp3","game_sound",  true);
        soundController.loadSound(filePrefix + "menu_song.mp3", "menu_sound", true);
        soundController.loadSound(filePrefix + "game_song_alt.mp3", "game_sound_alt", true);

        soundController.loadSound(filePrefix + "plop.mp3","pointSpawned_sound", false);
    }

    public void startNewGame(){
        int halfWinWidth = Config.WINDOW_WIDTH / 2;
        int halfWinHeight = Config.WINDOW_HEIGHT / 2;

        gameField = new GameField(viewController, halfWinWidth - 225, halfWinHeight - 250, 10, 10);
        player = new Player(viewController, halfWinWidth - 35, halfWinHeight - 60, playerColor);
        field = new BarField(viewController);
        pointBarStack = new VisualStack<>(viewController);
        pointBarOrig = new PointBar(20,255,0,0);
        enemy = new Enemy(viewController,10,halfWinWidth - 235, halfWinHeight - 260,40,this);
        defeat = new DefeatScreen(viewController,this);

        player.addBodyPart();

        playerPosY = 4;
        playerPosX = 4;
        pointsToSpawn = 3;
        gameTimer = 0;
        pointsSpawned = 0;
        dead = false;

        enemy.setDifficulty(hardDifficulty);

        spawnable = new List<>();
        spawned = new List<>();
        pointQueue = new Queue<>();

        SoundController.stopSound("menu_sound");
        SoundController.playSound("game_sound_alt");

        for (var item : items) {
            spawnable.append(item);
        }

        isRunning=true;
        showScene(SceneConfig.GAME_SCENE);
    }

    public void spawnRandomItem(){
        if(isRunning) {
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
    }

    public void spawnPoint(){
        if(isRunning) {
            int x = -1, y = -1;
            while (!gameField.isValidIndex(x, y) || gameField.get(x, y) != null) {
                x = rand.nextInt(10);
                y = rand.nextInt(10);
            }
            Point p = new Point(x, y);
            pointQueue.enqueue(p);
            gameField.set(p, x, y);
            SoundController.playSound("pointSpawned_sound");
        }
    }

    public void doPlayerAction(int key){
        int effectiveKey = key;
        if(dead) viewController.showScene(SceneConfig.MENU_SCENE); dead = false;
        if(isRunning) {
            if (player != null && !player.isStunned()) {
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
                    case KeyEvent.VK_H -> spawnPoint();
                }
            } else {
                if (items!=null&&((Stun) items[2]).increaseStunRemoval()) {
                    player.setStunned(false);
                }
            }
            if (effectiveKey == KeyEvent.VK_ESCAPE) viewController.showScene(SceneConfig.MENU_SCENE);
            if (spawned != null && !spawned.isEmpty()) {
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
                //check point
                if (!pointQueue.isEmpty()) {
                    if (pointQueue.front().getPosX() == playerPosX && pointQueue.front().getPosY() == playerPosY) {
                        gameField.set(null, pointQueue.front().getPosX(), pointQueue.front().getPosY());
                        pointQueue.dequeue();
                        spawnPoint();
                        for (int i = 0; i < player.getLength(); i++) {
                            addPoints();
                        }
                    }
                }
            }
        }
    }

    public void doMenuAction(int key){
        switch(key){
            case KeyEvent.VK_W -> menu.previous();
            case KeyEvent.VK_S -> menu.next();
            case KeyEvent.VK_A -> menu.left();
            case KeyEvent.VK_D -> menu.right();
            case KeyEvent.VK_SPACE -> menu.clickOn();
            case KeyEvent.VK_ESCAPE -> {
                viewController.showScene(SceneConfig.MENU_SCENE);
                isRunning=false;
            }
        }
    }

    public void addPoints(){
        if(isRunning) {
            if (pointBarStack.getCounter() == 11) {
                pointBarStack.setCounter(1);
                pointBarOrig.setR((int) (Math.random() * 255));
                pointBarOrig.setG((int) (Math.random() * 255));
                pointBarOrig.setB((int) (Math.random() * 255));
                PointBar newRec = new PointBar(20, 255, 0, 0);
                newRec.setR(pointBarOrig.getR());
                newRec.setG(pointBarOrig.getG());
                newRec.setB(pointBarOrig.getB());
                pointBarStack.pushInVisual(newRec);
            } else {
                PointBar newRec = new PointBar(20, 255, 0, 0);
                newRec.setR(pointBarOrig.getR());
                newRec.setG(pointBarOrig.getG());
                newRec.setB(pointBarOrig.getB());
                pointBarStack.pushInVisual(newRec);
            }
            field.increasePoints();
        }
    }

    public void showScene(int scene) {
        viewController.showScene(scene);
    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt) {
        if (isRunning) {
            timer += dt;
            gameTimer += dt;
            // every 5 seconds
            if (timer > 5) {
                timer = 0;
                if (spawnable != null && !spawnable.isEmpty())
                    spawnRandomItem();
            }
            if (gameTimer > 0.5 && pointsSpawned < pointsToSpawn) {
                spawnPoint();
                pointsSpawned++;
                gameTimer = 0;
            }
            if (player.gotHit(enemy.getX(), enemy.getY())) {
                if (player.gotHit(enemy.getX(), enemy.getY()) && !dead) {
                    if (getPoint() < 20) {
                        defeat.setFlame();
                    } else if (getPoint() < 100) {
                        defeat.setNormal();
                    } else {
                        defeat.setPraise();
                    }
                    showScene(SceneConfig.DEFEAT_SCENE);
                    dead = true;
                }
            }
        }
    }

        public Player getPlayer () {
            return player;
        }

        public ViewController getViewController () {
            return viewController;
        }

        public void setIsRunning (boolean to){
            isRunning = to;
        }

        public int getPoint () {
            return field.getPoints();
        }

        public boolean getIsRunning () {
            return isRunning;
        }

        public void setPlayerColor (Color newColor){
            playerColor = newColor;
        }

        public void setDifficult (boolean hard){
            hardDifficulty = hard;
        }
}
