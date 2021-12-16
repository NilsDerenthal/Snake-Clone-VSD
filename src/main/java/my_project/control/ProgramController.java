package my_project.control;

import KAGO_framework.control.ViewController;
import my_project.model.game.GameField;
import my_project.model.game.Player;
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
        viewController.showScene(SceneConfig.MENU_SCENE);
        new Menu(viewController);
        new GameField(viewController, 10, 10, 10, 10);
        new InputManager(this, viewController);

        player = new Player(viewController, 200, 200);
        player.addBodyPart();
    }

    public void doPlayerAction(int key){
        switch (key) {
            case KeyEvent.VK_W -> player.movePlayer(0, -40);
            case KeyEvent.VK_A -> player.movePlayer(0, 40);
            case KeyEvent.VK_S -> player.movePlayer(40, 0);
            case KeyEvent.VK_D -> player.movePlayer(-40, 0);
            case KeyEvent.VK_F -> player.addBodyPart();
            case KeyEvent.VK_G -> player.deleteBodyPart();
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
