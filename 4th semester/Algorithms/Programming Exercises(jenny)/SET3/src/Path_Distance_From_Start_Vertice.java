import java.util.ArrayList;
import java.util.List;

public class Path_Distance_From_Start_Vertice {
    String node_name;
    int distance_from_start_node;
    List<String> path_from_start_vertice;


    public Path_Distance_From_Start_Vertice(String name) {
        this.distance_from_start_node = Integer.MAX_VALUE;
        this.path_from_start_vertice = new ArrayList<>();
        this.node_name = name;
    }
}
