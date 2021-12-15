package my_project.view;

import KAGO_framework.control.Interactable;

import java.awt.event.MouseEvent;

/**
 * Allow only implementation of needed Methods
 */
public class InteractableAdapter implements Interactable {

    @Override
    public void keyPressed(int key) {}

    @Override
    public void keyReleased(int key) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}
}
