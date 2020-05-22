import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Askisi1_2Test {
    Random rand = new Random();

    @Test
    public void testRandomScenariosLargeTest(){
        final int ITERATIONS = 2000;
        ArrayList<Integer> arrayList = createRandomNumbersArraylsit(10000,600);

        for (int i = 0; i < ITERATIONS; ++i) {
            Collections.shuffle(arrayList);

            Askisi1.quickSort(arrayList); //Here is what we test.

            assertTrue(isArrayListInAscendingOrder(arrayList));
        }
    }

    @Test
    public void doNothingAtEmptyArrayList(){
        ArrayList<Integer> arrayList = new ArrayList<>();

        Askisi1.quickSort(arrayList);
        assertTrue(arrayList.isEmpty());

    }

    @Test
    public void testTeachersDataSet(){
        ArrayList<Integer> arrayList = createTeachersDataSet();
        final int ITERATIONS = 2000;

        for (int i = 0; i < ITERATIONS; i++) {
            Collections.shuffle(arrayList);

            Askisi1.quickSort(arrayList); //Here is what we test.

            assertTrue(isArrayListInAscendingOrder(arrayList));
        }
    }

    /**
     * Helping methods
     */

    private ArrayList<Integer> createRandomNumbersArraylsit(int size, int maxNumber){
        ArrayList<Integer> arrayList = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            arrayList.add(rand.nextInt(maxNumber));
        }

        return arrayList;
    }

    private ArrayList<Integer> createTeachersDataSet(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        String dataSet = "14 18 8 14 19 3 9 10 11 11 3 5 6 5 8 9 8 9 8 12 12 14 14 6 6 7 8 8 14 3 3 9 14 21 8 8 9 9 21 21 21";
        String[] tokens = dataSet.split(" ");
        for (String s : tokens) {
            arrayList.add(Integer.valueOf(s));
        }
        return arrayList;
    }

    private boolean isArrayListInAscendingOrder(ArrayList<Integer> arrayList) {
        for (int i = 1; i < arrayList.size(); ++i) {
            if(arrayList.get(i) < arrayList.get(i-1)) return false;
        }
        return true;
    }



}