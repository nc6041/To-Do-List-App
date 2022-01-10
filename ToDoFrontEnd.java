import java.io.FileNotFoundException;
import java.util.*;

/**
 * This is the interface for ToDoFrontEnd
 */
interface ToDoFrontEndInterface {


    //Load's a CSV ToDoList
    public boolean loadCsv(String pathName);

    //Insert a todo on a certain day
    public boolean insertTodo(int month, int day, String todo);


    //Prints the todo list on that day, returns true if there events to print that day otherwise
    // return false
    public void printDay(int month, int day);

    //Prints the todo's of the next seven days - In date order w/ date included, or until the end
    //of the month. If there isn't 7 days left in it, if there are no todo's in that week, return
    // false, otherwise return true.
    public void printWeek(int month, int startingDay);


    //Given a day and todo, prints out the event to be removed and removes it from the RBT.
    // Returns true if successful and false if not.
    public boolean removeToDo(int month, int day, String todo);

    //runs the program
    public void runner();


}


/**
 * This class runs the backend engine, which is a RBT, that runs a ToDo List and contains 6 options:
 * 1. Insert a ToDo into the database
 * 2. Print a day's ToDo's
 * 3. Print a week's ToDo's
 * 4. Load a database (CSV) into your ToDo List
 * 5. Remove a ToDo
 * 6. Quit");
 *
 */
public class ToDoFrontEnd implements ToDoFrontEndInterface {

    Scanner scnr = new Scanner(System.in);
    ToDoBackEnd tree = new ToDoBackEnd();


    /**
     * Inserts a ToDo given a month, day, and ToDo
     *
     * @param month of the ToDo
     * @param day of the ToDo
     * @param todo The string value of the todo
     * @return true if input correctly
     */
    @Override
    public boolean insertTodo(int month, int day, String todo) {

        try {
            tree.addToDo(month, day, todo);
            System.out.println("Inserted correctly");
            return true;
        } catch (IllegalArgumentException ignore) {
            System.out.println("The month or day you inserted for this ToDo isn't a valid date, try again");
            return false;
        }

    }

    /**
     * prints a day's ToDo's
     *
     * @param month of the ToDo
     * @param day of the ToDo
     */
    @Override
    public void printDay(int month, int day) {

        try {
            System.out.println("ToDos of " + month + "/" + day + ": " + Arrays.toString(tree.searchDate(month, day)));
        } catch (NoSuchElementException ignore) {
            System.out.println("No ToDos in this day\n");
        }

    }

    /**
     * prints the week of ToDo's
     *
     * @param month of the ToDo
     * @param startingDay of the ToDo
     */
    @Override
    public void printWeek(int month, int startingDay) {
        StringBuilder weekPrint = new StringBuilder(100);

        for (int i = startingDay; i < startingDay + 7; i++) {
            try {
                weekPrint.append("ToDos of " + month + "/" + i + ": ");
                weekPrint.append(Arrays.toString(tree.searchDate(month, i)) + "\n");
            } catch (NoSuchElementException ignore) {
                weekPrint.append("No ToDos this day.\n");
            }
        }
        System.out.println(weekPrint.toString());

    }

    /**
     * Loads a Csv of ToDo's
     * @param pathName of the csv
     * @return True if loaded correctly, false if not.
     */
    @Override
    public boolean loadCsv(String pathName) {
        try {
            tree.loadCSV(pathName);
            System.out.println("Loaded correctly");
            return true;
        } catch (FileNotFoundException ignore) {
            System.out.println("File was not found");
            return false;

        }
    }

    /**
     * Crosses off a ToDo given the month and day.
     *
     * @param month of the ToDo
     * @param day of the ToDo
     * @param todo name of ToDo
     * @return True if successful, false if not.
     */
    @Override
    public boolean removeToDo(int month, int day, String todo) {
        try {

            tree.removeEvent(month, day, todo);
            System.out.println("Removed correctly");
            return true;

        } catch (NoSuchElementException ignore) {
            System.out.println("Couldn't find ToDo");
            return false;
        }
    }

