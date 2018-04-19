package darun.csvloader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

public class DatabaseSession {
	
	DataSource dSource = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
		
	public DatabaseSession(){
		try {
			dSource = DataSourceFactory.getDataSource();
			conn = dSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void prepareStatement(String sql){
		try{
			if(sql != null){
				pstmt = conn.prepareStatement(sql);
				conn.setAutoCommit(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addBatch(String[] values){
		try{
			if(pstmt != null){
				for(int i=0; i<values.length; i++){
					pstmt.setString(i+1, values[i]);
				}
				pstmt.addBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addParam(int pos, Object obj, int type){
		try{
			if(pstmt != null){
				pstmt.setObject(pos, obj, type);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int[] executeBatch(){
		int[] count = null;
		try{
			if(pstmt != null){
				count = pstmt.executeBatch();
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int executeUpdate(){
		int count = 0;
		try{
			if(pstmt != null){
				count = pstmt.executeUpdate();
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ResultSet executeQuery(){
		try{
			if(pstmt != null){
				rs = pstmt.executeQuery();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet executeQuery(String sql){
		try{
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(stmt != null){
				stmt.close();
				stmt = null;
			}
			if(sql != null && conn != null){
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public int executeUpdate(String sql){
		int count = 0;
		try{
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(stmt != null){
				stmt.close();
				stmt = null;
			}
			if(sql != null && conn != null){
				stmt = conn.createStatement();
				count = stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void close(){
		try {
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(stmt != null){
				stmt.close();
				stmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
			//System.out.println("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
