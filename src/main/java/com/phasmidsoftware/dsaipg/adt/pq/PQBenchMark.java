package com.phasmidsoftware.dsaipg.adt.pq;

import com.phasmidsoftware.dsaipg.util.Benchmark_Timer;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class PQBenchMark {

	private static final int M = 4095; 
	private static final int amount_insert = 16000;
	private static final int amount_remove = 4000;
	
	public static void main(String[] args) {
		
		Random random = new Random();
		
		Integer[] elements = new Integer[amount_insert];
		
		for(int i = 0; i < amount_insert; i++) {
			elements[i] = random.nextInt(50000);
		}
		
		System.out.println("Start Benchmarking....");
		
		
		List<String[]> results = new ArrayList<>();
		
		
		
		/*
		 * Binary Heap without floyd
		 */
		Benchmark_Timer<Void> binaryHeapBenchmark = new Benchmark_Timer<>(
				"Binary Heap No floyd",
				unused -> {
					PriorityQueue<Integer> pq = new PriorityQueue<>(M, Integer::compare);
					
					Integer highestSpilled = null;
					
	                for(int i = 0; i < amount_insert; i++) {
	                	if (pq.size() >= M) { 
	                        try {
	                            Integer removed = pq.take();  // Remove the lowest priority element
	                            if (highestSpilled == null || removed > highestSpilled) {
	                                highestSpilled = removed; // Track the highest-priority spilled element
	                            }
	                        } catch (PQException e) {
	                            System.err.println("Error removing element: " + e.getMessage());
	                        }
	                    }
	                    pq.give(elements[i]);
//	                    pq.give(elements[i]);
	                }
	                
	                
	                
	                
	                
	                for(int i = 0; i < amount_remove; i++) {
	                	try {
	                		pq.take();
	                	} catch (PQException e) {
	                		System.err.println("Error removing element: " + e.getMessage());
	                	}
	                }
	                System.out.println("Highest priority spilled element: " + highestSpilled);
				}
			);
//		System.out.println("Binary Heap no floyd Time: " + binaryHeapBenchmark.run(null, 10) + " ms");	
		double binaryHeapTime = binaryHeapBenchmark.run(null, 10);
        results.add(new String[]{"Binary Heap No Floyd", String.valueOf(binaryHeapTime)});
        System.out.println("Binary Heap No Floyd Time: " + binaryHeapTime + " ms");
		
		/*
		 * Binary Heap using floyd
		 */
		Benchmark_Timer<Void> binaryHeapFloydBenchmark = new Benchmark_Timer<>(
				"Binary Heap using floyd",
				unused -> {
					PriorityQueue<Integer> pq = new PriorityQueue<>(M, Integer::compare, true, false);
	                for(int i = 0; i < amount_insert; i++) {
	                    pq.give(elements[i]);
	                }
	                for(int i = 0; i < amount_remove; i++) {
	                	try {
	                		pq.take();
	                	} catch (PQException e) {
	                		
	                	}
	                }
				}
			);
//		System.out.println("Binary Heap using floyd: " + binaryHeapFloydBenchmark.run(null, 10) + " ms");
		double binaryHeapFloydTime = binaryHeapFloydBenchmark.run(null, 10);
        results.add(new String[]{"Binary Heap Using Floyd", String.valueOf(binaryHeapFloydTime)});
        System.out.println("Binary Heap Using Floyd Time: " + binaryHeapFloydTime + " ms");
		
		/*
		 * 4-ary heap without floyd
		 */
		Benchmark_Timer<Void> FourAryBenchmark = new Benchmark_Timer<>(
				"4-ary Heap without floyd",
				unused -> {
					PriorityQueue<Integer> pq = new PriorityQueue<>(M, Integer::compare, false, true);
	                for(int i = 0; i < amount_insert; i++) {
	                    pq.give(elements[i]);
	                }
	                for(int i = 0; i < amount_remove; i++) {
	                	try {
	                		pq.take();
	                	}catch (PQException e) {
	                		
	                	}
	                }
				}
			);
//		System.out.println("4 ary Heap no floyd: " + FourAryBenchmark.run(null, 10) + " ms");
		double fourAryHeapTime = FourAryBenchmark.run(null, 10);
        results.add(new String[]{"4-Ary Heap Without Floyd", String.valueOf(fourAryHeapTime)});
        System.out.println("4-Ary Heap Without Floyd Time: " + fourAryHeapTime + " ms");
		
		
		/*
		 * 4-ary heap with floyd
		 */
		Benchmark_Timer<Void> FourAryFloydBenchmark = new Benchmark_Timer<>(
				"4-ary Heap with floyd",
				unused -> {
					PriorityQueue<Integer> pq = new PriorityQueue<>(M, Integer::compare, true, true);
	                for(int i = 0; i < amount_insert; i++) {
	                    pq.give(elements[i]);
	                }
	                for(int i = 0; i < amount_remove; i++) {
	                	try {
	                		pq.take();
	                	}catch (PQException e) {
	                		
	                	}
	                }
				}
			);
//		System.out.println("4 ary Heap using floyd: " + FourAryFloydBenchmark.run(null, 10) + " ms");
		double fourAryFloydTime = FourAryFloydBenchmark.run(null, 10);
        results.add(new String[]{"4-Ary Heap With Floyd", String.valueOf(fourAryFloydTime)});
        System.out.println("4-Ary Heap With Floyd Time: " + fourAryFloydTime + " ms");
		
        
        
        /*
         * Write results to CSV file
         */
        
        String filePath = "C:/Users/18065/Desktop/INFO6205/heap_benchmark_results.csv";
        
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Heap Type,Execution Time (ms)\n");
            for (String[] result : results) {
                writer.append(result[0]).append(",").append(result[1]).append("\n");
            }
            System.out.println("Results saved to" + filePath);
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
	}
}
