package com.example.cassandra;

import java.util.ArrayList;
import java.util.regex.Matcher;

import com.datastax.driver.core.BoundStatement;

public class ImportPackageToCassandraDB extends Thread {

	private Pack pack;
	private FileInfoBean FIB;
	private static int activeThreads;
	private static int totalThreads;

	public ImportPackageToCassandraDB(Pack pack) {
		this.pack = pack;
		this.FIB = pack.getFIB();
		this.start();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		// link to cassandra

		ImportPackageToCassandraDB.activeThreads++;
		ImportPackageToCassandraDB.totalThreads++;
		System.setErr(FileInfoBean.out);
		System.out.println(this.pack.getPackStrings().length + " (+): "
				+ ImportPackageToCassandraDB.getTotalThreads() + " : "
				+ ImportPackageToCassandraDB.getActiveThreads());
		if (this.pack.getPackStrings().length != 0) {
			for (String str : this.pack.getPackStrings()) {
				if (str != null) {
					// System.out.println(this.pack.getPackStrings().length
					// +" : "+this.pack.getPackStrings()[0]
					// +" : "+this.pack.getPackStrings()[1]
					// +" : "+this.pack.getPackStrings()[2]
					// +" : "+this.pack.getPackStrings()[3]
					// +" : "+this.pack.getPackStrings()[4]);
					Matcher m = this.FIB.getPattern().matcher(str);

					while (m.find()) {
						// hier array dataClasses doorlopen, eventueel type
						// convert en in bind inpassen
						int c = 1;
						for (String clss : this.FIB.getDataClasses()) {
							switch (clss) {
							case "Integer":
								this.FIB.getArrList().add(
										ClassTransFormer.fromStringToInteger(m
												.group(c)));
								break;
							case "Date":
								this.FIB.getArrList().add(
										ClassTransFormer.fromStringToDate(
												m.group(c), this.FIB));
								break;
							case "String":
								this.FIB.getArrList().add(m.group(c));
								break;
							default:
								this.FIB.getArrList().add(m.group(c));
								break;
							}
							c++;
						}
						synchronized (this.FIB) {
							
							BoundStatement cdao = this.FIB.bindStatementToConnection(this.FIB.getArrList(), this.FIB);
							
							this.FIB.getDbConnection().getSession()
									.execute(cdao);
							this.FIB.setArrList(new ArrayList<Object>());
						}
					}
				}
			}
		}
		ImportPackageToCassandraDB.activeThreads--;
		System.out.println(this.pack.getPackStrings().length + " (-): "
				+ ImportPackageToCassandraDB.totalThreads + " : "
				+ ImportPackageToCassandraDB.activeThreads);
		if (ImportPackageToCassandraDB.activeThreads <= 0
				&& this.FIB.isMore() == false) {
			System.out.println("this.FIB.isMore() == false");
			this.FIB.getDbConnection().close(this.FIB);
		}

	}

	public FileInfoBean getFIB() {
		return FIB;
	}

	public void setFIB(FileInfoBean fIB) {
		FIB = fIB;
	}

	public static int getActiveThreads() {
		return activeThreads;
	}

	public static void setActiveThreads(int activeThreads) {
		ImportPackageToCassandraDB.activeThreads = activeThreads;
	}

	public static int getTotalThreads() {
		return totalThreads;
	}

	public static void setTotalThreads(int totalThreads) {
		ImportPackageToCassandraDB.totalThreads = totalThreads;
	}

}
