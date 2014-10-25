package com.example.cassandra;

import java.util.Scanner;

public class CheckFilePrecompiled {

	public static FileInfoBean CheckFile(FileInfoBean FIB) {
		FIB.setFileExixts(false);
		if(FIB.getInputFileName().equals("nlwiki-latest-category.sql")){FIB.setFileExixts(true);}
		if(FIB.getInputFileName().equals("Hello1.txt")){FIB.setFileExixts(true);}
		if(FIB.getInputFileName().equals("nlwiki-latest-categorylinks.sql")){FIB.setFileExixts(true);}
		if(FIB.getInputFileName().equals("catlinkTest.txt")){FIB.setFileExixts(true);}
		while (!FIB.isFileExixts()){
			System.out.println("This program at this point can only handle:");
			System.out.println("- nlwiki-latest-category.sql (if exists)");
			System.out.println("- nlwiki-latest-categorylinks.sql if exists)");
			System.out.println("Please enter one of these or enter to quit:");
			Scanner inputReader = new Scanner(System.in);
			String input = inputReader.nextLine();
			if (input == "") {
				System.exit(0);
			}
			FIB.setInputFileName(input);
			inputReader.close();
			FIB = CheckIfFileExists.CheckFile(input);
		}
		if(FIB.getInputFileName().equals("nlwiki-latest-category.sql")){
			FIB = FIB.getNlwikiLatestCategory();
			FIB.setFileName("nlwiki-latest-category.sql");
			FIB.setFileExixts(true);
		} else if(FIB.getInputFileName().equals("Hello1.txt")){
			FIB = FIB.getNlwikiLatestCategoryTest();
			FIB.setFileName("Hello1.txt");
			FIB.setFileExixts(true);
		} else if(FIB.getInputFileName().equals("nlwiki-latest-categorylinks.sql")){
			FIB = FIB.getNlwikiLatestCategoryLinks();
			FIB.setFileName("nlwiki-latest-categorylinks.sql");
			FIB.setFileExixts(true);
		} else if(FIB.getInputFileName().equals("catlinkTest.txt")){
			FIB = FIB.getNlwikiLatestCategoryLinksTest();
			FIB.setFileName("catlinkTest.txt");
			FIB.setFileExixts(true);
		} else {
			System.out.println("Please try again");
			System.exit(0);}
		return FIB;
	}
}
