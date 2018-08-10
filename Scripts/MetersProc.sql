USE EnvironmentManagement
GO

CREATE OR ALTER PROC GetMeters AS
SELECT meterId, Meters.LAN_Address, Meter_Name, Meter_Type, Location, Socket, Comments FROM Meters
GO

CREATE OR ALTER PROC GetMeterEnvironmentRelationships 
	@id int
AS
SELECT environmentId, latestReadDate FROM DeviceEnvironmentRelations
WHERE deviceTypeId = 1
AND deviceId = @id;
GO

CREATE OR ALTER PROC AddMeter
	@lan varchar(50),
	@loc varchar(50),
	@sock varchar(50),
	@notes varchar(max)
AS
INSERT INTO Meters (LAN_Address, Location, Socket, Comments) VALUES (@lan, @loc, @sock, @notes);

SELECT SCOPE_IDENTITY();
GO

CREATE OR ALTER PROC DeleteMeter
	@id int
AS
DELETE FROM Meters WHERE meterId = @id;

DELETE FROM DeviceEnvironmentRelations 
WHERE deviceTypeId = 1
AND deviceId = @id;
GO

CREATE OR ALTER PROC UpdateMeterFromService
	@lan varchar(50),
	@mName varchar(50),
	@mType varchar(50),
	@latestVal smalldatetime,
	@environmentId int
AS
DECLARE @temp int;
IF EXISTS(SELECT LAN_Address FROM Meters WHERE LAN_Address = @lan) BEGIN
	DECLARE @id int = (SELECT meterId FROM Meters WHERE LAN_Address = @lan);

	UPDATE Meters
	SET Meter_Name = @mName,
		Meter_Type = @mType
		WHERE meterId = @id

	INSERT INTO DeviceEnvironmentRelations (deviceId, deviceTypeId, environmentId, latestReadDate)
	VALUES (@id, 1, @environmentId, @latestVal);
END
GO

CREATE OR ALTER PROC UpdateMeter
	@id int,
	@loc varchar(50),
	@sock varchar(50),
	@notes varchar(max)
AS
UPDATE Meters
SET Location = @loc,
	Socket = @sock,
	Comments = @notes
WHERE meterId = @id;
GO