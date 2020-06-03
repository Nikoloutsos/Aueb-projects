import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * ExperimentalStudy Class
 */
public class ExperimentalStudy {

    /**
     *
     * @param args for file input through CMD
     * @throws Exception Exception in case that file read/write is unsuccessful or data are wrong
     */
    public static void main(String[] args) throws Exception {
        long greedy_time = 0;
        long sort_time = 0;
        long start_time;
        long stop_time;
        int filecounter=1;
        int sum_of_disks_Greedy = 0;
        int sum_of_disks_Sort = 0;
        int sum_of_disk_Greedy_overall = 0;
        int sum_of_disk_Sort_overall = 0;
        int[] count_of_folders = getRandomList(5, 10000);
        for (int count_of_folder : count_of_folders) {
            for (int i = 0; i<10; i++){
                int[] folder_size = getRandomList(count_of_folder,1000000);
                String string = write("Experimental"+"_FileSeq_" +(i+1)+"_Nnum_"+filecounter, folder_size);
                start_time = System.nanoTime();
                sum_of_disks_Greedy = greedyReader(string);
                stop_time = System.nanoTime();
                greedy_time+=(stop_time-start_time);
                start_time = System.nanoTime();
                sum_of_disks_Sort = sortReader(string);
                stop_time = System.nanoTime();
                sort_time += (stop_time-start_time);
            }
            filecounter++;
            System.out.println("Disks used on average for 10 files created with " + count_of_folder + " folders: \nGreedy: " + sum_of_disks_Greedy/10 + " | Sort: " + sum_of_disks_Sort/10);
            sum_of_disk_Greedy_overall +=sum_of_disks_Greedy;
            sum_of_disk_Sort_overall += sum_of_disks_Sort;
        }
        System.out.println("Greedy disks used on average all files: " + sum_of_disk_Greedy_overall/5);
        System.out.println("Sort disks used on average for all files: " + sum_of_disk_Sort_overall/5);
        System.out.println("Greedy time needed: " + greedy_time/1_000_000 + " millisec");
        System.out.println("Sort time needed: " + sort_time/1_000_000 + " millisec");

    }

    /**
     * Creates random int table
     * @param streamSize the number of elements
     * @param highest the highest(non inclusive) number
     * @return an integer table
     */
    private static int[] getRandomList(int streamSize, int highest){
        Random random = new Random();
        int[] ints = new int[streamSize];
        for(int i = 0; i < streamSize; i++){
            ints[i] = random.nextInt(highest+1);
        }
        return ints;
    }

    /**
     * Writes a file
     * @param filename has the desired name of the file to be created
     * @param ints an int table which will be transfered to the file
     * @return a string with the file name
     * @throws Exception in case of wrong read
     */
    private static String write (String filename, int[] ints) throws Exception {
        try{
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(filename));
            for (int i : ints) {
                outputWriter.write(Integer.toString(i));
                outputWriter.newLine();
            }
            outputWriter.flush();
            outputWriter.close();
            return  filename;
        }
        catch (IOException ioException){
            throw new IOException(ioException + " occured");
        }
        catch (Exception exception){
            throw new Exception(exception + " occured");
        }
    }

    /**
     * Simulates Greedy.main() behavior
     * @param string the string with the file name
     * @return an int of disks used
     * @throws Exception in case of wrong file or wrong data
     */
    private static int greedyReader(String string) throws Exception {
        IntegerQueueImpl folder_size_queue;
        MaxPQ<Disk> diskMaxPQ;
        folder_size_queue = Greedy.read_Input(new File(string));
        diskMaxPQ = Greedy.allocate_Disks(folder_size_queue);
        return diskMaxPQ.getSize();
    }

    /**
     *Simulates Sort.main() behavior
     * @param string the string with the file name
     * @return an int of disks used
     * @throws Exception in case of wrong file or wrong data
     */
    private static int sortReader(String string) throws Exception {
        IntegerQueueImpl folder_size = Greedy.read_Input(new File(string));
        int folder_size_size = folder_size.size() ;
        int[] integers = Sort.integerQueueToTable(folder_size);
        Sort.quicksort(integers, 0, folder_size_size-1);
        folder_size = Sort.tableToIntegerQueueImpl(integers);
        MaxPQ<Disk> allocated_disks = Greedy.allocate_Disks(folder_size);
        return allocated_disks.getSize();
    }
}
