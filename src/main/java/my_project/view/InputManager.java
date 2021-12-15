package my_project.view;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.InteractiveGraphicalObject;
import my_project.control.ProgramController;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Realisiert ein Objekt, dass alle Eingaben empfängt und dann danach passende Methoden
 * im ProgramController aufruft
 */
public class InputManager extends InteractiveGraphicalObject {

    private ProgramController programController;
    private ViewController viewController;

    /**
     * Objekterzeugung
     * @param programController Nötig als Objekt vom Controllerbereich, das informiert wird
     */
    public InputManager(ProgramController programController, ViewController viewController){
        this.programController = programController;
        this.viewController = viewController;
        viewController.register(this);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void keyReleased(int key) {
        switch (key) {
            case KeyEvent.VK_W -> programController.doPlayerAction("up");
            case KeyEvent.VK_A -> programController.doPlayerAction("left");
            case KeyEvent.VK_S -> programController.doPlayerAction("down");
            case KeyEvent.VK_D -> programController.doPlayerAction("right");
            case KeyEvent.VK_F -> programController.doPlayerAction("add");
            case KeyEvent.VK_G -> programController.doPlayerAction("delete");
        }
    }
}
