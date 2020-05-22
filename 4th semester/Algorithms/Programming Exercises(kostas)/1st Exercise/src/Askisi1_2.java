

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Askisi1_2 {
    static Random rand = new Random();
    private static String PATH_FOR_READING_FILE_WITH_INTEGERS = "C:\\Users\\KwstasNiks\\IdeaProjects\\AlgorithmsKatia\\src\\DataSets\\dataset1.txt";


    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        updateWithValuesFromCommandLine(args);

        try {
            arrayList = (ArrayList<Integer>) Utilities.convertFileSequenceToList(new File(PATH_FOR_READING_FILE_WITH_INTEGERS));
        } catch (Exception e) {
            System.err.println("Problem with finding the file you specified(please change it) : \n" + e.getMessage());
            System.exit(1);
        }

        quickSort(arrayList);
        System.out.println("QuickSort finished : ");
        System.out.println(arrayList);

    }


    public static ArrayList<Integer> quickSort(ArrayList<Integer> array, int low, int high) {
        if(low >= high) return  array;
        Pair partitionInfo = partition(array, low, high);

        if(partitionInfo == null) return array;
        quickSort(array, low, partitionInfo.number1 - 1);
        quickSort(array, partitionInfo.number2 + partitionInfo.number1, high);

        return array;
    }

    public static ArrayList<Integer> quickSort(ArrayList<Integer> array) {
        return quickSort(array, 0, array.size() - 1);
    }



    private static Pair partition(ArrayList<Integer> array, int low, int high) {
        if (low >= high) return null;
        int randomNumber = rand.nextInt(high - low) + low;
        int pivot = array.get(randomNumber);
        ArrayList<Integer> smallerGroup = new ArrayList<>();
        ArrayList<Integer> equalGroup = new ArrayList<>();
        ArrayList<Integer> greaterGroup = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();

        for (int i = low; i<=high; ++i) {
            if (array.get(i) > pivot) greaterGroup.add(array.get(i));
            else if (array.get(i) == pivot) equalGroup.add(array.get(i));
            else smallerGroup.add(array.get(i));
        }

        temp.addAll(smallerGroup);
        temp.addAll(equalGroup);
        temp.addAll(greaterGroup);
        for (int i = low; i <= high; ++i) {
            array.set(i, temp.get(i - low));
        }

        return new Pair(smallerGroup.size() + low, equalGroup.size());
    }

    private static void updateWithValuesFromCommandLine(String[] args) {
        try{
            PATH_FOR_READING_FILE_WITH_INTEGERS = args[0];
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Default path is running.(Read the report.pdf for changing it)");
        }catch (Exception e){
            System.err.println("Something is going wrong please read the report.pdf");
        }
    }


}
