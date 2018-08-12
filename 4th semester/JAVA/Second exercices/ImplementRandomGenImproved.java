import acm.program.*;
public class ImplementRandomGenImproved extends Program{
	public void run(){	
		RandomGeneratorImproved rgen = new RandomGeneratorImproved();
		System.out.println("GENERATE RANDOM NUMBERS.Please select one type:");
		int response=0;
		while (response != -1){
			System.out.println("----------------------------------------");
			System.out.println(" 1->Random powers of two. \n 2->Random square numbers \n 3->Random prime \n 4->Random fibonacci");
			System.out.println("-1->EXIT.");
			response = readInt("-->");
			while(response!=1 && response!=2 && response!=3 && response!=4 && response!=-1){
				System.out.println("Something unexpected happened.Please try again.");
				response = readInt("-->");
			}
			if(response==-1) break;
			int low_bound = readInt("Lower bound: ");
			int high_bound = readInt("Higher bound: ");
			switch(response){
				case 1: 
					System.out.println(" OUTPUT ---> "+rgen.rand_powers_of_two(low_bound,high_bound));
					break;
				case 2:
					System.out.println(" OUTPUT ---> "+rgen.rand_square(low_bound,high_bound));
					break;
				case 3:
					System.out.println(" OUTPUT ---> "+rgen.rand_prime(low_bound,high_bound));
					break;
				case 4:
					System.out.println(" OUTPUT ---> "+rgen.rand_fibonacci(low_bound,high_bound));
					break;
			}
		}
	
} }