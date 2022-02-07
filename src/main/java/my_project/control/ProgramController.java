package my_project.control;

import KAGO_framework.control.SoundController;
import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.model.abitur.datenstrukturen.Queue;
import my_project.Config;
import my_project.model.LeaderBoard;
import my_project.model.NameField;
import my_project.model.game.PointBar;
import my_project.model.game.Point;
import my_project.model.visual_ds.*;
import my_project.model.game.GameField;
import my_project.model.game.*;
import my_project.model.item.*;
import my_project.model.menu.Menu;
import my_project.view.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import static my_project.control.SceneConfig.MENU_SCENE;

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
            gameExists,
            hardDifficulty=false,
            sound=true,
            twoEnemys=false;

    private int playerPosX,
            playerPosY;

    private String name="?";

    private final ViewController viewController;

    private Queue<Point> pointQueue;
    private GameItem[] items;
    private List<GameItem> spawnable, spawned;
    private VisualStack<PointBar> pointBarStack;

    private Player player;
    private Color playerColor;
    private Menu menu;
    private Enemy enemy,secondEnemy;
    private GameField gameField;
    private BarField field;
    private PointBar pointBarOrig;
    private DefeatScreen defeat;

    private Random rand;

    private NameField nameField;
    private LeaderBoard leaderBoard;
    private LeaderboardController l;

    int halfWinHeight = Config.WINDOW_HEIGHT / 2;
    int halfWinWidth = Config.WINDOW_WIDTH / 2;

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
        l=new LeaderboardController();
        this.playerColor = Color.BLUE;
        this.rand = new Random();

        // prepare four scenes
        viewController.createScene();
        viewController.createScene();
        viewController.createScene();
        viewController.createScene();

        // default starting scene
        viewController.showScene(SceneConfig.NAME_SCENE);

        // input managers
        new MenuInputManager(this,viewController);
        new GameInputManager(this, viewController);
        new DefeatScreenInputManager(this,viewController);
        new NameFieldInputManager(this,viewController);
        new LeaderBoardInputManager(this,viewController);

        menu = new Menu(viewController, this);
        nameField = new NameField(viewController);
        defeat = new DefeatScreen(viewController,this);
        field = new BarField(viewController);
        leaderBoard = new LeaderBoard(viewController,this,l);

        loadSounds();
        if(sound)SoundController.playSound("menu_sound");
    }

    /**
     * Loads all sounds into memory
     */
    private void loadSounds() {
        var soundController = viewController.getSoundController();
        String filePrefix = "src/main/resources/sound/";

        soundController.loadSound(filePrefix + "game_song.mp3","game_sound",  true);
        soundController.loadSound(filePrefix + "menu_song.mp3", "menu_sound", true);
        soundController.loadSound(filePrefix + "game_song_alt.mp3", "game_sound_alt", true);

        soundController.loadSound(filePrefix + "plop.mp3","pointSpawned_sound", false);
    }

    /**
     * Starts a new game by resetting all game-state variables
     */
    public void startNewGame(){
        gameField = new GameField(viewController, halfWinWidth - 225, halfWinHeight - 250, 10, 10);
        player = new Player(viewController, halfWinWidth - 35, halfWinHeight - 60, playerColor);
        enemy = new Enemy(viewController,10,halfWinWidth - 235, halfWinHeight - 260,40,this);
        if(twoEnemys) secondEnemy = new Enemy(viewController,10,10,10,halfWinWidth - 235, halfWinHeight - 260,40,this);
        pointBarOrig = new PointBar(20,255,0,0);
        pointBarStack = new VisualStack<>(viewController);

        items = new GameItem[]{
                new Shield(player, "shield.png"),
                new InvertControlsItem(player, "invert.png"),
                new Stun(player, "stun.jpg"),
                new AddBodypartItem(player, "plus.png"),
                new DeleteBodypartItem(player, "minus.png")
        };

        field.resetPoints();
        player.addBodyPart();

        player.setName(name);
        playerPosY = 4;
        playerPosX = 4;
        pointsToSpawn = 3;
        gameTimer = 0;
        pointsSpawned = 0;
        dead = false;
        gameExists = true;

        enemy.setDifficulty(hardDifficulty);
        if(twoEnemys){
            secondEnemy.setDifficulty(hardDifficulty);
            enemy.setOtherEnemy(secondEnemy);
            secondEnemy.setOtherEnemy(enemy);
            enemy.setTwoEnemys(true);
            secondEnemy.setTwoEnemys(true);
        }

        spawnable = new List<>();
        spawned = new List<>();
        pointQueue = new Queue<>();

        if(sound) {
            SoundController.stopSound("menu_sound");
            SoundController.playSound("game_sound_alt");
        }

        for (var item : items) {
            spawnable.append(item);
        }

        isRunning=true;
        showScene(SceneConfig.GAME_SCENE);
    }

    /**
     * Spawns a random item if possible
     */
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

    /**
     * Spawns a random point
     */
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
            if(sound)SoundController.playSound("pointSpawned_sound");
        }
    }

    /**
     * Executes a player action
     * @param key the keycode
     */
    public void doPlayerAction(int key){
        int effectiveKey = key;
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
            if (effectiveKey == KeyEvent.VK_ESCAPE) viewController.showScene(MENU_SCENE);
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
            if(key == KeyEvent.VK_ESCAPE){
                viewController.showScene(MENU_SCENE);
                isRunning=false;
            }
        }
    }

    public void enterLetter(int key){
        switch (key) {
            case KeyEvent.VK_ENTER -> {
                name = nameField.getTextToDraw();
                showScene(MENU_SCENE);
            }
            case KeyEvent.VK_BACK_SPACE -> nameField.removeLast();
            default -> {
                String inputChar = KeyEvent.getKeyText(key).toLowerCase();
                if (inputChar.matches("^[a-z]$"))
                    nameField.setTextToDraw(inputChar);
            }
        }
    }

    public void doLeaderBoardAction(){
        viewController.showScene(MENU_SCENE);
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

    public void doDefeatScreenAction(){
        viewController.showScene(MENU_SCENE);
        dead = gameExists = false;
        if(sound)SoundController.playSound("menu_sound");
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
            if (player.gotHit(enemy.getX(), enemy.getY()) && !dead ||secondEnemy!=null&& player.gotHit(secondEnemy.getX(), secondEnemy.getY())&&!dead) {
                if (getPoints() < 20) {
                    defeat.setFlame();
                } else if (getPoints() < 100) {
                    defeat.setNormal();
                } else {
                    defeat.setPraise();
                }
                showScene(SceneConfig.DEFEAT_SCENE);
                dead = true;
                isRunning = false;
                l.addToLeaderBoard(player.getName(),getPoints(),getDifficult());
                if(sound)SoundController.stopSound("game_sound_alt");
            }
        }
    }

    public String getDifficult(){
        String s;
        if(twoEnemys){
            if(hardDifficulty){
                s="hard two Enemys";
            }else{
                s="easy two Enemys";
            }
        }else{
            if(hardDifficulty){
                s="hard";
            }else{
                s="easy";
            }
        }
        return s;
    }

    public Player getPlayer () {
        return player;
    }

    public String getPlayerName(){
        return name;
    }

    public ViewController getViewController () {
        return viewController;
    }

    public void setIsRunning (boolean isRunning){
        this.isRunning = isRunning;
    }

    public int getPoints () {
        return field.getPoints();
    }

    public boolean isGameExists() {
        return gameExists;
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

    public void setTwoEnemys (boolean two){
        twoEnemys = two;
    }

    public void soundOn(){
        if(sound==false){
            sound=true;
            SoundController.playSound("menu_sound");
        }
    }

    public void soundOff(){
        if(sound==true){
            sound=false;
            SoundController.stopSound("menu_sound");
        }
    }
}
