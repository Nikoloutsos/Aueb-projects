# -----------------------
# File: hash.s
# -----------------------
# This porgram implements a hast table of length 10 as well as
# the insertion and search methods of numerical keys in it.
# -----------------------


# -------- Data segment --------
		.data

	# ------- Asciiz -------
menu			:	.asciiz " Menu: "												# Menu header
insert_k_m		: 	.asciiz "1.Insert Key"											# Insert key message
find_k_m		:	.asciiz "2.Find Key"											# Find key message
displ_m			:	.asciiz "3.Display Hash Table"									# Display hash table message
ext_m			:	.asciiz "4.Exit"												# Exit message
choice_p		:	.asciiz "\nChoice? "											# Choice prompt
insert_k_p		:	.asciiz "Give new key (greater than zero): "					# Prompt for new key
insert_k_e		:	.asciiz "Key must be greater than zero"							# Error message if given key is zero or less
search_k_p		:	.asciiz "Give key to search for: "								# Prompt for key to search for
search_k_nf		:	.asciiz "Key not in hash table."								# Message if key not in the hash table
search_k_f_k	:	.asciiz "Key value = "											# Message if key in hash table (key value)
search_k_f_po	:	.asciiz "Table position = "										# Message if key in hash table (position)
insert_k_exists	:	.asciiz "Key is already in hash table."							# Message during insrtkey function-key exists
insert_k_full	:	.asciiz "Hash table is full"									# Message during insrtkey funtion full trbl
display_table	:	.asciiz "\npos key\n"											# Message during displaytable function
CRLF			:	.asciiz	"\n"													# New line
sp 				:	.asciiz " " 													# Space
termination		:	.asciiz	"Program terminated"									# Termination message

	# ------- Numerical -------
key 			:	.word 0															# Assigns 0 to var key
pos 			:	.word 0															# Assigns 0 to var pos						
choice 			: 	.word 0															# Assigns 0 to var choice
telos 			:	.word 0															# Assigns 0 to var telos
keys			:	.word 0															# Assigns 0 to var keys
N 				=	 10																# Assigns the number 10 to constant N

	# ------- Array -------
hash 			: 	.space 40 														# Allocates space for array of 10 integers			

# -------- Text segment --------
		.text
		.globl main

# ------- Main --------
main:
	
	addi 			$t0, $zero, 0													# index - byte counter
	addi			$t1, $zero, 0													# counter 
	addi			$t2, $zero, 0													# element to be saved

	# ------- Initializing table elements to 0 -------

	loop_initialize_table:
		
		sw				$t2, hash($t0)												# Storing t2(0) to elmnt of hash tbl(byte $t0)
		addi			$t0, $t0, 4													# Incrementing by 4 bytes for next element
		addi			$t1, $t1, 1													# Incrementing counter by 1
		blt				$t1, N, loop_initialize_table 								# Continuing loop if true, else continues

