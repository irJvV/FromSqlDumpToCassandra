package com.example.cassandra;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;
import java.util.regex.Pattern;

import org.junit.Test;

public class JUnittestFile {
	
	@Test
	public void testFileExist() {
		FileInfoBean FIB = new FileInfoBean();
		String testFile = "nlwiki-latest-category.sql";
		FIB = CheckIfFileExists.CheckFile(testFile);
		assertEquals(testFile,FIB.getInputFileName());
	}
	
	@Test
	public void testFileIsPrecomiled() {
		FileInfoBean FIB = new FileInfoBean();
		String testFile = "nlwiki-latest-category.sql";
		FIB = CheckIfFileExists.CheckFile(testFile);
		assertEquals(testFile,FIB.getInputFileName());
		FIB = CheckFilePrecompiled.CheckFile(FIB);
		assertEquals(true,FIB.isFileExixts());
	}
	
	@Test
	public void testFileToFileObject1() {
		FileInfoBean FIB = new FileInfoBean();
		FIB = FIB.getNlwikiLatestCategory();
		File file = new File("nlwiki-latest-category.sql");
		assertEquals(file,FIB.getFile());
	}
	
	@Test
	public void testFileToFileObject2() {
		FileInfoBean FIB = new FileInfoBean();
		String testFile = "nlwiki-latest-category.sql";
		FIB = CheckIfFileExists.CheckFile(testFile);
		assertEquals(testFile,FIB.getInputFileName());
		FIB = CheckFilePrecompiled.CheckFile(FIB);
		File file = new File("nlwiki-latest-category.sql");
		assertEquals(file,FIB.getFile());
	}
	
	@Test
	public void testPatternToPattern1() {
		FileInfoBean FIB = new FileInfoBean();
		FIB = FIB.getNlwikiLatestCategory();
		//FIB = CheckFilePrecompiled.CheckFile(FIB);
		assertEquals(FIB.getRegex(), "\\(([0-9]*),('.*'),([0-9]*),([0-9]*),([0-9]*)\\)");

	}
	
	@Test
	public void testPatternToPattern2() {
		FileInfoBean FIB = new FileInfoBean();
		String testFile = "nlwiki-latest-category.sql";
		FIB = CheckIfFileExists.CheckFile(testFile);
		assertEquals(testFile,FIB.getInputFileName());
		FIB = CheckFilePrecompiled.CheckFile(FIB);
		assertEquals(FIB.getRegex(), "\\(([0-9]*),('.*'),([0-9]*),([0-9]*),([0-9]*)\\)");

	}
	
	@Test
	public void testFileInfoBean1() {
		FileInfoBean FIB = new FileInfoBean();
		FIB = FIB.getNlwikiLatestCategoryLinksTest();
		assertEquals(FIB.getFileName(),"catlink.txt");
		assertEquals(FIB.getKeySpace(), "testdb");
		assertEquals(FIB.getStringsPerPack(), 10);
		assertEquals(FIB.getDelay() ,FIB.getStringsPerPack()/3);
		assertEquals(FIB.getNode(), "127.0.0.1");
		assertEquals(FIB.getInsertSQL(), "INSERT INTO categorylinks (cl_from, cl_to, cl_sortkey, cl_timestamp, cl_sortkey_prefix, cl_collation, cl_type) VALUES(?,?,?,?,?,?,?);");
		assertEquals(FIB.getRegex(), "\\(([0-9]*),('.*'),('.*'),'([\\S]*[\\s]*[\\S]*)',('[\\S]*'),('[\\S]*'),('[\\S]*')\\)");
		//assertEquals(FIB.getBinder(),"Integer.parseInt(m.group(1)),m.group(2),m.group(3),date,m.group(5),m.group(6),m.group(7)");
		assertEquals(FIB.getBinder(),"FIB.getArrList()[0],FIB.getArrList()[1],FIB.getArrList()[2],FIB.getArrList()[3],FIB.getArrList()[4],FIB.getArrList()[5],FIB.getArrList()[6]");

	}
	
