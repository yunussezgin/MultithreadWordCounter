package com.yunus.multithreadwordcounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultithreadWordCounter {

	public static void main(final String[] args) {

		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}
		
		try {
			List<String> lineList = readFile(args[0]);
			
			for(String line : lineList) {
				System.out.println(line);
			}
			
		} catch (IOException e) {
			System.out.println("Dosya Okunurken Hata Olustu!");
		}

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