# ------- Continuing after initialization -------
exit_initi:
		
	# ------- Do / while -------
	do:
		# ------- Printing menu -------
		la 					$a0, menu												# Prints menu
		li 					$v0, 4
		syscall

		la 					$a0, CRLF												# New line
		li 					$v0, 4
		syscall

		la 					$a0, insert_k_m											# Prints insert key option
		li 					$v0, 4
		syscall

		la 					$a0, CRLF												# New line
		li 					$v0, 4
		syscall

		la 					$a0, find_k_m											# Prints find key option
		li 					$v0, 4
		syscall

		la 					$a0, CRLF												# New line
		li 					$v0, 4
		syscall

		la 					$a0, displ_m											# Print display hash table option
		li 					$v0, 4
		syscall

		la 					$a0, CRLF												# New line
		li 					$v0, 4
		syscall

		la 					$a0, ext_m												# Print exit option
		li 					$v0, 4
		syscall

		la 					$a0, CRLF												# New line
		li 					$v0, 4
		syscall

		# ------- Read user's choise -------

		la 					$a0, choice_p											# Prompts user to input choise
		li 					$v0, 4
		syscall
		
		li 					$v0, 5													# Reads choice
		syscall

		move 				$t0, $v0												# Stores choice
		

		la 					$a0, CRLF												# New line
		li 					$v0, 4
		syscall

		# ------- Check cases --------

		addi				$t1, $zero, 1											# Saves to $t0 value 1
		beq					$t0, $t1, if1											# Jump to if1
		addi				$t1, $zero, 2											# Saves to $t0 value 2
		beq					$t0, $t1, if2											# Jump to if2
		addi				$t1, $zero, 3											# Saves to $t0 value 3
		beq					$t0, $t1, if3											# Jump to if3
		addi				$t1, $zero, 4											# Saves to $t0 value 4	
		beq					$t0, $t1, if4											# Jump to if4
			# ------- User inputs 1 case -------
			if1:
				la 					$a0, insert_k_p									# Prompts user to input choise
				li 					$v0, 4
				syscall
		
				li 					$v0, 5											# Reads choice
				syscall

				move 				$s1, $v0										# Stores choice of key
		

				la 					$a0, CRLF										# New line
				li 					$v0, 4
				syscall

				# ------- Checks if key<=0 -------	
				if1_n_gt_0:
					ble					$s1, $zero, if_1_el							# key<=0 to go to else

					jal insertkey

					j 					while

				# ------- Case for key<0 -------

				if_1_el:

					la 					$a0, insert_k_e								# Prints key must be greater than 0
					li 					$v0, 4
					syscall

					la 					$a0, CRLF									# New line
					li 					$v0, 4
					syscall

					la 					$a0, CRLF									# New line
					li 					$v0, 4
					syscall

					j 					while

			# ------- User inputs 2 case -------
			if2:

				la 					$a0, search_k_p									# Prompts user to input choise
				li 					$v0, 4
				syscall
		
				li 					$v0, 5											# Reads choice
				syscall

				move 				$s0, $v0										# Stores choice of k
		
				la 					$a0, CRLF										# New line
				li 					$v0, 4
				syscall

				jal					findkey

				addi				$t0, $zero, -1 									# $t0 = -1

				beq					$s0, $t0, if2_min1								# Checks if findkey (pos) = -1
				j 					else_if2										# Jumps to else

				# ------- position of the key not found -------
				if2_min1:

										la 					$a0, search_k_nf		# Prints key not in table
										li 					$v0, 4
										syscall

										la 					$a0, CRLF				# New line
										li 					$v0, 4
										syscall

										la 					$a0, CRLF				# New line
										li 					$v0, 4
										syscall

										j 					while
				else_if2:

										la 					$a0, search_k_f_k		# Prints key value
										li 					$v0, 4
										syscall

										mul					$t0, $s0, 4				# Finding byte position

										lw					$t1, hash($t0)			# Storing the element in $t1

										la 					$a0, ($t1)				# Printing hash[i]
										li 					$v0, 1 
										syscall

										la 					$a0, CRLF				# New line
										li 					$v0, 4
										syscall

										la 					$a0, search_k_f_po		# Prints position
										li 					$v0, 4
										syscall

										move				$a0, $s0				# Prints position
										li 					$v0, 1
										syscall

										la 					$a0, CRLF				# New line
										li 					$v0, 4
										syscall

										la 					$a0, CRLF				# New line
										li 					$v0, 4
										syscall

										j 					while
			# ------- User inputs 3 case -------
			if3:

				jal					displaytable
				j 					while

			# ------- User inputs 4 case -------
			if4:
				li $t0, 1															# $t0 =1 to assign to telos
				sw $t0, telos 														# telos = $t0 = 1

		# ------- While part of do-while loop -------
	while:

		li 					$t0, 0 													# $t0 = 0 to check while
		lw					$t1, telos												# $t1 = telos to check while
		beq 				$t0, $t1, do 											# Checking if telos = 0

		li 					$v0,10													# Program termination
		syscall


# -------- Subroutines --------

# -------- Inserts new key in the hash table. -------
# -------- void insertkey(int[] hash, int k) --------

insertkey:

	sw				$ra, 0($sp) 													# Stores return address
	addi 			$s0, $s1, 0														# Find key works with $s0

	jal				findkey 														# jumps to findkey function
	addi			$t0, $zero, -1													# t0 = -1 for checking
	beq				$s0, $t0, else_insrt											# Jumps to elseinsrt if position !=-1

	la 				$a0, insert_k_exists											# Prints key already in table
	li 				$v0, 4
	syscall

	la 				$a0, CRLF														# New line
	li 				$v0, 4
	syscall

	la 				$a0, CRLF														# New line
	li 				$v0, 4
	syscall


	j 				finish_insrt													# Jumps to finish

	else_insrt:

		lw				$t2, keys													# t2 = keys for compuation

		bge 			$t2, N, tbl_fl 												# Jumps if table is full

		jal 			hashfunction 												# Jumps to hash function

		mul 			$t0, $s2, 4													# Byte value of position

		sw				$s1, hash($t0)												# Storing s1 to elmnt of hash tbl(byte $t0)
		lw				$t2,  keys													# t2 = keys for compuation

		addi 			$t2, $t2, 1													# Increment keys by 1

		sw				$t2, keys 													# Changing value of keys

		j 				finish_insrt												# Finishing subroutine


		tbl_fl:

			la 				$a0, insert_k_full										# Prints table is full
			li 				$v0, 4
			syscall

			la 				$a0, CRLF												# New line
			li 				$v0, 4
			syscall

			la 				$a0, CRLF												# New line
			li 				$v0, 4
			syscall

	finish_insrt:
		lw 				$ra, 0($sp)
		jr 				$ra 														# Returning

