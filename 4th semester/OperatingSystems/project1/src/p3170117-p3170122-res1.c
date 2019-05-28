#include "p3170117-p3170122-res1.h"



int book_seats(int number_of_seats, int cid);
void releaseBookedSeats(int cid);
void assert_successful_mutex_action(int response_code);


//Mutexes
pthread_mutex_t mutex_no_available_phones= PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_balance = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_seats = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_console = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_total_waiting_time = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_total_service_time = PTHREAD_MUTEX_INITIALIZER;

//Condition
pthread_cond_t cond_no_available_phones = PTHREAD_COND_INITIALIZER;

//Variables

int no_available_phones;
int * seats;
double total_waiting_time;
double total_service_time;
double balance;
unsigned int seed;


void * execute(void * customer_id)
{
	int response_code;
	int flag = flag_ok;
	int cid =  *(int *)customer_id;
	struct timespec customer_start;
	struct timespec	customer_serviced;
	struct timespec customer_finish;
	
	clock_gettime(CLOCK_REALTIME, &customer_start); //Track starting time.
	
	//printf("Customer: %d \n",cid);
	
	response_code = pthread_mutex_lock(&mutex_no_available_phones);
	assert_successful_mutex_action(response_code);
	
	while(no_available_phones <= 0)
	{
	pthread_cond_wait(&cond_no_available_phones ,&mutex_no_available_phones);
	}
	--no_available_phones;
	clock_gettime(CLOCK_REALTIME, &customer_serviced); //Track customer connected to phone call.
	
	response_code = pthread_mutex_unlock(&mutex_no_available_phones); //User is talking on the phone from now on.
	assert_successful_mutex_action(response_code);
	//printf("Start cid: %d \n", cid);
        

	 //start random booking
	int random_no_seats = rand_r(&seed) % no_seat_high + no_seat_low;
	int random_time = rand_r(&seed) % time_seat_high + time_seat_low;
	sleep(random_time);
	
	//Start transaction
	response_code = pthread_mutex_lock(&mutex_seats);
	assert_successful_mutex_action(response_code);

	int result = book_seats(random_no_seats, cid);
	response_code = pthread_mutex_unlock(&mutex_seats);
	assert_successful_mutex_action(response_code);
	if(result == flag_not_available_seats){
		//There are not seats
		flag = flag_not_available_seats ;
	}else if(result == flag_sold_out )
	{
		flag = flag_sold_out; 
	}else
	{
		//Start paying
		bool successfull_transaction = rand_r(&seed) % 100 / 100.0f <= p_card_success;
		
		if(!successfull_transaction)
		{//Print user didn't pay
			flag =flag_customer_did_not_pay ;
			
			response_code = pthread_mutex_lock(&mutex_seats);
			assert_successful_mutex_action(response_code);
	  		releaseBookedSeats(cid);
			response_code = pthread_mutex_unlock(&mutex_seats);
			assert_successful_mutex_action(response_code);
			
		}
		else
		{
			response_code = pthread_mutex_lock(&mutex_balance);
			assert_successful_mutex_action(response_code);
			balance += random_no_seats * cost_per_seat;
			response_code = pthread_mutex_unlock(&mutex_balance);
			assert_successful_mutex_action(response_code);
		}
	}

	
    //Release telephone , customer finished
	response_code = pthread_mutex_lock(&mutex_no_available_phones);
	assert_successful_mutex_action(response_code);
	++no_available_phones;
	pthread_cond_signal(&cond_no_available_phones);
	response_code = pthread_mutex_unlock(&mutex_no_available_phones);
	assert_successful_mutex_action(response_code);
	
	clock_gettime(CLOCK_REALTIME, &customer_finish);
	
	//Print transaction info
	response_code = pthread_mutex_lock(&mutex_console);
	assert_successful_mutex_action(response_code);
	if(flag == flag_not_available_seats)
	{       
		 print_fail_no_seats_msg();
	}
	else if(flag == flag_customer_did_not_pay)
	{
		 print_fail_no_pay_msg();
	}
	else if(flag == flag_sold_out)
	{

		print_fail_sold_out_msg();	
	}
	else
	{
	     print_success_transaction_msg(cid, seats, random_no_seats);
	}
	response_code = pthread_mutex_unlock(&mutex_console);
	assert_successful_mutex_action(response_code);

	//update total_waiting_time
	response_code = pthread_mutex_lock(&mutex_total_waiting_time);
	assert_successful_mutex_action(response_code);
	total_waiting_time += customer_serviced.tv_sec - customer_start.tv_sec;
	response_code = pthread_mutex_unlock(&mutex_total_waiting_time);
	assert_successful_mutex_action(response_code);

	//update total_service_time
	response_code = pthread_mutex_lock(&mutex_total_service_time);
	assert_successful_mutex_action(response_code);
	total_service_time += customer_finish.tv_sec - customer_serviced.tv_sec;
	response_code = pthread_mutex_unlock(&mutex_total_service_time);
	assert_successful_mutex_action(response_code);

	pthread_exit(NULL);
}




