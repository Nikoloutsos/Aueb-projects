#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>

#define n_cook 6
#define n_oven 5
#define t_orderlow 1
#define t_orderhigh 5
#define n_orderlow 1
#define n_orderhigh 5
#define t_prep 1
#define t_bake 10
#define BILLION 1000000000L

