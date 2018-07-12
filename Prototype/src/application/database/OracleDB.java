package application.database;

import java.sql.SQLException;

public class OracleDB extends AppDatabase {
	private static final String DRIVER_PATH = "oracle.jdbc.driver.OracleDriver";
	private String address;
	private String port;
	private String sid;
	private boolean useSID;
	
	public OracleDB(String address, String user, String pass, String port, String sid, String useSID) throws SQLException, ClassNotFoundException {
		super(user, pass);
		this.address = address;
		this.pass = pass;
		this.port = port;
		this.sid = sid;
		this.useSID = (useSID.compareTo("1")==0)? true : false;
		
		
		this.connect();
	}

	@Override
	public String generateURL() {
		if (useSID)
			return "jdbc:oracle:thin:@" + this.address +":" + this.port + ":" + this.sid;
		else
			return "jdbc:oracle:thin:@//" + this.address +":" + this.port + "/" + this.sid;
	}

	@Override
	protected String getDriverClassName() {
		return DRIVER_PATH;
	}

	@Override
	public boolean isSQL() {
		return false;
	}

}
