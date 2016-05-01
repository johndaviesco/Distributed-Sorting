
import java.io.IOException;

/**
 * @author John Davies-Colley
 *
 */
public class SerialMergeSort {
    private int[] array;
    private int[] tempMergArr;
    private int length;

    /**
     * Sort Setup
     * @param inputArray
     */
    public void sort(int inputArr[]) {
        this.array = inputArr;
        this.length = inputArr.length;
        this.tempMergArr = new int[length];
        mergeSort(0, length - 1);
    }
    /**
     * Main Merge sort method
     * @param lowerIndex
     * @param higherIndex
     */
    private void mergeSort(int lowerIndex, int higherIndex) {
        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            // Below step sorts the left side of the array
            mergeSort(lowerIndex, middle);
            // Below step sorts the right side of the array
            mergeSort(middle + 1, higherIndex);
            // Now merge both sides
            mergeParts(lowerIndex, middle, higherIndex);
        }
    }

    /**
     * Merges the separate parts together
     * @param lowerIndex
     * @param middle
     * @param higherIndex
     */
    private void mergeParts(int lowerIndex, int middle, int higherIndex) {
        for (int i = lowerIndex; i <= higherIndex; i++) {
            tempMergArr[i] = array[i];
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            if (tempMergArr[i] <= tempMergArr[j]) {
                array[k] = tempMergArr[i];
                i++;
            } else {
                array[k] = tempMergArr[j];
                j++;
            }
            k++;
        }
        while (i <= middle) {
            array[k] = tempMergArr[i];
            k++;
            i++;
        }
    }

    /**
     * Main performs the file read initiates
     * the sort and calculates the sort time
     * @param a
     */
    public static void main(String a[]){
		Scanner s = new Scanner();
		int[] inputArr = null;
		try {
			inputArr = s.scan(a);
		} catch (IOException e) {
			e.printStackTrace();
		}
        SerialMergeSort mms = new SerialMergeSort();
        long startTime = System.currentTimeMillis();
        mms.sort(inputArr);
        long endTime   = System.currentTimeMillis();
//        for(int i:inputArr){
//            System.out.print(i);
//            System.out.print("\n");
//        }
        long totalTime = endTime - startTime;
        System.out.printf("Sort finished in: %d ms\n", totalTime);
    }
}
