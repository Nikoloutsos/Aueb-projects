import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class LoadSongs {
    private static int numberOfSongs;

    public static Song[] loadSongFromTxt(String filename) throws IOException {
        Song[] songArray = new Song[100];
        numberOfSongs = 0;
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int id;
        int likes;
        String title ;

        while ((line = br.readLine()) != null) {
		    title = "";
            String[] words = line.split(" ");
            id = Integer.parseInt(words[0]);
            likes = Integer.parseInt(words[words.length - 1]);

            for (int k = 1; k < words.length - 1; k++) {
                if (k == words.length - 2) {
                    title = title + words[k];
                } else {
                    title = title + words[k] + " ";
                }
            }
            if (title.length() > 80 || (id <= 0 || id >= 10000)) { continue; }
            Song song = new Song(id, title, likes);
            if (numberOfSongs > songArray.length - 1) {
                songArray = Arrays.copyOf(songArray, numberOfSongs + 100);

            }
            songArray[numberOfSongs] = song;
            
            numberOfSongs++;

        }
        return songArray;
    }



    public static int getNumberOfSongs() {
        return numberOfSongs;
    }

    public static void resetNumberOfSongs(){
        numberOfSongs = 0;
    }
}
