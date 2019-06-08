import java.util.ArrayList;
import java.util.List;

public class EdgeHelper {

    private List<Pair<String>> edges = new ArrayList<>();
    private int numberOfEdges;

    public EdgeHelper() {

    }

    public void addEdge(Pair<String> edge) {
        if(!contains(edge)){
            edges.add(edge);
            numberOfEdges++;
        }
    }

    private boolean contains(Pair<String> edge){
        for (Pair<String> c : edges) {
            if(Pair.equals(c, edge)) return true;
        }
        return false;
    }

    public int getNumberOfEdges() {
        return numberOfEdges;
    }
}
