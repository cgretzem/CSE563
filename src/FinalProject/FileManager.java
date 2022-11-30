package FinalProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManager 
{
    private ArrayList<Student> students_list;
    public HashMap<String, Integer> loadAttendanceFile(String path) throws IOException
    {
        HashMap<String, Integer> attMap = new HashMap<String, Integer>();
        BufferedReader reader;

		
        reader = new BufferedReader(new FileReader(path));
        String line = reader.readLine();

        while (line != null) {
            String[] split = line.split(",");
            if(attMap.containsKey(split[0]))
            {
                attMap.put(split[0], attMap.get(split[0]) + Integer.parseInt(split[1]));
            }
            else
            {
                attMap.put(split[0], Integer.parseInt(split[1]));
            }
            
            // read next line
            line = reader.readLine();
        }

        reader.close();
		
        return attMap;
    }

    public void loadRoster(Component parent) throws IOException {
        //implement file chooser.
        String selectedPath="";
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv", "CSV");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(parent);
        if(returnVal == JFileChooser.APPROVE_OPTION)
            selectedPath= chooser.getSelectedFile().getAbsolutePath();
        if(!selectedPath.equals(""))
            loadDetailsToDataStructure(selectedPath);
    }

    private void loadDetailsToDataStructure(String selectedPath) throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(selectedPath));
        students_list=new ArrayList<>();
        String strLine;
        while( (strLine = br.readLine()) != null) {
            String[] student_details = strLine.split(",");
            if (student_details.length == 4) {
                System.out.println(Arrays.toString(student_details));
                int id = Integer.parseInt(student_details[0]);
                Student student = new Student(id, student_details[1], student_details[2], student_details[3]);
                students_list.add(student);
            }

        }

    }
}
