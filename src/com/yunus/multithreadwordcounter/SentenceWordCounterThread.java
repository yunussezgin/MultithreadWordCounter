package com.yunus.multithreadwordcounter;

public class SentenceWordCounterThread extends Thread {

	private String sentence;

	public SentenceWordCounterThread(String sentence) {
		this.sentence = sentence;
	}

	@Override
	public void run() {
		String[] wordList = sentence.split("\\s+");

		for (String word : wordList) {
			MultithreadWordCounter.wordListUpdate(word);
		}
	}
	
	
}
