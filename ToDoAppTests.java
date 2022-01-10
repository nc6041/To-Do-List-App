import java.lang.invoke.MethodHandles;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.platform.console.ConsoleLauncher;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
public class ToDoAppTests {


   

// TODO: Add test methods with the @Test annotation for each role here

    //DATA WRANGLER TESTS: Daisy Chen
    //1. dataWrangler_testCompareTo()
    //2. dataWrangler_testAddToDo100Inserts()
    //3. dataWrangler_testRemoveToDo100Removals()
    @Test
    public void dataWrangler_testCompareTo(){
        ArrayList<String> list1 = new ArrayList<String>(List.of("wash dishes", "do laundry"));
        ArrayList<String> list2 = new ArrayList<String>(List.of("do laundry", "walk dog"));
        for (int i = 1; i <13; i++){
            for (int j = 1; j < 29; j++){
                //Creating days of equal month and date but not list
                Day day1 = new Day(i, j);
                Day day2 = new Day(i, j);
                assertEquals(0, day1.compareTo(day2));
            }

            //Testing months with 30 days
            if (i == 4 || i == 6 || i == 9 || i == 11){
                for (int k = 29; k < 31; k++){
                    Day day1 = new Day(i, k);
                    Day day2 = new Day(i, k);
                    assertEquals(0, day1.compareTo(day2));
                }
            }

            //Testing months with 31 days
            if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12){
                Day day1 = new Day(i, 31);
                Day day2 = new Day(i, 31);
                assertEquals(0, day1.compareTo(day2));
            }
        }
    }


    @Test
    public void dataWrangler_testAddToDo100Inserts(){
        for (int i = 0; i < 100; i++){
            Day day = new Day(1, 1);
            day.addToDo("blah" + i);

            assertEquals("blah" + i, day.toDo.get(0));
        }
    }

    @Test
    public void dataWrangler_testRemoveToDo100Removals(){
        for (int i = 0; i < 100; i++){
            Day day = new Day(1, 1);
            day.addToDo("blah" + i);
            day.addToDo("blahblah");
            day.removeToDo("blahblah");

            //Checking to see that one task has been removed
            assertEquals(1, day.toDo.size());
        }
    }

    //FRONT END TESTS: Lucas Chow
    //1. frontEnd_test1()
    //2. frontEnd_test2()
    //3. frontEnd_test3()

    //BACK END TESTS:  Natalie Cheng
    //1. backEnd_testAddToDo()
    //2. backEnd_searchDate()
    //3. backEnd_removeEvent()
    @Test
    public void backEnd_testAddToDo() {
        ToDoBackEnd toDoList = new ToDoBackEnd();
        toDoList.addToDo(1,2,"brush teeth");
        toDoList.addToDo(1,3, "walk dog");
        toDoList.addToDo(1,4,"study");

        String expected = "[ \n" +
                "Month: January\n" +
                "Day: 2\n" +
                "ToDo: [brush teeth], \n" +
                "Month: January\n" +
                "Day: 3\n" +
                "ToDo: [walk dog], \n" +
                "Month: January\n" +
                "Day: 4\n" +
                "ToDo: [study] ]";

        assertEquals(expected, toDoList.tree.toString());

    }
    @Test
    public void backEnd_searchDate() {
        ToDoBackEnd toDoList = new ToDoBackEnd();
        toDoList.addToDo(1,2, "brush teeth");
        toDoList.addToDo(1,2, "walk dog");
        toDoList.addToDo(1,2,"study");

        String expected = "[brush teeth, walk dog, study]";
        assertEquals(expected, Arrays.toString(toDoList.searchDate(1,2)));
    }
    @Test
    public void backEnd_removeEvent() {
        ToDoBackEnd toDoList = new ToDoBackEnd();
        toDoList.addToDo(1,2,"brush teeth");
        toDoList.addToDo(1,2, "walk dog");
        toDoList.addToDo(1,2 ,"study");
        toDoList.removeEvent(1,2,"study");

        String expected = "[ Month: January\n" +
                "Day: 2\n" +
                "ToDo: [brush teeth, walk dog\n" +
                " ]";
        String actual = toDoList.tree.toString();
        assertEquals(expected, toDoList.tree.toString());
    }
    //INTEGRATION MANAGER TESTS:  Sabrina Huang
    //1. integrationManager_integrationManagerTest()
    //2. integrationManager_frontEndTest()
    //3. integrationManager_backEndTest()

    //Test to check if loadFile method works properly with a TestFile dataWrangler made
    @Test
    public void integrationManager_integrationManagerTest() throws FileNotFoundException{
        ToDoDataLoader tester = new ToDoDataLoader();
	LinkedList<DayInterface> list = (LinkedList<DayInterface>) tester.loadFile("TestFile.csv");
	Day temp = (Day) list.peek();
	assertEquals(1, temp.month);
	assertEquals(10, temp.day);
	assertEquals("[test1, \"1,2\", hello, why, huh]", temp.toDo);
    }








  public static void main(String[] args) {
    String className = MethodHandles.lookup().lookupClass().getName();
    String classPath = System.getProperty("java.class.path").replace(" ", "\\ ");
    String[] arguments = new String[] {
      "-cp",
      classPath,
      "--include-classname=.*",
      "--select-class=" + className };
    ConsoleLauncher.main(arguments);
  }

}
