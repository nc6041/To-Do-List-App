import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * This class can be used to test text based user interactions by 1) specifying
 * a String of text input (that will be fed to System.in as if entered by the user),
 * and then 2) capturing the output printed to System.out and System.err in String
 * form so that it can be compared to the expect output.
 *
 * @author Lucas Chow
 * @date 2021.11
 */
public class TextUITester {

    /**
     * This main method demonstrates the use of a TextUITester object to check
     * the behavior of the following run method.
     *
     * @param args from the commandline are not used in this example
     */
    public static void main(String[] args) {

        // 1. Create a new TextUITester object for each test, and
        // pass the text that you'd like to simulate a user typing as only argument.
        TextUITester tester = new TextUITester("lol\n7\n8");

        // 2. Run the code that you want to test here:
        ToDoFrontEnd toDo1 = new ToDoFrontEnd();
        toDo1.runner(); // (this code should read from System.in and write to System
        // .out)

        // 3. Check whether the output printed to System.out matches your expectations.

        //Test method 1, incorrect inputs

        String output = tester.checkOutput();
        if (output.startsWith("Please input the number that corresponds to what you would like to do.\n" +
                "ToDo List commands:\n" +
                "   1. Insert a ToDo into the database\n" +
                "   2. Print a day's ToDo's\n" +
                "   3. Print a week's ToDo's\n" +
                "   4. Load a database (CSV) into your ToDo List\n" +
                "   5. Remove a ToDo\n" +
                "   6. Quit") &&
                output.contains("Invalid input. Enter a number from 1-6 corresponding to the " +
                        "command you would like to execute."))
            System.out.println("Test 1 passed.");
        else
            System.out.println("Test 1 FAILED.");


        //Test method 2, Test insert and loading a CSV and Removing
        TextUITester tester2 = new TextUITester("1\n2\n9\nCelebrate Birthday\n");

        String output2 = tester2.checkOutput();
        if (output2.startsWith("Please input the number that corresponds to what you would like to do.\n" +
                "ToDo List commands:\n" +
                "   1. Insert a ToDo into the database\n" +
                "   2. Print a day's ToDo's\n" +
                "   3. Print a week's ToDo's\n" +
                "   4. Load a database (CSV) into your ToDo List\n" +
                "   5. Remove a ToDo\n" +
                "   6. Quit") &&
                output2.contains("What month is this ToDo in (numbers 1-12)?") &&
                output2.contains("Inserted correctly"))
            System.out.println("Test 2.1 passed.");
        else
            System.out.println("Test 2.1 FAILED.");

        tester2 = new TextUITester("4\nTestFile.csv");
        output2 = tester2.checkOutput();
        if (output2.startsWith("Please input the number that corresponds to what you would like to do.\n" +
                "ToDo List commands:\n" +
                "   1. Insert a ToDo into the database\n" +
                "   2. Print a day's ToDo's\n" +
                "   3. Print a week's ToDo's\n" +
                "   4. Load a database (CSV) into your ToDo List\n" +
                "   5. Remove a ToDo\n" +
                "   6. Quit") &&
                output2.contains("What is database (CSV path name) that you would like to load in?") &&
                output2.contains("Loaded correctly?"))

            System.out.println("Test 2.2 passed.");
        else
            System.out.println("Test 2.2 FAILED.");

        tester2 = new TextUITester("5\n2\n9\nCelebrate Birthday\n6\n");
        output2 = tester2.checkOutput();
        if (output2.startsWith("Please input the number that corresponds to what you would like to do.\n" +
                "ToDo List commands:\n" +
                "   1. Insert a ToDo into the database\n" +
                "   2. Print a day's ToDo's\n" +
                "   3. Print a week's ToDo's\n" +
                "   4. Load a database (CSV) into your ToDo List\n" +
                "   5. Remove a ToDo\n" +
                "   6. Quit") &&
                output2.contains("What month is the ToDo you want to cross off in?") &&
                output2.contains("Removed correctly"))

            System.out.println("Test 2.3 passed.");
        else
            System.out.println("Test 2.3 FAILED.");


        //Test method 3, Test Print day and Print Week
        TextUITester tester3 = new TextUITester("1\n2\n9\nCelebrate Birthday\n"+"2\n2\n9\n");
        ToDoFrontEnd toDo2 = new ToDoFrontEnd();
        toDo2.runner();

        String output3 = tester3.checkOutput();
        if (output3.startsWith("Please input the number that corresponds to what you would like to do.\n" +
                "ToDo List commands:\n" +
                "   1. Insert a ToDo into the database\n" +
                "   2. Print a day's ToDo's\n" +
                "   3. Print a week's ToDo's\n" +
                "   4. Load a database (CSV) into your ToDo List\n" +
                "   5. Remove a ToDo\n" +
                "   6. Quit") &&
                output3.contains("Which month is this day in?") &&
                output.contains("Which day's ToDo's do you want to print?") &&
                output.contains("ToDos of " + "2" + "/" + "9" + ": "))
            System.out.println("Test 3.1 passed.");
        else


            tester2 = new TextUITester("3\n6\n6\n6\n");
        output2 = tester2.checkOutput();
        if (output2.startsWith("Please input the number that corresponds to what you would like to do.\n" +
                "ToDo List commands:\n" +
                "   1. Insert a ToDo into the database\n" +
                "   2. Print a day's ToDo's\n" +
                "   3. Print a week's ToDo's\n" +
                "   4. Load a database (CSV) into your ToDo List\n" +
                "   5. Remove a ToDo\n" +
                "   6. Quit") &&
                output2.contains("Which month is this starting day in?") &&
                output2.contains("No ToDos this day.\n"))

            System.out.println("Test 3.2 passed.");
        else
            System.out.println("Test 3.2 FAILED.");
    }


    // Below is the code that actually implements the redirection of System.in and System.out,
    // and you are welcome to ignore this code: focusing instead on how the constructor and
    // checkOutput() method is used int he example above.

    private PrintStream saveSystemOut; // store standard io references to restore after test
    private PrintStream saveSystemErr;
    private InputStream saveSystemIn;
    private ByteArrayOutputStream redirectedOut; // where output is written to durring the test
    private ByteArrayOutputStream redirectedErr;

    /**
     * Creates a new test object with the specified string of simulated user input text.
     *
     * @param programInput the String of text that you want to simulate being typed in by the user.
     */
    public TextUITester(String programInput) {
        // backup standard io before redirecting for tests
        saveSystemOut = System.out;
        saveSystemErr = System.err;
        saveSystemIn = System.in;
        // create alternative location to write output, and to read input from
        System.setOut(new PrintStream(redirectedOut = new ByteArrayOutputStream()));
        System.setErr(new PrintStream(redirectedErr = new ByteArrayOutputStream()));
        System.setIn(new ByteArrayInputStream(programInput.getBytes()));
    }

    /**
     * Call this method after running your test code, to check whether the expected
     * text was printed out to System.out and System.err.  Calling this method will
     * also un-redirect standard io, so that the console can be used as normal again.
     *
     * @return captured text that was printed to System.out and System.err durring test.
     */
    public String checkOutput() {
        try {
            String programOutput = redirectedOut.toString() + redirectedErr.toString();
            return programOutput;
        } finally {
            // restore standard io to their pre-test states
            System.out.close();
            System.setOut(saveSystemOut);
            System.err.close();
            System.setErr(saveSystemErr);
            System.setIn(saveSystemIn);
        }
    }
}