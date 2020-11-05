package com.yunus.multithreadwordcounter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MultithreadWordCounter {

	public static String fileDirectory;
	public static Map<String, Integer> wordList = new HashMap<String, Integer>();

	public static void main(final String[] args) throws InterruptedException {
		FileReaderHelper fileReaderHelper = new FileReaderHelper(args[0]);
		fileReaderHelper.execute();

		List<String> lineList = fileReaderHelper.getLineList();
//		for (String line : lineList) {
//			System.out.println(line);
//		}

		int maxThreadCount = (args[1] != null && !args[1].isEmpty()) ? Integer.valueOf(args[1]) : 5;
		Thread[] sentenceThreadList = new Thread[maxThreadCount];
		int processCount = (lineList.size() / maxThreadCount) + (lineList.size() % maxThreadCount > 0 ? 1 : 0);

		for (int i = 0; i < processCount; i++) {
			sentenceThreadList[i] = new Thread();

			int threadCount = maxThreadCount;
			if (i == processCount - 1) {
				if (lineList.size() % maxThreadCount != 0) {
					threadCount = lineList.size() % maxThreadCount;
				}
			}

			for (int j = 0; j < threadCount; j++) {
				sentenceThreadList[j] = new SentenceWordCounterThread(lineList.get(i * threadCount + j));
				sentenceThreadList[j].run();
			}

			for (int k = 0; k < threadCount; k++) {
				sentenceThreadList[k].join();
			}
		}

		// Cumle sayisi ve Cumlelerdeki ortalama kelime sayisi ekrana yaziliyor
		System.out.println("Sentence Count: " + fileReaderHelper.getSentenceCount());
		System.out.println("Avg. Word Count: " + fileReaderHelper.getAverageWordCount());

		for (Entry<String, Integer> entry : wordList.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

	}

	public static synchronized void wordListUpdate(String word) {
		wordList.put(word, wordList.containsKey(word) ? wordList.get(word) + 1 : 1);
	}

}
