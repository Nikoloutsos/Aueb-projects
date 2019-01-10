# Author: TZENI BOLENA(3170117)- KONSTANTINOS NIKOLOYTSOS(3170122)
# Date: 12/12/18
# Description: 3rd exercise of OSY: This program gets the user to giv evalues for two arrays, and then creates the sparse arrays of each one
#as well as the addition of those two, and then if the user wants(options) prints the spars arrays
##in this progam we use the following registers
# In the program below i use the following registers:
# 1)$v0:to store the value of the syscall and i also use it when the user inputs the Integers, basically v0 is a return value of a function 
# 2)$a0-$a3:use it when i want to print a message to the user, using a registers is a parameter of a function 
# 3)$t0-t2,to store the lengths of the sparse arrays, $t9 and $t1, $t2 in the funcs as counters
# 4)$s0-$s6 , use it in the functions for different pupuses, either to sotre a value to it or  address. in the funs below there are commnets
#that describes what each s register holds 
#5) $ra register to know the return point when a fun is done, and get back to the mai
#6) $sp to help as store and load values that we don't wanna loose  or addresses to the stack, basicly it gives as access to the stack

.data
arrayA: .space 40 
arrayB: .space 40
sArrayA: .space 80
sArrayB: .space 80
sArrayC: .space 80

theColon: .asciiz ": "
theDash: .asciiz "-------------------------------\n"
position: .asciiz "Position "
val: .asciiz " Value: "
theN: .asciiz "\n"
values: .asciiz " values \n"

readingA: .asciiz "Reading Array A \n"
readingB: .asciiz "Reading Array B \n"
creatingSA: .asciiz "Creating Sparse Array A \n"
creatingSB: .asciiz "Creating Sparse Array B \n"
creatingSC: .asciiz "Creating Sparse Array C = A + B \n"
printingSA: .asciiz "Displaying Sparse Array A \n"
printingSB: .asciiz "Displaying Sparse Array B \n"
printingSC: .asciiz "Displaying Sparse Array C \n" 
notoption: .asciiz "Not an option, enter another one! \n"
welcomemsg: .asciiz "Welcome to the program!  \n"

choosemsg: .asciiz "Choise? "
op1: .asciiz "1. Read Array A  \n"
op2: .asciiz "2. Read Array B   \n"
op3: .asciiz "3. Create Sparse Array A   \n"
op4: .asciiz "4. Create Sparse Array B   \n"
op5: .asciiz "5. Create Sparse Array C = A + B   \n"
op6: .asciiz "6. Display Sparse Array A   \n"
op7: .asciiz "7. Display Sparse Array B   \n"
op8: .asciiz "8. Display Sparse Array C   \n"
op0: .asciiz "0. Exit \n"
endProgmsg: .asciiz "End of program! \n"