# -------- Performs hashing -------
# -------- int hashfunction(int[] hash, int k) --------

hashfunction:
	addi 			$t0, $s1, 0														# t0 = key
	rem				$t0, $t0, N 													# position = k% N;


	while_hash:
		mul				$t2, $t0, 4													# Finding byte position

		lw				$t3, hash($t2)												# Storing the element in $t3
		
		beq				$t3, $zero, ext_hash_funct									# Checking if hash[i]!=k

		addi 			$t0, $t0, 1													# Increments position by 1

		rem				$t0, $t0, N 												# position %= N

		j 				while_hash

	ext_hash_funct:
		addi 			$s2, $t0, 0 												# Storing position
		jr 				$ra 														# Returning


# -------- Searches for a key in the hash table. If found returns its position otherwise returns -1 -------
# -------- int findkey(int [] hash, int k) --------

findkey:
	addi			$t0, $zero, 0													# i =0
	addi			$t1, $zero, 0													# found = 0
	addi			$t3, $s0, 0														# t3 = k for computation
	rem				$t4, $t3, N 													# position = k%N
	la 				$t2, hash 														# Address of hash table

	while_find_key:
		bge				$t0, N, if_find_key											# Checks if i>=N to exit loop

	secnd_param:
		bne				$t1, $zero, if_find_key										# Checks if found!=0 to exit loop
		addi			$t0, $t0, 1													# Increments i by 1

		mul				$t5, $t4, 4													# Finding byte position
		add 			$t8, $t5, $t2

		lw				$t6, 0($t8)													# Storing the element in $t6
		bne				$t6, $t3, el_if_wh_findk									# Checking if hash[i]!=k
		addi			$t1, $zero, 1												# Found = 1
		j 				while_find_key												# Jumps to the beggining of while loop

		el_if_wh_findk:
			addi			$t4, $t4, 1												# Incerements position by 1
			rem				$t4, $t4, N 											# position %= N;
			j 				while_find_key											# Jumps to the beggining of while loop

	if_find_key:
		addi			$t7, $zero, 1 												# Temp to store value 1
		bne 			$t7, $t1, else_find_key 									# Jumps to else find_key
		addi 			$s0, $t4, 0													# Stores position to s0
		j 				finish_find_key

	else_find_key:
		addi 			$s0, $zero, -1 												# Store value -1 to be returned

	finish_find_key:
		jr 				$ra 														# Returning

# -------- Displays the table if 3 option was selected -------
# -------- void displaytable (int [] hash) { --------

displaytable:
	addi 			$t0, $zero, 0													# index - byte counter
	addi			$t1, $zero, 0													# counter 

	# -------- Printing npos key -------

	la				$a0, display_table												# Printing npos key
	li 				$v0, 4
	syscall

	loop_display_table:

		# ------- Printing (for loop) -------

		lw				$t2, hash($t0)												# Storing to $t2 elmnt of hash tbl (byte $t0)

		# ------- Printing " " + i + " " + hash[i] -------

		la 				$a0, sp 													# Printing space
		li 				$v0, 4
		syscall

		la 				$a0, ($t1)													# Printing i
		li 				$v0, 1
		syscall

		la 				$a0, sp 													# Printing space
		li 				$v0, 4
		syscall

		la 				$a0, ($t2)													# Printing hash[i]
		li 				$v0, 1 
		syscall

		la 				$a0, CRLF													#New line
		li 				$v0, 4
		syscall

		# ------- Finished printing line --------

		addi			$t0, $t0, 4													# Incrementing by 4 bytes for next element
		addi			$t1, $t1, 1													# Incrementing counter by 1

		blt				$t1, N, loop_display_table 									# Continuing loop if true, else continues

		la 				$a0, CRLF													#New line
		li 				$v0, 4
		syscall

		jr 				$ra 														# Returning