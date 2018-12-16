import java.io.IOException;
import java.util.Scanner;

public class Top_k {
	public static void main(String[] args) {
	  int k = 0;
	    try{
             k = Integer.parseInt(args[0]);
       }catch (NumberFormatException nfe){
	       System.out.println("K is not a number");
		   System.exit(0);
	   }
	   
		
		Song[] songArray = new Song[0];
		try {
			songArray = LoadSongs.loadSongFromTxt(args[1]);
		} catch (IOException e) {
			System.err.println("File not found");
			System.exit(0);
		}
		int numberOfSongs = LoadSongs.getNumberOfSongs();
		LoadSongs.resetNumberOfSongs();
		if(numberOfSongs > 0){
			SortUtil.mergeSort(songArray, 0, numberOfSongs - 1 );
		}else{
			System.out.println("There are no songs in the file");
			System.exit(0);
		}

		if(k>0){
			printKTopTitles(songArray, k, numberOfSongs);
		}else{
			System.out.println("Invalid input");
		}
	}


	private static void printKTopTitles(Song[] songArray,int k, int size) {
		if(k>size) {
			System.out.println("There are not these many songs");
		}else{
			System.out.println("The top " + k + " songs are : ");
			for(int i=1;i<=k;i++) {
				System.out.print(i +"." );
				System.out.println(songArray[i-1].getTitle());
			}
		}
	}
}