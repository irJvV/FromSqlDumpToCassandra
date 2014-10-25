package com.example.cassandra;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.datastax.driver.core.BoundStatement;

public class FileInfoBean {
	
	private boolean fileExixts;
	private String persistence;

	private String node;
	private String keySpace;

	private String inputFileName;
	private String fileName;
	private File file;

	private int stringsPerPack;
	private int stringsPerPackCounter;
	private int delay;

	private String InsertSQL;
	private String regex;
	private String binder;
	private String dateFormat;
	private Pattern pattern;
	
	private List<Object> arrList;
	private String[] dataClasses;
	private boolean recording;
	private boolean inText;
	private boolean more;
	
	public static PrintStream out;
	
	private BoundStatement cdao;
	
	private CassandraDAO dbConnection;
	
	public FileInfoBean(){
		this.fileExixts = false;
		this.persistence = "Cassandra";
		this.node = "127.0.0.1";
		this.stringsPerPackCounter = 0;
		this.recording =false;
		this.inText = false;
		this.more = true;
		this.dbConnection = null;
	}

	public FileInfoBean getNlwikiLatestCategoryLinks() {
		FileInfoBean FIB = new FileInfoBean();
		FIB.fileName = "nlwiki-latest-categorylinks.sql";
		FIB.file = new File(FIB.getFileName());
		FIB.keySpace = "citeflow";
		FIB.stringsPerPack = 45000;
		FIB.delay = FIB.stringsPerPack/3;
		FIB.arrList = new ArrayList<Object>();
		FIB.dataClasses = new String[7];
		FIB.dataClasses[0] = "Integer";
		FIB.dataClasses[1] = "String";
		FIB.dataClasses[2] = "String";
		FIB.dataClasses[3] = "Date";
		FIB.dataClasses[4] = "String";
		FIB.dataClasses[5] = "String";
		FIB.dataClasses[6] = "String";
		FIB.InsertSQL = "INSERT INTO categorylinks (cl_from, cl_to, cl_sortkey, cl_timestamp, cl_sortkey_prefix, cl_collation, cl_type) VALUES(?,?,?,?,?,?,?);";
		FIB.regex = "\\(([0-9]*),('.*'),('.*'),'(.*)',('.*'),('.*'),('.*')\\)";
		FIB.pattern = Pattern.compile(FIB.getRegex());
		//FIB.binder= "integer1,m.group(2),m.group(3),date4,m.group(5),m.group(6),m.group(7)";
		FIB.binder= "FIB.getArrList().get(0),FIB.getArrList().get(1),FIB.getArrList().get(2),FIB.getArrList().get(3),FIB.getArrList().get(4),FIB.getArrList().get(5),FIB.getArrList().get(6)";
		FIB.dateFormat = "yyyy-MM-dd HH:mm:ss";
		FIB.dbConnection = new CassandraDAO(FIB);
		FIB.setDbConnection(FIB.dbConnection);
		return FIB;
	}

	public FileInfoBean getNlwikiLatestCategoryLinksTest() {
		FileInfoBean FIB = new FileInfoBean();
		FIB = FIB.getNlwikiLatestCategoryLinks();
		FIB.fileName = "catlinkTest.txt";
		FIB.keySpace = "testdb";
		FIB.stringsPerPack = 10;
		FIB.delay = FIB.stringsPerPack/3;
		FIB.file = new File(FIB.getFileName());
		return FIB;
	}

	public FileInfoBean getNlwikiLatestCategory() {
		FileInfoBean FIB = new FileInfoBean();
		FIB.fileName = "nlwiki-latest-category.sql";
		FIB.file = new File(FIB.getFileName());
		FIB.keySpace = "citeflow";
		FIB.stringsPerPack = 45000;
		FIB.delay = FIB.stringsPerPack/3;
		FIB.arrList = new ArrayList<Object>();
		FIB.dataClasses = new String[5];
		FIB.dataClasses[0] = "Integer";
		FIB.dataClasses[1] = "String";
		FIB.dataClasses[2] = "Integer";
		FIB.dataClasses[3] = "Integer";
		FIB.dataClasses[4] = "Integer";
		FIB.InsertSQL = "INSERT INTO category (cat_id, cat_title, cat_pages, cat_subcats, cat_files) VALUES(?,?,?,?,?);";
		FIB.regex = "\\(([0-9]*),('.*'),([0-9]*),([0-9]*),([0-9]*)\\)";
		FIB.pattern = Pattern.compile(FIB.getRegex());
		FIB.binder= "this.FIB.getArrList().get(0),this.FIB.getArrList().get(1),this.FIB.getArrList().get(2),this.FIB.getArrList().get(3),this.FIB.getArrList().get(4)";
		FIB.dbConnection = new CassandraDAO(FIB);
		FIB.setDbConnection(FIB.dbConnection);
		return FIB;
	}
	
