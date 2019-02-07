n = zeros(1,10);
for i = 1:10   # n = 5,10..50
  n(1,i) = i*5;
endfor

valuesFromIntegration = zeros(1,10);
expectedValue = -cos(pi) + cos(0); #The real integration value needed for finding the error.

for i = 1:10
x = linspace(0,pi, n(1,i)); #Divides equally the range [0,pi]
y = sin(x); # It's like doing sin(x(1)) , sin(x(2)) ... sin(x(10)) but it's way faster.

computedValue = naiveintegration(x,y);
valuesFromIntegration(1,i) = computedValue;


endfor


difInIntegrationValues = abs(valuesFromIntegration - expectedValue); #The error.


figure
hold on
plot( n , difInIntegrationValues )

title("Error")
hold off

