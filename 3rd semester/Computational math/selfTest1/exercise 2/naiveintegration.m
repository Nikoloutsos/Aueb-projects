function integral = naiveintegration( x , y )
  if length(x) != length(y)
    display('Different length for entry vectors')
    return
  endif
  
  for i = 2:length(x)
    if x(i) < x(i-1)
      display('x vector is not sorted')
      return
    endif
  end
  n = length(x);
  
  h = x(2:n) - x(1:n-1);
  integral = sum( ( 0.5 * ( y(2:n) + y(1:n-1)) ).* h ); 