	public FileInfoBean getNlwikiLatestCategoryTest() {
		FileInfoBean FIB = new FileInfoBean();
		FIB = FIB.getNlwikiLatestCategory();
		FIB.fileName = "Hello1.txt";
		FIB.keySpace = "testdb";
		FIB.stringsPerPack = 10;
		FIB.delay = FIB.stringsPerPack/3;
		FIB.file = new File(FIB.getFileName());
		return FIB;
	}
	
	public BoundStatement bindStatementToConnection(List<Object> arrList2, FileInfoBean FIB) {
		
		if(FIB.getFileName().equals("nlwiki-latest-category.sql")|FIB.getFileName().equals("Hello1.txt")){
		this.cdao = FIB
				.getDbConnection()
				.getBstmt()
				.bind(	FIB.getArrList().get(0),
						FIB.getArrList().get(1),
						FIB.getArrList().get(2),
						FIB.getArrList().get(3),
						FIB.getArrList().get(4));
		}
		if(FIB.getFileName().equals("nlwiki-latest-categorylinks.sql")|FIB.getFileName().equals("catlinkTest.txt")){
			this.cdao = FIB
					.getDbConnection()
					.getBstmt()
					.bind(	FIB.getArrList().get(0),
							FIB.getArrList().get(1),
							FIB.getArrList().get(2),
							FIB.getArrList().get(3),
							FIB.getArrList().get(4),
							FIB.getArrList().get(5),
							FIB.getArrList().get(6));
		}
		return this.cdao;
	}
	
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getKeySpace() {
		return keySpace;
	}

	public void setKeySpace(String keySpace) {
		this.keySpace = keySpace;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getStringsPerPack() {
		return stringsPerPack;
	}

	public void setStringsPerPack(int stringsPerPackFM) {
		this.stringsPerPack = stringsPerPackFM;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public String getInsertSQL() {
		return InsertSQL;
	}

	public void setInsertSQL(String insertSQL) {
		InsertSQL = insertSQL;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
	
	public String getBinder() {
		return binder;
	}

	public void setBinder(String binder) {
		this.binder = binder;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getStringsPerPackCounter() {
		return stringsPerPackCounter;
	}

	public void setStringsPerPackCounter(int stringsPerPackCounter) {
		this.stringsPerPackCounter = stringsPerPackCounter;
	}

	public boolean isRecording() {
		return recording;
	}

	public void setRecording(boolean recording) {
		this.recording = recording;
	}

	public boolean isInText() {
		return inText;
	}

	public void setInText(boolean inText) {
		this.inText = inText;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public boolean isMore() {
		return more;
	}

	public void setMore(boolean more) {
		this.more = more;
	}

	public String getPersistence() {
		return persistence;
	}

	public void setPersistence(String persistence) {
		this.persistence = persistence;
	}

	public String[] getDataClasses() {
		return dataClasses;
	}

	public void setDataClasses(String[] dataClasses) {
		this.dataClasses = dataClasses;
	}

	@SuppressWarnings("rawtypes")
	public List getArrList() {
		return arrList;
	}


	public void setArrList(List<Object> arrList) {
		this.arrList = arrList;
	}

	public boolean isFileExixts() {
		return fileExixts;
	}

	public void setFileExixts(boolean fileExixts) {
		this.fileExixts = fileExixts;
	}

	public CassandraDAO getDbConnection() {
		return dbConnection;
	}

	public void setDbConnection(CassandraDAO dbConnection) {
		this.dbConnection = dbConnection;
	}

	public BoundStatement getCdao() {
		return cdao;
	}

	public void setCdao(BoundStatement cdao) {
		this.cdao = cdao;
	}


}
