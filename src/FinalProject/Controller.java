package FinalProject;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller is responsible for controlling the interactions between the GUI, the file system, and the internal storage system
 */
public class Controller extends JPanel implements ActionListener{
    private Ledger ledger;
    private Display display;
    private FileManager filemanager;



    /**
     * Creates a new controller instance
     * @param frame the frame for the display class to interact with
     */
    Controller(JFrame frame) {
        ledger = Ledger.getInstance();
        display = new Display(frame,this);
        filemanager = new FileManager();
    }


    /**
     * Responds to ActionEvents from the GUI
     * @param e the event that was triggered
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if(action == "Load a Roster")
        {
            try {
                String file_path=display.showJFileChooser();
                ArrayList<Student> roster = filemanager.loadDetailsToDataStructure(file_path);
                ledger.addRoster(roster);
                String[][] res=ledger.generateRosterData();
                display.displayTable(res);

            } catch (IOException ex) {
                /*
                 * TODO:
                 * Add Error popup that tells user that they must select a CSV File
                */
                display.displayError(new Exception("Error Loading File"));
                return;
            }
        }

        if(action=="Add Attendance")
        {
            if(ledger.getRoster().size()==0)
            {
                display.displayError(new Exception("Roster must be uploaded before\nuploading attendance."));
                return;
            }
            String path = display.displayAttendanceFileChooser();
            if(path == "")
            {
                //add error that tells user they must select csv file
                return;
            }
            try{
                HashMap<String, HashMap<String, Integer>> attMap = filemanager.loadAttendanceFolder(path);
                for(String d : attMap.keySet()){
                    ledger.addAttendance(d, attMap.get(d));
                    String recentDate = ledger.getRecentDate();
                    display.addAttendanceColumn(recentDate, ledger.generateColumn());
                }
                
                
            }
            catch(IOException ex)
            {
                display.displayError(ex);
            }
            catch (ParseException ex)
            {
                display.displayError(new Exception("Invalid file name: Attendance file name must be\nDDMMYYYY.csv"));
            }
        
        }
    }

}
