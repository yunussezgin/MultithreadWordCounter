package com.yunus.multithreadwordcounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileReaderThread extends Thread {
	
	private String fileDirectory;
	private List<String> sentenceList;
	private int sentenceCount;
	private Double averageWordCount;
	
	public FileReaderThread(String fileDirectory) {
		this.fileDirectory = fileDirectory;
		this.sentenceList = new ArrayList<String>();
	}

	@Override
	public void run() {
		try {
			File file = new File(fileDirectory);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

			String line = "";
			String message = "";
			while ((line = bufferedReader.readLine()) != null) {
				message += line + " ";
			}
			message = message.trim();

			for (String sentence : Arrays.asList(message.split("[!?.:]+")))
				sentenceList.add(sentence.trim());
			
			List<Integer> wordCountList = new ArrayList<Integer>();
			for(String sentence : sentenceList)
				wordCountList.add(sentence.split("\\s+").length);

			
			sentenceCount = sentenceList.size();
			
			averageWordCount = wordCountList.stream().mapToInt(val -> val).average().orElse(0.0);

			bufferedReader.close();

		} catch (IOException e) {
			System.out.println("Dosya Okunurken Hata Olustu!");
		}
	}

	public List<String> getLineList() {
		return sentenceList;
	}

	public int getSentenceCount() {
		return sentenceCount;
	}

	public Double getAverageWordCount() {
		return averageWordCount;
	}
	
}
