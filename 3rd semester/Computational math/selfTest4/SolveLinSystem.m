
function retval = SolveLinSystem (A, b)
  
  # Do checks --> 1.square matrix 2.lower triangular 3. no 0 in diagonal 4. restrictions for b
  
  legthOfA = size(A)(1,1);
  retval = ones(size(A)(1,2),1);
  
  for i = 1:legthOfA;
    extra = 0;
    
    for j = 1:i-1
      extra = extra + retval(j,1)*A(i,j);
    endfor
    
    retval(i,1) = (b(i,1) - extra )/A(i,i);
   
  endfor  
endfunction
