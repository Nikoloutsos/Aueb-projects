package Utils;

import Model.State;
import com.google.gson.*;

import java.io.*;

import static Model.Info.*;

/**
 * This class is responsible for parsing the data from JSON
 */
public class ParserUtil
{
    private static ParserUtil instance;
    private Gson gson;

    private ParserUtil()
    {
       gson = new GsonBuilder().create();
    }

    public static  ParserUtil getInstance()
    {
        if (instance == null)
        {
            instance = new ParserUtil();
        }

        return instance;
    }

    /**
     * @param txtFileName The path of txt
     * @param classType The class that you want to parse. (e.g Lesson.class)
     * @return The POJO
     */
    public <T> T parseIt(String txtFileName, Class<T> classType) throws IOException
    {
        return gson.fromJson( loadTxtInString(txtFileName) , classType);
    }

    private String loadTxtInString(String txtFileName) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        File file = new File(txtFileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null)
        {
            sb.append(st);
        }
        return sb.toString();
    }

    public void saveStateToFile(State state) throws IOException {
        ParserUtil p = new ParserUtil();
        String result = p.stateToJson(state);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(result);
        String prettyJsonString = gson.toJson(je);
        File file = new File("output.json");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(prettyJsonString);
        bw.flush();
        bw.close();
    }

    public String stateToJson(State state){
        String[] days = {"Δευτέρα", "Τρίτη", "Τετάρτη", "Πέμπτη", "Παρασκευή"};
        JsonObject mainJson = new JsonObject();
        for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
        {
            JsonObject groupJson = new JsonObject();
            mainJson.addProperty("found_solution", "true");
            mainJson.add("group" + i, groupJson);
            for (int j = 0; j < NUMBER_OF_DAYS; ++j)
            {
                JsonObject dayJson = new JsonObject();
                groupJson.add(days[j], dayJson);
                for (int k = 0; k < NUMBER_OF_MAX_HOUR_PER_DAY ; ++k)
                {
                    dayJson.addProperty((k + 1) + " ώρα", getBlockInfo(state.data[i][j][k]));
                }
            }
        }
        return mainJson.toString();
    }

    private String getBlockInfo(State.Block block)
    {
        if (block.teacher == null) return "κενό";
        return "( "+ block.teacher.name + " ," + block.lesson.mTitle +" )";
    }
}
