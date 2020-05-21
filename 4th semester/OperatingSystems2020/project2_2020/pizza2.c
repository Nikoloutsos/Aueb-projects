#include "pizza2.h"

pthread_mutex_t lock;
pthread_cond_t cond;

unsigned int sleep(unsigned int seconds);

struct timespec start;
struct timespec stop;

double total_time = 0;
double max_time = -1;
double total_cold_time = 0;
double max_cold_time  = -1;
int n_cust;
unsigned int* seed;
int n_available_cooks = n_cook;
int n_available_ovens = n_oven;
int n_available_deliverers = n_deliverer;

void *exec_thread(void *threadid) {

	int tid = (int) threadid;
	double elapsed_time;
	double thread_time;
	int n_pizzas;
	int status;
	double delivery_time;
	double oven_to_customer_time;

	status = pthread_mutex_lock(&lock);
	clock_gettime(CLOCK_REALTIME, &start);
	while (n_available_cooks == 0) {
		status = pthread_cond_wait(&cond, &lock);
	}
	clock_gettime(CLOCK_REALTIME, &stop);
	elapsed_time = (stop.tv_sec - start.tv_sec) + (stop.tv_nsec - start.tv_nsec)/BILLION;
	n_available_cooks --;
	status  = pthread_cond_signal(&cond);
	status  = pthread_mutex_unlock(&lock);

	n_pizzas = rand_r(&seed) % (n_orderhigh + 1 - n_orderlow) + n_orderlow;
	sleep(t_prep*n_pizzas);
	thread_time= elapsed_time + t_prep*n_pizzas;

	status = pthread_mutex_lock(&lock);
	clock_gettime(CLOCK_REALTIME, &start);
	while (n_available_ovens == 0) {
		status = pthread_cond_wait(&cond, &lock);
	}
	clock_gettime(CLOCK_REALTIME, &stop);
	elapsed_time = (stop.tv_sec - start.tv_sec) + (stop.tv_nsec - start.tv_nsec)/BILLION;
	n_available_ovens --;
	n_available_cooks ++;
	sleep(t_bake);
	status  = pthread_cond_signal(&cond);
	status  = pthread_mutex_unlock(&lock);

	thread_time += elapsed_time + t_bake;
	
	status = pthread_mutex_lock(&lock);
	clock_gettime(CLOCK_REALTIME, &start);
	while (n_available_deliverers == 0) {
		status = pthread_cond_wait(&cond, &lock);
	}
	clock_gettime(CLOCK_REALTIME, &stop);
	elapsed_time = (stop.tv_sec - start.tv_sec) + (stop.tv_nsec - start.tv_nsec)/BILLION;
	n_available_ovens ++;
	n_available_deliverers --;
	status  = pthread_cond_signal(&cond);
	status  = pthread_mutex_unlock(&lock);

	delivery_time = rand_r(&seed) % (t_high + 1 - t_low) + t_low;
	thread_time += elapsed_time + delivery_time;
	oven_to_customer_time = elapsed_time + delivery_time;
	sleep(2*delivery_time);

	status = pthread_mutex_lock(&lock);
	n_available_deliverers ++;
	printf("Η παραγγελία με αριθμό %d παραδόθηκε σε %d λεπτά και κρύωνε για %d λεπτά.\n", tid, (int) thread_time, (int) oven_to_customer_time);
	total_time += thread_time;
	if (thread_time > max_time) {
		max_time =  thread_time;
	}
	total_cold_time += oven_to_customer_time;
	if (oven_to_customer_time > max_cold_time) {
		max_cold_time =  oven_to_customer_time;
	}
	status  = pthread_cond_signal(&cond);
	status  = pthread_mutex_unlock(&lock);
	
	pthread_exit(NULL);
}

int main(int argc, char *argv[]) {

	if (argc < 2) {
		printf("Please enter at least two arguments.\n");
		exit(-1);
	}

	double next_order;
	n_cust = atoi(argv[1]);
	seed = atoi(argv[2]);
	int customer_id[n_cust];
	int status;
	pthread_t threads[n_cust];
	pthread_mutex_init(&lock, NULL);	

	for (int i = 0; i < n_cust; i++) {
		customer_id[i] = i + 1;
		status = pthread_create(&threads[i], NULL, exec_thread, customer_id[i]);
		next_order = rand_r(&seed) % (t_orderhigh + 1 - t_orderlow) + t_orderlow;
		sleep(next_order);
	}

	for (int i = 0; i < n_cust; i++) {
		pthread_join(threads[i], NULL);
	}

	printf("Μέσος χρόνος παράδοσης παραγγελιών: %.2f λεπτά.\n", total_time/ n_cust);
	printf("Μέγιστος χρόνος παράδοσης παραγγελίας: %d λεπτά.\n", (int) max_time);
	printf("Μέσος χρόνος κρυώματος παραγγελιών: %.2f λεπτά.\n", total_cold_time/ n_cust);
	printf("Μέγιστος χρόνος κρυώματος παραγγελίας: %d λεπτά.\n", (int) max_cold_time);

	pthread_mutex_destroy(&lock);
	pthread_cond_destroy(&cond);

	return 0;

}
