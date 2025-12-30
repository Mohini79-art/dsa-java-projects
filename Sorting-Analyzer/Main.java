import java.util.*;

public class SmartSortingAnalyzer {

    static int comparisons = 0;
    static int swaps = 0;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter array size: ");
        int n = sc.nextInt();

        int[] arr = new int[n];
        System.out.println("Enter elements:");
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

        int[] original = Arrays.copyOf(arr, n);

        System.out.println("\nChoose Data Structure:");
        System.out.println("1. Array\n2. LinkedList\n3. Stack\n4. Queue");
        int dsChoice = sc.nextInt();
        String dataStructure = getDS(dsChoice);

        System.out.println("\nChoose Sorting:");
        System.out.println("1. Bubble  2. Selection  3. Insertion  4. Quick  5. Merge");
        int algoChoice = sc.nextInt();

        int disorder = calculateDisorder(original);
        String dataNature = dataNature(original);

        long start = System.nanoTime();
        sort(arr, algoChoice);
        long end = System.nanoTime();

        String recommended = recommendAlgo(dataStructure, disorder, n);
        String userAlgo = algoName(algoChoice);
        int confidence = confidenceScore(disorder, n);

        System.out.println("\n--- FINAL REPORT ---");
        System.out.println("Data Structure: " + dataStructure);
        System.out.println("Data Nature: " + dataNature);
        System.out.println("Disorder Level: " + disorder);
        System.out.println("User Chosen Algorithm: " + userAlgo);
        System.out.println("Recommended Algorithm: " + recommended);
        System.out.println("Confidence Score: " + confidence + "%");

        explain(recommended, dataNature);
        explainUserChoice(userAlgo, recommended);

        printComplexityTable();

