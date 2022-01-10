import java.util.ArrayList;
import java.util.Arrays;

interface DayInterface extends Comparable<Day>
{
    public String addToDo(String name);
    public String removeToDo(String name);
    public int compareTo(Day d);
}
public class Day implements DayInterface
{
    protected ArrayList<String> toDo;
    protected int month;
    protected int day;

    /**
     * Constructor that creates Day object, testing for valid month and day before
     * initializing data fields
     * @param thisMonth int specifying month 1-12
     * @param thisDay   int specifying day of the month, bound depending on which
     *                  month user enters
     * @throws IllegalArgumentException thrown when invalid month or day entered
     */
    public Day(int thisMonth, int thisDay) throws IllegalArgumentException
    {
        // Handle out of bounds month
        if(thisMonth > 12 || thisMonth < 1) {
            throw new IllegalArgumentException("Invalid month");
        }
        // Handle 30 day months
        if((thisMonth == 4 || thisMonth == 6 || thisMonth == 9 || thisMonth == 11) && thisDay > 30){
            throw new IllegalArgumentException("Invalid day in month");
        }
        // Handle February
        else if((thisMonth == 2) && thisDay > 28) {
            throw new IllegalArgumentException("Invalid day in month");
        }
        // Handle all other months
        else if(thisDay > 31) {
            throw new IllegalArgumentException("Invalid day in month");
        }

        // Setup all fields
        this.month = thisMonth;
        this.day = thisDay;

        // Array List for easy adding/removing/iterating through tasks
        toDo = new ArrayList<String>();
    }

    /**
     * Constructor that creates a Day object that initializes month and day fields,
     * and adds a list of to-dos loaded from csv file
     * @param thisMonth
     * @param thisDay
     * @param list
     * @throws IllegalArgumentException
     */
    public Day(int thisMonth, int thisDay, ArrayList<String> list) throws IllegalArgumentException
    {
        this(thisMonth, thisDay);

        // Array List for easy adding/removing/iterating through tasks
        toDo = list;
    }
    /**
     *  This method is to add a new task to the to-do list for a day by utilizing
     *  the add() method from ArrayList
     * @param task String of task that user wants to add to the to-do list
     * @return Output confirming that task has been added to the list
     */
    @Override
    public String addToDo(String task)
    {
        toDo.add(task);
        return ("Added to the to do list for " + intToMonth(month) + " " + day + ": " + task);
    }

    /**
     * This method is to remove a task from the to-do list for a day by utilizing
     * the remove() method from ArrayList
     * @param task String of task that user wants to remove from the to-do list
     * @return  Output confirming that task has been removed from the list
     */
    @Override
    public String removeToDo(String task)
    {
        toDo.remove(task);
        return ("Removed from the to do list for " + intToMonth(month) + " " + day + ": " + task);
    }

    /**
     * This method returns the list of tasks for the day
     * @return ArrayList of Strings containing the To-Do list tasks 
     */
    public ArrayList<String> getToDo(){
	return toDo;
    }

    /**
     * This method compares Day objects to each other by comparing their month and day ints.
     * Month is compared first to see which is smaller, and if they are equal, days are
     * compared.
     * @param d Day object passed in to be compared
     * @return 0 if equal, -1 if first day is smaller, 1 if first day is larger
     */
    @Override
    public int compareTo(Day d)
    {
        if(this.month < d.month) // This instance is in an earlier month of the year
        {
            return -1;
        }
        else if (this.month > d.month) // This instance is in a later month of the year
        {
            return 1;
        }
        else
        {
            if(this.day < d.day) // This instance is in an earlier day of the month
            {
                return -1;
            }
            else if (this.day > d.day) // This instance is in a later day of the month
            {
                return 1;
            }
        }
        // Reached by both Day objects being the same
        return 0;
    }

    /**
     * Method that returns a string of the data of a Day
     */
    public String toString() {
      String s = "Month: " + this.intToMonth(month) + "\nDay: " + Integer.toString(this.day) + "\nToDo: " + toDo + "\n";
      return  s;
    }

    
    /**
     * This method converts an int month to the String representation
     * @param input month number out of the year
     * @return String month name
     */
    private static String intToMonth(int input)
    {
        if(input == 1) return "January";
        if(input == 2) return "February";
        if(input == 3) return "March";
        if(input == 4) return "April";
        if(input == 5) return "May";
        if(input == 6) return "June";
        if(input == 7) return "July";
        if(input == 8) return "August";
        if(input == 9) return "September";
        if(input == 10) return "October";
        if(input == 11) return "November";
        if(input == 12) return "December";
        return null;
    }
}

//Placeholders
class DayPlaceholderA implements DayInterface {
    ArrayList<String> tasks = new ArrayList<String>(Arrays.asList("wash dishes", "laundry"));
    public String addToDo(String name) {return "added";}
    public String removeToDo(String name) {return "removed";}
    public int compareTo(Day d) { return 0; }
}
class DayPlaceholderB implements DayInterface {
    ArrayList<String> tasks = new ArrayList<String>(Arrays.asList("wash dishes", "laundry"));
    public String addToDo(String name) {return "added";}
    public String removeToDo(String name) {return "removed";}
    public int compareTo(Day d) { return 1; }
}
class DayPlaceholderC implements DayInterface {
    ArrayList<String> tasks = new ArrayList<String>(Arrays.asList("wash dishes", "laundry"));
    public String addToDo(String name) {return "added";}
    public String removeToDo(String name) {return "removed";}
    public int compareTo(Day d) { return -1; }
}
