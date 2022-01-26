package my_project.view;

import my_project.control.ProgramController;
import my_project.model.game.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NameFieldGUI {

    private JTextField textField1;
    private JButton confirmButton;
    private Player player;
    private JPanel mainPanel;

    public NameFieldGUI(ProgramController controller) {

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setName(getTextFieldContent());
                System.out.println(player.getName());
            }
        });
    }

    public String getTextFieldContent() {
        return textField1.getText();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
