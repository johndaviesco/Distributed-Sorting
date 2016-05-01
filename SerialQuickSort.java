import java.io.IOException;

/**
 * @author John Davies-Colley
 *
 */
public class SerialQuickSort {
    private int array[];
    private int length;

    /**
     * Sort Setup
     * @param inputArray
     */
    public void sort(int[] inputArray) {
        if (inputArray == null || inputArray.length == 0) {
            return;
        }
        this.array = inputArray;
        length = inputArray.length;
        quickSort(0, length - 1);
    }

    /**
     * Main Quick sort method
     * @param lowerIndex
     * @param higherIndex
     */
    private void quickSort(int lowerIndex, int higherIndex) {
        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number
        int pivot = array[lowerIndex+(higherIndex-lowerIndex)/2];
        // Divide into two arrays
        while (i <= j) {
            while (array[i] < pivot) {
                i++;
            }
            while (array[j] > pivot) {
                j--;
            }
            if (i <= j) {
                swap(i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort method recursively
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }

    /**
     * swap method
     * @param i
     * @param j
     */
    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Main performs the file read initiates
     * the sort and calculates the sort time
     * @param a
     */
    public static void main(String a[]){
		Scanner s = new Scanner();
		int[] inputArray = null;
		try {
			inputArray = s.scan(a);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    	SerialQuickSort sorter = new SerialQuickSort();
    	long startTime = System.currentTimeMillis();
        sorter.sort(inputArray);
        long endTime   = System.currentTimeMillis();
//        for(int i:inputArray){
//            System.out.print(i);
//            System.out.print("\n");
//        }
        long totalTime = endTime - startTime;
        System.out.printf("Sort finished in: %d ms\n", totalTime);
    }
}
