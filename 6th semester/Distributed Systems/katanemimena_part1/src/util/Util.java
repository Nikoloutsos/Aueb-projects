package util;


import model.ArtistCollection;
import model.Song;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Util {
//    public static final String BIG_PRIME_NUMBER = "15485867";
    public static final String BIG_PRIME_NUMBER = "7919";
    public static final boolean DEBUG = true;
    public static Util.Pair<String, Integer> zookeeperConnectionInfo = new Util.Pair<>("localhost", 666);

    /**
     *
     * @param song pojo
     * @return the mp3 binary data for that song
     */
    public static byte[] loadSongFromDiskToRam(Song song){
        try{
            File file = new File(song.os_location);
            FileInputStream fis = new FileInputStream(file);
            byte[] fileData = new byte[(int)file.length()];
            fis.read(fileData);
            return fileData;
        }catch (FileNotFoundException e){
            System.err.println("File not found exception for selected song");
        }catch (IOException e){
            System.err.println("IOException from loadSongFromDiskToRam() in util package");
        }
        return null;
    }

    /**
     * Splits a file's data into smaller parts
     */
    public static List<byte[]> chunkifyFile(byte[] data){
        int chunkSize = 1024*16;
        return chunkifyFile(data, chunkSize);
    }

    /**
     * Splits a file's data into smaller parts
     * @param chunkSize the chunk size in bytes
     */
    public static List<byte[]> chunkifyFile(byte[] data, int chunkSize){
        List<byte[]> result = new ArrayList<>();
        int chunks = data.length / chunkSize;

        for (int i = 0; i < chunks ; i++) {
            byte[] current = new byte[chunkSize];
            for (int j = 0; j < chunkSize ; j++) {
                current[j] = data[j + i*chunkSize];
            }
            result.add(current);
        }

        //Add last chunk
        boolean isRemaining = data.length % chunkSize != 0;
        if (isRemaining){
            int remaining = data.length % chunkSize;
            int offset = chunks * chunkSize;

            byte[] current = new byte[remaining];
            for(int i = 0; i < remaining; ++i){
                current[i] = data[offset + i];
            }

            result.add(current);
        }

        return result;
    }



    public static int getModMd5(String value){
        BigInteger bi = getMd5(value);
        BigInteger m = new BigInteger(BIG_PRIME_NUMBER);
        BigInteger moded = bi.mod(m);

        int i = Integer.parseInt(moded.toString());

        return i;
    }


    private static BigInteger getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
            return no;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    /*get ip*/

    public static String getIP(){
        try{
            return Inet4Address.getLocalHost().getHostAddress();
        }catch (Exception e){
            System.out.println("ip not available");
        }

        return  "null";


    }
    /**
     * Used for debugging
     */
    public static void debug(String msg){
        if (DEBUG) System.out.println(msg);
    }


    public static class Pair<T1, T2>{
        public T1 item1;
        public T2 item2;

        public Pair(T1 item1, T2 item2) {
            this.item1 = item1;
            this.item2 = item2;
        }
    }



    public static long getFileSizeBytes(Song song){
        File f = new File(song.os_location);
        return f.length();
    }

    public static List<ArtistCollection> getArtistCollection(int index){
        ArrayList<ArtistCollection> result = new ArrayList<>();
        String path = "src\\dataset\\";
        String[] artistNames = {"Adele\\", "Eminem\\", "JamesBlunt\\", "Kaleo\\", "Nickelback\\", "OneRepublic\\", "Pink\\", "U2\\"};

        switch (index){
            case 1:
                ArtistCollection adele = new ArtistCollection("Adele", "British, born on 05/05/1988",
                        "https://jenny.gr/sites/default/files/styles/article_large/public/articles/2016/08/ADELE-4.jpg?itok=aQ35UlqL",
                        "pop");
                adele.addSong(new Song(adele, "don't_you_remember", path + artistNames[0] + "don't_you_remember.mp3", "", 242));
                adele.addSong(new Song(adele, "one_and_only", path + artistNames[0] + "one_and_only.mp3", "", 345));
                result.add(adele);

                ArtistCollection eminem = new ArtistCollection("Eminem", "American, born on 17/10/1972",
                        "https://www.iefimerida.gr/sites/default/files/styles/horizontal_rectangle_mob/public/archive-files/eminem_708x320.jpg?itok=mDvRNncq",
                        "rap");
                eminem.addSong(new Song(eminem, "lose_yourself", path + artistNames[1] + "lose_yourself.mp3", "", 323));
                eminem.addSong(new Song(eminem, "not_afraid", path + artistNames[1] + "not_afraid.mp3", "", 258));
                result.add(eminem);
                break;
            case 2:
                ArtistCollection jamesBlunt = new ArtistCollection("James Blunt", "British, born on 22/02/1974",
                        "https://media.resources.festicket.com/www/artists/JamesBlunt_New.jpg",
                        "mix of rock, jazz and soul");
//                jamesBlunt.addSong(new Song(jamesBlunt, "don't_give_me_those_eyes", path + artistNames[2] + "don't_give_me_those_eyes.mp3", "", 224));
                jamesBlunt.addSong(new Song(jamesBlunt, "don't_give_me_those_eyes", "src/dataset/JamesBlunt/don't_give_me_those_eyes.mp3", "", 224));
                jamesBlunt.addSong(new Song(jamesBlunt, "goodbye_my_lover", path + artistNames[2] + "goodbye_my_lover.mp3", "", 237));
                result.add(jamesBlunt);

                ArtistCollection kaleo = new ArtistCollection("Kaleo", "Icelandic band",
                        "https://www.rockrooster.gr/wp-content/uploads/2016/03/Kaleo.jpg",
                        "rock");
                kaleo.addSong(new Song(kaleo, "break_my_baby", path + artistNames[3] + "break_my_baby.mp3", "", 262));
                kaleo.addSong(new Song(kaleo, "i_want_more", path + artistNames[3] + "i_want_more.mp3", "", 210));
                result.add(kaleo);
                break;
            case 3:
                ArtistCollection nickelback = new ArtistCollection("Nickelback", "Canadian band",
                        "https://townsquare.media/site/366/files/2017/06/Nickelback-2.jpg?w=980&q=75",
                        "hard rock");
                nickelback.addSong(new Song(nickelback, "far_away", path + artistNames[4] + "far_away.mp3", "", 238));
                nickelback.addSong(new Song(nickelback, "lullaby", path + artistNames[4] + "lullaby.mp3", "", 224));
                result.add(nickelback);

                ArtistCollection oneRepublic = new ArtistCollection("Adele", "American band",
                        "https://e-cdns-images.dzcdn.net/images/artist/18d1bd9156b54eb54b68cea319d6f1d1/500x500.jpg",
                        "pop rock");
                oneRepublic.addSong(new Song(oneRepublic, "if_i_lose_myself", path + artistNames[5] + "if_i_lose_myself.mp3", "", 297));
                oneRepublic.addSong(new Song(oneRepublic, "no_vacancy", path + artistNames[5] + "no_vacancy.mp3", "", 223));
                result.add(oneRepublic);
                break;

            case 4:
                ArtistCollection pink = new ArtistCollection("Pink", "American, born on 08/09/1979",
                        "https://i.pinimg.com/originals/35/d7/d7/35d7d785750e3a868c40ee9eba24e66a.jpg",
                        "pop - pop rock");
                pink.addSong(new Song(pink, "sober", path + artistNames[6] + "sober.mp3", "", 252));
                pink.addSong(new Song(pink, "try", path + artistNames[6] + "try.mp3", "", 249));
                result.add(pink);

                ArtistCollection u2 = new ArtistCollection("U2", "Irish band",
                        "https://images.rocking.gr/afieromata/2010/418x249/u2-cover.jpg",
                        "alternative rock");
                u2.addSong(new Song(u2, "beautiful_day", path + artistNames[7] + "beautiful_day.mp3", "", 248));
                u2.addSong(new Song(u2, "one", path + artistNames[7] + "one.mp3", "", 278));
                u2.addSong(new Song(u2, "with_or_without_you", path + artistNames[7] + "with_or_without_you.mp3", "", 295));
                result.add(u2);
                break;
            default:
                Util.debug("Datasource has no more artist to give");
        }

        return result;
    }


}
