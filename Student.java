public class Student {
   private int ID;
   private String firstName;
   private String lastName;
   private String asurite;


    public Student(int id, String firstName, String lastName, String asurite)
    {
        ID = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.asurite = asurite;
    }
   public int getID()
   {
        return ID;
   }

   public String getFirst()
   {
        return firstName;
   }
   
   public String getLast()
   {
        return lastName;
   }

   public String getAsurite()
   {
        return asurite;
   }
}
