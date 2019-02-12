

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * A datasource that reads items from a text file
 * The file is given as parameter during the construction of the instance
 */
public class DataSource {

    private BufferedReader reader;
    private String theFile;

    /**
     * Creates a new DataSource instance
     * @param file the path of file with the item
     */
    public DataSource(String file) {
        theFile = file;
    }

    /**
     * Reads the item with identified by the given key
     * @param key the key to identify the item
     * @return the value of the item in String format
     * @throws IOException if something "bad" happens while parsing the text file
     */
    public String readItem(String key) throws IOException {
        reader = new BufferedReader(new FileReader(theFile));
        String line = null;
        String data = null;
        while((line = reader.readLine())!= null){
            StringTokenizer tokenizer = new StringTokenizer(line, "\t");
            String firstToken = tokenizer.nextToken();
            if(key.equals(firstToken)){
                data = tokenizer.nextToken();
            }
        }

        reader.close();
        return data;
    }
}
