import java.util.ArrayList;
import java.util.List;

public class ConnectNodes {
    private static List<Pair> help = new ArrayList<>();

    public ConnectNodes() {
        help = new ArrayList<>();
    }

    public static void addConnection(String n1, String n2) {
        if (checkIfConnectionAlreadyExists(n1, n2) == true) return;

        help.add(new Pair(n1, n2));

    }

    private static boolean checkIfConnectionAlreadyExists(String n1, String n2) {
        for (Pair temp : help) {
            if (temp.node1.equalsIgnoreCase(n1) && temp.node2.equalsIgnoreCase(n2)) return true;
            if (temp.node1.equalsIgnoreCase(n2) && temp.node2.equalsIgnoreCase(n1)) return true;
        }

        return false;
    }


    public static boolean CheckIFAllEDgesAreCovered(int edgesNumber) {
        if (edgesNumber == help.size()) {

            return true;
        }
        help = new ArrayList<>();
        return false;
    }
}