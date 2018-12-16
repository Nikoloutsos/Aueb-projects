import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TagMatching {
    static StringStack<String> st=new StringStackImplementation<>();
	public static void main(String[] args) {
		boolean tagStarted=false;
		BufferedReader br = null;
		String line;
		StringBuilder sb= new StringBuilder();
		boolean flag=false;
		
		
		try {
		    FileReader fr = new FileReader(args[0]);
		    br = new BufferedReader(fr);
		  char temp;
		  char currentChar;
		   
		    while ((line = br.readLine()) != null && !flag) {
		    		for(int i=0;i<line.length();i++) {
		    			currentChar=line.charAt(i);
		    			if(currentChar=='<') {
		    				tagStarted=true;
		    				sb.setLength(0);
		    			}
		    			else if(tagStarted && currentChar!='>') {
		    				sb.append(currentChar);
		    			}
		    			else if(currentChar=='>' && tagStarted) {
		    				tagStarted=false;
		    				temp=sb.toString().trim().charAt(0);
		    				if(temp=='/') {
		    					if(st.peek().equalsIgnoreCase(sb.toString().trim().substring(1))) {
		    						st.pop();
		    						sb.setLength(0);
		    					}else {
		    						flag=true;
		    					     break;
		    					}
		    					   
		    					     
		    				}else {
		    					st.push(sb.toString().trim());
		    					sb.setLength(0);
		    				}
		    			}
		    		}
		   }

		   
		
		
		}catch (IOException e) {
				System.out.println("file not found");
				flag=true;

	}catch (NoSuchElementException l ) {
		flag=true;
	} finally {
		
		try {
			br.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
    if(flag || !st.isEmpty()) {
    	System.out.println("NOT Matching");
    }else {
    	System.out.println("Matching");
    }
  }
}
