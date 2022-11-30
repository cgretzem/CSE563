package FinalProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FileManager 
{
    public HashMap<String, Integer> loadAttendanceFile(File f) throws IOException
    {
        HashMap<String, Integer> attMap = new HashMap<String, Integer>();
        BufferedReader reader;

		
        reader = new BufferedReader(new FileReader(f.getPath()));
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
}
