import java.util.*;

class OperationCounter {
    int comparisons = 0;
    int swaps = 0;
    int assignments = 0;

    void reset() {
        comparisons = 0;
        swaps = 0;
        assignments = 0;
    }
}

class AlgoResult {
    String name;
    int comparisons, swaps, assignments;

    AlgoResult(String name, int comparisons, int swaps, int assignments) {
        this.name = name;
        this.comparisons = comparisons;
        this.swaps = swaps;
        this.assignments = assignments;
    }

    int totalOperations() {
        return comparisons + swaps + assignments;
    }
}

public class Main {

    // Bubble Sort
    static void bubbleSort(int[] arr, OperationCounter oc) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                oc.comparisons++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    oc.swaps++;
                    oc.assignments += 3;
                }
            }
        }
    }

    // Insertion Sort
    static void insertionSort(int[] arr, OperationCounter oc) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            oc.assignments++;
            int j = i - 1;
            while (j >= 0) {
                oc.comparisons++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    oc.assignments++;
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
            oc.assignments++;
        }
    }

    // Quick Sort
    static void quickSort(int[] arr, int low, int high, OperationCounter oc) {
        if (low < high) {
            int pi = partition(arr, low, high, oc);
            quickSort(arr, low, pi - 1, oc);
            quickSort(arr, pi + 1, high, oc);
        }
    }

    static int partition(int[] arr, int low, int high, OperationCounter oc) {
        int pivot = arr[high];
        oc.assignments++;
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            oc.comparisons++;
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                oc.swaps++;
                oc.assignments += 3;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        oc.swaps++;
        oc.assignments += 3;
        return i + 1;
    }

    // Merge Sort
    static void mergeSort(int[] arr, int l, int r, OperationCounter oc) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m, oc);
            mergeSort(arr, m + 1, r, oc);
            merge(arr, l, m, r, oc);
        }
    }

    static void merge(int[] arr, int l, int m, int r, OperationCounter oc) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = arr[l + i];
            oc.assignments++;
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[m + 1 + j];
            oc.assignments++;
        }

        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            oc.comparisons++;
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            oc.assignments++;
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
            oc.assignments++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
            oc.assignments++;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OperationCounter oc = new OperationCounter();

        // User se input
        System.out.print("Enter array size: ");
        int n = sc.nextInt();
        int[] arr = new int[n];
        System.out.println("Enter elements:");
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

        System.out.println("\nOriginal Array: " + Arrays.toString(arr));

        // Bubble Sort
        oc.reset();
        int[] arrBubble = arr.clone();
        bubbleSort(arrBubble, oc);
        AlgoResult bubbleRes = new AlgoResult("Bubble Sort", oc.comparisons, oc.swaps, oc.assignments);

        // Insertion Sort
        oc.reset();
        int[] arrInsert = arr.clone();
        insertionSort(arrInsert, oc);
        AlgoResult insertionRes = new AlgoResult("Insertion Sort", oc.comparisons, oc.swaps, oc.assignments);

        // Quick Sort
        oc.reset();
        int[] arrQuick = arr.clone();
        quickSort(arrQuick, 0, n - 1, oc);
        AlgoResult quickRes = new AlgoResult("Quick Sort", oc.comparisons, oc.swaps, oc.assignments);

        // Merge Sort
        oc.reset();
        int[] arrMerge = arr.clone();
        mergeSort(arrMerge, 0, n - 1, oc);
        AlgoResult mergeRes = new AlgoResult("Merge Sort", oc.comparisons, oc.swaps, oc.assignments);

        // Print Results
        AlgoResult[] results = { bubbleRes, insertionRes, quickRes, mergeRes };

        System.out.println("\n🔹 Sorting Analysis Report:");
        for (AlgoResult r : results) {
            System.out.println(r.name + " -> Comparisons: " + r.comparisons +
                    ", Swaps: " + r.swaps +
                    ", Assignments: " + r.assignments +
                    ", Total Ops: " + r.totalOperations());

            // Time Complexity Table
            switch (r.name) {
                case "Bubble Sort":
                    System.out.println("   Time Complexity -> Best: O(n), Avg: O(n^2), Worst: O(n^2)");
                    break;
                case "Insertion Sort":
                    System.out.println("   Time Complexity -> Best: O(n), Avg: O(n^2), Worst: O(n^2)");
                    break;
                case "Quick Sort":
                    System.out.println("   Time Complexity -> Best: O(n log n), Avg: O(n log n), Worst: O(n^2)");
                    break;
                case "Merge Sort":
                    System.out.println("   Time Complexity -> Best: O(n log n), Avg: O(n log n), Worst: O(n log n)");
                    break;
            }
        }

        // Best Algo
        AlgoResult best = results[0];
        for (AlgoResult r : results) {
            if (r.totalOperations() < best.totalOperations()) {
                best = r;
            }
        }

        System.out.println("\n✅ Best Algorithm for this input: " + best.name);

        // Explanation why it's best
        switch (best.name) {
            case "Bubble Sort":
                System.out.println("➡ Reason: Works well if array is already/nearly sorted (best case O(n)).");
                break;
            case "Insertion Sort":
                System.out.println("➡ Reason: Efficient for small or nearly sorted arrays.");
                break;
            case "Quick Sort":
                System.out.println("➡ Reason: Fastest on average with O(n log n), good for large datasets.");
                break;
            case "Merge Sort":
                System.out.println("➡ Reason: Consistent O(n log n) performance and stable sorting.");
                break;
        }
    }
}
