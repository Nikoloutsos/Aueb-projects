#include "p3170117-p3170122-res2.h"



int book_seats(int * zone,  int total_seats, int number_of_seats, int cid);
void releaseBookedSeats(int * zone, int total_seats, int cid);
void assert_successful_mutex_action(int response_code);


//Mutexes
pthread_mutex_t mutex_no_available_phones= PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_no_available_cashiers = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_balance = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_console = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_total_waiting_time = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_total_service_time = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_seats_zoneA= PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_seats_zoneB = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_seats_zoneC = PTHREAD_MUTEX_INITIALIZER;

pthread_mutex_t mutex_transactions_no_sold_out_case= PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_transactions_no_adequate_seats_case = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_transactions_no_succesfull_trans = PTHREAD_MUTEX_INITIALIZER;



//Condition
pthread_cond_t cond_no_available_phones = PTHREAD_COND_INITIALIZER;
pthread_cond_t cond_no_available_cashier = PTHREAD_COND_INITIALIZER;

//Global Variables
int no_sold_out_case; //statistics
int no_adequate_seats_case;
int no_succesfull_trans;

int no_available_phones;
int no_available_cashiers;
int * seats_zoneA;
int * seats_zoneB;
int * seats_zoneC;

double total_waiting_time;
double total_service_time;
long total_transactions;
double balance;
unsigned int seed;



