/*
 * File: Combinations.java
 *
 * This program computes the mathematical combinations function
 * C(n, k), which is the number of ways of selecting k objects
 * from a set of n distinct objects.
 */
import acm.program.*;
public class Combinations extends ConsoleProgram {
    public void run() {
        int n = readInt("Enter number of objects in the set (n): ");
        int k = readInt("Enter number to be chosen (k): ");
        if (n >= k && k >= 0)
            println("C(" + n + ", " + k + ") = " + combinations(n, k));
        else
            println("Please enter n >= k >= 0");
    }

    // end of run
    /*
     * Returns the mathematical combinations function C(n, k),
     * which is the number of ways of selecting k objects
     * from a set of n distinct objects.
     */
    int combinations(int n, int k) {
        int i;
        int factorial_n = 1;
        for (i = 1; i <= n; i++) {
            factorial_n *= i;
        }
        int factorial_k = 1;
        for (i = 1; i <= k; i++) {
            factorial_k *= i;
        }
        int factorial_n_k = 1;
        for (i = 1; i <= n - k; i++) {
            factorial_n_k *= i;
        }
        return factorial_n / (factorial_k * factorial_n_k);
    }
}