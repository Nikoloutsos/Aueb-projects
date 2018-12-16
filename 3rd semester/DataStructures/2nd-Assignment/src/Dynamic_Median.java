import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

public class Dynamic_Median {
    private static PQ<Song> lowerHalfPQ;
    private static PQ<Song> upperHalfPQ;
    private static int i;

    public static void main(String[] args) {
        lowerHalfPQ = new PQ<>(100, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o1.compareTo(o2);
            }
        });
        upperHalfPQ = new PQ<>(100, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return (-1) * o1.compareTo(o2);
            }
        });
        lowerHalfPQ.insert(new Song(0, "fake", -1));
        upperHalfPQ.insert(new Song(10001, "fake", Integer.MAX_VALUE));

        //Reading the file
        FileReader fr = null;
        try {
            fr = new FileReader(args[0]);
            BufferedReader br = new BufferedReader(fr);

            String line;
            int id;
            int likes;
            String title;
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
                if (title.length() > 80 || (id <= 0 || id >= 10000)) {
                    continue;
                }
                Song song = new Song(id, title, likes);
                insert(song);
                if (i % 5 == 0) {
                    Song medianSong = getMedian();
                    System.out.println("median: " + medianSong);
                }

            }


        } catch (IOException e1) {
            System.out.println("Problem with reading the file");
            System.exit(0);
        }
    }

    private static void balance() {
        if (lowerHalfPQ.size() - upperHalfPQ.size() > 1) {
            upperHalfPQ.insert(lowerHalfPQ.getMax());

        } else if (upperHalfPQ.size() - lowerHalfPQ.size() > 1) {
            lowerHalfPQ.insert(upperHalfPQ.getMax());
        }
    }

    private static void insert(Song song) {
        i++;
        if (lowerHalfPQ.max().compareTo(song) < 0) {
            upperHalfPQ.insert(song);
        } else {
            lowerHalfPQ.insert(song);
        }

        balance();
    }

    private static Song getMedian() {
        if (upperHalfPQ.size() != lowerHalfPQ.size()) {
            if (upperHalfPQ.size() > lowerHalfPQ.size()) return upperHalfPQ.max();
            else return lowerHalfPQ.max();
        } else {
            return upperHalfPQ.max().compareTo(lowerHalfPQ.max()) > 0 ? upperHalfPQ.max() : lowerHalfPQ.max();
        }
    }
}