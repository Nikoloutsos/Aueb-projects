
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Should you pass this test you are good to go..!
 * Created for Algorithms subject (4th semester) AUEB university.
 *
 */
class Askisi1_1Test {
    Random ran = new Random();


    /**
     * ---------------
     * Testing methods
     * ---------------
     */



    /**
     * checks for emptyList
     *
     */
    @Test
    void shouldReturnMinusOne() {
        List<Integer> emptyList = createEmptyList();

        int expected_Value = -1;

        for(int i=0; i<=1000; ++i){
            int integer_To_Be_Found = ran.nextInt(2019);
            int result_From_Finding_First_Occurence = Askisi1_1.findFirstOccurrence(emptyList, i, 0, 0);
            int result_From_Finding_Last_Occurence = Askisi1_1.findLastOccurrence(emptyList, i, 0, 0);

            assertTrue(result_From_Finding_First_Occurence == expected_Value);
            assertTrue(result_From_Finding_Last_Occurence == expected_Value);
        }
    }




    /**
     * Check with createAscendingOrderedList as data.
     */
    @Test
    void shouldReturnSameResultFirstAndLastAscending() {
        List<Integer> ascendingList = createAscendingOrderedList(1000);

        for(int i=400;i<=500;++i){
            int occurrence = Askisi1_1.doNaiveBinarySearch(ascendingList, i, 0, ascendingList.size() - 1 );
            int result_From_Finding_First_Occurence = Askisi1_1.findFirstOccurrence(ascendingList, i, 0,
                    occurrence);
            int result_From_Finding_Last_Occurence = Askisi1_1.findLastOccurrence(ascendingList, i,
                    occurrence, ascendingList.size() -1 );

            assertTrue(result_From_Finding_First_Occurence == i - 1);
            assertTrue(result_From_Finding_Last_Occurence == i - 1);
        }
    }



    /**
     * Check with createAscendingOrderedList as data.
     */
    @Test
    void simpleCaseScenarioTest() {
        List<Integer> simpleList = createSimpleCaseScenario();
        int searchingForNumber = 0;
        int firstOccurrenceExpectation = 0;
        int lastOccurrenceExpectation = 2;

        int occurrence = Askisi1_1.doNaiveBinarySearch(simpleList, searchingForNumber, 0, simpleList.size() - 1 );
        int result_From_Finding_First_Occurence =  Askisi1_1.findFirstOccurrence(simpleList, searchingForNumber,
                0, occurrence);
        int result_From_Finding_Last_Occurence =  Askisi1_1.findLastOccurrence(simpleList, searchingForNumber, occurrence, simpleList.size() - 1);


        assertTrue(result_From_Finding_First_Occurence == firstOccurrenceExpectation);
        assertTrue(result_From_Finding_Last_Occurence == lastOccurrenceExpectation);
    }


    /**
     * Check with createListWithOneNumber as data.
     */
    @Test
    void checkCreateListWithOneNumber() {
        List<Integer> sameNumberList = createListWithOneNumber(1000, 2019);
        int firstOccurrenceExpectation = 0;
        int lastOccurrenceExpectation = sameNumberList.size() - 1;

        int occurrence = Askisi1_1.doNaiveBinarySearch(sameNumberList,2019, 0, sameNumberList.size() -1);


        int result_From_Finding_First_Occurence =  Askisi1_1.findFirstOccurrence(
                sameNumberList, 2019, 0, occurrence);

        int result_From_Finding_Last_Occurence = Askisi1_1.findLastOccurrence(sameNumberList, 2019, occurrence, sameNumberList.size() - 1);



        assertTrue(result_From_Finding_First_Occurence == firstOccurrenceExpectation);
        assertTrue( result_From_Finding_Last_Occurence == lastOccurrenceExpectation);
    }


    @Test
    void checkNumberDoesntExistInList(){
        List<Integer> list =  createAscendingOrderedList(100);
        int number_That_Doesnt_Exist_In_List = 1333;

        int occurence = Askisi1_1.doNaiveBinarySearch(list, number_That_Doesnt_Exist_In_List,
                0, list.size() - 1);

        assertTrue(Askisi1_1.findLastOccurrence(list, number_That_Doesnt_Exist_In_List, occurence, list.size() - 1) == -1);
        assertTrue(Askisi1_1.findLastOccurrence(list, number_That_Doesnt_Exist_In_List, 0, occurence) == -1);

    }







    /**
     * --------------------------------------
     * List<Integer> factory used for testing purposes
     * --------------------------------------
     */


    /**
     * {}
     */
    private List<Integer> createEmptyList() {
        return new ArrayList<>();
    }

    /**
     * {1,2,3,4,5....sizeOfList}
     */
    private List<Integer> createAscendingOrderedList(int sizeOfList) {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 1; i <= sizeOfList; ++i){
            list.add(i);
        }
        return list;
    }

    /**
     * {sizeOfList......5,4,3,2,1}
     */
    private List<Integer> createDescendingOrderedList(int sizeOfList) {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = sizeOfList; i <= 1; --i){
            list.add(i);
        }
        return list;
    }

    /**
     * {2019,2019,2019.......,2019}
     */
    private List<Integer> createListWithOneNumber(int sizeOfList, int number) {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 1; i <= sizeOfList; ++i){
            list.add(number);
        }
        return list;
    }

    /**
     * {2019,2019,2019.......,2019}
     */
    private List<Integer> createSimpleCaseScenario() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0); //0
        list.add(0);
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(4);
        list.add(5); //9

        return  list;
    }






}