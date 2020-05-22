import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class Exercise2 {

    private String filename;
    private int numOfEdges = 0;

    private Map<String, List<String>> nodes;
    private List<String> solutionGreedy;
    private long timeGreedy;
    private List<String> solutionBruteForce;
    private long timeBruteForce;

    //help for greedy
    Map<String, Integer> countCollaboratorsGreedy = new HashMap<>();

    //help for brute force
    Map<Integer, String> nodeIntMatch = new HashMap<>();

    public Exercise2(Map<String, List<String>> nodes, String filename) {
        this.filename = filename;
        this.nodes = nodes;
        this.solutionGreedy = new ArrayList<>();
        this.timeGreedy = 0L; // time in milliseconds
        this.solutionBruteForce = new ArrayList<>();
        this.timeBruteForce = 0L;  // time in milliseconds


    }

    public void solveExercise2() {
        numOfEdges = countEdges();

        //solve brute force
        System.out.println("Solving brute force");
        long startTimeBrute = System.currentTimeMillis();
        solutionBruteForce = doBruteForce();
        long finishTimeBrute = System.currentTimeMillis();
        timeBruteForce = finishTimeBrute - startTimeBrute;
        System.out.println("BruteForce Solved");


        //solve greedy
        for (String key : nodes.keySet()) {
            countCollaboratorsGreedy.put(key, nodes.get(key).size());
        }

        System.out.println("Solving greedy");
        long startTimeGreedy = System.currentTimeMillis();
        doGreedy();
        long finishTimeGreedy = System.currentTimeMillis();
        timeGreedy = finishTimeGreedy - startTimeGreedy;
        System.out.println("Greedy solved");

    }


    private void doGreedy() {
        while (true) {
            String student = getStudentMax();
            if (student == "") break;
            solutionGreedy.add(student);
            removeAndUpdateStudents(student);

        }

    }

    private String getStudentMax() {
        String maxStudent = "";
        int maxStudentCount = 0;

        for (String key : countCollaboratorsGreedy.keySet()) {
            if (countCollaboratorsGreedy.get(key) > maxStudentCount) {
                maxStudent = key;
                maxStudentCount = countCollaboratorsGreedy.get(key);
            }
        }

        return maxStudent;
    }

    private void removeAndUpdateStudents(String student) {
        List<String> st = nodes.get(student);
        nodes.put(student, new ArrayList<>());
        countCollaboratorsGreedy.put(student, 0);

        for (String stud : st) {
            List<String> help = nodes.get(stud);
            help.remove(student);
            int help1 = countCollaboratorsGreedy.get(stud) - 1;
            countCollaboratorsGreedy.put(stud, help1);
        }
    }


    private List<String> doBruteForce() {

        KeyNodeToIntegerMatching();
        ArrayList<Integer> arrayComb = new ArrayList<>();
        for (int i = 0; i < nodes.keySet().size(); ++i) {
            arrayComb.add(0);
        }


        List<Integer> subVertex = getCombinationOfNodes(arrayComb, nodes.keySet().size(), 0);

        for (int i = 0; i < Math.pow(2, nodes.keySet().size() - 1); ++i) {
            subVertex = getCombinationOfNodes(subVertex, nodes.keySet().size(), 0);

            List<String> vertexCombination = new ArrayList<>();

            for (int j = 0; j < subVertex.size(); ++j) {
                if (subVertex.get(j) == 0) {
                    continue;
                }
                vertexCombination.add(nodeIntMatch.get(subVertex.get(j)));
            }
            if (isVertex(vertexCombination) == true) return vertexCombination;
        }


        return null;
    }


    private boolean isVertex(List<String> vertexCombination) {

        for (String myNode : vertexCombination) {
            List<String> tempHelp = new ArrayList<>();
            tempHelp = nodes.get(myNode);
            if (tempHelp == null) {
                tempHelp = new ArrayList<>();
            }
            for (String ss : tempHelp) {
                ConnectNodes.addConnection(myNode, ss);
            }
        }

        if (ConnectNodes.CheckIFAllEDgesAreCovered(numOfEdges) == true) return true;
        return false;
    }


    public static List<Integer> getCombinationOfNodes(List<Integer> prev, int max, int pos) {
        List<Integer> result = new ArrayList<Integer>(prev);

        int maxVal = 0;

        if (result.get(pos) == max) {
            result = getIntegers(max, pos, result, maxVal);
        } else {
            result.set(pos, result.get(pos) + 1);
        }
        return result;
    }

    private static List<Integer> getIntegers(int max1, int pos, List<Integer> result, int max2) {
        result = getCombinationOfNodes(result, max1 - 1, pos + 1);

        for (int i = result.size() - 1; i > pos; --i) {
            if (result.get(i) > max2) max2 = result.get(i);
        }

        result.set(pos, max2 + 1);
        return result;
    }


    public void KeyNodeToIntegerMatching() {
        int i = 0;
        for (String help : nodes.keySet()) {
            ++i;
            nodeIntMatch.put(i, help);
        }
    }

    public int countEdges() {
        File file = new File(filename);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (Exception e) {

        }


        int countEdges = 0;
        try {
            while ((br.readLine()) != null) {
                ++countEdges;
            }

        } catch (Exception e) {
        } finally {
            try {
                br.close();
            } catch (IOException e) {

            }
        }
        return countEdges;
    }


    public void printSolution() {
        System.out.println("\nSolution using greedy is: ");
        System.out.println(solutionGreedy);
        System.out.println("Size of cover is: " + solutionGreedy.size() + " students.");
        System.out.println("Greedy solved in: " + timeGreedy + "milliSeconds!\n");


        System.out.println("Solution using Brute Force is: ");
        /*There is always going to be a vertex cover, at worst case it will include all edges, but i just check
        whether there is one or not just to do so!*/
        if (solutionBruteForce == null) {
            System.out.println("No vertex cover found");
            System.out.println("Brute force solved in: " + timeBruteForce + "milliSeconds!");
        } else {
            System.out.println("There is a vertex cover!");
            System.out.print("Solution is : ");
            System.out.println(solutionBruteForce);
            System.out.println("Brute force solved in: " + timeBruteForce + "milliSeconds!");
        }
    }


}