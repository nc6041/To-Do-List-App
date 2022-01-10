import java.util.List;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

interface ToDoDataLoaderInterface {
    public List<DayInterface> loadFile(String csvFilePath) throws FileNotFoundException;

    public List<DayInterface> loadAllFilesInDirectory(String directoryPath) throws FileNotFoundException;
}

/**
 * This method reads through each line of the csv file and makes a linked list of Day
 * objects, holding an ArrayList of String to-dos
 */
public class ToDoDataLoader implements ToDoDataLoaderInterface {
    @Override
    public List<DayInterface> loadFile(String csvFilePath) throws FileNotFoundException {
        if (!new File(csvFilePath).exists()) {
            throw new FileNotFoundException("File does not exist");
        } else {
            Scanner scanner = new Scanner(new File(csvFilePath), "UTF-8");
            List<DayInterface> listToDo = new LinkedList<>();

            int index = 0;
            int month = 0;
            int date = 0;

            int monthIdx = 0;
            int dateIdx = 0;
            int taskIdx = 0;

            String header = scanner.nextLine();
            String[] headerArray = header.split(",");
            for (int k = 0; k < headerArray.length; k++) {
                if (headerArray[k].contains("Month")) {
                    monthIdx = k;
                } else if (headerArray[k].contains("Date")) {
                    dateIdx = k;
                } else if (headerArray[k].contains("Task 1")) {
                    taskIdx = k;
                }
            }

            // Each Day object will have an ArrayList of tasks
            //ArrayList<String> toDo = new ArrayList<>();

            while (scanner.hasNextLine()) {
                Scanner rowData = new Scanner(scanner.nextLine());
                ArrayList<String> toDo = new ArrayList<>();

                // While row has more columns to read
                while (rowData.hasNext()) {
                    rowData.useDelimiter(",");
                    String data = rowData.next();
                    while (quoteCounter(data) % 2 != 0) {
                        data = data + "," + rowData.next();
                    }

                    if (index == monthIdx) {
                        month = Integer.parseInt(data);
                    } else if (index == dateIdx) {
                        date = Integer.parseInt(data);
                    } else if (index == taskIdx) {
                        toDo.add(data);
                    } else if (index == taskIdx + 1){
                        toDo.add(data);
                    } else if (index == taskIdx + 2){
                        toDo.add(data);
                    } else if (index == taskIdx + 3){
                        toDo.add(data);
                    } else if (index == taskIdx + 4) {
                        toDo.add(data);
                    }
                    index++;
                }
                // Goes to next row, reset index
                index = 0;

                // Make new Day object with info read from file, add it to the list
                Day newDay = new Day(month, date, toDo);
                listToDo.add(newDay);

            }
            scanner.close();
            return listToDo;
        }
    }


    /**
     * This method loads all csv files from the directory
     * @param directoryPath String path to the file location
     * @return  List of all the Day objects
     * @throws FileNotFoundException thrown during loadFile() if file not found
     */
    @Override
    public List<DayInterface> loadAllFilesInDirectory(String directoryPath) throws FileNotFoundException {
        File directory = new File(directoryPath);
        File[] list = directory.listFiles();
        List<DayInterface> listToDo = new LinkedList<>();
        List<DayInterface> allListToDos = new LinkedList<>();
        for(int i = 0; i< list.length; i++) {
            listToDo = loadFile(list[i].getAbsolutePath()); // merge lists
            allListToDos.addAll(listToDo);
        }
        return allListToDos ;
    }

    private int quoteCounter(String entry) {
        int count = 0;
        for (int i = 0; i < entry.length(); i++) {
            if (entry.charAt(i) == '"') {
                count++;
            }
        }
        return count;
    }
}

//Placeholders
class CalendarDataLoaderPlaceholder implements ToDoDataLoaderInterface {
    public List<DayInterface> loadFile(String csvFilePath) throws FileNotFoundException {
        List<DayInterface> list = new LinkedList<>();
        list.add(new DayPlaceholderA());
        list.add(new DayPlaceholderB());
        return list;
    }

    public List<DayInterface> loadAllFilesInDirectory(String directoryPath) throws FileNotFoundException {
        List<DayInterface> list = new LinkedList<>();
        list.add(new DayPlaceholderA());
        list.add(new DayPlaceholderB());
        list.add(new DayPlaceholderC());
        return list;
    }
}
