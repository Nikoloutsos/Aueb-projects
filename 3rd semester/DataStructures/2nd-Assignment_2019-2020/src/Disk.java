/***
 * Disk class
 */

public class Disk implements Comparable<Disk> {
    private static int counter = 1;
    private String disk_UUID = "Disk ";
    private MaxPQ<Integer> folders = new MaxPQ<>(new IntegerComparator());
    private static int megabytes = 1000000;
    private int capacity_remaining = 1000000;


    /***
     * Disk empty constructor
     */
    public Disk() {
        this.disk_UUID = this.disk_UUID + counter;
        counter++;
        folders.insert(0);
    }

    /**
     *
     * @return Disk's UUID
     */
    public String getUUID() {
        return disk_UUID;
    }

    /**
     *
     * @return remaining free space
     */
    public int getFreeSpace() {
        return capacity_remaining;
    }

    /**
     *
     * @param folder the folder that will be added ("saved") in this disk
     *               inrements also the capacity that is used
     */
    public void addFolder(int folder){
        this.folders.insert(folder);
        capacity_remaining -=folder;
    }


    /**
     *
     * @return the biggest folder saved in this disc, and removes it
     */
    public int removeFolder(){
        capacity_remaining += this.folders.peek();
        return this.folders.getMax();
    }

    /**
     *
     * @return the biggest folder saved in this disc without removing it
     */
    public int peekFolder(){
        return this.folders.peek();
    }

    public int folderSize(){
        return this.folders.getSize();
    }
    /**
     *
     * @param b is a Disk which we compare with this disc
     * @return 1 if Disk free Space > b free space, 0 if the Disk free space = b free space, -1 if the Disk free space < b free space
     */
    @Override
    public int compareTo(Disk b) {
        return Integer.compare(this.getFreeSpace(), b.getFreeSpace());
    }
}
