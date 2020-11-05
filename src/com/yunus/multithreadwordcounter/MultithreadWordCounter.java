package com.yunus.multithreadwordcounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultithreadWordCounter {

	public static String fileDirectory;
	
	
	public static void main(final String[] args) throws InterruptedException {

		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}
		
		FileReaderThread fileReaderThread = new FileReaderThread(args[0]);
		Thread mainThread = new Thread(fileReaderThread);
		mainThread.run();
	    mainThread.join();
		
		
		List<String> lineList = fileReaderThread.getLineList();
		for (String line : lineList) {
			System.out.println(line);
		}
		
		System.out.println(fileReaderThread.getSentenceCount());
		System.out.println(fileReaderThread.getAverageWordCount());

	}
	
	private static List<String> readFile(String directoy) throws IOException {
		List<String> lineList = new ArrayList<String>();
		File fileDirectory = new File(directoy);

		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileDirectory));

		String line;
		while ((line = bufferedReader.readLine()) != null) {
			lineList.add(line);
		}
		
		bufferedReader.close();

		return lineList;
	}

}
