
import java.io.IOException;

import mpi.MPI;
import mpi.MPIException;


public class DistributedMergeSort {
    private int[] array;
    private int[] tempMergArr;
    private int length;
    private static int[] unsorted;
	private static final int ROOT = 0;
	/**
	 * Does the Distribution workload
	 * @param data
	 * @throws MPIException
	 */
	public DistributedMergeSort(int[] data) throws MPIException{
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long startTime = System.currentTimeMillis();
    	try {
    		DistributedMergeSort msort = new DistributedMergeSort(inputArr);
		} catch (MPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime   = System.currentTimeMillis();
    	try {
			MPI.Finalize();
		} catch (MPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long totalTime = endTime - startTime;
		System.out.printf("Sort finished in: %d ms\n", totalTime);
    }
}