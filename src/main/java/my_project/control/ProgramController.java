package my_project.control;

import KAGO_framework.control.ViewController;
import my_project.model.game.GameField;
import my_project.model.game.Player;
import my_project.model.menu.Menue;
import my_project.view.InputManager;

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
        new Menue(viewController);
        new GameField(viewController, 10, 10, 10, 10);
        new InputManager(this,viewController);

        player = new Player(viewController, 200, 200);
        player.addBodyPart();
    }

    public void doPlayerAction(String action){
        switch (action) {
            case "up" -> player.movePlayer(0,-40);
            case "down" -> player.movePlayer(0,40);
            case "right" -> player.movePlayer(40,0);
            case "left" -> player.movePlayer(-40,0);
            case "add" -> player.addBodyPart();
            case "delete" -> player.deleteBodyPart();
        }
    }
    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){

    }
}
