/*Exercice 1 */
class RandomGeneratorImproved extends acm.util.RandomGenerator{
	private static acm.util.RandomGenerator rgen = new acm.util.RandomGenerator();
	public RandomGeneratorImproved(){ 
		super(); //Call the parent constructor
	}
	
	public int rand_powers_of_two(int min, int max){
		if(min<=0)min=1; 
		if((min<=0 || max < min)) {
			System.out.println("This range do not have any number of this type.");
			return(-1);
		}
		min = (int)java.lang.Math.ceil((java.lang.Math.log(min)/java.lang.Math.log(2))); // Change-of-Logarithmic_Base Formula mathematics.
		max = (int)(java.lang.Math.log(max)/java.lang.Math.log(2)); //min is rounded up and max is rounded down.
		if((max < min)) {
			System.out.println("This range do not have any number of this type.");
			return(-1);
		}
		return((int)java.lang.Math.pow(2, rgen.nextInt(min, max)));
	} 
	public int rand_powers_of_two(int max){
		return(rand_powers_of_two(1,max)); //Alternative constructor.
	} 
	
	public int rand_square(int min, int max){
		if(min<=0) min=1;
		if((min<0 || max < min)){
			System.out.println("This range do not have any number of this type.");
			return(-1);
		}
		if (min==0) min=1 ; // Because the exercice do not want to produce 0, as output of 0^2.
		min = (int)java.lang.Math.ceil(java.lang.Math.sqrt(min));
		max = (int)java.lang.Math.sqrt(max); //min is rounded up and max is rounded down.
		if((max < min)){
			System.out.println("This range do not have any number of this type.");
			return(-1);
		}
		return((int)java.lang.Math.pow(rgen.nextInt(min, max), 2));
	} 
	public int rand_square(int max){
		return(rand_square(1, max)); //Alternative constructor.
	}
	
	public int rand_prime(int min, int max){
		if(min<=0)min=1;
		if((min<=0 || max < min)){
			System.out.println("This range do not have any number of this type.");
			return(-1);
		}
		if (hasAtLeastOnePrime(min,max)){ // It checks if at least 1 prime exists in the interval [min,max].
		while(true){
			int temp = rgen.nextInt(min,max);
			if(checkIfPrime(temp))return(temp); } //BRUTEFORCE
		}else{
			System.out.println("This range do not have any number of this type.");
			return(-1);
		}
		}
	public int rand_prime(int max){
		return(rand_prime(1,max)); //Alternative constructor
	} 
	
	public int rand_fibonacci(int min, int max){
		if(min<=0)min=1;
		if(max<min || max<0 || min<0){
			System.out.println("This range do not have any number of this type.");
			return(-1);
		}
		int n1 = 1;
		int n2 = 1;
		int n3 = 2;
	if(min==1){
		int accumulator = 0; //It counts how many fib numb are in interval [1,max]
		while(true){
			n3 = n2 + n1;
			n1 = n2;
			n2 = n3;
			if(n2>max)break;
			accumulator++;
		}
		int rand_num = rgen.nextInt(0,accumulator);
		n1 = 1;
		n2 = 1;
		for(int i=1;i<=rand_num;i++){
			n3 = n2 + n1;
			n1 = n2;
			n2 = n3;
		}
	}else{
		while(true){ // It finds the nearest fib numbers to min.
			n3 = n2 + n1;
			if (n3>=min)break;
			n1 = n2;
			n2 = n3;
		}
		int lower1 = n1;
		int lower2 = n2;
		int accumulator=0;
		while(true){ //counts the fib in the interval [min,max]
			n3 = n2 + n1;
			if (n3>max)break;
			n1 = n2;
			n2 = n3;
			accumulator++;
		}
		if (accumulator==0){
			System.out.println("This range do not have any number of this type.");
			return(-1);
		}
		int rand_num = rgen.nextInt(1,accumulator);
		for(int i=1;i<=rand_num;i++){
			n3 = lower1 + lower2;
			lower1 = lower2;
			lower2 = n3;
		}
		return(lower2);
	}
	return(n2);
	} 
	public int rand_fibonacci(int max){
		return(rand_fibonacci(1,max));
	}
	
	private static boolean checkIfPrime(int num){
		double sqrt = java.lang.Math.sqrt(num);
		for(int i=2;i<=sqrt;i++){
			if(num%i==0) return(false);
		}
		return(true);
	}
	private static boolean hasAtLeastOnePrime(int min, int max){ // It checks if at least 1 prime exists in the interval [min,max].
		for(int i=min;i<=max;i++){
			if(checkIfPrime(i))return (true);}
		return (false);
		}
	}