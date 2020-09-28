package edu.unca.csci;

import java.util.Random;
import java.util.Scanner;

/**
 * HW1 for CSCI 333 - 8/27/2019
 * Manages data and user input for Trinary Search function.
 * @author      Brent Rawls <brawls@unca.edu>
 * @version     1.0  
 */
public class TrinarySearch {

	public static void main(String[] args) {

		int[] dataArray = null;
		Scanner scan = new Scanner(System.in);
		dataArray = newArray();
		
		while(true) {
			System.out.println("Enter a command, or ? for help:");
			String command = scan.next();
			if (command.equalsIgnoreCase("help") || command.equalsIgnoreCase("?")) {
				printHelp();
			}
			else if (command.equalsIgnoreCase("new")) {
				dataArray = newArray();
			}
			else if (command.equalsIgnoreCase("search") || command.equalsIgnoreCase("find")) {
				searchArray(dataArray, scan.nextInt());
			}
			else if (command.equalsIgnoreCase("print")) {
				printArray(dataArray);
			}
			else if (command.equalsIgnoreCase("quit")) {
				System.out.println("Bye!");
				break;
			}
			else {
				System.out.println("I don't know that command. Try again, or type \"help\" for help.");
			}
			
		}
		scan.close();
		
	}
	
	/**
	 * Outputs command information to the console.
	 */
	public static void printHelp() {
		System.out.println("\tnew: \t\t generate a new array of data");
		System.out.println("\tprint: \t\t print the contents of the array");
		System.out.println("\tsearch/find #: \t search the array for the integer #");
		System.out.println("\thelp/?: \t view this list of commands");
		System.out.println("\tquit: \t\t quit the program");
	}
	/**
	 * Calls the Trinary Search function and prints the result to the console.
	 * @param int[] dataArray: the array to be searched
	 * @param int target: the target value searched for
	 */
	public static void searchArray(int[] dataArray, int target) {
		int location = find(dataArray, target);
		if (location == -1) {
			System.out.println("The integer " + target + " was not present in the array.");
		}
		else System.out.println("The integer " + target + " was first found at index " + location + ".");
	}
	
	/**
	 * Generates a new array from user specifications.
	 * @return int[] array filled with integer data in sorted order.
	 */
	public static int[] newArray() {
		int[] dataArray = null;
		Scanner newscan = new Scanner(System.in);
		System.out.println("how many elements should we generate?");
		int arraySize = newscan.nextInt();
		System.out.println("what will be the minimum value in the array?");
		int minValue = newscan.nextInt();
		System.out.println("what will be the maximum value in the array?");
		int maxValue = newscan.nextInt();
		
		Random rand = new Random();
		dataArray = new int[arraySize];
		for(int i = 0; i < arraySize; i++) {
			dataArray[i] = rand.nextInt((maxValue + 1) - minValue) + minValue;
		}
		quicksort(dataArray, 0, dataArray.length - 1);
		return dataArray;
	}

	/**
	 * Prints the array's contents to the console, for verification.
	 */
	public static void printArray(int[] dataArray) {
		for(int i = 0; i < dataArray.length; i++) {
			System.out.println("\t" + i + ":\t" + dataArray[i]);
		}
	}

	/**
	 * Helper method for recursive search; calls recursive function with initial bounds. 
	 * @param int[] a - the array to sort
	 * @param int target - the integer value being searched for
	 * @return int - the index of the first instance of the target value, or -1 if not found
	 */
	public static int find(int[] a, int target) {
		return recursiveSearch(a, 0, a.length - 1, target);
	}
	
	/**
	 * Recursive trinary search. 
	 * @param int[] a - the array to sort
	 * @param int leftBound - left boundary of the array being searched in this instance
	 * @param int rightBound - right boundary of the array being searched in this instance
	 * @param int target - the integer value being searched for
	 * @return int - result from the called search instance: this method or sequentialSearch
	 */	
	public static int recursiveSearch(int[] a, int leftBound, int rightBound, int target) {
		int range = (rightBound - leftBound) + 1;
		if (range < 3) return sequentialSearch(a, leftBound, rightBound, target);
		int rangeSubset = range / 3;
		if (a[leftBound + rangeSubset] >= target) return recursiveSearch(a, leftBound, leftBound + rangeSubset, target);
		else if (a[leftBound + 2*rangeSubset] >= target) return recursiveSearch(a, leftBound + rangeSubset + 1, leftBound + 2*rangeSubset, target);
		else return recursiveSearch(a, leftBound + 2*rangeSubset + 1, rightBound, target);
	}
	
	/**
	 * Base case of the recursive search. Called when there are fewer than 3 remaining elements in the array partition being sorted.
	 * @param int[] a - the array to sort
	 * @param int leftBound - left boundary of the array being searched in this instance
	 * @param int rightBound - right boundary of the array being searched in this instance
	 * @param int target - the integer value being searched for
	 * @return int - array index of first instance of the target location, or -1 if target value is not present in array.
	 */	
	public static int sequentialSearch(int[] a, int leftBound, int rightBound, int target) {
		for (int i = leftBound; i <= rightBound; i++) {
			if(a[i] == target) return i;
		}
		return -1;
	}

	/**
	 * A typical implementation of quicksort.
	 * @param int[] a - the array to sort
	 * @param int left - left bound of array to be sorted in this instance
	 * @param int right - right bound of array to be sorted in this instance
	 */
	public static void quicksort(int[] a, int left, int right){
		if (left >= right) return;
		int mid = partition(a, left, right);
		quicksort(a, left, mid-1);
		quicksort(a, mid+1, right);
	}
	/**
	 * Partition method for a typical implementation of quicksort.
	 * @param int[] a - the array to sort
	 * @param int left - left bound of array to be sorted in this instance
	 * @param int right - right bound of array to be sorted in this instance
	 * @return int - partition for quicksort
	 */
	public static int partition(int[] a, int left, int right) {
		int i = left - 1;
		int j = right;
		int compareTo = a[right];
		while(true) {
			while(a[++i] < compareTo);
			while(compareTo < a[--j]) if (j == left) break;
			if (i >= j) break;
			int tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
		int tmp = a[i];
		a[i] = a[right];
		a[right] = tmp;
		return i;
	}

	
}
