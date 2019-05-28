#pragma once
#include <stdio.h>
#include <pthread.h>
#include <stdbool.h>
#include <stdlib.h>


/*
* storing constants.
*/

const int no_seats = 250;

const int no_telephones = 8;

const int no_seat_low = 1;
const int no_seat_high = 5;

const long time_seat_low = 5;
const long time_seat_high = 10;

const float p_card_success = 0.9;

const double cost_per_seat = 20.0;

const int flag_not_available_seats = -1;

const int flag_customer_did_not_pay = -2;

const int flag_sold_out = -3;

const int seats_found_success = 200;

const int flag_ok = 0;





/*
*storing messages. Supported language: greek
*/

void print_fail_no_pay_msg()
{
	printf("Η κράτηση σας ακυρώθηκε κατα την πληρωμή με πιστωτική καρτα . \n");
	
	/*Your reservation canceled because the transaction was stopped at paying with credit-card*/
}


void print_fail_no_seats_msg()
{
	printf("Η κράτηση σας ακυρώθηκε γιατι δεν υπαρχουν αρκετες θεσεις οσες χρειαζεστε.. \n");
	/*Your reservation canceled because there are no as much seats as you want to book*/
}

void print_fail_sold_out_msg()
{
	printf("Η κράτηση σας ακυρώθηκε γιατι το θέοτρο ειναι sold-out \n");
	/*Your reservation canceled because the theater is sold-out*/
}




/*Prints the final message for the customer if everything was successful */
void  print_success_transaction_msg(int cid, const int* seats, int ctotal_seats)
{
	printf("Η κρατηση ολοκληρώθηκε με επιτυχία. \nid πελατη ειναι:  %d \nΟι θέσεις σας ειναι: ", cid);
	for (int i = 0; i < no_seats; i++)
	{
		if (seats[i] == cid) printf("%d θέση, ", i);
	}
	printf("\nΤο συνολικο ποσο πληρωμής ειναι %.2f € \n\n", ctotal_seats*cost_per_seat);

	/*The reservation was successful. Your transaction number is . Your seats are.
The total cost is*/
}
