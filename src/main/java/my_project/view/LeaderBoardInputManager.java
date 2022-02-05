package my_project.view;

import KAGO_framework.control.ViewController;
import my_project.control.ProgramController;
import my_project.view.InteractableAdapter;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static my_project.control.SceneConfig.LEADERBOARD_SCENE;

/**
 * Realisiert ein Objekt, dass alle Eingaben empfängt und dann danach passende Methoden
 * im ProgramController aufruft
 */
public class LeaderBoardInputManager extends InteractableAdapter {

    private ProgramController programController;
    private ViewController viewController;

    /**
     * Objekterzeugung
     * @param programController Nötig als Objekt vom Controllerbereich, das informiert wird
     */
    public LeaderBoardInputManager(ProgramController programController, ViewController viewController){
        this.programController = programController;
        this.viewController = viewController;
        viewController.register(this, LEADERBOARD_SCENE);
    }

    @Override
    public void keyReleased(int key) {
        if(KeyEvent.VK_SPACE == key){
            programController.doLeaderBoardAction();
        }
    }
}
