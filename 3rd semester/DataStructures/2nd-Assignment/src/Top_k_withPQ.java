import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Top_k_withPQ {
    private static PQ<Song> priorityQueue;

    public static void main(String args[]) {

       int k = 0;
	    try{
             k = Integer.parseInt(args[0]);
       }catch (NumberFormatException nfe){
	       System.out.println("K is not a number");
		   System.exit(0);
	   }
        if (k <= 0) {
            System.out.println("Invalid input");
            System.exit(0);
        }
		
        priorityQueue = new PQ<>(k, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return (-1)*o1.compareTo(o2); //The greater Song is the song with the least likes.
            }
        });
        try {
            readFileSmartly(args[1], k);
        } catch (IOException e) {
            System.err.println("I/O error has occurred");
            System.exit(1);
        }


        System.out.println("The top " + k + " songs are: ");

        Song []songs = new Song[k];
        for(int i = k-1; i>=0; i--){
             songs[i] = priorityQueue.getMax();
        }  
       
        for(int j=0; j<k; j++){
             System.out.println( (j+1) + ". " + songs[j].getTitle());
        }
    }


    private static void readFileSmartly(String path, int kTopSongs) throws IOException, NoSuchElementException {
        FileReader fr = null;
        try {
            fr = new FileReader(path);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
        BufferedReader br = new BufferedReader(fr);

        String line;
        int id;
        int likes;
        String title;
        while ((line = br.readLine()) != null) {
		    title = "";
            line = line.trim();
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
            if (title.length() > 80 || (id <= 0 || id >= 10000)) {
                continue;
            }
            Song song = new Song(id, title, likes);
            if (priorityQueue.size() < kTopSongs) {
                priorityQueue.insert(song);
            } else {
                if (priorityQueue.max().compareTo(song) < 0) {
                    priorityQueue.getMax();
                    priorityQueue.insert(song);
                }

            }
        }
    }
}

