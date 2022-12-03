import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * The FileManager class is responsible for interfacing with the file system and loading file data.
 * This class checks the validity of files, loads the data from the structure, and transfers the data
 * to Controller, which in turn transfers it to Ledger class where it is stored
 */

public class FileManager 
{
    /**
     * LoadAttendanceFile is responsible for taking a file path and returning a HashMap which associates
     * student asurites with the time they attended a lecture on a certain date.
     * @param path the full path of the file the user selected as an attendance file
     * @throws IOException if the reader cannot find the file specified, will only happen
     * if the file gets deleted or moved before it is loaded
     * @return A hashmap mapping dates to another hashmap, which maps asurite IDs to the time attended
     */
    public HashMap<String, HashMap<String, Integer>> loadAttendanceFolder(String path) throws IOException
    {
        HashMap<String, HashMap<String, Integer>> bigMap = new HashMap<String, HashMap<String, Integer>>();
        File folder = new File(path);
        File[] listFiles = folder.listFiles();
        for(File f : listFiles)
        {
            if(f.getName().endsWith(".csv") && f.getName().length() == 23)
            {
                String date = f.getName().substring(0,8);
                String att = f.getName().substring(9);
                
                if(date.matches("^[0-9]+$") && att.equals("attendance.csv")) // make sure there is only numbers
                {
                    HashMap<String, Integer> attMap = new HashMap<String, Integer>();
                    BufferedReader reader;

                    
                    reader = new BufferedReader(new FileReader(path +"/"+ f.getName()));
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
                    bigMap.put(date, attMap);
                }
            }
        }
        
		
        return bigMap;
    }

    /**
     * loadDetailsToDataStructure is responsible for taking a file path and returning a ArrayList of students.
     * @param selectedPath the full path of the file the user selected as an roster file
     * @throws IOException if the selectedPath is empty.
     * @return An ArrayList of Students with IDs, first name, last name, ASURITE id.
     */
    public ArrayList<Student> loadDetailsToDataStructure(String selectedPath) throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(selectedPath));
        ArrayList<Student> students_list=new ArrayList<Student>();
        String strLine;
        while( (strLine = br.readLine()) != null) {
            String[] student_details = strLine.split(",");
            if (student_details.length == 4) {
                System.out.println(Arrays.toString(student_details));
                Student student = new Student(student_details[0], student_details[1], student_details[2], student_details[3]);
                students_list.add(student);
            }
            else{
                throw new IOException("Please upload correct format of the roster file");
            }
        }
        br.close();
        return students_list;

    }
}