	@Test
	public void testFileInfoBean2() {
		FileInfoBean FIB = new FileInfoBean();
		FIB = FIB.getNlwikiLatestCategoryLinks();
		assertEquals(FIB.getFileName(),"nlwiki-latest-categorylinks.sql");
		assertEquals(FIB.getKeySpace(), "citeflow");
		assertEquals(FIB.getStringsPerPack(), 180000);
		assertEquals(FIB.getDelay() ,FIB.getStringsPerPack()/3);
		assertEquals(FIB.getNode(), "127.0.0.1");
		assertEquals(FIB.getInsertSQL(), "INSERT INTO categorylinks (cl_from, cl_to, cl_sortkey, cl_timestamp, cl_sortkey_prefix, cl_collation, cl_type) VALUES(?,?,?,?,?,?,?);");
		assertEquals(FIB.getRegex(), "\\(([0-9]*),('.*'),('.*'),'([\\S]*[\\s]*[\\S]*)',('[\\S]*'),('[\\S]*'),('[\\S]*')\\)");
		assertEquals(FIB.getBinder(),"FIB.getArrList()[0],FIB.getArrList()[1],FIB.getArrList()[2],FIB.getArrList()[3],FIB.getArrList()[4],FIB.getArrList()[5],FIB.getArrList()[6]");
	}

	@Test
	public void testFileInfoBean3() {
		FileInfoBean FIB = new FileInfoBean();
		FIB = FIB.getNlwikiLatestCategoryTest();
		assertEquals(FIB.getFileName(),"Hello1.txt");
		assertEquals(FIB.getKeySpace(), "testdb");
		assertEquals(FIB.getStringsPerPack(), 10);
		assertEquals(FIB.getDelay() ,FIB.getStringsPerPack()/3);
		assertEquals(FIB.getNode(), "127.0.0.1");
		assertEquals(FIB.getInsertSQL(), "INSERT INTO category (cat_id, cat_title, cat_pages, cat_subcats, cat_files) VALUES(?,?,?,?,?);");
		assertEquals(FIB.getRegex(), "\\(([0-9]*),('.*'),([0-9]*),([0-9]*),([0-9]*)\\)");
		assertEquals(FIB.getBinder(),"Integer.parseInt(m.group(1)),m.group(2),Integer.parseInt(m.group(3)),Integer.parseInt(m.group(4)),Integer.parseInt(m.group(5))");
	}
	
	@Test
	public void testFileInfoBean4() {
		FileInfoBean FIB = new FileInfoBean();
		FIB = FIB.getNlwikiLatestCategory();
		assertEquals(FIB.getFileName(),"nlwiki-latest-category.sql");
		assertEquals(FIB.getKeySpace(), "citeflow");
		assertEquals(FIB.getStringsPerPack(), 90000);
		assertEquals(FIB.getDelay() ,FIB.getStringsPerPack()/3);
		assertEquals(FIB.getNode(), "127.0.0.1");
		assertEquals(FIB.getInsertSQL(), "INSERT INTO category (cat_id, cat_title, cat_pages, cat_subcats, cat_files) VALUES(?,?,?,?,?);");
		assertEquals(FIB.getRegex(), "\\(([0-9]*),('.*'),([0-9]*),([0-9]*),([0-9]*)\\)");
		assertEquals(FIB.getBinder(),"Integer.parseInt(m.group(1)),m.group(2),Integer.parseInt(m.group(3)),Integer.parseInt(m.group(4)),Integer.parseInt(m.group(5))");
	}
	
	@Test
	public void testStringToDate1() {
		FileInfoBean FIB = new FileInfoBean();
		FIB = FIB.getNlwikiLatestCategoryLinks();
		Date dateDirect = new Date(1408515259000L);
		Date dateResponse = ClassTransFormer.fromStringToDate("2014-08-20 08:14:19",FIB);
		assertEquals(dateDirect,dateResponse);
	}
	
	@Test
	public void testStringToInteger1() {
		Integer i = ClassTransFormer.fromStringToInteger("111");
		assertEquals((int)i,111);
	}
	

}
