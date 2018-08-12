import matplotlib.pyplot as plt #This is for plotting the results.
import pandas as pd #This is for creating the table.
def F(x,y):
    return(y*(y-1))
#EULER'S METHOD
def euler(f, x0, y0, delta_x, n):
    x0_lst = [x0]
    y0_lst = [y0]
    for i in range(0,n):
        x0_lst.append(x0_lst[i]+delta_x)
        y0_lst.append(y0_lst[i]+delta_x*f(x0_lst[i], y0_lst[i]))
    df = pd.DataFrame()
    df["x"]= x0_lst
    df["y"]= y0_lst
    print(df)
    plt.figure(num='Toubis homework||Differential equations')
    plt.scatter(x0_lst, y0_lst, label="Euler's method. y'(x)=y(x)(1-y(x))")
    plt.legend()
    plt.show()

euler(F, 0, 0.5, 0.5, 10)