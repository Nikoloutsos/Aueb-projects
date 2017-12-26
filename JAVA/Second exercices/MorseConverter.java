import acm.program.*;
/*Exercice 3 Methods used from : https://docs.oracle.com/javase/7/docs/api/java/lang/String.html
								 https://docs.oracle.com/javase/tutorial/java/data/characters.html */

public class MorseConverter extends Program{
	//This Morse converter supports only alpha characters.
	private final String[] dottie = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
                "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.",
                "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
                "-.--", "--.."}; //Do not be modified.
	public void run(){
		convert_to_morse_and_print_it(readLine("Type a string: "));
	}
	private boolean is_a_letter(Character ch){
		return(
		(( (int)ch >= "A".codePointAt(0) && (int)ch <= "Z".codePointAt(0)) || //DOCS: It returns true if char is between A-Z a-z.
		( (int)ch >= "a".codePointAt(0) && (int)ch <= "z".codePointAt(0)
		) ? true : false));
	}
	
	public void convert_to_morse_and_print_it(String raw_str){ 
		for(int i=0;i < raw_str.length() ; i++){
			if (is_a_letter(raw_str.charAt(i))){
				if ((int)raw_str.charAt(i) <= "Z".codePointAt(0)){
					print(dottie[(int)raw_str.charAt(i) - "A".codePointAt(0)]);
				}else{
					print(dottie[(int)raw_str.charAt(i) - "a".codePointAt(0)]);
			}
		}	else if (raw_str.charAt(i)==' '){ //(int)' ' is the UnicodeCodePoint of the space character.
				println(); //skip a line. This is what the exercice wants.
		}	else{
				print(raw_str.charAt(i));
			}
		}
	}
}

