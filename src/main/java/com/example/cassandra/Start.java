package com.example.cassandra;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Start {

	public static void main(String[] args) {
		try {
			FileInfoBean.out = new PrintStream(new FileOutputStream("error_text.txt"));
			System.setErr(FileInfoBean.out);
		} catch (FileNotFoundException e) {
			System.err.println(e.getStackTrace());
		}

		if (args.length == 0) {
			System.out.println("Please enter with file name to proces:");
			System.out
					.println("(can be nlwiki-latest-category.sql or nlwiki-latest-categorylinks.sql)");
			System.out.println("(to process both enter on command line)");
			Scanner inputReader = new Scanner(System.in);
			String input = inputReader.nextLine();
			args = new String[1];
			args[0] = input;
			inputReader.close();
		}
		
		for (String input : args) {
			FileInfoBean FIB;
			FIB = CheckIfFileExists.CheckFile(input);
			FIB = CheckFilePrecompiled.CheckFile(FIB);
			new ParseFileIntoPackages(FIB);
		}
	}
}
