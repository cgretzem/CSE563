package FinalProject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManager {
    ArrayList<Student> students_list;
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
