import acm.program.*;
import acm.util.*;
class ImplementRandomGenImproved extends Program{
	public void run(){
		println(rand_powers_of_two(0)); //unit testing for rand_powers_of_two method! 
		println(rand_powers_of_two(100,3170122)); 
		println("---------------------------------");
		println(rand_square(100)); //unit testing for rand_squares method! 
		println(rand_square(10,15)); 
		println("---------------------------------");
		println(rand_prime(152,1000000000)); //unit testing for rand_primes method! https://goo.gl/DDsiuN --> Check it this is a prime number
		println(rand_prime(1,1)); 
		println(rand_prime(100,50)); 
		println(rand_prime(10,12)); 
		println("---------------------------------");
		println(rand_fibonacci(1,1)); 
		println(rand_fibonacci(6,1000000000)); 
	}
}