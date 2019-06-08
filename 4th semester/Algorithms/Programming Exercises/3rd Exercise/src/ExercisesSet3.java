import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExercisesSet3 {
    public static final String PATH1 = "C:\\Users\\LaptopPc\\Desktop\\Katia3\\philosophy_edgelist.txt";
    public static final String PATH2 = "C:\\Users\\LaptopPc\\Desktop\\Katia3\\neee.txt";

    public static void main(String[] args) {

        try {
            String ex1fileName = args[0];
            String name1 = args[1];
            String name2 = args[2];
            String ex2fileName = args[3];

            exercise1(ex1fileName, name1, name2);
            exercise2(ex2fileName);

        } catch (Exception e) {
            System.err.println("Make sure you have added all args from command line!");
        }
//

    }

    public static void exercise1(String fileName, String name1, String name2) {
        Map<String, List<String>> file1Data = parseData(fileName);
        Exercise1 exercise1 = new Exercise1(file1Data, name1, name2);
        // exercise1.printInputData();
        exercise1.solveExercise1();
        exercise1.printSolution();
    }

    public static void exercise2(String fileName) {
        Map<String, List<String>> file2Data = parseData(fileName);
        Exercise2 exercise2 = new Exercise2(file2Data);
        // exercise2.printInputData();
        exercise2.solveExercise2();
        exercise2.printSolution();
    }



    public static Map<String, List<String>> parseData(String fileName) {
        try {
            File file = new File(fileName);
            List<List<String>> parsedData = UtilitiesForSet3.convertFileMatrixToListOfLists(file);

            if (validateListOfElements(parsedData))
                return createAdjacencyMatrix(parsedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean validateListOfElements(List<List<String>> parsedData) {
        for (int i = 0; i < parsedData.size(); i++) {
            List<String> elements = parsedData.get(i);
            if (elements.size() != 2) {
                System.out.println("- Problem with line " + i);
                return false;
            }
        }
        return true;
    }

    public static Map<String, List<String>> createAdjacencyMatrix(List<List<String>> parsedData) {
        Map<String, List<String>> AdjacencyList = new HashMap<String, List<String>>();

        for (int i = 0; i < parsedData.size(); i++) {
            List<String> elements = parsedData.get(i);

            if (AdjacencyList.containsKey(elements.get(0))) {
                List<String> listElements = AdjacencyList.get(elements.get(0));
                if (!listElements.contains(elements.get(1)))
                    listElements.add(elements.get(1));
            } else {
                List<String> list = new ArrayList<>();
                list.add(elements.get(1));
                AdjacencyList.put(elements.get(0), list);
            }

            if (AdjacencyList.containsKey(elements.get(1))) {
                List<String> listElements = AdjacencyList.get(elements.get(1));
                if (!listElements.contains(elements.get(0)))
                    listElements.add(elements.get(0));
            } else {
                List<String> list = new ArrayList<>();
                list.add(elements.get(0));
                AdjacencyList.put(elements.get(1), list);
            }

        }
        return AdjacencyList;
    }
}