void * execute(void * customer_id)
{
	int response_code;
	int flag = flag_ok;
	int cid =  *(int *)customer_id;
	struct timespec customer_start;
	struct timespec	customer_serviced;
	struct timespec customer_telephone_finish;
	struct timespec customer_cashier_start;
	struct timespec customer_finish;


	clock_gettime(CLOCK_REALTIME, &customer_start); //Track starting time.
	
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
        
	 //start random booking
	int random_no_seats = rand_r(&seed) % no_seat_high + no_seat_low;
	int random_time = rand_r(&seed) % time_seat_high + time_seat_low;
	sleep(random_time);

	//todo user selects a random zone.
	float random_zone = (rand_r(&seed) % 100)/100.0f;
	
	int * zone;
	pthread_mutex_t zone_mutex;
	double cost_zone_per_seat;
	int total_seats_in_zone;

	if (random_zone <= p_zoneA){
		zone = seats_zoneA;
		zone_mutex = mutex_seats_zoneA;
		cost_zone_per_seat = cost_per_seat_zoneA;
		total_seats_in_zone = no_seats * no_rows_zoneA;

	}else if(random_zone <= p_zoneA + p_zoneB){
		zone = seats_zoneB;
		zone_mutex = mutex_seats_zoneB;
		cost_zone_per_seat = cost_per_seat_zoneB;
		total_seats_in_zone = no_seats * no_rows_zoneB;

	}else{
		zone = seats_zoneC;
		zone_mutex = mutex_seats_zoneC;
		cost_zone_per_seat = cost_per_seat_zoneC;
		total_seats_in_zone = no_seats * no_rows_zoneC;
	}

	response_code = pthread_mutex_lock(&zone_mutex);
	assert_successful_mutex_action(response_code);
	int result = book_seats(zone, total_seats_in_zone, random_no_seats, cid); 
	response_code = pthread_mutex_unlock(&zone_mutex);
	assert_successful_mutex_action(response_code);


	response_code = pthread_mutex_lock(&mutex_no_available_phones);
	assert_successful_mutex_action(response_code);
	++no_available_phones;       //Release one telephone
	pthread_cond_signal(&cond_no_available_phones);
	response_code = pthread_mutex_unlock(&mutex_no_available_phones);
	assert_successful_mutex_action(response_code);
	clock_gettime(CLOCK_REALTIME, &customer_telephone_finish);
	clock_gettime(CLOCK_REALTIME, &customer_finish); //Track in case user stoped imidietly.
	clock_gettime(CLOCK_REALTIME, &customer_cashier_start);
	

	if(result == flag_not_available_seats){
		//There are not seats
		flag = flag_not_available_seats;
		
		response_code = pthread_mutex_lock(&mutex_transactions_no_adequate_seats_case);
		assert_successful_mutex_action(response_code);
		no_adequate_seats_case++;
		response_code = pthread_mutex_unlock(&mutex_transactions_no_adequate_seats_case);
		assert_successful_mutex_action(response_code);
		
		
		
	}else if(result == flag_sold_out)
	{
		flag = flag_sold_out; 
		response_code = pthread_mutex_lock(&mutex_transactions_no_sold_out_case);
		assert_successful_mutex_action(response_code);
		no_sold_out_case++;
		response_code = pthread_mutex_unlock(&mutex_transactions_no_sold_out_case);
		assert_successful_mutex_action(response_code);
	}else
	{
		response_code = pthread_mutex_unlock(&mutex_no_available_cashiers);
	        assert_successful_mutex_action(response_code);
        
        	while(no_available_cashiers <= 0)
		{
			pthread_cond_wait(&cond_no_available_cashier ,&mutex_no_available_cashiers); //Block until are available cashiers
	    	}
		--no_available_cashiers;
	    	response_code = pthread_mutex_unlock(&mutex_no_available_cashiers);
	    	assert_successful_mutex_action(response_code);
		
		
		
		//Cashier from now on.
		clock_gettime(CLOCK_REALTIME, &customer_cashier_start);  //track starting time for cashier
		bool successfull_transaction = rand_r(&seed) % 100 / 100.0f <= p_card_success;  //Start paying
		
		if(!successfull_transaction)
		{//Print user didn't pay
			flag = flag_customer_did_not_pay ;
			//release seats
			response_code = pthread_mutex_lock(&zone_mutex);
			assert_successful_mutex_action(response_code);
	  		releaseBookedSeats(zone, total_seats_in_zone,  cid); //Release booked seats
			response_code = pthread_mutex_unlock(&zone_mutex);
			assert_successful_mutex_action(response_code);
			
		}
		else
		{
			
			response_code = pthread_mutex_lock(&mutex_transactions_no_succesfull_trans );
			assert_successful_mutex_action(response_code);
			no_succesfull_trans++;
			response_code = pthread_mutex_unlock(&mutex_transactions_no_succesfull_trans );
			assert_successful_mutex_action(response_code);
			
			//todo update transactions
			//Update balance
			response_code = pthread_mutex_lock(&mutex_balance);
			assert_successful_mutex_action(response_code);
			balance += random_no_seats * cost_zone_per_seat; //User pay
			response_code = pthread_mutex_unlock(&mutex_balance);
			assert_successful_mutex_action(response_code);


		}

		response_code = pthread_mutex_lock(&mutex_no_available_cashiers);
		assert_successful_mutex_action(response_code);
	        ++no_available_cashiers;
	    	pthread_cond_signal(&cond_no_available_cashier);
	   	response_code = pthread_mutex_unlock(&mutex_no_available_cashiers);
	    	assert_successful_mutex_action(response_code);
	    	clock_gettime(CLOCK_REALTIME, &customer_finish); //Track finish time
	}
	
	
	
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
	     print_success_transaction_msg(cid, zone, random_no_seats, total_seats_in_zone, cost_zone_per_seat ); //TODO we need to pass the zone as well,
	}
	response_code = pthread_mutex_unlock(&mutex_console);
	assert_successful_mutex_action(response_code);

	//update total_waiting_time
	response_code = pthread_mutex_lock(&mutex_total_waiting_time);
	assert_successful_mutex_action(response_code);
	total_waiting_time += (customer_serviced.tv_sec - customer_start.tv_sec) + (customer_cashier_start.tv_sec - customer_telephone_finish.tv_sec) ;
	response_code = pthread_mutex_unlock(&mutex_total_waiting_time);
	assert_successful_mutex_action(response_code);

	//update total_service_time
	response_code = pthread_mutex_lock(&mutex_total_service_time);
	assert_successful_mutex_action(response_code);
	total_service_time += (customer_finish.tv_sec - customer_serviced.tv_sec) + (customer_finish.tv_sec - customer_cashier_start.tv_sec);
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
	
	no_sold_out_case = 0;
 	no_adequate_seats_case = 0;
 	no_succesfull_trans = 0;
	
    	total_service_time = 0;
   	total_waiting_time = 0;
	no_available_phones = no_telephones;
	no_available_cashiers = no_cashiers;
	seed = atoi(argv[2]);

	printf("\n \nΚαλωσήρθατε στο θέατρο AUEB(simulation)\nΕπιλέξατε %d πελατες και seed %d \n \n \n", no_customers, seed);
	

	//Initializing seats
	int total_seatsA = no_seats*no_rows_zoneA;
	seats_zoneA = malloc(sizeof(int)*total_seatsA);
	for(int i=0;i<total_seatsA;++i)
	{
		seats_zoneA[i] = -1;
	}

	int total_seatsB = no_seats*no_rows_zoneB;
	seats_zoneB = malloc(sizeof(int)*total_seatsB);
	for(int i=0;i<total_seatsB;++i)
	{
		seats_zoneB[i] = -1;
	}

	int total_seatsC = no_seats*no_rows_zoneC;
	seats_zoneC = malloc(sizeof(int)*total_seatsC);
	for(int i=0;i<total_seatsC;++i)
	{
		seats_zoneC[i] = -1;
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

	printf("Πλάνο θέσεων για ζώνη Α \n");
	for (int i = 0; i < no_seats*no_rows_zoneA; i++) 
	{	
		if(seats_zoneA[i] != -1){
			printf("θέση %d πελάτης %d \n", i, seats_zoneA[i]);
		}
		else{
			printf("θέση %d κανένας \n", i);	
		}
		
	}
	printf("\n\nΠλάνο θέσεων για ζώνη B \n");
	for (int i = 0; i < no_seats*no_rows_zoneB; i++) 
	{	
		if(seats_zoneB[i] != -1){
			printf("θέση %d πελάτης %d \n", i, seats_zoneB[i]);
		}
		else{
			printf("θέση %d κανένας \n", i);	
		}
		
	}
	printf("\n\nΠλάνο θέσεων για ζώνη C \n");
	for (int i = 0; i < no_seats*no_rows_zoneC; i++) 
	{	
		if(seats_zoneC[i] != -1){
			printf("θέση %d πελάτης %d \n", i, seats_zoneC[i]);
		}
		else{
			printf("θέση %d κανένας \n", i);	
		}
		
	}
	printf("\n");
	printf("Τα συνολικά έσοδα είναι: %f € \n", balance);
	
	printf("Ο μέσος χρόνος αναμονής είναι: %f \n", total_waiting_time/no_customers);
	
	printf("Ο μέσος χρόνος εξυπηρέτησης είναι: %f \n", total_service_time / no_customers);
	
	printf("Το ποσοστό των sold_out περιπτώσεων ειναι: %f \n",  no_sold_out_case*1.0f/no_customers);  
	
	printf("Το ποσοστό των οχι αρκετων θέσεων περιπτώσεων ειναι: %f \n",  no_adequate_seats_case*1.0f/no_customers);  
	
	printf("Το ποσοστό των επιτυχών μεχρι πληρωμής περιπτώσεων ειναι: %f \n",  no_succesfull_trans*1.0f/no_customers); 
	
	printf("Το ποσοστό των αποτυχημένων πληρωμών περιπτώσεων ειναι: %f \n", (1 - ((no_sold_out_case + no_adequate_seats_case + no_succesfull_trans)*1.0f)/no_customers));  
	

	//Free memory allocated
	free(seats_zoneA);
	free(seats_zoneB);
	free(seats_zoneC);
	       
	       
	       
	
	       
	return 0;
}




