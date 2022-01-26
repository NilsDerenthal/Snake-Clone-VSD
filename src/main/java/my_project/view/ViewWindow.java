package my_project.view;

import my_project.control.ProgramController;

import javax.swing.*;

public class ViewWindow {

    private JFrame jFrame;
    private ProgramController control;

    public ViewWindow(ProgramController control){
        this.control = control;
        jFrame = new JFrame();
        jFrame.setContentPane(new NameFieldGUI(control).getMainPanel());
        jFrame.setVisible(true);
        jFrame.setSize(200,200);
    }
}
