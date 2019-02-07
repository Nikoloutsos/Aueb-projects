function retval = naivePolyEvaluation (coef, x)
  retval = zeros(1,size(x)(1,2));
  order = size(coef)(1,2) - 1;   # x^2 + x + 1
  
  for i = 1:size(x)(1,2) 
    sum = 0;
    
    for j = order:-1:0
      sum = sum + coef(size(coef)(1,2) - j ) * x(i)^j;
      
      
    endfor  
    
    retval(1,i) = sum;
  
  
  endfor

endfunction
