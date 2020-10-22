package view;

import control.MainController;

import javax.swing.*;

/**
 * Created by Jean-Pierre on 05.11.2016.
 */
public class MainView extends JFrame{

    public MainView(MainController controller){
        this.setBounds(50,50,1230,700);
        this.setTitle("Anwendungsfall zur Datenstruktur Liste");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(new MainPanelHandler(controller).getPanel());
        this.setVisible(true);
    }
}
