package application.database;

public class Queries {

	private static final String SQL_METERS = "select meterNo, meterName, meterType, CRC, latestValueDate, custom1, custom2, notes from (\r\n" + 
			"	select distinct Meters.meterNo, MeterModels.name as meterName, MeterTypes.name as meterType, GridStreamNtwrks.gridStreamNtwrkQT as CRC, IDReadingLatestValues.latestValueDate, Meters.custom1, Meters.custom2, Notes.notes, ROW_NUMBER() OVER (PARTITION BY Meters.meterNo ORDER BY IDReadingLatestValues.latestValueDate DESC) rn  from Endpoints \r\n" + 
			"	inner join Meters on Meters.meterId = Endpoints.meterId\r\n" + 
			"	inner join MeterTypes on Meters.meterTypeId = MeterTypes.meterTypeId\r\n" + 
			"	left join IDReadingLatestValues on IDReadingLatestValues.endpointId = EndPoints.endPointId\r\n" + 
			"	left join RFEndpointProperties on RFEndpointProperties.endpointId = Endpoints.endPointId\r\n" + 
			"	left join MeterModels on RFEndpointProperties.meterModelId = MeterModels.meterModelId\r\n" + 
			"	left join (Select distinct Notes2.endpointId, \r\n" + 
			"		substring(\r\n" + 
			"			(\r\n" + 
			"				Select ';'+note  AS [text()]\r\n" + 
			"				From (\r\n" + 
			"					select CONVERT(VARCHAR(max), note) as note, endpointID from EndpointNotes\r\n" + 
			"				) as Notes1\r\n" + 
			"				Where Notes1.endpointId = Notes2.endpointId\r\n" + 
			"				ORDER BY Notes1.endpointId\r\n" + 
			"				For XML PATH ('')\r\n" + 
			"			), 2, 1000) notes\r\n" + 
			"	From (\r\n" + 
			"		select CONVERT(VARCHAR(max), note) as note, endpointID from EndpointNotes\r\n" + 
			"	) as Notes2) as Notes on Notes.endpointId = Endpoints.endPointId\r\n" + 
			"	cross join GridStreamNtwrks\r\n" + 
			") as result where rn = 1";
	
	private static final String ORACLE_METERS = "select meterNo, meterName, meterType, CRC, latestValueDate, custom1, custom2, notes from (\r\n" + 
			"	select Meters.meterNo, MeterModels.name as meterName, MeterTypes.name as meterType, GridStreamNtwrks.gridStreamNtwrkQT as CRC, Meters.custom1, Meters.custom2, IDReadingLatestValues.latestValueDate, Notes.notes, ROW_NUMBER() OVER (PARTITION BY Meters.meterNo ORDER BY IDReadingLatestValues.latestValueDate DESC) rn from Endpoints \r\n" + 
			"    inner join Meters on Meters.meterId = Endpoints.meterId\r\n" + 
			"	inner join MeterTypes on Meters.meterTypeId = MeterTypes.meterTypeId\r\n" + 
			"	left join IDReadingLatestValues on IDReadingLatestValues.endpointId = EndPoints.endPointId\r\n" + 
			"	left join RFEndpointProperties on RFEndpointProperties.endpointId = Endpoints.endPointId\r\n" + 
			"	left join MeterModels on RFEndpointProperties.meterModelId = MeterModels.meterModelId\r\n" + 
			"    left join (select endpointID, LISTAGG(note, '; ') WITHIN GROUP (ORDER BY NULL) notes from EndpointNotes GROUP BY endpointID) Notes on Notes.endpointID = Endpoints.ENDPOINTID\r\n" + 
			"	cross join GridStreamNtwrks\r\n" + 
			") result where rn = 1";
	
