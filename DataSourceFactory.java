package darun.csvloader;

import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
 
import oracle.jdbc.pool.OracleDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.postgresql.ds.PGPoolingDataSource;

public class DataSourceFactory {

	private static Properties props = null;
	private static MysqlDataSource mysqlDS = null;
	private static OracleDataSource oracleDS = null;
	private static SQLServerDataSource sqlServerDS = null;
	private static PGPoolingDataSource postGreSQLDS = null;
			
    public static DataSource getDataSource() {
    	if(props == null){
    		props = ConfigLoader.getConfig();
    	}
    	String target = props.getProperty("TARGET");
    	switch(target){
    		case "MYSQL":
    			return getMySQLDataSource();
    		case "MSSQL":
    			return getSQLServerDataSource();
    		case "PGSQL":
    			return getPostGreSQLDataSource();
    		case "ORACLE":
    			return getOracleDataSource();
    		default:
    			System.out.println("ERROR: Invalid Target in properties file");
    			return null;
    	}
    }
    
    public static DataSource getMySQLDataSource() {
        if(mysqlDS == null){
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        }
        return mysqlDS;
    }
    
    public static DataSource getOracleDataSource(){
    	if(oracleDS == null){
    		try{
	            oracleDS = new OracleDataSource();
	            oracleDS.setURL(props.getProperty("ORACLE_DB_URL"));
	            oracleDS.setUser(props.getProperty("ORACLE_DB_USERNAME"));
	            oracleDS.setPassword(props.getProperty("ORACLE_DB_PASSWORD"));
    		}catch (SQLException e) {
    			e.printStackTrace();
    		}
        }
        return oracleDS;
    }

    public static DataSource getSQLServerDataSource(){
    	if(sqlServerDS == null){
    		try{
			    sqlServerDS = new SQLServerDataSource();
			    sqlServerDS.setUser(props.getProperty("MSSQL_DB_USERNAME"));
			    sqlServerDS.setPassword(props.getProperty("MSSQL_DB_PASSWORD"));
			    sqlServerDS.setURL(props.getProperty("MSSQL_DB_URL"));
       		}catch (Exception e) {
       			e.printStackTrace();
       		}
        }
        return sqlServerDS;
    }

    public static DataSource getPostGreSQLDataSource(){
    	if(postGreSQLDS == null){
    		try{
    			postGreSQLDS = new PGPoolingDataSource();
    			postGreSQLDS.setDataSourceName("PGSQL_DB_DS");
    			postGreSQLDS.setUrl(props.getProperty("PGSQL_DB_URL"));
    			postGreSQLDS.setUser(props.getProperty("PGSQL_DB_USERNAME"));
    			postGreSQLDS.setPassword(props.getProperty("PGSQL_DB_PASSWORD"));
    			postGreSQLDS.setMaxConnections(10);
	   		}catch (Exception e) {
				e.printStackTrace();
	    	}
    	}
        return postGreSQLDS;
    }
}