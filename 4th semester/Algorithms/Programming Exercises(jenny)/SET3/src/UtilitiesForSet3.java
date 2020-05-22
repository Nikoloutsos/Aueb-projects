import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UtilitiesForSet3 {

    public static List<String> convertStringSequenceToList(String input) {
        List<String> list = new ArrayList<>();
        String[] elements = input.split("\\s+");
        for (String element : elements) {
            list.add(element);
        }
        return list;
    }

    public static List<List<String>> convertFileMatrixToListOfLists(File input) throws IOException {
        List<List<String>> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line = null;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            list.add(convertStringSequenceToList(line));
        }
        br.close();
        return list;
    }

}