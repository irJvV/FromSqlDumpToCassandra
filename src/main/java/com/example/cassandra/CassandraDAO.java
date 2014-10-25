package com.example.cassandra;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

public class CassandraDAO {
	
	private Cluster cluster;
	private Session session;
	private PreparedStatement pstmt;
	private BoundStatement bstmt;
	
	public CassandraDAO(FileInfoBean FIB){
		this.connect(FIB);
		this.session = this.cluster.connect(FIB.getKeySpace());
		this.createQueryStatement(FIB);
	}
	
	public CassandraDAO connect(FileInfoBean FIB) {
		/* one connection as part of specific file */
		this.cluster = Cluster.builder()
		         .addContactPoint(FIB.getNode())
		         .build();
		System.out.println("CassandraDAO connected");
		return this;
	}
	
	public void close(FileInfoBean FIB) {
		/* close at the end if file is read and threads are finished */
		this.cluster.close();
	}
	
	public void createQueryStatement(FileInfoBean FIB){
		this.pstmt = this.session.prepare(FIB.getInsertSQL());
		this.bstmt = new BoundStatement(this.pstmt);
	}
	
	public BoundStatement bindStatement(FileInfoBean FIB){
		BoundStatement boundBstmt = bstmt.bind(FIB.getBinder());
		return boundBstmt;
		
	}
	
	public void toCassandra(BoundStatement boundBstmt, FileInfoBean FIB){
		this.session.execute(boundBstmt);
	}
	
	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public PreparedStatement getPstmt() {
		return pstmt;
	}

	public void setPstmt(PreparedStatement pstmt) {
		this.pstmt = pstmt;
	}

	public BoundStatement getBstmt() {
		return bstmt;
	}

	public void setBstmt(BoundStatement bstmt) {
		this.bstmt = bstmt;
	}
}
