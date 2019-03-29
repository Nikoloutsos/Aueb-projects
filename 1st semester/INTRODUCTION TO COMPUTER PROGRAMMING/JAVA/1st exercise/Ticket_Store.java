import acm.program.*; //importing packages.
/* Exercice 3 
		Buy tickets */
public class Ticket_Store extends Program {
   public void run() {
	   println("WELCOME TO THE TICKET STORE");
	   change(insert_coins());
}

   private void change(double money){
	   /*DOCS: It returns null and it prints in terminal the change.
	   */
		if (money>=TICKET_COST){
			money-=TICKET_COST;
			println("Here is your ticket.");
			}else{
			println("Not enough money to buy the ticket.");}
		while (money>0){
			if (money>=5){
				money-=5;
				println("You have change: 5 euros");
			}else if(money>=2){
				money-=2;
				println("You have change: 2 euros") ;
			}else if(money>=1){
					money-=1;
					println("You have change: 1 euros");
			}else if(money>=0.5){
				money-=0.5;
				println("You have change: 0.5 euros");
			}else if(money>=0.2){
				money-=0.2;
				println("You have change: 0.2 euros");
			}else if(money>=0.1){
				money-=0.1;
				println("You have change: 0.1 euros");
			}}}
   
   
   private double insert_coins(){
	   /*DOCS: It is interacting with the user and it asks him to insert
	   coins and it returns the double type sum of those . */
	   println(" ------------------------------ "); //decoration
	   println("| INFO :  Give -1 for exiting  |");
	   println(" ------------------------------ ");
	   double accumulator=0;
	   double coin;
	   while(true){
			while (true){
				coin = readDouble("Insert coin: ");
				if(coin==-1) return accumulator;
				if ((coin==5)||(coin==2)||(coin==1)||(coin==0.5)||(coin==0.2)||(coin==0.1)){
					break;
				}
				else{
					println("Sorry but you have inserted an illegal coin.");
					println("Please try again..");
					}
			}
		accumulator+=coin ;
   }
}
	private static final double TICKET_COST=1.2; //Feel free to set the price in whatever you want.
}
