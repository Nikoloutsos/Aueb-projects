import acm.program.*;
public class ImplementRandomGenImproved extends Program{
	public static void main(String args[]){
		System.out.println("Welcome. /n Please select one of number of the following. /n 1-->random power of two  ");
		System.out.println("2-->random square /n 3-->random prime /n 4-->random fibonacci");
		int users_reply = readInt("-->")
		while(user_reply!=1 && user_reply!=2 && user_reply!=3 && user_reply!=4){
			System.out.println("Something went wrong.Please try again.");
			users_reply = readLine("-->");
				
		
}
/*
		System.out.println(RandomGeneratorImproved.rand_powers_of_two(0)); //unit testing for rand_powers_of_two method! 
		System.out.println(RandomGeneratorImproved.rand_powers_of_two(100,3170122)); 
		System.out.println("---------------------------------");
		System.out.println(RandomGeneratorImproved.rand_square(100)); //unit testing for rand_squares method! 
		System.out.println(RandomGeneratorImproved.rand_square(10,15)); 
		System.out.println("---------------------------------");
		System.out.println(RandomGeneratorImproved.rand_prime(152,1000000)); //unit testing for rand_primes method! https://goo.gl/DDsiuN --> Check it this is a prime number
		System.out.println(RandomGeneratorImproved.rand_prime(1,1)); 
		System.out.println(RandomGeneratorImproved.rand_prime(100,50)); 
		System.out.println(RandomGeneratorImproved.rand_prime(10,12)); 
		System.out.println("---------------------------------");
		System.out.println(RandomGeneratorImproved.rand_fibonacci(6,1000000000)); //unit testing for rand_primes method!
		System.out.println(RandomGeneratorImproved.rand_fibonacci(8,9)); 
		System.out.println(RandomGeneratorImproved.rand_fibonacci(10000000)); 
		// Feel free to use it. Just keep in mind those methods are static.
	*/
}}