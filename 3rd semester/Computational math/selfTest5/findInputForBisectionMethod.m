
#Find the x1,x2 for bisection method.
function retval = findInputForBisectionMethod (f,a,b,h,numberOfX1X2)
  retval = zeros(1,numberOfX1X2);
  retval(1,1) = a;
  
  index = 2;
  
  for i = a:h:b
  if(sign( f(retval(1,index-1)) )*sign(f(i)) < 0)
  retval(1,index) = i;
  index= index + 1;
  endif

  endfor  
    

endfunction
