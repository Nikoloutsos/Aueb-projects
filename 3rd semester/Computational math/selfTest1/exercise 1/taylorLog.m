
function retval = taylorLog (x, n)
  sizeOfx = size(x)(1,2);
  retval = zeros(1,sizeOfx);
  
  for i = 1:sizeOfx
    sum = 0;
    
    for j = 1:n
      sum = sum + ((-1)^(j+1)) * (x(1,i)^j)/j;
    endfor
    
    retval(1,i) = sum;
  endfor

endfunction
