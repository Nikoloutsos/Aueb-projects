import java.io.*;
import java.text.NumberFormat;
import java.util.Locale;
/**
 * Greedy Class (deliverable B)
 */
public class Greedy{

    /**
     *
     * @param args for file input through cmd
     * @throws Exception in case that file read is unsuccessful or data are wrong
     */
    public static void main(String[] args) throws Exception {
        IntegerQueueImpl folder_size;
        MaxPQ<Disk> diskMaxPQ;
        folder_size = read_Input(new File(args[0]));
        diskMaxPQ = allocate_Disks(folder_size);
        print_Output(diskMaxPQ);
    }

    /**
     *
     * @param file the input file by the user
     * @return an IntegerQueue of the folders which were included in the input file
     * @throws Exception in case of wrong path or other formating error (eg. Folder size is <0 or >1.000.000 MB
     */
    static IntegerQueueImpl read_Input(File file) throws Exception {
        IntegerQueueImpl integerQueue = new IntegerQueueImpl() {
        };
        String temp;
        int line = 1;
        try{
            BufferedReader read = new BufferedReader(new FileReader(file));
            while((temp = read.readLine())!=null){
                if(Integer.parseInt(temp)>=0 && Integer.parseInt(temp)<=1000000) integerQueue.put(Integer.valueOf(temp));
                else{
                    throw new Exception("Excepion file size in line " + line + " is not between 0 and 1.000.000 MB");
                }
                line++;
            }
        }
        catch (FileNotFoundException filenotfoundexception) {
            throw new FileNotFoundException("File not found, consider using another type of path");
        } catch (IOException ioexception) {
            throw new IOException(ioexception + " occurred");
        } catch (Exception exception) {
            throw new Exception(exception + " occurred");
        }
        return integerQueue;
    }

    /**
     *
     * @param integerQueue is the queue which has the folders read from the file
     * @return a MaxPQ with the disks that we used
     */
    static MaxPQ<Disk> allocate_Disks(IntegerQueueImpl integerQueue){
        MaxPQ<Disk> diskMaxPQ = new MaxPQ<>(new DiskComparator());
        while(!integerQueue.isEmpty()){
            diskMaxPQ.insert(new Disk());
            while(!integerQueue.isEmpty() && diskMaxPQ.peek().getFreeSpace()>=integerQueue.peek()){
                Disk disc = diskMaxPQ.getMax();
                disc.addFolder(integerQueue.get());
                diskMaxPQ.insert(disc);
            }
        }
        return diskMaxPQ;
    }

    /**
     * Print the needed format if num of folders is <=100
     * @param diskMaxPQ the queue with the disks that we used
     */
    static void print_Output(MaxPQ<Disk> diskMaxPQ){
        int sum_of_folders = 0;
        int count_of_folders = 0;
        StringBuilder header = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        header.append("Number of disks used  = " ).append(diskMaxPQ.getSize()).append("\n");
        while(diskMaxPQ.peek()!=null){
            stringBuilder.append(diskMaxPQ.peek().getUUID()).append(" ").append(NumberFormat.getIntegerInstance(Locale.GERMANY).format(diskMaxPQ.peek().getFreeSpace())).append(": ");
            while(diskMaxPQ.peek().folderSize()>0){
                if((diskMaxPQ.peek().peekFolder() != 0)){
                    stringBuilder.append(NumberFormat.getIntegerInstance(Locale.GERMANY).format(diskMaxPQ.peek().peekFolder())).append(" ");
                    count_of_folders++;}
                sum_of_folders += diskMaxPQ.peek().peekFolder();
                diskMaxPQ.peek().removeFolder();
            }
            diskMaxPQ.getMax();
            stringBuilder.append("\n");
        }
        header.append("Sum of all folders = ").append(NumberFormat.getIntegerInstance(Locale.GERMANY).format(-sum_of_folders)).append(" TB\n");
        if(count_of_folders<=100){
            System.out.println(header.toString() + stringBuilder.toString());
        }
        else{
            System.out.println(header.toString());
        }
    }
}
