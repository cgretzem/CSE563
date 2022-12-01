package FinalProject;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.util.HashMap;
import java.util.ArrayList;

public class Display extends JPanel{

    JFrame frame;
    JMenuBar menuBar;
    private JTable table;
    private JScrollPane scp;
    Controller parent;
    private DefaultTableModel model;

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
        JMenuItem addAttendanceItem=new JMenuItem("Add Attendance");
        addAttendanceItem.addActionListener(parent);
        menu.add(menuItem);
        menu.add(addAttendanceItem);
    }


    public void displayError(Exception ex)
    {
        JDialog box = new JDialog(frame, "ERROR");
        //rendering trick to make JLabel able to have a new line
        JLabel label = new JLabel("<html>" + ex.getMessage().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>", SwingConstants.CENTER);
        box.add(label);
        box.setSize(300,200);
        box.setVisible(true);

    }

    public void notifyUser(HashMap<String, Integer> newStudents, int numLoaded)
    {
        String message = "Data loaded for " + numLoaded + " users in the roster.\n" + newStudents.size();

        if(newStudents.size() == 1)
            message += " additional attendee was found.\n\n";
        else
            message += " additional attendees were found.\n\n";

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

        // JOptionPane.showMessageDialog(frame, message);
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.OK_OPTION);
        JDialog dialog = new JDialog(frame, "New Students Found", true);
        dialog.setContentPane(optionPane);
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
    
    public void displayTable(String[][] data)
    {
        if(table == null)
        {
            String[] columnNames = {"ID", "First Name", "Last Name", "Asurite"};
            model = new DefaultTableModel(data, columnNames);
            table = new JTable(model);
            table.setBounds(30, 40, 200, 300);
            scp = new JScrollPane(table);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            frame.add(scp, BorderLayout.CENTER);
            frame.revalidate();
        }
    
    }

    public void addAttendanceColumn(String date, String[] col)
    {
        model.addColumn(date, col);
        frame.revalidate();
    }


    public String displayAttendanceFileChooser()
    {
        String selectedPath = "";
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv", "CSV");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(frame);
        if(returnVal == JFileChooser.APPROVE_OPTION)
            selectedPath = chooser.getSelectedFile().getAbsolutePath();
        return selectedPath;
    }

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
}
