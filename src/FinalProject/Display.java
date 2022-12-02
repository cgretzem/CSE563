package FinalProject;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import java.util.HashMap;


/**
 * The Display class is responsible for rendering everything the user sees, including the attendance table, the graphs,
 * and the GUI.
 * 
 */
public class Display extends JPanel{

    /**
     * The main frame that Display class will modify
     */
    JFrame frame;
    /**
     * The Menu bar at the top of the GUI
     */
    JMenuBar menuBar;



    private JTable table;
    private JScrollPane scp;
    Controller parent;

    /**
     * The table model for the table
     */
    private DefaultTableModel model;

    /**
     * Constructs a display object and displays the GUI
     * @param frame the frame to modify
     * @param parent the controller to use for listeners
     */
    public Display(JFrame frame, Controller parent) {
        this.frame=frame;
        this.parent=parent;
        displayGUI();

    }

    /**
     * Constructs a display object and displays the GUI
     *
     */
    public void displayGUI()
    {
        menuBar=new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem menuItem=new JMenuItem("Load a Roster");
        menuItem.addActionListener(parent);
        menu.add(menuItem);

        JMenuItem addAttendanceItem=new JMenuItem("Add Attendance");
        addAttendanceItem.addActionListener(parent);
//        menu.add(menuItem);
        menu.add(addAttendanceItem);

        JMenuItem saveMenuItem=new JMenuItem("Save");
        saveMenuItem.addActionListener(parent);
        menu.add(saveMenuItem);
        JMenu about = new JMenu("About");
        menuBar.add(about);
        about.addMenuListener(parent);
 
    }

    /**
     * Display Error is a general purpose method that will display the specified error as a dialog box on the user's screen
     * @param ex the exception to display
     */
    public void displayError(Exception ex)
    {
        JDialog box = new JDialog(frame, "ERROR");
        //rendering trick to make JLabel able to have a new line
        JLabel label = new JLabel("<html>" + ex.getMessage().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>", SwingConstants.CENTER);
        box.add(label);
        box.setSize(300,200);
        box.setVisible(true);

    }

    public void displaySuccessAfterSave()
    {
        JDialog box = new JDialog(frame, "SUCCESS");
        JLabel label = new JLabel("<html>" + "File Saved Successfully!" + "</html>", SwingConstants.CENTER);
        box.add(label);
        box.setSize(300,200);
        box.setVisible(true);

    }

    /**
     * Create a window that displays the number of un-rostered students found when loading attendance files.
     * Shows a list of un-rostered students including the amount of time they were connected for.
     * @param newStudents
     * @param numLoaded
     */
    public void notifyUser(HashMap<String, Integer> newStudents, int numLoaded)
    {
        String message = "Data loaded for " + numLoaded + " users in the roster.\n" + newStudents.size();

        // Format "additional attendee" message wording
        if(newStudents.size() == 1)
            message += " additional attendee was found.\n\n";
        else
            message += " additional attendees were found.\n\n";

        // Format "asurite, connected" message wording
        for(int i = 0; i < newStudents.size(); i++)
        {
            String asurite = newStudents.keySet().toArray()[i].toString();
            int minutes = newStudents.get(asurite);
            String result;
            if(minutes == 1)
                result = asurite + ", connected for " + minutes + " minute\n";
            else
                result = asurite + ", connected for " + minutes + " minutes\n";

            message += result;
        }      

        JOptionPane optionPane = new JOptionPane(message, JOptionPane.OK_OPTION);
        JDialog dialog = new JDialog(frame, "New Students Found", true);
        dialog.setContentPane(optionPane);
        // Add property listener to allow the JDialog to close
        optionPane.addPropertyChangeListener(
            new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) {
                    String prop = e.getPropertyName();

                    if (dialog.isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                        dialog.setVisible(false);
                    }
                }
            }
        );
        dialog.pack();
        dialog.setVisible(true);
    }
    
    /**
     * Displays a table with roster information. Cannot run without a roster.
     * @param data a String[][] filled with the rows and columns of the table, the roster data
     */
    public void displayTable(String[][] data)
    {
        if(model == null)
        {
            String[] columnNames = {"ID", "First Name", "Last Name", "Asurite"};
            model = new DefaultTableModel(data, columnNames);
            table = new JTable(model);
            table.setBounds(30, 40, 200, 300);
            JScrollPane scp = new JScrollPane(table);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            frame.add(scp, BorderLayout.CENTER);
            frame.revalidate();
        }
    
    }

    /**
     * Adds a new attendance column
     * @param date String formatted date of new attendance
     * @param col the column data containing the amount of time students attended
     */
    public void addAttendanceColumn(String date, String[] col)
    {
        model.addColumn(date, col);
        frame.revalidate();
    }

    /**
     * Displays a file chooser to select an attendance csv file
     * @return the full path of the file chosen by the user
     */
    public String displayAttendanceFileChooser()
    {
        String selectedPath = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(frame);
        if(returnVal == JFileChooser.APPROVE_OPTION)
            selectedPath = chooser.getSelectedFile().getAbsolutePath();
        
        return selectedPath;
    }

    /**
     * This method is to implement the JFileChooser for upload.
     * @throws IOException when the CSV file is not selected.
     * @return the full path of the file chosen by the user
     */
    public String showJFileChooser() throws IOException {
        //implement file chooser.
        String selectedFile="";
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv", "CSV");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(parent);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            selectedFile= chooser.getSelectedFile().getAbsolutePath();
            if(!selectedFile.equals(""))
                return selectedFile;
            else
            {
                throw new IOException("CSV file not selected");
            }
        }
        else
        {
            throw new IOException("CSV File not selected");
        }
    }

    public String getDestinationPath() throws IOException {
        //implement file chooser.
        String path = "";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showOpenDialog(frame);
        if(option == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
           path = file.getAbsolutePath();

        if(!path.equals("")) {
            return path;
        }
        else
        {
            throw new IOException("CSV file not selected");
        }
        }
        else{
            throw new IOException("CSV file not selected");
        }

    }

    public void displayAbout()
    {
        JDialog teamInfo = new JDialog(frame, "Team comprised of ");
        String s = "<html>Yu-Cheng Chen<br/>Cooper Gretzema<br/>Ariana Rajewski<br/>Charishma Anubrolu<br/>Jayasai Kalyan Reddy Tummuru<br/>Gnana chaitanya ummadisingu</html>";
        JLabel label = new JLabel(s, SwingConstants.CENTER);
        teamInfo.add(label);
        teamInfo.setSize(300,200);
        teamInfo.setVisible(true);
    }


    public JTable getTable() {
        return table;
    }
}
