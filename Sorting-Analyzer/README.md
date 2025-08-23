# Sorting Algorithm Analyzer (Java)

This project analyzes and compares the performance of **Bubble Sort, Insertion Sort, Quick Sort, and Merge Sort**.  
It counts **comparisons, swaps, assignments**, and also shows **time complexity** for each algorithm.  
Finally, it recommends the **best sorting algorithm** based on the input array.

---

## 🚀 Features
- Implements 4 sorting algorithms:
  - Bubble Sort
  - Insertion Sort
  - Quick Sort
  - Merge Sort
- Tracks **comparisons, swaps, and assignments**
- Displays **Best / Average / Worst case complexities**
- Recommends the **best algorithm** for the given array

---

## 🖥️ How to Run
```bash
javac Main.java
java Main
📊 Sample Output
mathematica
Copy
Edit
Original Array: [5, 2, 9, 1, 6, 3]

🔹 Time Complexity Info:
Bubble Sort:    Best O(n), Avg O(n^2), Worst O(n^2)
Insertion Sort: Best O(n), Avg O(n^2), Worst O(n^2)
Quick Sort:     Best O(n log n), Avg O(n log n), Worst O(n^2)
Merge Sort:     Best O(n log n), Avg O(n log n), Worst O(n log n)

🔹 Sorting Results:
Bubble Sort    -> Comparisons: 15, Swaps: 7, Assignments: 21, Total Ops: 43
Insertion Sort -> Comparisons: 11, Swaps: 0, Assignments: 16, Total Ops: 27
Quick Sort     -> Comparisons: 12, Swaps: 10, Assignments: 30, Total Ops: 52
Merge Sort     -> Comparisons: 9,  Swaps: 0,  Assignments: 24, Total Ops: 33

✅ Best Algorithm for this input: Insertion Sort
➡ Reason: Fewer operations for this array (small size & nearly sorted).
🧑‍💻 Concepts Used
Java OOP (methods, classes)

Arrays

Divide & Conquer (Quick & Merge Sort)

Complexity Analysis

📌 Future Improvements
Add more algorithms (Heap Sort, Counting Sort)

Measure execution time in nanoseconds

Visualize sorting steps
