package FinalProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
                ArrayList<Student> roster = filemanager.loadRoster(frame);
                ledger.addRoster(roster);
                String[][] res=ledger.generateRosterData();
                // get data from output ledger and print on the screen in JTable.
                constructJTable(res);

            } catch (IOException ex) {
                /*
                 * TODO:
                 * Add Error popup that tells user that they must select a CSV File
                */
                throw new RuntimeException(ex);
            }
        }

        if(action=="Add Attendance")
        {
            
            String path = display.displayAttendanceFileChooser();
            if(path == "")
            {
                //add error that tells user they must select csv file
                return;
            }
            try{
                HashMap<String, Integer> attMap = filemanager.loadAttendanceFile(path);
                ledger.addAttendance(path, attMap);
            }
            catch(IOException ex)
            {
                throw new RuntimeException(ex);
            }
        
        }
    }
    //can be used for both plotting the attendance data and roster data.
    private void constructJTable(String[][] table_data) {

        // convert 2D array to data inside table.




    }
}