.text 
.globl main
#this is the main part of the program. It's where we call all the function so that then they can do the work needed
main:
        li $v0,4
		la $a0, welcomemsg
		syscall


        MAIN_FOR:
	
		jal PRINT_OPTIONS #print all the possible options to the user by calling the method called PRINT_OPTIONS
	
		#read users answer
		li $v0, 5
		syscall
		move $t0, $v0 #users choice
		
		li $v0, 4
		la $a0, theN
		syscall
       
		beq $t0, 0, EXIT_MAIN_FOR
		beq $t0, 1, readA
		beq $t0,2, readB
		beq $t0, 3, createA
		beq $t0, 4, createB
		beq $t0, 5, createC
		beq $t0, 6, dispA
		beq $t0, 7, dispB
		beq $t0, 8, dispC
	        ##it means the user didn't give any number between 0-8 so we get him to give as a number(an option) again
			li $v0, 4
			la $a0, notoption
			syscall
			j MAIN_FOR #jumps at the begining of the while loop 
	   
		readA:
		    li $v0, 4
		    la $a0, readingA
		    syscall
						
		    ##first we load the address of arrayA to $a0, so that it can be passed as a parameter in the read_array func
		    ##and then get initialized(same thing happens for readB(for arrayB))
						
			la $a0, arrayA   
			jal  READ_ARRAY #jumping to the method but first the PC + 4 address is saved in $ra, so we now where the func will return
			j MAIN_FOR
	   
		readB:
		        li $v0, 4
			la $a0, readingB
			syscall
						
			la $a0 , arrayB
			jal  READ_ARRAY
			j MAIN_FOR
	  
	   
		createA:
		        li $v0, 4
			la $a0, creatingSA
			syscall
						
		     ##creates sparse array A, it passes the length of arrayA and the beggining(address) of sparse array A 
		     ##as parameters through the help of registers $a0, $a1 (same also happens for creating sparse arrayB)
						
			la $a0, arrayA
			la $a1, sArrayA
			jal CREATE_SPARSE_ARRAY 
			move $t1, $v0  #$t1 is where the length of sparseA is saved, $v0 is where the value returned from the func has been stored
						
			#show how many values are in the sparse array
			div $a0, $t1, 2
			li $v0, 1
			syscall
						
			li $v0, 4
			la $a0, values
			syscall
						
			j MAIN_FOR
	   
		createB:
			li $v0, 4
			la $a0, creatingSB
			syscall
				
		        la $a0, arrayB
			la $a1, sArrayB
			jal CREATE_SPARSE_ARRAY
			move $t2, $v0    #t2 is where the length of sparseB is saved
						
			#show how many values are in the sparse array
			div $a0, $t2, 2
			li $v0, 1
			syscall
						
			li $v0, 4
			la $a0, values
			syscall
						
			j MAIN_FOR
	   
	   
		createC:
			li $v0, 4
			la $a0, creatingSC
			syscall
						
			##calls func CREATE_SPARSE_ARRAY_ADD that creates sparse arrayC which is the addition of sArrayA and sArrayB
			##the func CREATE_SPARSE_ARRAY_ADD has 5 parameters, we pass four of them using the four $a0 register
			##and the other one we can "pass" it by using the stack, so we store it in the stack 
						
			la $a0, sArrayA 				#a0 has the address of  sparse array A
			la $a1, sArrayB 				#a1 has the address of  sparse array B
			la $a2, sArrayC 				#a2 has the address of  sparse array A
			move $a3, $t1  				        #a3 has the length of sparse array A
			
						
			##in this part we store in the stack the temporary registers so that any changes of them
			## in the func want affect their values here(because afterwars we load them back, give them their previous values)
						
			addi $sp, $sp, -12
			sw $t2, ($sp)       			#let's not forget that t2 has the length of sparse array b
			sw $t1, 4($sp)
			sw $t9, 8($sp) 				#t9 is used in func to keep track of the num of elements in the sparse array C 
			jal CREATE_SPARSE_ARRAY_ADD
			move $t3, $v0 				#t3 is where the length of sparseC is saved
			
			
			##loading the previous values the temporay registers had
			lw $t2, ($sp)
			lw $t1, 4($sp)
			lw $t9, 8($sp)
			addi $sp, $sp, 12
						
			#show how many values are in the sparse array
			div $a0, $t3, 2
			li $v0, 1
			syscall
						
			li $v0, 4
			la $a0, values
			syscall
						
			j MAIN_FOR
		
	  
		dispA:
			li $v0, 4
			la $a0, printingSA
			syscall
						
			##calls the DISPLAY_SPARSE_ARRAY so that the elments of the sparse array A will be printed to the console
			##we pass as parameters the length of the array ,as well as the address(begging) of the array(same happens
			## also when we call  DISPLAY_SPARSE_ARRAY to print sparse arrays B and C)
						
			move $a0, $t1
			la $a1, sArrayA
			addi $sp, $sp, -4
			sw $t1, ($sp)
			jal DISPLAY_SPARSE_ARRAY
			lw $t1, ($sp)
			addi $sp, $sp, 4
		  
			j MAIN_FOR
	   
		dispB:
   			li $v0, 4
			la $a0, printingSB
			syscall
						
			move $a0, $t2
			la $a1, sArrayB
			addi $sp, $sp, -4
			sw $t2, ($sp)
			jal DISPLAY_SPARSE_ARRAY
			lw $t2, ($sp)
			addi $sp, $sp, 4
			j MAIN_FOR			
				      

 						
			
		dispC:
			li $v0, 4
			la $a0, printingSC
			syscall
						
			move $a0, $t3
			la $a1, sArrayC
			addi $sp, $sp, -4
			sw $t3, ($sp)
			jal DISPLAY_SPARSE_ARRAY
			lw $t3, ($sp)
			addi $sp, $sp, 4
			j MAIN_FOR
			
		EXIT_MAIN_FOR:
			##just printing a message to let the user know that the program has reached to the end
			li $v0, 4
			la $a0, endProgmsg
			syscall

			li $v0,10 
			syscall #exit the program


