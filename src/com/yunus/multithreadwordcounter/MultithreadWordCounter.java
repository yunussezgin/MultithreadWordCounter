package com.yunus.multithreadwordcounter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MultithreadWordCounter {

	public static String fileDirectory;
	public static Map<String, Integer> wordList = new HashMap<String, Integer>();

	public static void main(final String[] args) throws InterruptedException {
		FileReaderHelper fileReaderHelper = new FileReaderHelper(args[0]);
		fileReaderHelper.execute();

		List<String> lineList = fileReaderHelper.getLineList(); // sentences which read from file

		int maxThreadCount = (args[1] != null && !args[1].isEmpty()) ? Integer.valueOf(args[1]) : 5; // thread count (default 5)
		Thread[] sentenceThreadList = new Thread[maxThreadCount]; // thread list to count word in sentence
		int processCount = (lineList.size() / maxThreadCount) + (lineList.size() % maxThreadCount > 0 ? 1 : 0); // main thread loop count

		for (int i = 0; i < processCount; i++) {
			sentenceThreadList[i] = new Thread();

			int threadCount = maxThreadCount;
			if (i == processCount - 1) { // last iteration to calculate last thread count
				if (lineList.size() % maxThreadCount != 0) {
					threadCount = lineList.size() % maxThreadCount;
				}
			}

			// Started thread
			for (int j = 0; j < threadCount; j++) {
				sentenceThreadList[j] = new SentenceWordCounterThread(lineList.get(i * threadCount + j));
				sentenceThreadList[j].run();
			}

			// Waited thread
			for (int k = 0; k < threadCount; k++) {
				sentenceThreadList[k].join();
			}
		}

		// Writes sentence count and average word of sentences
		System.out.println("Sentence Count: " + fileReaderHelper.getSentenceCount());
		System.out.println("Avg. Word Count: " + fileReaderHelper.getAverageWordCount());

		// 
		Map<String, Integer> sortedWordList = sortByValue(wordList);
		
		// Writes word list and count
		for (Entry<String, Integer> entry : sortedWordList.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

	}

	public static synchronized void wordListUpdate(String word) {
		wordList.put(word, wordList.containsKey(word) ? wordList.get(word) + 1 : 1);
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
		list.sort(Collections.reverseOrder(Entry.comparingByValue()));

		Map<K, V> sortedMap = new LinkedHashMap<>();
		for (Entry<K, V> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

}
