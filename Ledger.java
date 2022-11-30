import java.time.LocalDate;
import java.util.ArrayList;

import java.util.HashMap;


public class Ledger{
    private static Ledger theLedger;
    private ArrayList<Student> roster;
    //these two are intertwined, attendanceDates and attendanceData indecies are the same. For example,
    //if 11/21/2020 is in index 3 of attendanceDates, the attendanceData for 11/21/2020 will be in index 3 of attendanceData
    private ArrayList<HashMap<String, Integer>> attendanceData;
    private ArrayList<LocalDate> attendanceDates; 
    
    //singleton design pattern
    private Ledger()
    {
        roster = new ArrayList<Student>();
        attendanceData = new ArrayList<HashMap<String, Integer>>();
        attendanceDates = new ArrayList<LocalDate>();
    }

    public static Ledger getInstance()
    {
        if (theLedger == null)
        {
            theLedger = new Ledger();
        }
        return theLedger;
    }

    public void addRoster(ArrayList<Student> list)
    {
        roster = list;
    }


    public void addAttendance(String date, HashMap<String, Integer> attMap)
    {
        //date must be YYYYMMDD
        LocalDate lDate = LocalDate.parse(date);
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

    public ArrayList<Student> getRoster()
    {
        return roster;
    }
    public ArrayList<HashMap<String, Integer>> getAttendance()
    {
        return attendanceData;
    }

    public String[] generateColumn()
    {
        int size = roster.size();
        String[] output = new String[size];
        HashMap<String, Integer> data = attendanceData.get(attendanceData.size()-1);
        output[0] = attendanceDates.get(attendanceDates.size()-1).toString().replace('-', '/');
        int count = 1;
        for(int i = 0; i < roster.size(); i++)
        {
            String asurite = roster.get(i).getAsurite();
            
            if(data.containsKey(asurite))
            {
                output[count] = String.valueOf(data.get(asurite));
            }
            else
            {
                output[count] = "0";
            }
            count++;
        }
        return output;
    }

    public String[][] generateRosterData()
    {
        String[][] output = new String[roster.size()][4];
        output[0][0] = "ID";
        output[0][1] = "First Name";
        output[0][2] = "Last Name";
        output[0][3] = "Asurite";
        for(int i = 1; i <= roster.size(); i++)
        {
            Student curr = roster.get(i-1);
            output[i][0] = String.valueOf(curr.getID());
            output[i][1] = curr.getFirst();
            output[i][2] = curr.getLast();
            output[i][3] = curr.getAsurite();
        }
        return output;
    }

}