//Checks whether there are any available seets(not necesserily contiguous one) and and replace their value with cid otherwise returns an error message from Messages.c.
int book_seats(int * zone,  int total_seats, int number_of_seats, int cid)
{
	int number_of_rows = total_seats/no_seats;
	int sum_seats;
	bool max_so_far = false;
	int flag;
	int index;
	
	for(int i=0; i<number_of_rows; i++){
		sum_seats = 0;
		for(int seat_index=0; seat_index < no_seats; seat_index++){
			if(zone[(i)*no_seats + seat_index] == -1)
			{
				++sum_seats;
				max_so_far = true;
				if(sum_seats == number_of_seats){
					flag = seats_found_success;
					index = (i)*no_seats + seat_index;
					break;
				}
			}else{
				sum_seats = 0;
			}
		}
		if(sum_seats == number_of_seats) break;
	}
		
		
		if(max_so_far && flag != seats_found_success) return flag_not_available_seats ;
		if(flag != seats_found_success && !max_so_far) return flag_sold_out;
		
		//Change zone!
		for(int i=index; i > index - number_of_seats; i--){
			zone[i] = cid;
		}
	
		return seats_found_success ;
}

//release all the seats booked from customer with id: cid 
void releaseBookedSeats(int * zone, int total_seats, int cid)
{
	for(int i=0;i<total_seats;i++)
	{
		if(zone[i] == cid)   zone[i] = -1;
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