    /**
     * Private helper that prints the main menu.
     */
    private void printMainMenu() {

        System.out.println("Please input the number that corresponds to what you would like to do.\n" +
                "ToDo List commands:\n" +
                "   1. Insert a ToDo into the database\n" +
                "   2. Print a day's ToDo's\n" +
                "   3. Print a week's ToDo's\n" +
                "   4. Load a database (CSV) into your ToDo List\n" +
                "   5. Remove a ToDo\n" +
                "   6. Quit");

    }


    /**
     * Helps quit the program and closes the scanner.
     */
    private void quit() {
        System.out.println("Closing this ToDo List. Bye!");
        scnr.close();
    }


    /**
     * Runs the loop where the user will choose options and this runner will run the corresponding method
     */
    @Override
    public void runner() {

        boolean run = true;
        printMainMenu();
        int userVal = 0;

        while (userVal == 0) {
            try {
                userVal = scnr.nextInt();
                scnr.nextLine();

            } catch (InputMismatchException ignore) {
                System.out.println("Invalid input. Enter a number from 1-6 corresponding to the " +
                        "command you would like to execute.");
                printMainMenu();
                scnr.nextLine();

            }
        }

        while (run) {
            try {
                if (userVal == 1) {
                    //Try to deal with wrong month and day inputs in the Day node
                    boolean insertSuccessful = false;
                    while (!insertSuccessful) {
                        System.out.println("What month is this ToDo in (numbers 1-12)?");
                        int month = scnr.nextInt();
                        scnr.nextLine();
                        System.out.println("What day is this ToDo in?");
                        int day = scnr.nextInt();
                        scnr.nextLine();
                        System.out.println("What is the ToDo?");
                        String todo = scnr.nextLine();
                        insertSuccessful = insertTodo(month, day, todo);
                    }

                } else if (userVal == 2) {
                    System.out.println("Which month is this day in?");
                    int month = scnr.nextInt();
                    scnr.nextLine();
                    System.out.println("Which day's ToDo's do you want to print?");
                    int day = scnr.nextInt();
                    scnr.nextLine();
                    printDay(month, day);

                } else if (userVal == 3) {
                    System.out.println("Which month is this starting day in?");
                    int month = scnr.nextInt();
                    scnr.nextLine();
                    System.out.println("What is the starting day of the week you want to print?");
                    int day = scnr.nextInt();
                    scnr.nextLine();
                    printWeek(month, day);


                } else if (userVal == 4) {

                    System.out.println("What is database (CSV path name) that you would like to load in?");
                    String pathName = scnr.nextLine();
                    loadCsv(pathName);


                } else if (userVal == 5) {

                    System.out.println("What month is the ToDo you want to cross off in?");
                    int month = scnr.nextInt();
                    scnr.nextLine();

                    System.out.println("What day is the ToDo you want to cross off in?");
                    int day = scnr.nextInt();
                    scnr.nextLine();

                    System.out.println("Which ToDo would you like to cross off?");
                    String todo = scnr.nextLine();
                    removeToDo(month, day, todo);

                } else if (userVal == 6){
                    run = false;
                    quit();

                } else {
                    System.out.println("Invalid input. Enter a number from 1-6 corresponding to the " +
                            "command you would like to execute.");
                }
                if (run) {
                    printMainMenu();
                    userVal = scnr.nextInt();
                    scnr.nextLine();
                }


            } catch (InputMismatchException ignore) {
                System.out.println("Invalid input. Try again.");
                printMainMenu();
                userVal = scnr.nextInt();
                scnr.nextLine();
            } catch (IllegalArgumentException ignore) {
                System.out.println("Invalid input. Try again.");
                printMainMenu();
                userVal = scnr.nextInt();
                scnr.nextLine();
            }
        }


    }
}

