package FinalProject;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller extends JPanel implements ActionListener{
    private Ledger ledger;
    private Display display;
    private FileManager filemanager;

    private JFrame frame;


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
                HashMap<String, Integer> attMap = filemanager.loadAttendanceFile(path);
                ArrayList<Student> roster = ledger.getRoster();
                HashMap<String, Integer> newStudents = new HashMap<String, Integer>();
                
                for(String key : attMap.keySet())
                {
                    boolean inRoster = false;
                    for(Student student : roster)
                    {
                        if(key.equals(student.getAsurite()))
                        {
                            inRoster = true;
                            break;
                        }
                    }
                    
                    if(!inRoster)
                        newStudents.put(key, attMap.get(key));
                }

                ledger.addAttendance(path.substring(path.length()-12, path.length()-3), attMap);
                String recentDate = ledger.getRecentDate();

                display.addAttendanceColumn(recentDate, ledger.generateColumn());
                if(newStudents.size() > 0)
                    display.notifyUser(newStudents, roster.size());
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
