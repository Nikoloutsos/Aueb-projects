import math
def taylor_polynomial_sinx(x, x0, error):
    """

    :param x: x must be in this set [0,pi/2].If you give x=1 then maybe you want to calculate sin(1).
    :param x0: Taylor polynomial centered at x0
    :param error: The smaller error the more precice results you'll receive.
    :return: sin(x)
    """
    n=0
    while(True): #Find the smallest degree of derivative in order to compute smaller errors than error variable.
        if((((x-x0)**(n+1))/math.factorial(n+1)) < error):
            break
        else:
            n += 1
    output = 0
    for i in range(0,n): #This is the math formula(Sum from k=0 to k=n).
        output += (((-1)**i)/math.factorial(2*i+1))*x**(2*i+1)
    return output
print(taylor_polynomial_sinx(math.pi/4, 0, 10**-6))
print(taylor_polynomial_sinx(math.pi/2, 0, 0.0000001))