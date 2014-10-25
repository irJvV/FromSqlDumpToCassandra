package com.example.cassandra;

import static org.junit.Assert.*;

import org.junit.Test;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

public class DBtesting {

	@Test
	public void createHasADbConnectionForFIB() {
		FileInfoBean FIB = new FileInfoBean();
		String testFile = "nlwiki-latest-category.sql";
		FIB = CheckIfFileExists.CheckFile(testFile);
		assertEquals(testFile,FIB.getInputFileName());
		FIB = CheckFilePrecompiled.CheckFile(FIB);
		CassandraDAO cdao = new CassandraDAO(FIB);
		FIB.setDbConnection(cdao);
		System.out.println(FIB.getDbConnection().getCluster());
		System.out.println(FIB.getDbConnection().getSession());
		System.out.println(FIB.getDbConnection().getPstmt());
		System.out.println(FIB.getDbConnection().getBstmt());
		assertEquals(true,true);
	}

}
