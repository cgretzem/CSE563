package FinalProject;
/**
 * This is a Student model class for holding the student details.
 *
 */
public class Student {
   private String ID;
   private String firstName;
   private String lastName;
   private String asurite;

    /**
     * Creates a new student instance.
     * @param id,firstName,lastName,asurite specifying the student details.
     *
     */

    public Student(String id, String firstName, String lastName, String asurite)
    {
        ID = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.asurite = asurite;
    }
    /**
     * This is a getter method for ID of student.
     * @return returns the student ID.
     *
     */
   public String getID()
   {
        return ID;
   }

    /**
     * This is a getter method for firstname of student.
     * @return returns the first name of student.
     *
     */
   public String getFirst()
   {
        return firstName;
   }

    /**
     * This is a getter method for lastname of student.
     * @return returns the last name of student.
     *
     */
   public String getLast()
   {
        return lastName;
   }
    /**
     * This is a getter method for ASURITE id of student.
     * @return returns the ASURITE id of student.
     *
     */
   public String getAsurite()
   {
        return asurite;
   }
}
