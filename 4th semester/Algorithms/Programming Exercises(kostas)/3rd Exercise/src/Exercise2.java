import java.util.*;

/**
 * According to given submission files
 */
class Exercise2 {

    private Map<String, List<String>> nodes;
    private Map<String, Boolean> coveredNodes;
    private List<String> solution1;
    private long t1;
    private List<String> solution2;
    private long t2;
    private long bruteforceTime;
    private long greedyTime;
    private int size;
    private int numberOfEdges;
    private List<String> temp = new ArrayList<>(); //Used for mapping String to integer (Used in bruteforce)

    public Exercise2(Map<String, List<String>> nodes) {
        this.nodes = nodes;
        this.solution1 = new ArrayList<>();
        this.t1 = 0L; // time in milliseconds
        this.solution2 = new ArrayList<>();
        this.t2 = 0L;  // time in milliseconds
        this.size = nodes.size();
    }

    public void solveExercise2() {


        t1 = System.currentTimeMillis();
        solveBruteforce();
        solveGreedy();
        t2 = System.currentTimeMillis();


    }

    private void solveBruteforce() {
        System.out.println("\n------------------------------------------------------------------");
        System.out.println("EXERCISE 2");

        System.out.println("\n!!Be careful bruteforce takes a lot of time...");
        System.out.println("It's like watching a paint dry\n");
        findNumberOfEdges();
        mapStringNodesToInteger();


        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < nodes.keySet().size(); i++) {
            arrayList.add(0);
        }

        List<Integer> subset = createPowerSet(arrayList, nodes.keySet().size(), 0);
        boolean hasSolution = false;
        for (int i = 0; i < Math.pow(2, nodes.keySet().size() ); i++) {
            subset = createPowerSet(subset, nodes.keySet().size(), 0);
            hasSolution = assertVertexCover(subset);
            if(hasSolution) break;
        }

        if(hasSolution){
            solution2 = new ArrayList<>();
            for (Integer i : subset) {
                if(i == 0) continue;
                solution2.add(temp.get(i - 1));
            }
            bruteforceTime = System.currentTimeMillis() - t1;
        }else{
            System.out.println("No solution found!!");
        }

    }

    public void printSolution() {

        System.out.println("Greedy approach:");
        System.out.println("S = " + solution1);
        System.out.println("Number of nodes, |S| = " + solution1.size());
        System.out.println("Elapsed time --> " + greedyTime + " milliseconds");


        System.out.println("\nBruteForce approach:");
        System.out.println("K = " + solution2);
        System.out.println("Number of nodes, |K| = " + solution2.size());
        System.out.println("Elapsed time --> " + bruteforceTime + " milliseconds");

    }


    private void solveGreedy() {
        coveredNodes = new HashMap<>();

        while (!isEveryEdgeCovered()) {

            String famousStudent = getMaxStudent();
            coveredNodes.put(famousStudent, true);
            solution1.add(famousStudent);


            for (String cNeighbors : nodes.get(famousStudent)) {
                nodes.get(cNeighbors).remove(famousStudent); //Remove students that point to him.
                coveredNodes.put(cNeighbors, true);
            }
            nodes.remove(famousStudent);

        }

        greedyTime = System.currentTimeMillis() - t1;


    }


    /**
     * @return The node with the greater grade.
     */
    private String getMaxStudent() {
        int max = 0;
        String student = null;
        for (String key : nodes.keySet()) {
            if (nodes.get(key).size() > max) {
                max = nodes.get(key).size();
                student = key;
            }
        }
        return student;
    }

    /**
     * @return true only if solution1 contains a subset that is a vertex cover.
     */
    private boolean isEveryEdgeCovered() {
        for (String s : nodes.keySet()) {
            if (nodes.get(s).size() != 0) return false;
        }
        return true;
    }

    private boolean assertVertexCover(List<Integer> subVertex) {
        EdgeHelper edgeHelper = new EdgeHelper();

        ArrayList<String> realMap = new ArrayList<>();
        for (Integer i : subVertex) {
            if(i==0) continue;
            realMap.add(temp.get(i - 1));
        }

        for (String cString : realMap) {
            for (String inner : nodes.get(cString)) {
                edgeHelper.addEdge(new Pair<>(cString, inner));
            }
        }
        return numberOfEdges == edgeHelper.getNumberOfEdges();
    }


    private void findNumberOfEdges() {
        EdgeHelper edgeHelper = new EdgeHelper();
        for (String s : nodes.keySet()) {
            for (String inner : nodes.get(s)) {
                edgeHelper.addEdge(new Pair<>(s, inner));
            }
        }
        numberOfEdges = edgeHelper.getNumberOfEdges();
    }

    private void mapStringNodesToInteger() {
        for (String c : nodes.keySet()) {
            boolean hasThisNode = false;
            for (String tempo : temp) {
                if (tempo.equalsIgnoreCase(c)) hasThisNode = true;
            }
            if (!hasThisNode) temp.add(c);
        }

    }

    private static List<Integer> createPowerSet(List<Integer> pre, int upperLimit, int position) {
        List<Integer> result = new ArrayList<Integer>(pre);

        int maxVal = 0;

        if (result.get(position) == upperLimit) {
            result = createPowerSet(result, upperLimit - 1, position + 1);

            maxVal = getMaxVal(position, result, maxVal);

            result.set(position, maxVal + 1);
        } else {
            result.set(position, result.get(position) + 1);
        }
        return result;
    }

    private static int getMaxVal(int position, List<Integer> result, int maxVal) {
        for (int i = result.size() - 1; i > position; --i) {
            if (result.get(i) > maxVal) maxVal = result.get(i);
        }
        return maxVal;
    }


}