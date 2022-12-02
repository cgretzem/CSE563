package FinalProject;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.awt.Graphics;
import java.awt.Color;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


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
//        menu.add(menuItem);
        menu.add(addAttendanceItem);

        JMenuItem saveMenuItem=new JMenuItem("Save");
        saveMenuItem.addActionListener(parent);
        menu.add(saveMenuItem);

        JMenuItem plotData=new JMenuItem("Plot Data");
        plotData.addActionListener(parent);
        menu.add(plotData);

        JMenu about = new JMenu("About");
        menuBar.add(about);
        about.addMenuListener(parent);
 
    }

    private class Plot extends JPanel
    {
        
        private ArrayList<Integer> studentsAttended;
        private ArrayList<String> dates;
        public Plot(ArrayList<Integer> studentsAttended , ArrayList<String> dates)
        {
            this.studentsAttended = studentsAttended;
            this.dates = dates;
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            //this.setBackground(Color.DARK_GRAY);
            int width = 500;
            int height = 400;

            int bar_width = width / (dates.size() * 2 + 1);
            int max = Integer.MIN_VALUE;
            for(Integer n : studentsAttended)
            {
                max = Math.max(max, n);
            }
            int yPos = 100;
            int xPos = bar_width;
            for(int i = 0; i < dates.size(); i++)
            {
                int barHeight = height *  (studentsAttended.get(i)/ max);
                g.setColor(Color.BLUE);
                g.fillRect(xPos, yPos, bar_width, barHeight);
                //g.fillRect(xPos, yPos, bar_width, barHeight);
                xPos += bar_width*2;
            }
        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 500);

        }
    }

    public void createPlot(ArrayList<Integer> studentsAttended , ArrayList<String> dates)
    {
        scp.setVisible(false);
        Plot plot = new Plot(studentsAttended, dates);
        frame.add(plot, BorderLayout.EAST);
        frame.revalidate();
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

    public void displaySuccessAfterSave()
    {
        JDialog box = new JDialog(frame, "SUCCESS");
        JLabel label = new JLabel("<html>" + "File Saved Successfully!" + "</html>", SwingConstants.CENTER);
        box.add(label);
        box.setSize(300,200);
        box.setVisible(true);

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



    public JTable getTable() {
        return table;
    }
}
