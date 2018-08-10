package application.data.framework;

public class Queries {
	
	private static final String SQL_METERS = "select meterNo, meterName, meterType, latestValueDate from (\r\n" + 
			"	select Meters.meterNo, MeterModels.name as meterName, MeterTypes.name as meterType, IDReadingLatestValues.latestValueDate, ROW_NUMBER() OVER (PARTITION BY Meters.meterNo ORDER BY IDReadingLatestValues.latestValueDate DESC) rn  from Endpoints \r\n" + 
			"	inner join Meters on Meters.meterId = Endpoints.meterId\r\n" + 
			"	inner join MeterTypes on Meters.meterTypeId = MeterTypes.meterTypeId\r\n" + 
			"	left join IDReadingLatestValues on IDReadingLatestValues.endpointId = EndPoints.endPointId\r\n" + 
			"	left join RFEndpointProperties on RFEndpointProperties.endpointId = Endpoints.endPointId\r\n" + 
			"	left join MeterModels on RFEndpointProperties.meterModelId = MeterModels.meterModelId\r\n" + 
			") as result where rn = 1";
	
	private static final String ORACLE_METERS = "select meterNo, meterName, meterType, latestValueDate from (\r\n" + 
			"	select Meters.meterNo, MeterModels.name as meterName, MeterTypes.name as meterType, IDReadingLatestValues.latestValueDate, ROW_NUMBER() OVER (PARTITION BY Meters.meterNo ORDER BY IDReadingLatestValues.latestValueDate DESC) rn from Endpoints \r\n" + 
			"   inner join Meters on Meters.meterId = Endpoints.meterId\r\n" + 
			"	inner join MeterTypes on Meters.meterTypeId = MeterTypes.meterTypeId\r\n" + 
			"	left join IDReadingLatestValues on IDReadingLatestValues.endpointId = EndPoints.endPointId\r\n" + 
			"	left join RFEndpointProperties on RFEndpointProperties.endpointId = Endpoints.endPointId\r\n" + 
			"	left join MeterModels on RFEndpointProperties.meterModelId = MeterModels.meterModelId\r\n" + 
			") result where rn = 1";
	
	private static final String SQL_ROUTERS = 
			"select meterNo, routerName, latestValueDate from (\r\n" + 
			"	select distinct Meters.meterNo, EndpointModels.name as routerName, IDReadingLatestValues.latestValueDate, ROW_NUMBER() OVER (PARTITION BY Meters.meterNo ORDER BY IDReadingLatestValues.latestValueDate DESC) rn  from Endpoints \r\n" + 
			"	inner join Meters on Meters.meterId = Endpoints.meterId\r\n" + 
			"	inner join MeterTypes on Meters.meterTypeId = MeterTypes.meterTypeId\r\n" + 
			"	left join IDReadingLatestValues on IDReadingLatestValues.endpointId = EndPoints.endPointId\r\n" + 
			"	left join EndPointModels on endpointmodels.endPointModelId = endpoints.endPointModelId\r\n" + 
			"	where MeterTypes.MeterTypeID in (4,5)\r\n" +
			") as result where rn = 1";
	
	private static final String ORACLE_ROUTERS = "select meterNo, routerName, latestValueDate from (\r\n" + 
			"	select distinct Meters.meterNo, EndpointModels.name as routerName, IDReadingLatestValues.latestValueDate, ROW_NUMBER() OVER (PARTITION BY Meters.meterNo ORDER BY IDReadingLatestValues.latestValueDate DESC) rn from Endpoints \r\n" + 
			"   inner join Meters on Meters.meterId = Endpoints.meterId\r\n" + 
			"	inner join MeterTypes on Meters.meterTypeId = MeterTypes.meterTypeId\r\n" + 
			"	left join IDReadingLatestValues on IDReadingLatestValues.endpointId = EndPoints.endPointId\r\n" + 
			"	left join EndPointModels on endpointmodels.endPointModelId = endpoints.endPointModelId\r\n" + 
			"	where MeterTypes.meterTypeID in (4,5)\r\n" + 
			") result where rn = 1";
	
	/*private static final String COLLECTORS_QUERY = "select Collectors.ipAddress, Radios.Radios, GridStreamNtwrks.gridStreamNtwrkQT, CollectorTypes.name\r\n" + 
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
			*/
	
	
	private static final String SQL_CCVERSIONINFO = "SELECT TOP 1 version FROM VersionHistory\r\n" + 
			"ORDER BY versionId DESC";
	
	private static final String ORACLE_CCVERSIONINFO = "SELECT version FROM (\r\n" + 
			"    SELECT * FROM VERSIONHISTORY ORDER BY versionId DESC)\r\n" + 
			"WHERE rownum = 1";

	private static final String SQL_CRC = "SELECT gridStreamNtwrkQT FROM GridStreamNtwrks";

	private static final String ORCALE_CRC = "SELECT gridStreamNtwrkQT FROM GridStreamNtwrks";
	
	public static String getMetersDataQuery(boolean sql) {
		if (sql) {
			return SQL_METERS;
		} else {
			return ORACLE_METERS;
		}
	}
	/*
	public static String getCollectorsQuery() {
		return COLLECTORS_QUERY;
	}*/

	public static String getRoutersDataQuery(boolean sql) {
		if (sql) {
			return SQL_ROUTERS;
		} else {
			return ORACLE_ROUTERS;
		}
	}
	
	public static String ccVersionInfoQuery(boolean sql) {
		if (sql) {
			return SQL_CCVERSIONINFO;
		} else {
			return ORACLE_CCVERSIONINFO;
		}
	}

	public static String ccCRCQuery(boolean sql) {
		if (sql) {
			return SQL_CRC;
		} else {
			return ORCALE_CRC;
		}
	}
}
