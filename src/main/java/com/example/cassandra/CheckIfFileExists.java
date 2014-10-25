package com.example.cassandra;

import java.io.File;
import java.util.Scanner;

public class CheckIfFileExists {
	
	public static FileInfoBean CheckFile(String input){
		FileInfoBean FIB = new FileInfoBean();
		File file = new File(input);
		while(!file.exists()) {
			System.out.println("input file(s) not found, please enter with proper file name:");
			Scanner inputReader = new Scanner(System.in);
			input = inputReader.nextLine();
			inputReader.close();
			file = new File(input);
		}
		
		FIB.setInputFileName(input);
		System.out.println("File exits: "+input);
		return FIB;
	}
}
