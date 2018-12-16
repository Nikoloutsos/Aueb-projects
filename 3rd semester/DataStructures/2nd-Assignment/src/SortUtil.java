
public class SortUtil {

    public static <T extends Comparable<T>>void mergeSort(T[] songArray, int l, int r) {
        if(l<r) {
            int m= (l+r)/2;
            mergeSort(songArray,l,m);
            mergeSort(songArray,m+1,r);
            merge(songArray,l,m,r);
        }
    }

    private static <T extends Comparable<T>> void merge(T[] songArray, int l, int m, int r) {
        //calculating the size of the temporary arrays
        int len1 =m-l+1;
        int len2 = r-m;
        //creating temp arrays
        T leftArr [] = (T[])new Comparable[len1];
        T rightArr[] = (T[])new Comparable[len2];

        for(int i=0;i<len1;i++)
            leftArr[i]= songArray[l+i];
        for(int j=0;j<len2;j++)
            rightArr[j]=songArray[m+1+j];

        int i=0, j=0;
        int index=l;

        while(index<=r) {
            if(i<len1 && j<len2) {
                if(leftArr[i].compareTo(rightArr[j])==1){ // left is bigger
                    songArray[index] = leftArr[i];
                    index++;
                    i++;

                }else {
                    songArray[index] = rightArr[j];
                    j++;
                    index++;
                }


            }else if(i<len1) {
                songArray[index] = leftArr[i];
                i++;
                index++;
            }else {
                songArray[index] = rightArr[j];
                j++;
                index++;
            }

        }

    }
}