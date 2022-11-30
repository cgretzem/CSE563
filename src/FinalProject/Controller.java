package FinalProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Controller extends JPanel implements ActionListener{
    private Ledger ledger;
    private Display display;
    private FileManager filemanager;

    private JFrame frame;

    private JTable table;
    Controller(JFrame frame) {
        ledger = Ledger.getInstance();
        this.frame=frame;
        display = new Display(frame,this);
        filemanager = new FileManager();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if(action == "Upload")
        {
            try {
                filemanager.loadRoster(frame);
                ledger.addRoster(filemanager.students_list);
                String[][] res=ledger.generateRosterData();
                // get data from output ledger and print on the screen in JTable.
                constructJTable(res);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    //can be used for both plotting the attendance data and roster data.
    private void constructJTable(String[][] table_data) {

        // convert 2D array to data inside table.




    }
}