	private static final String SQL_ROUTERS = 
			"select meterNo, meterName, CRC, latestValueDate, custom1, notes from (\r\n" + 
			"	select distinct Meters.meterNo, MeterModels.name as meterName, GridStreamNtwrks.gridStreamNtwrkQT as CRC, IDReadingLatestValues.latestValueDate, Meters.custom1, Notes.notes, ROW_NUMBER() OVER (PARTITION BY Meters.meterNo ORDER BY IDReadingLatestValues.latestValueDate DESC) rn  from Endpoints \r\n" + 
			"	inner join Meters on Meters.meterId = Endpoints.meterId\r\n" + 
			"	inner join MeterTypes on Meters.meterTypeId = MeterTypes.meterTypeId\r\n" + 
			"	left join IDReadingLatestValues on IDReadingLatestValues.endpointId = EndPoints.endPointId\r\n" + 
			"	left join RFEndpointProperties on RFEndpointProperties.endpointId = Endpoints.endPointId\r\n" + 
			"	left join MeterModels on RFEndpointProperties.meterModelId = MeterModels.meterModelId\r\n" + 
			"	left join (Select distinct Notes2.endpointId, \r\n" + 
			"		substring(\r\n" + 
			"			(\r\n" + 
			"				Select ';'+note  AS [text()]\r\n" + 
			"				From (\r\n" + 
			"					select CONVERT(VARCHAR(max), note) as note, endpointID from EndpointNotes\r\n" + 
			"				) as Notes1\r\n" + 
			"				Where Notes1.endpointId = Notes2.endpointId\r\n" + 
			"				ORDER BY Notes1.endpointId\r\n" + 
			"				For XML PATH ('')\r\n" + 
			"			), 2, 1000) notes\r\n" + 
			"	From (\r\n" + 
			"		select CONVERT(VARCHAR(max), note) as note, endpointID from EndpointNotes\r\n" + 
			"	) as Notes2) as Notes on Notes.endpointId = Endpoints.endPointId\r\n" + 
			"	cross join GridStreamNtwrks\r\n" + 
			"	where MeterTypes.MeterTypeID in (4,5)\r\n" +
			") as result where rn = 1";
	
	private static final String ORACLE_ROUTERS = "select meterNo, meterName, CRC, latestValueDate, custom1, notes from (\r\n" + 
			"	select distinct Meters.meterNo, MeterModels.name as meterName, GridStreamNtwrks.gridStreamNtwrkQT as CRC, IDReadingLatestValues.latestValueDate, Meters.custom1, Notes.notes, ROW_NUMBER() OVER (PARTITION BY Meters.meterNo ORDER BY IDReadingLatestValues.latestValueDate DESC) rn from Endpoints \r\n" + 
			"    inner join Meters on Meters.meterId = Endpoints.meterId\r\n" + 
			"	inner join MeterTypes on Meters.meterTypeId = MeterTypes.meterTypeId\r\n" + 
			"	left join IDReadingLatestValues on IDReadingLatestValues.endpointId = EndPoints.endPointId\r\n" + 
			"	left join RFEndpointProperties on RFEndpointProperties.endpointId = Endpoints.endPointId\r\n" + 
			"	left join MeterModels on RFEndpointProperties.meterModelId = MeterModels.meterModelId\r\n" + 
			"    left join (select endpointID, LISTAGG(note, '; ') WITHIN GROUP (ORDER BY NULL) notes from EndpointNotes GROUP BY endpointID) Notes on Notes.endpointID = Endpoints.ENDPOINTID\r\n" + 
			"	cross join GridStreamNtwrks\r\n" + 
			"	where MeterTypes.meterTypeID in (4,5)\r\n" + 
			") result where rn = 1";
	
