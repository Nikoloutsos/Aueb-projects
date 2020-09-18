#include "p3160011-p3160098-pizza1.h"

void *gabagool(void *order_id){
    int *oid;
    oid = (int *)order_id;
    unsigned int seedp = seed^(*oid); // initialize a seed unique for each thread
    int rc;
    struct timespec order_start; // when the order is given
    struct timespec order_prep; // when the order starts being prepared
    struct timespec order_bake; // when the order is inserted in the oven
    struct timespec order_stop; // when the order is out of the oven

    //get the time the order was given
    clock_gettime(CLOCK_REALTIME, &order_start);
    printf("Taking order %d.\n", *oid);
    //WAITING TIME: find a cook to take the order
    rc = pthread_mutex_lock(&mutex_no_available_cooks);
    if(rc != 0){
        printf("Mutex: Error in locking with code %d\n", rc);
        pthread_exit(&rc);
    }
    while(no_available_cooks<=0){
        rc = pthread_cond_wait(&cond_no_available_cooks, &mutex_no_available_cooks);
        if(rc != 0){
            printf("Mutex: Error in condition wait with code %d\n", rc);
            pthread_exit(&rc);
        }
    }
    printf("Cook was found for order %d.\n", *oid);
    --no_available_cooks; // available cook was found and taken for the order
    rc = pthread_mutex_unlock(&mutex_no_available_cooks);
    if(rc != 0){
        printf("Mutex: Error in unlocking with code %d\n", rc);
        pthread_exit(&rc);
    }
    //get the time the order was started to be prepared
    clock_gettime(CLOCK_REALTIME, &order_prep);
    //PREPARATION TIME: how many pizzas need to be prepared
    int no_pizzas = rand_r(&seedp) % N_order_high + N_order_low;
    //prepare each pizza for T_prep duration
    sleep(no_pizzas*T_prep);
    printf("%d pizzas prepared for order %d.\n", no_pizzas, *oid);
    //BAKE TIME: firstly wait for an oven to be empty for the pizzas
    rc = pthread_mutex_lock(&mutex_no_available_ovens);
    if(rc != 0){
        printf("Mutex: Error in locking with code %d\n", rc);
        pthread_exit(&rc);
    }
    while(no_available_ovens<=0){
        rc = pthread_cond_wait(&cond_no_available_ovens, &mutex_no_available_ovens);
        if(rc != 0){
            printf("Mutex: Error in condition wait with code %d\n", rc);
            pthread_exit(&rc);
        }
    }
    printf("Oven was found for order %d, starting to bake...\n", *oid);
    --no_available_ovens; // empty oven was found and pizzas were inserted for the baking
    rc = pthread_mutex_unlock(&mutex_no_available_ovens);
    if(rc != 0){
        printf("Mutex: Error in unlocking with code %d\n", rc);
        pthread_exit(&rc);
    }
    //get the time the pizzas were inserted in the oven
    clock_gettime(CLOCK_REALTIME, &order_bake);
    //BAKE TIME: baking is simulated with the use of sleep for a standard duration and the baker is busy until the pizzas are done and out of the oven
    sleep(T_bake);
    printf("Pizzas for order %d are done, ready for take-away!\n", *oid);
    //Oven is released because the pizzas are done
    rc = pthread_mutex_lock(&mutex_no_available_ovens);
    if(rc != 0){
        printf("Mutex: Error in locking with code %d\n", rc);
        pthread_exit(&rc);
    }
    ++no_available_ovens;
    pthread_cond_signal(&cond_no_available_ovens);
    rc = pthread_mutex_unlock(&mutex_no_available_ovens);
    if(rc != 0){
        printf("Mutex: Error in unlocking with code %d\n", rc);
        pthread_exit(&rc);
    }
    //get the time the pizzas were out of the oven
    clock_gettime(CLOCK_REALTIME, &order_stop);
    //The cook is also released because he took the pizzas out of the oven and passed them to the counter
    rc = pthread_mutex_lock(&mutex_no_available_cooks);
    if(rc != 0){
        printf("Mutex: Error in locking with code %d\n", rc);
        pthread_exit(&rc);
    }
    ++no_available_cooks;
    pthread_cond_signal(&cond_no_available_cooks);
    rc = pthread_mutex_unlock(&mutex_no_available_cooks);
    if(rc != 0){
        printf("Mutex: Error in unlocking with code %d\n", rc);
        pthread_exit(&rc);
    }
    //update total order time
    rc = pthread_mutex_lock(&mutex_total_order_time);
    if(rc != 0) {
        printf("Mutex: Error in locking with code %d\n", rc);
        pthread_exit(&rc);
    }
    rc = pthread_mutex_lock(&mutex_lock_screen);
    if(rc != 0){
        printf("Mutex: Error in locking with code %d\n", rc);
        pthread_exit(&rc);
    }
    order_time = ((order_stop.tv_sec - order_bake.tv_sec) + (order_bake.tv_sec - order_prep.tv_sec) + (order_prep.tv_sec - order_start.tv_sec));
    total_order_time += order_time;
    //print the final order message
    print_order_final(*oid, order_time);
    rc = pthread_mutex_unlock(&mutex_lock_screen);
    if(rc != 0){
        printf("Mutex: Error in unlocking with code %d\n", rc);
        pthread_exit(&rc);
    }
    //update max waiting time
    rc = pthread_mutex_lock(&mutex_max_order_time);
    if(rc != 0) {
        printf("Mutex: Error in locking with code %d\n", rc);
        pthread_exit(&rc);
    }
    if(order_time>max_order_time){
        max_order_time = order_time;
    }
    rc = pthread_mutex_unlock(&mutex_max_order_time);
    if(rc != 0){
        printf("Mutex: Error in unlocking with code %d\n", rc);
        pthread_exit(&rc);
    }
    rc = pthread_mutex_unlock(&mutex_total_order_time);
    if(rc != 0){
        printf("Mutex: Error in unlocking with code %d\n", rc);
        pthread_exit(&rc);
    }
    pthread_exit(oid);
}

