import java.util.*;


public class Exercise1 {

    private Map<String, List<String>> nodes;
    private List<String> solution = new ArrayList<>();
    private List<String> solution2 = new ArrayList<>();
    private Map<String, String> parent = new HashMap<>();
    private String name1;
    private String name2;
    private boolean areNamesInGraph = true;
    private boolean isDestinationFound = false;




    public Exercise1(Map<String, List<String>> nodes, String name1, String name2) {
        this.nodes = nodes;
        this.name1 = name1;
        this.name2 = name2;
        this.solution = new ArrayList<>();
    }

    /**
     * The problem is solved with the use of BFS (Breadth first search)
     * IMAO, it is the most efficient algorithm comparing to Dijkstra.
     */
    public synchronized void solveExercise1() {
        ArrayList<String> result1 = doBfs();
        if (result1 == null) return;

        for (int i = 0; i < result1.size() - 1; i++) {
            List<String> temp1 = nodes.get(result1.get(i));
            List<String> temp2 =  nodes.get(result1.get(i + 1));
            temp1.remove(result1.get(i + 1));
            temp2.remove(result1.get(i));
        }

        ArrayList<String> result2 = doBfs();


        Collections.reverse(result1);
        Collections.reverse(result2);

        solution = result1;
        solution2 = result2;

    }

    private ArrayList<String> doBfs() {
        if (!(nodes.containsKey(name1) && nodes.containsKey(name2))) {
            System.err.println("Exercise 1");
            System.err.println("Sorry, either \"" + name1 + "\" or \"" + name2 + "\" are not found in the graph!");
            areNamesInGraph = false;
            return null;
        }

        HashMap<String, Boolean> visited = new HashMap<>();
        LinkedList<String> queue = new LinkedList<String>();
        parent = new HashMap<>();


        //Initialization
        for(String cString : nodes.keySet()){
            visited.put(cString, false);
        }
        //Queue



        queue.addLast(name1);
        isDestinationFound = false;


        while (queue.size() != 0 && !isDestinationFound){
            String head = queue.poll();
            if(visited.get(head)) continue;

            visited.put(head, true);


            for (String cNeighbours : nodes.get(head)) {
                queue.addLast(cNeighbours);
                if(!parent.containsKey(cNeighbours)) parent.put(cNeighbours, head);
                if (cNeighbours.equalsIgnoreCase(name2)) {
                    isDestinationFound = true;
                    break;
                }
            }
        }

        ArrayList<String> result = new ArrayList<>();
        String par = name2;
        result.add(name2);

        while (par != name1) {
            par = parent.get(par);
            result.add(par);
        }
        return result;
    }

    public void printSolution() {
        System.out.println("\n------------------------------------------------------------------");
        if(areNamesInGraph){
            System.out.println("EXERCISE 1 \n(Use this nodes and a cycle will be formed)\n");
            if(isDestinationFound){
                Collections.reverse(solution2);
                solution2.remove(0);
                System.out.println("The length of this cycle is " + (solution.size() + solution2.size() - 1));
                solution.addAll(solution2);
                System.out.print(solution);

            }else{
                System.out.println("Sadly, there is no cycle between the given names ");
            }
        }

    }
}
