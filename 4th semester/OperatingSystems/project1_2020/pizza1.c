#include "pizza1.h"

pthread_mutex_t lock;
pthread_cond_t cond;

unsigned int sleep(unsigned int seconds);

struct timespec start;
struct timespec stop;

double total_time = 0;
double max_time = -1;
int n_cust;
unsigned int* seed;
int n_available_cooks = n_cook;
int n_available_ovens = n_oven;

void *exec_thread(void *threadid) {

	int tid = (int) threadid;
	double elapsed_time;
	double thread_time;
	int n_pizzas;
	int status;

	status = pthread_mutex_lock(&lock);
	while (n_available_cooks == 0) {
		status = pthread_cond_wait(&cond, &lock);
	}
	n_available_cooks --;
	status  = pthread_cond_signal(&cond);
	status  = pthread_mutex_unlock(&lock);

	n_pizzas = rand_r(&seed) % (n_orderhigh + 1 - n_orderlow) + n_orderlow;
	sleep(t_prep*n_pizzas);

	status = pthread_mutex_lock(&lock);
	clock_gettime(CLOCK_REALTIME, &start);
	while (n_available_ovens == 0) {
		status = pthread_cond_wait(&cond, &lock);
	}
	clock_gettime(CLOCK_REALTIME, &stop);
	elapsed_time = (stop.tv_sec - start.tv_sec) + (stop.tv_nsec - start.tv_nsec)/BILLION;
	n_available_ovens --;
	sleep(t_bake);
	n_available_cooks ++;
	n_available_ovens ++;
	status  = pthread_cond_signal(&cond);
	status  = pthread_mutex_unlock(&lock);

	thread_time = t_prep*n_pizzas + elapsed_time + t_bake;

	status  = pthread_mutex_lock(&lock);
	total_time += thread_time;
	if (thread_time > max_time) {
		max_time =  thread_time;
	}
	status  = pthread_cond_signal(&cond);
	status  = pthread_mutex_unlock(&lock);

	status  = pthread_mutex_lock(&lock);
	printf("The order with number %d was completed in %d minutes.\n", tid, (int) thread_time);
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

	printf("Average order completion time: %.2f minutes.\n", total_time/ n_cust);
	printf("Maximum order completion time: %d minutes.\n", (int) max_time);

	pthread_mutex_destroy(&lock);
	pthread_cond_destroy(&cond);

	return 0;

}
