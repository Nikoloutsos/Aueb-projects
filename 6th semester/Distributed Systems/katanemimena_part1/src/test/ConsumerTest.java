package test;

import model.ArtistCollection;
import model.Song;
import org.junit.Test;
import util.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.*;

public class ConsumerTest {

    @Test
    public void should_music_sent_not_modified() throws Exception {
        Socket socket = new Socket("192.168.1.44", 3001);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String requestString = "{ \"action\" : \"GET_MUSIC\", \"artist\" : \"James Blunt\", \"song_name\" : \"don't_give_me_those_eyes\"}";
        out.println(requestString);

        assertTrue(!in.readLine().isEmpty());

        ArtistCollection artistCollection = new ArtistCollection("aa", "aa", "aa", "aa");
        byte[] data = Util.loadSongFromDiskToRam(new Song(artistCollection, "name", "src/dataset/JamesBlunt/don't_give_me_those_eyes.mp3", "", 111));

        int index = 0;

        while (index < data.length){
            int a = socket.getInputStream().read();
            byte b = (byte) a;

//            System.out.println( data[index]+ " | " + b);
            assertTrue(data[index] == b);

            index++;
            //System.out.println(index);
        }
    }


    @Test
    public void should_always_get_response() throws Exception{
        Socket socket = new Socket("192.168.1.7", 4000);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String requestString = "{ \"action\" : \"GET_MUSIC\", \"artist\" : \"adele\", \"song_name\" : \"rolling_in_the_deep\", \"auth\" : \"frgruifrwbfb2yu4fv2y3hyfv74gfget\" }".replaceAll("\n", "").replaceAll("\t", "");
        out.println(requestString);

        assertTrue(!in.readLine().isEmpty());
    }


}