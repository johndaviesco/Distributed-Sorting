import java.io.IOException;
import mpi.MPI;
import mpi.MPIException;

/**
 * @author John Davies-Colley
 *
 */
public class DistributedQuickSort {
    private int array[];
    private int length;
    private static int[] unsorted;
	private static final int ROOT = 0;

	/**
	 * Does the Distribution workload
	 * @param data
	 * @throws MPIException
	 */
	public DistributedQuickSort(int[] data) throws MPIException{
		unsorted = data.clone();
		int thisRank = MPI.COMM_WORLD.getRank(); // machine rank
		int size = MPI.COMM_WORLD.getSize(); // how many processors there are avalable
		int partition = (int)(unsorted.length/size); // sending block size
		int[]senderArray = new int[partition];
		/**
		 * scatter divides up senderArray(sending array) into partition sized chunks
		 * and populates it using the unsorted array with the partition offset
		 */
		MPI.COMM_WORLD.scatter(senderArray, partition, MPI.INT, unsorted, partition, MPI.INT, ROOT);

		// sort performed on respective machines
		sort(senderArray);

		/**
		 * Gather gets the resulting arrays from the individual machines after
		 * there respective sorts and sets them to the unsorted array
		 */
		MPI.COMM_WORLD.gather(unsorted, partition, MPI.INT, senderArray, unsorted.length, MPI.INT, ROOT);

		// Final sort performed on the root machine
		if(thisRank == 0){
			sort(unsorted);
		}
	}

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
		int[] inputArr = null;
		try {
			inputArr = s.scan(a);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		long startTime = System.currentTimeMillis();
    	try {
			DistributedQuickSort quickSort = new DistributedQuickSort(inputArr);
		}
    	catch (MPIException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.printf("Sort finished in: %d ms\n", totalTime);
    }
}
