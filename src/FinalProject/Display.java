package FinalProject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Display extends JPanel{

    JFrame frame;
    JMenuBar menuBar;

    Controller parent;

    public Display(JFrame frame, Controller parent) {
        this.frame=frame;
        this.parent=parent;
        displayGUI();

    }
    public void displayGUI()
    {
        menuBar=new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem menuItem=new JMenuItem("Upload");
        menuItem.addActionListener(parent);
        menu.add(menuItem);
    }
}