	private static final String COLLECTORS_QUERY = "select Collectors.ipAddress, Radios.Radios, GridStreamNtwrks.gridStreamNtwrkQT, CollectorTypes.name\r\n" + 
			"from Collectors\r\n" + 
			"inner join (Select distinct Radios2.spuId, \r\n" + 
			"    substring(\r\n" + 
			"        (\r\n" + 
			"            Select ','+Radios1.RadioName  AS [text()]\r\n" + 
			"            From (\r\n" + 
			"				select Right(CONVERT(varchar(30), CONVERT(VARBINARY(8), CAST(Endpoints.serialNumber as bigint)), 1),12) as RadioName, Endpoints.spuId from Endpoints\r\n" + 
			"				inner join EndpointModels on Endpoints.endPointModelId = EndPointModels.endPointModelId\r\n" + 
			"				inner join Meters on Meters.meterId = Endpoints.meterId\r\n" + 
			"				where Endpoints.EndpointModelId in (65558,65600)\r\n" + 
			"			) as Radios1\r\n" + 
			"            Where Radios1.spuId = Radios2.spuId\r\n" + 
			"            ORDER BY Radios1.spuId\r\n" + 
			"            For XML PATH ('')\r\n" + 
			"        ), 2, 1000) [Radios]\r\n" + 
			"From (\r\n" + 
			"	select Right(CONVERT(varchar(30), CONVERT(VARBINARY(8), CAST(Endpoints.serialNumber as bigint)), 1),12) as RadioName, Endpoints.spuId from Endpoints\r\n" + 
			"	inner join EndpointModels on Endpoints.endPointModelId = EndPointModels.endPointModelId\r\n" + 
			"	inner join Meters on Meters.meterId = Endpoints.meterId\r\n" + 
			"	where Endpoints.EndpointModelId in (65558,65600)\r\n" + 
			") as Radios2) as Radios on Collectors.collectorId = Radios.spuId \r\n" + 
			"inner join CollectorTypes on Collectors.collectorTypeId = CollectorTypes.collectorTypeId\r\n" + 
			"cross join GridStreamNtwrks;";
	
	private static final String ROUTERS_QUERY = "select Right(CONVERT(varchar(30), CONVERT(VARBINARY(8), CAST(Endpoints.serialNumber as bigint)), 1),12) as LANADDRESS, gridStreamNtwrkQT, EndpointModels.name from Endpoints \r\n" + 
			"inner join EndpointModels on Endpoints.endPointModelId = EndPointModels.endPointModelId\r\n" + 
			"cross join GridStreamNtwrks\r\n" + 
			"where EndpointModels.name like '%Router%';";

	private static final String SQL_UPDATEENVIRONMENTMETERS = "DECLARE\r\n" + 
			"@lan_address VARCHAR(50) = ?,\r\n" + 
			"@loc VARCHAR(50) = ?,\r\n" + 
			"@sock VARCHAR(50) = ?\r\n" + 
			"\r\n" + 
			"UPDATE Meters\r\n" + 
			"SET custom1 = @loc,\r\n" + 
			"    custom2 = @sock\r\n" +
			"WHERE meterNo = @lan_address";

	private static final String ORACLE_UPDATEENVIRONMENTMETERS = "DECLARE\r\n" + 
			"lan_address varchar(50) := ?;\r\n" + 
			"loc VARCHAR(50) := ?;\r\n" + 
			"sock VARCHAR(50) := ?;\r\n" +
			"BEGIN\r\n" + 
			"UPDATE Meters\r\n" + 
			"SET custom1 = loc,\r\n" +
			"    custom2 = sock\r\n" +
			"WHERE meterNo = lan_address;\r\n" + 
			"END;";
	
	private static final String ORACLE_ENVIRONCONNPARAM = null;
	
	private static final String SQL_ENVIRONIDFROMCRC = "SELECT id from Environments where CRC = ?";
	
	private static final String ORACLE_ENVIRONIDFROMCRC = null;
	
	private static final String SQL_CLEARMETERENVIRONRELATIONS = "DELETE FROM AltEnvironmentRelations";
	
	private static final String ORACLE_CLEARMETERENVIRONRELATIONS = null;

