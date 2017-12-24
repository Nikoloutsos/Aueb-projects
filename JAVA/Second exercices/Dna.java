import acm.program.*;
/*Exercice 2 Methods used from : https://docs.oracle.com/javase/7/docs/api/java/lang/StringBuffer.html
								 https://docs.oracle.com/javase/7/docs/api/java/lang/String.html
								 */

public class Dna extends Program{
	public void run(){
		println(findFirstMatchingPosition("aTt" , "ATAGGACTaAtaa")); //Unit testing 1. It should display 7
		println(findFirstMatchingPosition("" , "AAAAAA")); //Unit testing 2. It should display -1
		println(findFirstMatchingPosition("AATTAATT" , "TTAATTACT")); //Unit testing 3. It should display -1
	}
	public int findFirstMatchingPosition (String shortDNA, String longDNA){
		if (shortDNA.length()==0 || longDNA.length()==0 || longDNA.length() < shortDNA.length()) return(-1);
		shortDNA = shortDNA.toUpperCase();
		StringBuffer shortDNA_to_be_finded = new StringBuffer(""); //init the StringBuffer Object.
		for(int i=0;i<shortDNA.length();i++){
			if 		(shortDNA.charAt(i)=='A')shortDNA_to_be_finded.append('T'); //Convert the DNA to what we wind to find.(e.x "Aatt"-->"TTAA")
			else if (shortDNA.charAt(i)=='T')shortDNA_to_be_finded.append('A');
			else if (shortDNA.charAt(i)=='C')shortDNA_to_be_finded.append('C');
			else if (shortDNA.charAt(i)=='G')shortDNA_to_be_finded.append('G');
			else return(-1); // If the user has putted something like "AAW" by mistake, -1 will be returned.
		}
		return(longDNA.toUpperCase().indexOf( shortDNA_to_be_finded.toString() ));
	}	
}