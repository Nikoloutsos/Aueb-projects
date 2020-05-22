
import java.util.*;

class Exercise1 {

    private Map<String, List<String>> nodes;
    private String name1;
    private String name2;

    private Path_Distance_From_Start_Vertice[] sol;
    private Path_Distance_From_Start_Vertice[] sol2;
    private Map<String, Integer> posOfName = new HashMap<>();
    private Map<String, Integer> posOfName2 = new HashMap<>();

    private List<String> pathOfSol1;
    private List<String> pathOfSol2;


    public Exercise1(Map<String, List<String>> nodes, String name1, String name2) {
        this.nodes = nodes;
        this.name1 = name1;
        this.name2 = name2;
    }

    public int solveExercise1() {
        //PART 1: FIND SHORTEST PATH FROM FIRST NAME TO EVERY OTHER AUTHOR
        sol = new Path_Distance_From_Start_Vertice[nodes.size()];
        int k = 0;
        for (String key : nodes.keySet()) {
            if (key.equals(name1)) {
                sol[k] = new Path_Distance_From_Start_Vertice(key);
                sol[k].distance_from_start_node = 0;
                posOfName.put(key, k);
            } else {
                sol[k] = new Path_Distance_From_Start_Vertice(key);
                posOfName.put(key, k);
            }
            ++k;
        }


        Queue<String> not_covered_name_nodes = new LinkedList<>();
        Queue<String> covered_name_nodes = new LinkedList<>();

        not_covered_name_nodes.add(name1);

        while (not_covered_name_nodes.size() > 0) {
            String help = not_covered_name_nodes.remove();
            covered_name_nodes.add(help);

            int helpPos = posOfName.get(help);
            List<String> help_list = nodes.get(help);
            for (int i = 0; i < help_list.size(); ++i) {
                int inHelpVerticePos = posOfName.get(help_list.get(i));

                if (!covered_name_nodes.contains(help_list.get(i))) {
                    if (sol[helpPos].distance_from_start_node + 1 <= sol[inHelpVerticePos].distance_from_start_node) {
                        sol[inHelpVerticePos].distance_from_start_node = sol[helpPos].distance_from_start_node + 1;

                        sol[inHelpVerticePos].path_from_start_vertice.clear();
                        for (int j = 0; j < sol[helpPos].path_from_start_vertice.size(); ++j) {
                            sol[inHelpVerticePos].path_from_start_vertice.add(sol[helpPos].path_from_start_vertice.get(j));
                        }
                        sol[inHelpVerticePos].path_from_start_vertice.add(help);
                    }

                    not_covered_name_nodes.add(help_list.get(i));
                }
            }
        }

        if (sol[posOfName.get(name2)].distance_from_start_node == Integer.MAX_VALUE) {
            return -1;
        }


        /*System.out.println(sol[posOfName.get(name2)].node_name + "   " + sol[posOfName.get(name2)].distance_from_start_node
                + "   " + sol[posOfName.get(name2)].path_from_start_vertice + "  " + name2);*/


        //--------------------------------------------------------------------------------------------------------------//
        //PART 2:DELETE THE CURRENT BEST PATH
        pathOfSol1 = sol[posOfName.get(name2)].path_from_start_vertice;
        pathOfSol1.add(name2);
        int sizeOfPath = pathOfSol1.size() - 1;
        while (sizeOfPath > 0) {
            List<String> nodesConnected = nodes.get(pathOfSol1.get(sizeOfPath - 1));
            List<String> nodesConnected1 = nodes.get(pathOfSol1.get(sizeOfPath));
            nodesConnected.remove(pathOfSol1.get(sizeOfPath));
            nodes.put(pathOfSol1.get(sizeOfPath - 1), nodesConnected);

            nodesConnected1.remove(pathOfSol1.get(sizeOfPath - 1));
            nodes.put(pathOfSol1.get(sizeOfPath), nodesConnected1);
            --sizeOfPath;
        }


        //-----------------------------------------------------------------------------------------------------------------//
        //PART 3 :RUN DIJKSTRA AGAIN THE SAME WAY
        sol2 = new Path_Distance_From_Start_Vertice[nodes.size()];
        int index = 0;
        for (String key : nodes.keySet()) {
            if (key.equals(name1)) {
                sol2[index] = new Path_Distance_From_Start_Vertice(key);
                sol2[index].distance_from_start_node = 0;
                posOfName2.put(key, index);
            } else {
                sol2[index] = new Path_Distance_From_Start_Vertice(key);
                posOfName2.put(key, index);
            }
            ++index;
        }


        Queue<String> not_covered_name_nodes2 = new LinkedList<>();
        Queue<String> covered_name_nodes2 = new LinkedList<>();

        not_covered_name_nodes2.add(name1);

        while (not_covered_name_nodes2.size() > 0) {
            String help = not_covered_name_nodes2.remove();
            covered_name_nodes2.add(help);

            int helpPos = posOfName2.get(help);
            List<String> help_list = nodes.get(help);
            for (int i = 0; i < help_list.size(); ++i) {
                int inHelpVerticePos = posOfName2.get(help_list.get(i));

                if (!covered_name_nodes2.contains(help_list.get(i))) {
                    if (sol2[helpPos].distance_from_start_node + 1 <= sol2[inHelpVerticePos].distance_from_start_node) {
                        sol2[inHelpVerticePos].distance_from_start_node = sol2[helpPos].distance_from_start_node + 1;

                        sol2[inHelpVerticePos].path_from_start_vertice.clear();
                        for (int j = 0; j < sol2[helpPos].path_from_start_vertice.size(); ++j) {
                            sol2[inHelpVerticePos].path_from_start_vertice.add(sol2[helpPos].path_from_start_vertice.get(j));
                        }
                        sol2[inHelpVerticePos].path_from_start_vertice.add(help);
                    }

                    not_covered_name_nodes2.add(help_list.get(i));
                }
            }
        }

        //CHECK ID THERE IS A CYCLE(IF YES, IT WILL BE THE SHORTEST)
        if (sol2[posOfName2.get(name2)].distance_from_start_node == Integer.MAX_VALUE) {
            return 1;
        }

        pathOfSol2 = sol2[posOfName2.get(name2)].path_from_start_vertice;

        /*System.out.println(sol2[posOfName2.get(name2)].node_name + "   " + sol2[posOfName2.get(name2)].distance_from_start_node
                + "   " + sol2[posOfName2.get(name2)].path_from_start_vertice + "  " + name2);*/
        return 0;
    }


    public void printSolution() {
        System.out.println("There is a cycle between: " + name1 + " and " + name2 + "!");
        System.out.println("[path1: " + pathOfSol1 + "]");
        System.out.println("[path2: " + pathOfSol2 + "," + name2 + "]]");
        System.out.println("The shortest(best) cycle is:");
        int shortestPathLength = 0;
        for (String node_name : pathOfSol1) {
            System.out.print(node_name + "-->");
            ++shortestPathLength;
        }

        for (int i = pathOfSol2.size() - 1; i >= 1; --i) {
            System.out.print(pathOfSol2.get(i) + "-->");
            ++shortestPathLength;
        }
        System.out.print(pathOfSol2.get(0) + "!!!");
        System.out.println("\nVERTICES = " + shortestPathLength + "!" + "\nEDGES = " + shortestPathLength + "!");
    }
}





