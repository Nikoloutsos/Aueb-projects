# -----------------------
# File: Combinations.s
# -----------------------
# This program computes the mathematical combinations function
# C(n, k), which is the number of ways of selecting k objects
# from a set of n distinct objects.
# Calculates numbers with 16 bits or less
# -----------------------

	# -------- Text segment --------
		.text
		.globl main

main:

	# -------- Prompts user for input of n and shows it on screen while storing it for futher use --------
	# -------- int n = readInt("Enter number of objects in the set (n): "); --------

		la 					$a0,prompt_n										# Prompts user to input n
		li 					$v0, 4
		syscall
		
		li 					$v0, 5												# Reads n
		syscall
			
		move				$a0, $v0											# Show n on screen after input										
		li 					$v0, 1												
		syscall
		
		move 				$s0, $a0											# Stores n
		

		la 					$a0, CRLF											# New line
		li 					$v0, 4
		syscall


	# -------- Prompts user for input of k and shows it on screen while storing it for futher use --------
	# -------- int k = readInt("Enter number to be chosen (k): "); --------

		la 					$a0,prompt_k										# Prompts user to input k
		li 					$v0, 4
		syscall
		
		li 					$v0, 5												# Reads k
		syscall
				
		move				$a0, $v0											# Show k on screen after input									
		li 					$v0, 1												
		syscall
		
		move 				$s1, $a0											# Stores k

	# -------- Check if statement --------
	# -------- if (n>=k && k>=0) --------
		bge 				$s0, $s1, sif										# Checks if n>=k (first part of if statement) and jumps to second part (sif)
		
		j 					elif												# If false jumps to else if

sif:	
		bge 				$s1, $zero, true		 							# Checks if k>=0 (second part of if statement) and jumps to true (before jumping to combinations)

	# -------- Else statement --------
elif:	
		la 					$a0, CRLF											# New line
		li 					$v0, 4
		syscall

		la					$a0, ans_2_1										# Prints else message, println ("Please enter n >= k >= 0");
		li 					$v0, 4
		syscall

		j 					finish

	# -------- println("C("+n+", "+k+") = "+combinations(n, k)); --------	
true:
		jal					Combinations 										# If sif is true, jump here before jumping to subroutine

		la 					$a0, CRLF											# New line
		li 					$v0, 4
		syscall

		la					$a0, ans_1_1										# Printing "C(" without changing line					
		li 					$v0, 4
		syscall

		move				$a0, $s0											# Printing n without changing line
		li 					$v0, 1
		syscall

		la					$a0, ans_1_2										# Printing ", " without changing line
		li 					$v0, 4
		syscall

		move				$a0, $s1											# Printing k without changing line
		li 					$v0, 1
		syscall

		la					$a0, ans_1_3										# Printing ") = " without changing line
		li 					$v0, 4
		syscall

		move				$a0, $s2											# Printing result of combinations subrouting. Finishing string concatenation
		li 					$v0, 1
		syscall

	# -------- Terminates program --------
finish:
		la 					$a0, CRLF											#New line
		li 					$v0, 4
		syscall

		la 					$a0, termination									#Program termination message
		li 					$v0, 4
		syscall

		la 					$a0, CRLF											#New line
		li 					$v0, 4
		syscall

		li 					$v0,10												#Program termination
		syscall 	

	# -------- Combinations subroutine --------
#-------------------------------------------------------
# Returns the mathematical combinations function C(n, k),
# which is the number of ways of selecting k objects
# from a set of n distinct objects.
#--------------------------------------------------------

	# -------- int combinations(int n, int k) { --------
Combinations:
		li 					$t0, 1												# i = 1; Initializes registry to serve as counter

		li 					$t1, 1												# factorial_n = 1; Initializes registry to serve as var

	# -------- First loop --------
	# -------- for (i = 1; i <= n; i++) { --------
loop1:
		bgt 				$t0, $s0, comb_cont_1								# i>n jump comb_cont_1

		mul 				$t1, $t1, $t0										# factorial_n *= i

		add					$t0, $t0, 1											# i = i + 1

		j 					loop1												# continue loop1

	# -------- Continuing after first loop --------
	# -------- int factorial_k = 1; --------
comb_cont_1:

		li 					$t2, 1												# int factorial_k = 1;

		li 					$t0, 1												# int i; Reinitializing counter


	# -------- Second loop --------
	# -------- for (i = 1; i <= k; i++) { --------
loop2:
		bgt 				$t0, $s1, comb_cont_2								# i>k jump to comb_cont_2

		mul 				$t2, $t2, $t0										# factorial_k *= i; Initializes registry to serve as var

		add					$t0, $t0, 1											# i = i + 1

		j 					loop2												# continue loop2


	# -------- Continuing after second loop --------
	# -------- int factorial_n_k = 1; --------
comb_cont_2:
		
		li 					$t3, 1												# int factorial_n_k = 1;

		sub 				$t4, $s0, $s1										# n-k to Serve as termination variable

		li 					$t0, 1												# int i; Reinitializing counter


	# -------- Third loop --------
	# -------- for (i = 1; i <= n-k; i++) --------
loop3:
		bgt					$t0, $t4, comb_cont_3								# i>n-k jump comb_cont_3

		mul 				$t3, $t3, $t0										# factorial_n_k *= i; Initializes registry to serve as var

		add					$t0, $t0, 1											# i = i + 1

		j 					loop3												# continue loop3

	# -------- Continuing after third loop --------
	# -------- return factorial_n / (factorial_k*factorial_n_k); --------
comb_cont_3:
		
		mul					$t0, $t2, $t3										# multiply factorial_k and factorial_n_k

		div					$s2, $t1, $t0										# divide factorial_n with (factorial_k * factorial_n_k)

		jr 					$ra 												# Returning to main. end of combinations

# -------- Data segment --------
		.data
prompt_n		:	.asciiz "Enter number of objects in the set (n): "		# Prompts user to enter the number of objects to be selected
prompt_k		:	.asciiz "Enter number to be chosen (k): "				# Prompts user to enter how many elements will be selected
ans_1_1			:	.asciiz "C("											# To be used for concatenation of positive reply
ans_1_2			:	.asciiz ", "											# To be used for concatenation of positive reply
ans_1_3			:	.asciiz	") = "											# To be used for concatenation of positive reply
ans_2_1			:	.asciiz "Please enter n >= k >= 0"						# To be used if numbers are not valid to be used in program
CRLF			:	.asciiz	"\n"											# New line
termination		:	.asciiz	"Program terminated"							# Termination message











		