import java.io.File;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;

class ExercisesSet3 {

    public static void main(String args[]) {
        System.out.println("SOLVING EXERCISE 1");
        String ex1fileName = args[0];
        String name1 = args[1];//"thomas_reid";
        String name2 = args[2];//"jean_wahl";
        exercise1(ex1fileName, name1, name2);
        System.out.println("EXERCISE 1 SOLVED\n\n");

        System.out.println("SOLVING EXERCISE 2");
        String ex2fileName = args[3];//"C:\\Users\\JennyB\\IdeaProjects\\Set_3_Algorithms\\src\\helpSmallBrute.txt";
        exercise2(ex2fileName);
        System.out.println("EXERCISE 2 SOLVED\n\n");
    }

    public static void exercise1(String fileName, String name1, String name2) {
        Map<String, List<String>> file1Data = parseData(fileName);
        if (!file1Data.containsKey(name1) || !file1Data.containsKey(name2)) {
            System.out.println("One or both names given are not part of the list, therefore it is "
                    + "obvious that no cycle can be found between them with the given connections!");
            return;
        }

        Exercise1 exercise1 = new Exercise1(file1Data, name1, name2);

        int result = exercise1.solveExercise1();
        if (result == -1) {
            System.out.println("There is no connection at all between: " + name1 + " and " + name2 + "!");
        } else if (result == 1) {
            System.out.println("There is no cycle  between: " + name1 + " and " + name2 + "!");
        } else {
            exercise1.printSolution();
        }
    }

    public static void exercise2(String fileName) {
        Map<String, List<String>> file2Data = parseData(fileName);
        Exercise2 exercise2 = new Exercise2(file2Data, fileName);
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