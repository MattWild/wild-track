USE EnvironmentManagement
GO

CREATE OR ALTER PROC GetRouters AS
SELECT routerId, Routers.LAN_Address, Radio_Type, Location, Comments FROM Routers
GO

CREATE OR ALTER PROC GetRouterEnvironmentRelationships 
	@id int
AS
SELECT environmentId, latestReadDate FROM DeviceEnvironmentRelations
WHERE deviceTypeId = 3
AND deviceId = @id;
GO

CREATE OR ALTER PROC AddRouter
	@lan varchar(50),
	@loc varchar(50),
	@notes varchar(50)
AS
INSERT INTO Routers (LAN_Address,Location,Comments) VALUES (@lan, @loc,@notes);

SELECT SCOPE_IDENTITY();
GO

CREATE OR ALTER PROC DeleteRouter
	@id int
AS
DELETE FROM Routers WHERE routerId = @id;

DELETE FROM DeviceEnvironmentRelations 
WHERE deviceTypeId = 3
AND deviceId = @id;
GO

CREATE OR ALTER PROC UpdateRouterFromService
	@lan varchar(50),
	@rType varchar(50),
	@latestVal smalldatetime,
	@environmentId int
AS
DECLARE @temp int;
IF EXISTS(SELECT LAN_Address FROM Routers WHERE LAN_Address = @lan) BEGIN
	DECLARE @id int = (SELECT routerId FROM Routers WHERE LAN_Address = @lan);

	UPDATE Routers
	SET Radio_Type = @rType
		WHERE routerID = @id

	INSERT INTO DeviceEnvironmentRelations (deviceId, deviceTypeId, environmentId, latestReadDate)
	VALUES (@id, 3, @environmentId, @latestVal);
END
GO


CREATE OR ALTER PROC UpdateRouter 
	@id	int,
	@loc varchar(50),
	@notes varchar(max)
AS
UPDATE Routers
SET Location = @loc,
	Comments = @notes
WHERE routerId = @id;
GO