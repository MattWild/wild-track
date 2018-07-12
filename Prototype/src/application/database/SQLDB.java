package application.database;

import java.sql.SQLException;

public class SQLDB extends AppDatabase {
	private static final String DRIVER_PATH = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	private String address;
	private String dbName;

	public SQLDB(String address, String user, String pass, String dbName) throws SQLException, ClassNotFoundException {
		super(user, pass);
		this.address = address;
		this.dbName = dbName;
		
		this.connect();
	}

	@Override
	public String generateURL() {
		return "jdbc:sqlserver://" + this.address +";databaseName=" + this.dbName;
	}

	@Override
	protected String getDriverClassName() {
		return DRIVER_PATH;
	}

	@Override
	public boolean isSQL() {
		return true;
	}

}