int main(int argc, char *argv[]) {

    if(argc != 3) {
        printf("Parameter number error.\n\n");
        exit(-1);
    }

    int N_cust = atoi(argv[1]);
    if(N_cust <0) {
        printf("Customer number error: Negative number.\n\n");
        exit(-1);
    }

    seed = atoi(argv[2]);

    printf("\nWelcome to the Sopranos Pizzeria, jabroni!\nMamma mia, we got'a %d customers and the seed is %d, molto bene.\n\n", N_cust, seed);

    //initialize variables to be used
    order_time = 0;
    total_order_time = 0;
    max_order_time = 0;
    no_available_cooks = N_cook;
    no_available_ovens = N_oven;

    //initialize order id's
    int order_id[N_cust];
    for(int i=0;i<N_cust;++i)
    {
        order_id[i] = i+1;
    }

    //initialize order threads

    pthread_t *threads;
    threads = malloc(N_cust*sizeof(pthread_t));
    if(threads==NULL){
        printf("Error allocating memory.\n");
        exit(-1);
    }

    int rc;
    for(int i=0; i<N_cust; ++i)
    {
        rc = pthread_create(&threads[i], NULL, &gabagool, &order_id[i]);
	//simulate the waiting time for the next order to come
        int wait = rand_r(&seed)%T_order_high+T_order_low;
        sleep(wait);
        if (rc != 0) {
            printf("Error: pthread_create() returned %d\n", rc);
            exit(-1);
        }
    }

    //join threads
    void *status;
    for(int i=0; i<N_cust; i++)
    {
        rc = pthread_join(threads[i], &status);
        if (rc != 0) {
            printf("Error: pthread_join() returned %d on thread %d\n", rc, order_id[i]);
            exit(-1);
        }
    }

    //Print average order preparation time
    printf("\n\nAverage order time is %f", total_order_time/N_cust);
    //Print max order preparation time
    printf("\nMax order time is %f\n\n", max_order_time);
    free(threads);
    return 1;
}
