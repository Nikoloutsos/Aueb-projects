x = -0.99:0.1:1.3;
yLog = log(1+x);
yTaylor = taylorLog( x , 30)

figure
hold on
plot( x , yLog , 'k' , 'LineWidth' , 6)
plot( x , yTaylor , 'r' , 'LineWidth' , 2)
title(['Log(1+x) black line';'Taylor approximation of order 30 red line'])
hold off