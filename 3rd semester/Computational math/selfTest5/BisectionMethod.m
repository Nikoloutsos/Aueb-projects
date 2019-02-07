
function retval = BisectionMethod (f, x1, x2, tolerance)
  #Checks
  if(sign(f(x1))*sign(f(x2)) >= 0)
  printf("f(x1)*f(x2)>0 . They ought to have different sign"); #Bad inputs
  return;
  endif
  
  
  size = abs(x1-x2);
  
  while(size > tolerance) #Bisection formula
  m = (x1+x2)/2;
  size = size/2;
  fm = f(m);
  if(fm==0)
    retval = m;
    return;
  elseif (sign(fm)*sign(f(x1)) > 0)
    x1 = m;
  else
    x2 = m;
  endif
  
  retval = fm;
  
  
  
endwhile
retval = 0;
printf("No solution found!");
endfunction
