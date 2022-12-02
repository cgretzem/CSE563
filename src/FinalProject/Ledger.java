package FinalProject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


/**
 * This class is responsible storing the data which is to be
 * displayed on the screen.
 *
 */
public class Ledger{
    private static Ledger theLedger;
    private ArrayList<Student> roster;
    //these two are intertwined, attendanceDates and attendanceData indecies are the same. For example,
    //if 11/21/2020 is in index 3 of attendanceDates, the attendanceData for 11/21/2020 will be in index 3 of attendanceData
    private ArrayList<HashMap<String, Integer>> attendanceData;
    private ArrayList<String> attendanceDates; 
    
    //singleton design pattern
    /**
     * Creates a ledger instance.
     *
     */
    private Ledger()
    {
        roster = new ArrayList<Student>();
        attendanceData = new ArrayList<HashMap<String, Integer>>();
        attendanceDates = new ArrayList<String>();
    }
    /**
     * Gets the instance of ledger by calling the constructor
     *
     */
    public static Ledger getInstance()
    {
        if (theLedger == null)
        {
            theLedger = new Ledger();
        }
        return theLedger;
    }

    /**
     * Sets the roster data.
     *
     */

    public void addRoster(ArrayList<Student> list)
    {
        roster = list;
    }


    public void addAttendance(String date, HashMap<String, Integer> attMap) throws ParseException
    {
        //date must be YYYYMMDD
        
        DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.US);
        Date theDate = new Date();
        theDate = format.parse(date);
        DateFormat printFormat = new SimpleDateFormat("MM/dd/yyyy");
        String lDate = printFormat.format(theDate);
        
        int index = 0;
        if(!attendanceDates.contains(lDate))
        {
            attendanceDates.add(lDate);
            index = attendanceDates.size()-1;
            attendanceData.add(new HashMap<String, Integer>());
        }
        else
        {
            index = attendanceDates.indexOf(lDate);
        }

        attendanceData.get(index).putAll(attMap);
    }
    /**
     * This method is responsible for setting the no of students
     * for a particular attendance date
     * @return returns the roster data.
     */
    public ArrayList<Student> getRoster()
    {
        return roster;
    }

    public ArrayList<HashMap<String, Integer>> getAttendance()
    {
        return attendanceData;
    }

    public String getRecentDate()
    {
        return attendanceDates.get(attendanceDates.size()-1);
    }

    public ArrayList<String> getDates()
    {
        return attendanceDates;
    }

    public ArrayList<Integer> getStudentsAttended()
    {
        ArrayList<Integer> output = new ArrayList<Integer>();
        for(int i = 0; i < attendanceDates.size(); i++)
        {
            int total = 0;
            for(Integer val : attendanceData.get(i).values())
            {
                if(val != 0)
                {
                    total++;
                }
            }
            output.add(total);
        }
        return output;
    }

    public String[] generateColumn()
    {
        int size = roster.size();
        String[] output = new String[size];
        HashMap<String, Integer> data = attendanceData.get(attendanceData.size()-1);
        for(int i = 0; i < roster.size(); i++)
        {
            String asurite = roster.get(i).getAsurite();
            
            if(data.containsKey(asurite))
            {
                output[i] = String.valueOf(data.get(asurite));
            }
            else
            {
                output[i] = "0";
            }
        }
        return output;
    }
    /**
     * This method is responsible to generate a 2d matrix of roster data.
     * @return return 2d matrix of roster data to the controller.
     */
    public String[][] generateRosterData()
    {
        String[][] output = new String[roster.size()][4];

        for(int i = 0; i < roster.size(); i++)
        {
            Student curr = roster.get(i);
            output[i][0] = String.valueOf(curr.getID());
            output[i][1] = curr.getFirst();
            output[i][2] = curr.getLast();
            output[i][3] = curr.getAsurite();
        }
        return output;
    }

    public Object getRecentDate(String v) {
        return null;
    }

}
