function retval = PolyEvaluation (x, factor)
  retval = zeros(size(x,1), size(x,2));
  
  for i = 1:1:size(x,2) %For every x
    sum = factor(1);
    xValue = x(i);
    
    for j = 2:1:size(factor,2) %For every factor
      
      sum = sum + xValue^(j - 1) * factor(j);
      
    endfor 
    retval(i) = sum;
  endfor  
  
endfunction