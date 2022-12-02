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


    Controller(JFrame frame) {
        ledger = Ledger.getInstance();
        this.frame=frame;
        display = new Display(frame,this);
        filemanager = new FileManager();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        System.out.print("actionPerformed: "+ action);
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
                ledger.addAttendance(path.substring(path.length()-12, path.length()-3), attMap);
                String recentDate = ledger.getRecentDate();
                display.addAttendanceColumn(recentDate, ledger.generateColumn());
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