#####################################################################################
#####################################################################################
#####################################################################################
#Here we just get the user to give as 10 integers so that we can store then to the arrays, so that later in the program we can create 
#the sparse arrays

READ_ARRAY:
            ##this is the part were we store to the stack the saved registers so that their previous values are not lost
	    ##we later load them back
					
		addi $sp, $sp, -12
		sw $s0, 0($sp)
		sw $s1, 4($sp)
		sw $s2, 8($sp)
 
		li $s0,0 				#counter
		move $s1, $a0 		#address of array where the data read will be saved

		FOR:	
			beq $s0,10,EXIT_OF_FOR  #if the user has given 10 integers then exit the for loop
						
			#print the number in which position is gonna be read
			li $v0, 4
			la $a0, position
			syscall
						
			#print which number isread at this moment (...first? second?...)
			li $v0,1
			move $a0, $s0
			syscall
						
			#print ":" 
			li $v0, 4
			la $a0, theColon
			syscall
						
			# read users input
			li $v0,5
			syscall #read int
			move $s2,$v0
	                       
			sw $s2, ($s1)
			addi $s0, $s0, 1
			addi $s1, $s1 ,4
			j FOR

		EXIT_OF_FOR:
				
		##loading back the previous values the s registers had 
		lw $s0, 0($sp)
		lw $s1, 4($sp)
		lw $s2, 8($sp)
		addi $sp, $sp ,12
		jr $ra #returnToMain



#####################################################################################
#####################################################################################
#####################################################################################
#Creating the sparse arrays (A and B)(one at a time) by taking the non zero values and their position of the arrays that were read(given) by 
# the user

CREATE_SPARSE_ARRAY:
		##storing the values of the s registers
		addi $sp, $sp ,-20
		sw $s0, 0($sp)
		sw $s1, 4($sp)
		sw $s2, 8($sp)
		sw $s3, 12($sp)
		sw $s4, 16($sp)


		li $s0,0 					#counter to see if the regular array has eache to it's end	
		move $s1, $a0 			#used for array (address)
		move  $s2, $a1 			# for sparseArray (address)
		li $s3,0 					#counter of elements in sparse array

		FOR_S:
			beq $s0,10, EXIT_FOR_S 	#we have reache the end of the regular array
				lw $s4, ($s1) 				#current value of array
				beq $s4, 0,SKIP 			#if the value is 0, then we don't need to store it in the sparse array
				sw $s0, ($s2) 		#stores to the sparse array first the position of the element
				addi $s2, $s2,4
				sw $s4, ($s2) 		#and then stores the element
				addi $s2,$s2, 4
	
				addi $s3, $s3,2     #the elemnts of the sparse array are now increased by two
												
			SKIP:
				addi $s0, $s0, 1
				addi $s1, $s1, 4
				j FOR_S
												   
		EXIT_FOR_S:
			move $v0, $s3 #save to v0 the number of elements in sparse array, $v0 is the return value
												
			##loading back to the s registers their previous values
			lw $s0, 0($sp)
			lw $s1, 4($sp)
			lw $s2, 8($sp)
			lw $s3, 12($sp)
			lw $s4, 16($sp)
			addi $sp, $sp ,20
			jr $ra #return

#####################################################################################
#####################################################################################
#####################################################################################
#printing the values of the sparse array (that were created above )

