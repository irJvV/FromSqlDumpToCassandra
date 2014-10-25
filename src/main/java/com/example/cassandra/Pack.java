package com.example.cassandra;

public class Pack {
	
	private FileInfoBean FIB;
	private String[] packStrings;
	
	public Pack(FileInfoBean FIB){
		this.FIB = FIB;
		this.packStrings = new String[FIB.getStringsPerPack()];
	}
	
	public FileInfoBean getFIB() {
		return FIB;
	}
	public void setFIB(FileInfoBean fIB) {
		FIB = fIB;
	}
	public String[] getPackStrings() {
		return packStrings;
	}
	public void setPackStrings(String[] packStrings) {
		this.packStrings = packStrings;
	}

}
