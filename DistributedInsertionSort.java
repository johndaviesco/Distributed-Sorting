
import java.io.IOException;

import mpi.*;

/**
 * @author John Davies-Colley
 *
 */
public class DistributedInsertionSort {
	private static int[] unsorted;
	private static final int ROOT = 0;

	/**
	 * Does the Distribution workload
	 * @param data
	 * @throws MPIException
	 */
	public DistributedInsertionSort(int[] data) throws MPIException{
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
		insertionSort(senderArray);

		/**
		 * Gather gets the resulting arrays from the individual machines after
		 * there respective sorts and sets them to the unsorted array
		 */
		MPI.COMM_WORLD.gather(unsorted, partition, MPI.INT, senderArray, unsorted.length, MPI.INT, ROOT);

		// Final sort performed on the root machine
		if(thisRank == 0){
			insertionSort(unsorted);
		}
	}

	/**
	 * Main Insertion sort method
	 * @param input
	 * @return input
	 */
	public static int[] insertionSort(int[] input){

		int temp;
		for (int i = 1; i < input.length; i++) {
			for(int j = i ; j > 0 ; j--){
				if(input[j] < input[j-1]){
					temp = input[j];
					input[j] = input[j-1];
					input[j-1] = temp;
				}
			}
		}
		return input;
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
		long startTime = System.currentTimeMillis();
    	try {
			DistributedInsertionSort sorter = new DistributedInsertionSort(inputArr);
		} catch (MPIException e) {
			e.printStackTrace();
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.printf("Sort finished in: %d ms\n", totalTime);
    }

}