        System.out.println("\nComparisons: " + comparisons);
        System.out.println("Swaps: " + swaps);
        System.out.println("Execution Time: " + ((end - start) / 1_000_000.0) + " ms");
        System.out.println("Sorted Output: " + Arrays.toString(arr));
    }

    // ---------------- LOGIC METHODS ----------------

    static String getDS(int c) {
        return switch (c) {
            case 1 -> "Array";
            case 2 -> "LinkedList";
            case 3 -> "Stack";
            case 4 -> "Queue";
            default -> "Unknown";
        };
    }

    static String algoName(int a) {
        return switch (a) {
            case 1 -> "Bubble Sort";
            case 2 -> "Selection Sort";
            case 3 -> "Insertion Sort";
            case 4 -> "Quick Sort";
            case 5 -> "Merge Sort";
            default -> "Unknown";
        };
    }

    static int calculateDisorder(int[] a) {
        int d = 0;
        for (int i = 1; i < a.length; i++)
            if (a[i] < a[i - 1]) d++;
        return d;
    }

    static String dataNature(int[] a) {
        int inc = 0, dec = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i] > a[i - 1]) inc++;
            else if (a[i] < a[i - 1]) dec++;
        }
        if (dec == 0) return "Sorted";
        if (inc == 0) return "Reverse Sorted";
        if (dec <= a.length / 3) return "Nearly Sorted";
        return "Random";
    }

    static String recommendAlgo(String ds, int disorder, int n) {
        if (disorder == 0)
            return "Already Sorted (No Sorting Needed)";
        if (disorder <= n / 4)
            return "Insertion Sort (Best for Nearly Sorted Data)";
        if (ds.equals("LinkedList"))
            return "Merge Sort (Efficient for Linked List)";
        if (n >= 20)
            return "Merge Sort (Large Dataset)";
        return "Quick Sort (General Purpose)";
    }

    static int confidenceScore(int disorder, int n) {
        if (disorder == 0) return 100;
        if (disorder <= n / 4) return 90;
        if (disorder <= n / 2) return 70;
        return 55;
    }

    // ---------------- NEW FEATURE 1 ----------------
    static void explainUserChoice(String user, String recommended) {
        System.out.println("\nUser Choice Analysis:");
        if (user.equals(recommended)) {
            System.out.println("✔ Good choice! You selected the optimal algorithm.");
        } else {
            System.out.println("⚠ Your choice is not optimal.");
            System.out.println("Reason:");
            if (user.contains("Selection"))
                System.out.println("• Selection sort always takes O(n²) comparisons.");
            if (user.contains("Bubble"))
                System.out.println("• Bubble sort is inefficient for large or nearly sorted data.");
            if (user.contains("Quick"))
                System.out.println("• Quick sort adds recursion overhead for nearly sorted data.");
            if (user.contains("Insertion"))
                System.out.println("• Insertion sort is slow for large random data.");
        }
    }

    // ---------------- NEW FEATURE 2 ----------------
    static void printComplexityTable() {
        System.out.println("\nAlgorithm Complexity Table:");
        System.out.println("--------------------------------------------");
        System.out.println("Algorithm     Time        Space   Stability");
        System.out.println("--------------------------------------------");
        System.out.println("Bubble         O(n²)       O(1)    Stable");
        System.out.println("Selection      O(n²)       O(1)    Unstable");
        System.out.println("Insertion      O(n)        O(1)    Stable");
        System.out.println("Quick          O(n log n)  O(logn) Unstable");
        System.out.println("Merge          O(n log n)  O(n)    Stable");
        System.out.println("--------------------------------------------");
    }

    // ---------------- SORTING ----------------

    static void sort(int[] a, int ch) {
        comparisons = 0;
        swaps = 0;
        switch (ch) {
            case 1 -> bubble(a);
            case 2 -> selection(a);
            case 3 -> insertion(a);
            case 4 -> quick(a, 0, a.length - 1);
            case 5 -> mergeSort(a, 0, a.length - 1);
        }
    }

    static void bubble(int[] a) {
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a.length - i - 1; j++) {
                comparisons++;
                if (a[j] > a[j + 1]) swap(a, j, j + 1);
            }
    }

    static void selection(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                comparisons++;
                if (a[j] < a[min]) min = j;
            }
            swap(a, i, min);
        }
    }

    static void insertion(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i], j = i - 1;
            while (j >= 0 && a[j] > key) {
                comparisons++;
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    static void quick(int[] a, int l, int h) {
        if (l < h) {
            int p = partition(a, l, h);
            quick(a, l, p - 1);
            quick(a, p + 1, h);
        }
    }

    static int partition(int[] a, int l, int h) {
        int pivot = a[h];
        int i = l - 1;
        for (int j = l; j < h; j++) {
            comparisons++;
            if (a[j] < pivot) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, h);
        return i + 1;
    }

    static void mergeSort(int[] a, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(a, l, m);
            mergeSort(a, m + 1, r);
            merge(a, l, m, r);
        }
    }

    static void merge(int[] a, int l, int m, int r) {
        int n1 = m - l + 1, n2 = r - m;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for (int i = 0; i < n1; i++) L[i] = a[l + i];
        for (int j = 0; j < n2; j++) R[j] = a[m + 1 + j];
        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            comparisons++;
            if (L[i] <= R[j]) a[k++] = L[i++];
            else a[k++] = R[j++];
        }
        while (i < n1) a[k++] = L[i++];
        while (j < n2) a[k++] = R[j++];
    }
    static void explain(String recommended, String nature) {
    System.out.println("\nExplanation:");

    if (recommended.contains("Insertion")) {
        System.out.println("• Data is nearly sorted.");
        System.out.println("• Insertion sort works in O(n) for nearly sorted data.");
    }
    else if (recommended.contains("Merge")) {
        System.out.println("• Merge sort is efficient for large datasets.");
        System.out.println("• It guarantees O(n log n) time complexity.");
    }
    else if (recommended.contains("Quick")) {
        System.out.println("• Quick sort performs well on average.");
        System.out.println("• Suitable for random data.");
    }
    else {
        System.out.println("• Algorithm chosen based on general performance.");
    }

    System.out.println("• Data Nature: " + nature);
}


    static void swap(int[] a, int i, int j) {
        swaps++;
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
