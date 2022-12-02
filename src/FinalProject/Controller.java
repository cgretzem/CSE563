package FinalProject;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.event.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller extends JPanel implements ActionListener, MenuListener{
    private Ledger ledger;
    private Display display;
    private FileManager filemanager;
    private JFrame frame;


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


        if(action == "Save") {

            if (display.getTable() != null && display.getTable().getColumnCount()>4) {
                try {
                    String destinationPath = display.getDestinationPath();
                    TableModel tableModel = display.getTable().getModel();
                    FileWriter saveFile = new FileWriter(new File(destinationPath + "/saveFile.csv"));
                    for (int i = 0; i < tableModel.getColumnCount(); i++) {
                        saveFile.write(tableModel.getColumnName(i) + ",");
                    }
                    saveFile.write("\n");

                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        for (int j = 0; j < tableModel.getColumnCount(); j++) {
                            saveFile.write(tableModel.getValueAt(i, j).toString() + ",");
                        }
                        saveFile.write("\n");
                    }
                    saveFile.close();
                    display.displaySuccessAfterSave();
                } catch (IOException ex) {
                    display.displayError(new Exception("Error saving file"));
                    return;
                }
            }
            else{
                display.displayError(new Exception("Please upload roster and attendance data before saving."));
            }
        }
    }

    @Override
    public void menuSelected(MenuEvent e) {
        System.out.println("menuSelected");
        JDialog teamInfo = new JDialog(frame, "Team comprised of ");
        String s = "<html>Yu-Cheng Chen<br/>Cooper Gretzema<br/>Ariana Rajewski<br/>Charishma Anubrolu<br/>Jayasai Kalyan Reddy Tummuru<br/>Gnana chaitanya ummadisingu</html>";
        JLabel label = new JLabel(s, SwingConstants.CENTER);
        teamInfo.add(label);
        teamInfo.setSize(300,200);
        teamInfo.setVisible(true);
    }

    @Override
    public void menuDeselected(MenuEvent e) {
        System.out.println("menuDeselected");
    }

    @Override
    public void menuCanceled(MenuEvent e) {
        System.out.println("menuCanceled");
    }

}
