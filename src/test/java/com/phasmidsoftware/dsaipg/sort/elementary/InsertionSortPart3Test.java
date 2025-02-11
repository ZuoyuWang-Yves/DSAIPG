package com.phasmidsoftware.dsaipg.sort.elementary;

import java.util.Comparator;
import java.util.Random;
import java.util.function.Supplier;

import org.junit.Test;

import com.phasmidsoftware.dsaipg.util.Benchmark_Timer;
import com.phasmidsoftware.dsaipg.util.Config_Benchmark;

public class InsertionSortPart3Test {
	
	private static final Random random = new Random();
	
	private static final int[] sizeOfn = {50, 100, 200, 400, 800};
	
	
	//Generate random array
	public Integer[] generateRandomArr(int n) {
		Integer[] array = new Integer[n];
		
		for (int i = 0; i < n; i++) {
			array[i] = random.nextInt(1000);
		}
		return array;
	}
	
	//Generate ordered array
	public Integer[] generateOrderedArr(int n) {
		Integer[] array = new Integer[n];
		
		for(int i = 0; i < n; i++) {
			array[i] = i;
		}
		return array;
	}
	
	//Generate partially ordered array
	public Integer[] generatePOArr(int n) {
		Integer[] array = generateOrderedArr(n);
		for(int i = 0; i < n/5 ; i++) {
			int index = random.nextInt(n);
			array[index] = random.nextInt(1000);
		}
		return array;
	}
	
	//Generate reverse ordered array
	public Integer[] generateReverseOrderedArr(int n) {
		Integer[] array = new Integer[n];
		
		for(int i = 0; i<n; i++) {
			array[i] = n - i;
		}
		return array;
	}
	
	public void MeasureInsertionSort(String type, InsertionSortComparator<Integer> Insertionsorter, 
			Supplier<Integer[]> supplier) {
		Benchmark_Timer<Integer[]> benchmark = 
				new Benchmark_Timer<>("InsertionSort - " + type, array -> Insertionsorter.sort(array, 0, array.length));
		double time = benchmark.runFromSupplier(supplier, 10);
		System.out.println(type + ": average sorting time: " + time + "ms");
//		System.out.printf("%-20s: %.3f ms%n", type, time);

		
	}
	
	@Test
	public void InsertionSortBenchmark() {
		Comparator<Integer> comparator = Integer::compareTo;
		
		for(int N:sizeOfn) {
			System.out.printf("\nBenchmarking Insertion Sort for n = %d:\n", N);
			
			InsertionSortComparator<Integer> Insertionsorter = 
					new InsertionSortComparator<>(comparator, N, 1, Config_Benchmark.setupConfigFixes());
			
			MeasureInsertionSort("Random", Insertionsorter, () -> generateRandomArr(N));
			MeasureInsertionSort("Ordered", Insertionsorter, () -> generateOrderedArr(N));
			MeasureInsertionSort("Partially Ordered", Insertionsorter, () -> generatePOArr(N));
			MeasureInsertionSort("Reversed", Insertionsorter, () -> generateReverseOrderedArr(N));
		
		}
		
		
	}
	
	
	
}