DISPLAY_SPARSE_ARRAY:
		##storing the values of the s registers
		addi $sp, $sp, -12
		sw $s0, 0($sp)  
		sw $s1, 4($sp)  
		sw $s2, 8($sp)  

		move $s0, $a0 		#length of sparse array
		li $s1, 0           		#counter
		move $s2, $a1 		# for sparse array (address)
									
		FOR_DISPLAY_S_ARRAY:
				beq $s1, $s0, EXIT_FOR_DISPLAY_S_ARRAY ##exit for if all elemnts of the sparse array have been displayed
													
					#prints "position"
					li $v0,4
					la $a0, position
					syscall 
															
					#prints ": "
					la $a0, theColon
					syscall 
														
															
					#prints the position of the integer that is to be folllowed
					li $v0, 1
					lw $a0, ($s2)
					syscall    
					addi $s2, $s2, 4
															
					#prints "value: "
					li $v0,4
					la $a0,val
					syscall
															
					#prints the integer that was given by the user
					li $v0, 1
					lw $a0, ($s2)
					syscall
					addi $s2, $s2, 4
															
					#it just skips a line("\n") 
					li $v0, 4
					la $a0,theN
					syscall
															
					addi $s1, $s1 ,2
					j FOR_DISPLAY_S_ARRAY

		 EXIT_FOR_DISPLAY_S_ARRAY:
												
	    	##loading back to the s registers their previous values
		lw $s0, 0($sp)  
		lw $s1, 4($sp)  
		lw $s2, 8($sp)  
		addi $sp, $sp, 12

		jr $ra #return

#####################################################################################
#####################################################################################
#####################################################################################
#Creating the sparse arrays C by adding sparseArrayA and sparseArrayB(it is like we merge the sparse arrays A and B)

