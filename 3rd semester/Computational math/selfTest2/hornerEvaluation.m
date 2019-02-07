
function retval = hornerEvaluation (coef, x)
  retval = zeros(1,size(x)(1,2));   #return values
  
  for i = 1:size(x)(1,2)
    sum = coef(i); 
    
    for j = 2:(size(coef)(1,2))
      sum = sum*x(1,i) + coef(1,j); #Horner's formula
    endfor
    retval(1,i) = sum;  #Store the result
    
   
  endfor  

endfunction
