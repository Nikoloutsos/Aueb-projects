import java.io.PrintStream;
import java.util.ArrayList;


///////////////////////////////////////////////////////////////////////////
// Diet-Problem
///////////////////////////////////////////////////////////////////////////
public class Exercise2 {

    /*Global variables*/
    private final int C;
    private int[][] F;
    private Pair dp[][];
    int total_items;


    //Data injection
    public Exercise2(int c, int[][] f) {
        total_items = f[0].length;
        C = c;
        F = f;
    }


    public synchronized void solveExercise2() {
        //init dp
        dp = new Pair[C + 1][total_items + 1];
        for (int i = 0; i <= C; i++) {
            dp[i][0] = new Pair(0, 0);
        }
        for (int i = 0; i <= total_items; i++) {
            dp[0][i] = new Pair(0, 0);
        }

        //Start Bottom up approach(Suggested for not getting a stackOverflow exception ;)

        for (int i = 1; i <= total_items; i++) {
            for (int j = 0; j <= C; j++) {
                if (F[0][i - 1] > j) {
                    dp[j][i] = dp[j][i - 1];
                } else {
                    int cal = F[0][i - 1];
                    int fat = F[1][i - 1];

                    dp[j][i] = returnBestPair(Pair.addTwoPairs(new Pair(cal, fat), dp[j - cal][i - 1]), dp[j][i - 1]);
                }
            }
        }


    }

    public void printSolution(PrintStream pStream) {
        ArrayList<Integer> foodsUsedInOptimalSolution = findItemsUsedInOptimalSolution();

        pStream.print("Menu was created. \nTotal Calories : " + dp[C][total_items].calorie + ", total fat : " + dp[C][total_items].fat
        + "\nContains : " + "{ ");

        for (Integer c : foodsUsedInOptimalSolution) {
            pStream.print("food " + c + " ");
        }
        pStream.print("}\n\n");

    }


    /*Helping methods*/
    private ArrayList<Integer> findItemsUsedInOptimalSolution() {
        ArrayList<Integer> result = new ArrayList<>();
        int i = C;
        int j = total_items;

        Pair current = dp[i][j];
        //Back-tracking
        while (j > 0) {
            j--;
            if(current.equals(dp[i][j])){
                continue;
            }else{
                result.add(j+1);
                i = C - F[0][j];
                current = dp[i][j];
            }
        }
        return result;

    }

    //Returns which pair is better from the two
    private Pair returnBestPair(Pair p1, Pair p2) {
        if (p1.isBetterThan(p2)) return p1;
        return p2;
    }

    /*Holds a Calorie-fat pair*/
    private static class Pair {
        int calorie;
        int fat;

        public Pair(int calorie, int fat) {
            this.calorie = calorie;
            this.fat = fat;
        }

        //Returns true if pair is "better" than argument p.
        public boolean isBetterThan(Pair p) {
            if (this.calorie > p.calorie) return true;
            if (this.calorie == p.calorie && this.fat <= p.fat) return true;
            return false;
        }


        //Creates a new pair from the two given.
        public static Pair addTwoPairs(Pair p1, Pair p2) {
            return new Pair(p1.calorie + p2.calorie, p1.fat + p2.fat);
        }



        //Useful for debugging
        @Override
        public String toString() {
            return "Pair --> (" + calorie + ", " + fat + ")";
        }



        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pair) {
                return this.calorie == ((Pair) obj).calorie && this.fat == ((Pair) obj).fat;
            }
            return false;
        }
    }
}
