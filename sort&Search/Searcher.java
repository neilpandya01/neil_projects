public class Searcher {
    public static void main (String[] args) {
        int[] nums = {3,4,5,6,9,13,16,17};
        for (int i=0; i<20; i++) {
            System.out.println("Searching for " + i + ", found at: " + binarySearch(nums, i));
        }
    }
    
    public static int linearSearch (int[] elements, int target) {
        for (int i=0; i<elements.length; i++) {
            if (elements[i] == target) {
                return i;
            }
        }
        return -1;  // value not found
    }
    
    public static int binarySearch (int[] elements, int target) {
        int lowIndex = 0;
        int highIndex = elements.length-1;
        while (true) {
            int middleIndex = (highIndex+lowIndex) / 2;
            if (middleIndex > highIndex || middleIndex < lowIndex) {
                return -1;  // value not found
            }
            if (target > elements[middleIndex]) {
                lowIndex = middleIndex+1;
            }
            else if (target < elements[middleIndex]) {
                highIndex = middleIndex-1;
            }
            else {
                return middleIndex;
            }
        }
    }
}