int main(int argc, char *argv[])
{
	
	if (argc != 3) {
		printf("ERROR: Το προγραμμα πρεπει να παιρνει 2 arguments(number of clients--> int, seed --> int)\n\n\n");
		exit(-1);
	}
	
	int no_customers = atoi(argv[1]);
   	if (no_customers <= 0) {
		printf("ERROR: Ο αριθμός των πελατών πρεπει να ειναι ακεραιος θετικός\n");
		exit(-1);
	}

    total_service_time = 0;
    total_waiting_time = 0;
	no_available_phones = no_telephones;
	seed = atoi(argv[2]);

	printf("\n \nΚαλωσήρθατε στο θέατρο AUEB(simulation)\nΕπιλέξατε %d πελατες και seed %d \n \n \n", no_customers, seed);
	

	//Initializing seats
	seats = malloc(sizeof(int)*no_seats);
	for(int i=0;i<no_seats;++i)
	{
		seats[i] = -1;
	}

	
	/*Initializing customers id*/
	int customer_id[no_customers];
	for(int i=0;i<no_customers;++i)
	{
		customer_id[i] = i;
	}

	/*Create threads*/
	pthread_t tid[no_customers];
	for(int i=0; i<no_customers; ++i)
	{
		pthread_create(&tid[i], NULL, &execute, &customer_id[i]);
	}



	/*Wait all threads to finish*/
	for(int i=0; i<no_customers; ++i)
	{
		pthread_join(tid[i], NULL);
	}

	/*Print results*/
	
	//if a seat is not booked the result will be -1


	printf("Πλάνο θέσεων: \n \n");
	for (int i = 0; i < no_seats; i++) 
	{	if(seats[i] != -1)
		printf("θέση %d πελάτης %d \n", i, seats[i]);
		else
		printf("θέση %d κανένας \n", i);
	}
	printf("\n");
	printf("Τα συνολικά έσοδα είναι: %f € \n", balance);
	
	printf("Ο μέσος χρόνος αναμονής είναι: %f \n", total_waiting_time/no_customers);
	
	printf("Ο μέσος χρόνος εξυπηρέτησης είναι: %f \n", total_service_time / no_customers);




	
	return 0;
}




//Checks whether there are any available seets(not necesserily contiguous one) and and replace their value with cid otherwise returns an error message from Messages.c.
int book_seats(int number_of_seats, int cid)
{
	int sum = 0;
	for(int i = 0; i<no_seats;i++)
	{
		if(seats[i] == -1) sum++;

	}
	if(sum == 0) return flag_sold_out ; // full house
	if(sum >= number_of_seats){
		sum = 0;
		for(int i = 0; i<no_seats;i++)
		{
			if(seats[i] == -1)
			{
		    		seats[i] = cid;
		    		sum++;
	 		}
		   
			if(sum == number_of_seats) return seats_found_success ;
		}
	}else
	{
		return flag_not_available_seats ;
	}
}

//release all the seats booked from customer with id: cid 
void releaseBookedSeats(int cid)
{
	for(int i=0;i<no_seats;i++)
	{
		if(seats[i] == cid)   seats[i] = -1;
	}
}

//Makes sure that mutexe's lock and unlock works as expected.(OS handle this)
void assert_successful_mutex_action(int response_code)
{
	if (response_code != 0) 
    {	
		printf("Σφάλμα στο κλείδωμα/ξεκλείδωμα του mutex με κωδικο σφαλματος %d\n", response_code);
		pthread_exit(&response_code);
	}
}
