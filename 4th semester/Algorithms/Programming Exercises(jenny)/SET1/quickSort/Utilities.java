import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utilities {

	public static List<Integer> convertIntegerSequenceToList(String input) {
		List<Integer> list = new ArrayList<>();
		String[] elements = input.split("\\s+");
		for (String element : elements) {
			list.add(Integer.valueOf(element));
		}
		return list;
	}
	
	public static List<Integer> convertFileSequenceToList(File input) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line = br.readLine();
		br.close();
		return convertIntegerSequenceToList(line);
	}
	
	public static List<List<Integer>> convertFileMatrixToListOfLists(File input) throws IOException {
		List<List<Integer>> list = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line = null;
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			list.add(convertIntegerSequenceToList(line));
		}
		br.close();
		return list;
	}
	
}

