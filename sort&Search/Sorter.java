import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Sorter {
    public static void main (String[] args) {
        int[] nums = {2,4,5,1,6,7,1,9,7,2};
        nums = quickSort(nums, 0, nums.length-1);
        for (int a: nums) {
            System.out.println(a);
        }
    }

    public static int[] worstSort (int[] elements) {
        ArrayList<Integer> numbersSorted = new ArrayList();
        ArrayList<Integer> frequencies = new ArrayList();
        while (true) {
            int count = 0;
            for (int a: frequencies) {
                count += a;
            }
            if (count == elements.length) {
                break;
            }
            int currentMin = Integer.MAX_VALUE;
            for (int i=0; i<elements.length; i++) {
                if (!numbersSorted.contains(elements[i])) {
                    currentMin = Math.min(currentMin, elements[i]);
                }
            }
            numbersSorted.add(currentMin);
            count = 0;
            for (int i=0; i<elements.length; i++) {
                if (elements[i] == currentMin) {
                    count++;
                }
            }
            frequencies.add(count);
        }
        int b=0;
        for (int i=0; i<frequencies.size(); i++) {
            for (int a=0; a<frequencies.get(i); a++) {
                elements[b] = numbersSorted.get(i);
                b++;
            }
        }
        return elements;
    }

    public static int[] bubbleSort (int[] elements) {     // WORKS!!
        while (true) {
            for (int i=0; i<elements.length-1; i++) {
                if (elements[i] > elements[i+1]) {
                    break;
                }
                else if (i==elements.length-2) {
                    return elements;
                }
            }
            for (int i=0; i<elements.length-1; i++) {
                if (elements[i] > elements[i+1]) {
                    int temp = elements[i];
                    elements[i] = elements[i+1];
                    elements[i+1] = temp;
                }
            }
        }
    }

    public static int[] selectionSort (int[] elements) {   // WORKS!!!
        int numLow = Integer.MAX_VALUE;
        int indLow = -1;
        int prev = -1;
        boolean x = false;
        for (int i=0; i<elements.length; i++) {
            for (int j=i+1; j<elements.length; j++) {
                if (elements[i] > elements[j]) {
                    prev = numLow;
                    numLow = Math.min(numLow, elements[j]);
                    x = true;
                    if (numLow != prev) {
                        indLow = j;
                    }
                }
            }
            if (x) {
                int temp = elements[i];
                elements[i] = numLow;
                elements[indLow] = temp;
            }
            numLow = Integer.MAX_VALUE;
            x = false;
        }
        return elements;
    }

    public static int[] insertionSort (int[] elements) {  // WORKS!!
        for (int i=1; i<elements.length; i++) {
            int val = elements[i];
            int insertIndex=i;
            for (int b=i-1; b>=0; b--) {
                if (val < elements[b]) {
                    insertIndex=b;
                }
                else {
                    break;
                }
            }
            for (int c=i-1; c>=insertIndex; c--) {
                elements[c+1] = elements[c];
            }
            elements[insertIndex]=val;
        }
        return elements;
    }

    public static int[] quickSort (int[] elements, int lowIndex, int highIndex) {  // WORKS!!
        int i = lowIndex-1;
        int pivot = elements[highIndex];
        for (int j=lowIndex; j<highIndex; j++) {
            if (elements[j] <= pivot) {
                i++;
                int temp = elements[j];
                elements[j] = elements[i];
                elements[i] = temp;
            }
        }
        elements[highIndex] = elements[i+1];
        elements[i+1] = pivot;

        // call for left side
        if (i > lowIndex) {
            quickSort(elements, lowIndex, i);
        }

        // call for right side
        if (i+2 < highIndex) {
            quickSort(elements, i+2, highIndex);
        }
        return elements;
    }
}