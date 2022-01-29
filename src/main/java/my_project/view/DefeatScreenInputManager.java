package my_project.view;

import KAGO_framework.control.ViewController;
import my_project.control.ProgramController;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static my_project.control.SceneConfig.DEFEAT_SCENE;

public class DefeatScreenInputManager extends InteractableAdapter{

    private ProgramController programController;

    /**
     * Objekterzeugung
     * @param programController Nötig als Objekt vom Controllerbereich, das informiert wird
     */
    public DefeatScreenInputManager(ProgramController programController, ViewController viewController){
        this.programController = programController;
        viewController.register(this, DEFEAT_SCENE);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void keyReleased(int key) {
        if(key != KeyEvent.VK_SPACE) {
            programController.doDefeatScreenAction();
        }
    }
}
