
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetBenefit {
	static IntQueue<Integer> queueQuantity= new IntQueueImplementation<Integer>();
	static IntQueue<Integer> queuePrice= new IntQueueImplementation<Integer>();
	static int revenue = 0;
	
	public static void main(String[] args) {
		Integer temp[]=new Integer[2];
		BufferedReader br = null;
		Pattern p;
		Matcher m; 
		
		try {
			FileReader fr = new FileReader(args[0]);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			      p=Pattern.compile("[0-9]+"); 
			      m=p.matcher(line);
			      for(int i=0;m.find(); i++ ){
			    	  temp[i] = Integer.parseInt(m.group());
			      }
			      if(line.matches("[ ]*[bB][uU][yY][ ]+[0-9]+[ ][pP][rR][iI][cC][eE][ ]+[0-9]+[ ]*")){
			    	  queueQuantity.put(temp[0]);
			    	  queuePrice.put(temp[1]); 
			    	  
			      }else if(line.matches("[ ]*[sS][eE][lL][lL][ ]+[0-9]+[ ]+[pP][rR][iI][cC][eE][ ]+[0-9]+[ ]*")){
			    	  sellStocks(temp[0], temp[1]); 
			      }
			}
			
		}catch (IOException e) {
			System.out.println("file not found");
		}finally {
		
			try {
				br.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		System.out.println("Revenue is --->> " + revenue+ "$");
	}
	
	/**
	 * It sells stocks and changes the revenue variable accordingly.
	 * @return Nothing
	 * @
	 */
	private static void sellStocks(int quantity, int price) {
		Node<Integer> temp;
		try {
			while(quantity > 0) {
				if(quantity >= queueQuantity.peek()) {
					quantity -= queueQuantity.peek();
					revenue += queueQuantity.get()*(price - queuePrice.get());
				} else {
					temp = ((IntQueueImplementation<Integer>) queueQuantity).getHead();
					temp.data -= quantity;
					revenue += quantity*(price - queuePrice.peek());
					quantity =0;
				}
			}
		}catch (NoSuchElementException e) {
			System.out.println("User don't have more stocks to sell.");
		}
	}
}