	private static final String SQL_ADDNOTEENVIRONMENT = "DECLARE @endpointID int;\r\n" + 
			"\r\n" + 
			"BEGIN\r\n" + 
			"	set @endpointID = (select EndpointID from Endpoints\r\n" + 
			"		inner join Meters on Meters.meterId = Endpoints.meterId\r\n" + 
			"		where Meters.meterNo = ?)\r\n" + 
			"\r\n" + 
			"	insert into EndpointNotes (endpointId, userId, created, noteType, note)\r\n" + 
			"	values (@endpointID, 4, CURRENT_TIMESTAMP, 0, ?)\r\n" + 
			"END";

	private static final String ORACLE_ADDNOTEENVIRONMENT = "DECLARE eid int;\r\n" + 
			"     noteId int;" + 
			"\r\n" + 
			"BEGIN\r\n" + 
			"	select EndpointID into eid from Endpoints\r\n" + 
			"		inner join Meters on Meters.meterId = Endpoints.meterId\r\n" + 
			"		where Meters.meterNo = ?;\r\n" + 
			"\r\n" +
			"	SELECT EndpointNotes_endpointNoteId_S.nextval into noteId FROM DUAL;" +
			"\r\n" + 
			"	insert into EndpointNotes (endpointNoteId, endpointId, userId, created, noteType, note)\r\n" + 
			"	values (noteId, eid, 4, CURRENT_TIMESTAMP, 0, ?);\r\n" + 
			"END;";

	private static final String ORACLE_UPDATEENVIRONMENTROUTERS = "DECLARE\r\n" + 
			"lan_address varchar(50) := ?;\r\n" + 
			"loc VARCHAR(50) := ?;\r\n" +
			"BEGIN\r\n" + 
			"UPDATE Meters\r\n" + 
			"SET custom1 = loc\r\n" +
			"WHERE meterNo = lan_address;\r\n" + 
			"END;";

	private static final String SQL_UPDATEENVIRONMENTROUTERS =  "DECLARE\r\n" + 
			"@lan_address VARCHAR(50) = ?,\r\n" + 
			"@loc VARCHAR(50) = ?\r\n" + 
			"\r\n" + 
			"UPDATE Meters\r\n" + 
			"SET custom1 = @loc\r\n" +
			"WHERE meterNo = @lan_address";
	
	public static String getMetersDataQuery(boolean sql) {
		if (sql) {
			return SQL_METERS;
		} else {
			return ORACLE_METERS;
		}
	}
	
	public static String getCollectorsQuery() {
		return COLLECTORS_QUERY;
	}

	public static String getRoutersQuery() {
		return ROUTERS_QUERY;
	}
	public static String updateEnvironmentMetersTable(boolean sql) {
		if (sql) {
			return SQL_UPDATEENVIRONMENTMETERS;
		} else {
			return ORACLE_UPDATEENVIRONMENTMETERS;
		}
	}
	
	public static String environmentIDfromCRC(boolean sql) {
		if (sql) {
			return SQL_ENVIRONIDFROMCRC;
		} else {
			return ORACLE_ENVIRONIDFROMCRC;
		}
	}
	
	public static String clearEnvironmentRelations(boolean sql) {
		if (sql) {
			return SQL_CLEARMETERENVIRONRELATIONS;
		} else {
			return ORACLE_CLEARMETERENVIRONRELATIONS;
		}
	}

	public static String addNoteEnvironment(boolean sql) {
		if (sql) {
			return SQL_ADDNOTEENVIRONMENT;
		} else {
			return ORACLE_ADDNOTEENVIRONMENT;
		}
	}

	public static String getRoutersDataQuery(boolean sql) {
		if (sql) {
			return SQL_ROUTERS;
		} else {
			return ORACLE_ROUTERS;
		}
	}

	public static String updateEnvironmentRoutersTable(boolean sql) {
		if (sql) {
			return SQL_UPDATEENVIRONMENTROUTERS;
		} else {
			return ORACLE_UPDATEENVIRONMENTROUTERS;
		}
	}
}
