package model;

import javazoom.jl.player.Player;
import util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception {
        System.out.println("aa");

        Util u = new Util();
        List<ArtistCollection> artistCollection = Util.getArtistCollection(2);


        String cwd = new File("").getAbsolutePath();

        System.out.println(cwd);

//        ServerSocket ss = new ServerSocket(1111);
//
//
//        Socket accept = ss.accept();
//
//
//        ArtistCollection artistCollection = Util.getMockArtistCollection().get(0);
//
//        byte[] bytes = Util.loadSongFromDiskToRam(artistCollection.songs.get(0));
//        List<byte[]> bytesChunks = Util.chunkifyFile(bytes);
//
//        OutputStream out = accept.getOutputStream();
//
//
//        for (byte[] chunk : bytesChunks) {
//            out.write(chunk);
//            out.flush();
//        }


//
//        Player player2 = new Player(is);
//        player2.play();
    }
}
