
public class Sorter {

	public static int[] sort(int[] input) {

		int length = input.length;
		for (int i = length; i >= 0; i--) {
			for (int j = 0; j < length - 1; j++) {
				if (input[j] > input[j + 1]) {
					swapNumbers(j, j + 1, input);
				}
			}
		}

		return input;
	}

	private static void swapNumbers(int i, int j, int[] array) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
}










































/*
	public static int[] sort_insertion(int[] input) {
	
		for (int i = 1; i < input.length; i++) {
			for (int j = i; j > 0; j--) {
				if (input[j] < input[j - 1]) {
					swapNumbers(j, j - 1, input);
				}
			}
		}
		return input;
	}
	
	public static int[] sort_bubble(int[] input) {
	
		int length = input.length;
		for (int i = length; i >= 0; i--) {
			for (int j = 0; j < length - 1; j++) {
				if (input[j] > input[j + 1]) {
					swapNumbers(j, j + 1, input);
				}
			}
		}
	
		return input;
	}
*/