
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author John Davies-Colley
 *
 */
public class SerialInsertionSort {

	/**
	 * Main Insertion sort method
	 * @param input
	 * @return
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
		int[] arr1 = null;
		try {
			arr1 = s.scan(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long startTime = System.currentTimeMillis();
		int[] arr2 = insertionSort(arr1);
		long endTime   = System.currentTimeMillis();
//		for(int i:arr2){
//			System.out.print(i);
//			System.out.print("\n");
//		}
        long totalTime = endTime - startTime;
        System.out.printf("Sort finished in: %d ms\n", totalTime);
	}

}
