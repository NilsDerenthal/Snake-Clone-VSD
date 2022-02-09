package my_project.view;

import KAGO_framework.control.ViewController;
import my_project.control.ProgramController;

import static my_project.control.SceneConfig.NAME_SCENE;

/**
 * Realisiert ein Objekt, dass alle Eingaben empfängt und dann danach passende Methoden
 * im ProgramController aufruft
 */
public class NameFieldInputManager extends InteractableAdapter {

    private ProgramController programController;
    private ViewController viewController;

    /**
     * Objekterzeugung
     * @param programController Nötig als Objekt vom Controllerbereich, das informiert wird
     */
    public NameFieldInputManager(ProgramController programController, ViewController viewController){
        this.programController = programController;
        this.viewController = viewController;
        viewController.register(this, NAME_SCENE);
    }

    @Override
    public void keyReleased(int key) {
        programController.changeName(key);
    }
}