CREATE_SPARSE_ARRAY_ADD:
			lw $t1, ($sp) #this is where we stored the length of the sparse arrayB, because we have only four a registers
		        #and we needed to pass 5 parameters
			##storing the values of the s registers
			addi $sp, $sp , -28
			sw $s0, 0($sp)
			sw $s1, 4($sp)
			sw $s2, 8($sp)
			sw $s3, 12($sp)
			sw $s4, 16($sp)
			sw $s5, 20($sp)
			sw $s6, 24($sp)



			move $s2, $a0 #addres of sparse A
			move $s3, $a1 #addres of sparse B
			move $s4, $a2 #addres of sparse C

			move $s0, $a3 #length of sparse A
			move $s1, $t1  #length of sparse B

			li $t1, 0 #counter for sparse A
			li $t2, 0 #counter for sparse B
			li $t9, 0  #counter for num of elements in sparse array C


			FOR_S_C:
				bge $t1, $s0, EXIT_S_C #if we have reachrd the end of sparce A
				bge $t2, $s1, EXIT_S_C #if we have reachrd the end of sparce B
					lw $s5, ($s2) #element in sparse A
					lw $s6, ($s3) #element in sparse B
														  
					blt $s5, $s6, JUMP1  #if the position of s5 is less that the one of the s6 go to jump
					blt $s6, $s5, JUMP2  #if the position of s5 is less that the one of the s6 go to jump1
						#s5, s6 are equal
						##int this part we store to the new sparse array C the position of the A sparse array
						##(sArrayB and sArray in this part have the same position) and then we just add the 
						##values that the initial arrays had in those positions
																	 
						##this part just stores to the C sparse array a position and it's value, same also
						##does the code below(which is similar)
						lw $s5 , ($s2)
						addi $s2, $s2, 4
						sw $s5, ($s4)
						addi $s4, $s4, 4
																	 
						lw $s5, ($s2)
						addi $s2, $s2 ,4
																	 
						addi $s3, $s3, 4
						lw $s6, ($s3)
						addi $s3, $s3 ,4
						add $s5, $s5, $s6 
						sw $s5,($s4)
						addi $s4, $s4, 4
																	 
						addi $t1, $t1, 2 #update how many elements of sa have already been used
						addi $t2, $t2, 2 #update how many elements of sb have already been used
						addi $t9, $t9 ,2 #update the num of elements in sparse array C
						j FOR_S_C
					JUMP1:
						##so in this part of the program we add to the new sparse array first the position of
						##the a sparse array and then the value
																
    						lw $s5, ($s2)
						addi $s2, $s2, 4
						sw $s5, ($s4)
						addi $s4, $s4, 4
																 
						lw $s5, ($s2)
						addi $s2, $s2 ,4
						sw $s5, ($s4)
						addi $s4, $s4, 4
																	
						addi $t1, $t1, 2 #update how many elements of sa have already been used
						addi $t9, $t9 ,2 #update the num of elements in sparse array C
						j FOR_S_C
					JUMP2:
						##so in this part of the program we add to the new sparse array first the position of
						##the b sparse array and then the value
																	 
						lw $s6, ($s3)
						addi $s3, $s3, 4
						sw $s6,($s4)
						addi $s4, $s4, 4
																 
						lw $s6, ($s3)
						addi $s3, $s3 ,4
						sw $s6,($s4)
						addi $s4, $s4, 4
																	 
						addi $t2, $t2, 2 #update how many elements of sb have already been used
						addi $t9, $t9 ,2 #update the num of elements in sparse array C
						j FOR_S_C
			EXIT_S_C:
											
				#FOR_A will only be excecuted if B sparse array is the one that all his elements are in the new sparce array cC
				#otherwise it means that A sparse array is the one that all his elements are in the new sparce array C
				#so the FOR_B will be excecuted
											
			FOR_A:
			    beq $t1, $s0, EXIT_FOR_A    #if not all elements of sarrayA have been puted in the new s array then put them
				lw $s5, ($s2)
				addi $s2, $s2, 4
				sw $s5, ($s4)
				addi $s4, $s4 ,4
														 
				lw $s5, ($s2)
				addi $s2, $s2, 4
				sw $s5, ($s4)
				addi $s4, $s4 ,4
														 
				addi $t1, $t1, 2 #update how many elements of sa have already been used
				addi $t9, $t9 ,2 #update the num of elements in sparse array C
			     j  FOR_A
			EXIT_FOR_A :

			FOR_B:
			    beq $t2, $s1, EXIT_FOR_B     #if not all elements of sarrayB have been puted in the new s array then put them 
				lw $s6, ($s3)
				addi $s3, $s3, 4
				sw $s6, ($s4)
				addi $s4, $s4 ,4
														 
				lw $s6, ($s3)
				addi $s3, $s3, 4
				sw $s6, ($s4)
				addi $s4, $s4 ,4
														 
				addi $t2, $t2, 2 #update how many elements of sa have already been used
				addi $t9, $t9 ,2 #update the num of elements in sparse array C
			    j FOR_B
			EXIT_FOR_B :


			move $v0, $t9   #the number of elements in sparse array C is returned

			##loading back to the s registers their previous values
			lw $s0, 0($sp)
			lw $s1, 4($sp)
			lw $s2, 8($sp)
			lw $s3, 12($sp)
			lw $s4, 16($sp)
			lw $s5, 20($sp)
			lw $s6, 24($sp)
			addi $sp, $sp, 28
											
			jr $ra #return 



#####################################################################################
#####################################################################################
#####################################################################################
#Telling the user all the options available for him to choose

PRINT_OPTIONS:
		li $v0,4
		la $a0,theN
		syscall
						
		la $a0, theDash
		syscall
						
		la $a0, op1
		syscall

		la $a0, op2
		syscall
                        
		la $a0, theDash
		syscall
						
		la $a0, op3
		syscall

		la $a0, op4
		syscall

		la $a0, op5
		syscall

		la $a0, theDash
	      	syscall
						
		la $a0, op6
		syscall

		la $a0, op7
		syscall

		la $a0, op8
		syscall
						
		la $a0, theDash
		syscall
						
		la $a0, op0
		syscall
						
		la $a0, theDash
		syscall
						
		la $a0, choosemsg
		syscall

	       	jr $ra
				