package com.example.cassandra;

public class SendPackageToPersistence extends Thread {

	private Pack pack;

	public SendPackageToPersistence(Pack pack) {
		this.pack = pack;
		this.start();
	}

	@Override
	public void run() {
		switch (this.pack.getFIB().getPersistence()) {
		case "Cassandra":	new ImportPackageToCassandraDB(this.pack);
							break;
		}
	}

}
