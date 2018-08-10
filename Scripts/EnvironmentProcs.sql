USE EnvironmentManagement
GO

CREATE OR ALTER PROC ClearEnvironmentRelations AS
DELETE FROM AltEnvironmentRelations
GO

CREATE OR ALTER PROC EnvironmentConnectionParameters AS
Select isSQL, ip, Components.username, Components.password, port, SID, useSID from Components
inner join Servers on Components.serverId = Servers.server_id
inner join Environments on Environments.id = Servers.environmentId
Where componentTypeId = 4;
GO

CREATE OR ALTER PROC GetEnvironments AS
SELECT id, name, collectorId, crc, notes  FROM Environments
GO

CREATE OR ALTER PROC GetDeviceEnvironmentRelations AS
SELECT deviceId, deviceTypeID, environmentId, latestReadDate FROM DeviceEnvironmentRelations;
GO

CREATE OR ALTER PROC UpdateEnvironment 
	@id int,
	@name varchar(50),
	@collid int,
	@crc int,
	@notes varchar(max)
AS
UPDATE Environments 
SET name = @name,
	collectorId = @collid,
	crc = @crc,
	notes = @notes
WHERE id = @id;
GO

CREATE OR ALTER PROC AddEnvironment
	@name varchar(50)
AS
INSERT INTO Environments (name)
VALUES (@name);

SELECT SCOPE_IDENTITY();
GO

CREATE OR ALTER PROC DeleteEnvironment
	@id int
AS
DELETE FROM Components
WHERE serverId in (
	SELECT server_id FROM Servers 
	WHERE environmentId = @id
);

DELETE FROM Servers
WHERE environmentId = @id;

DELETE FROM Checkpoints
WHERE environmentId = @id;

DELETE FROM Environments
WHERE id = @id;
GO

CREATE OR ALTER PROC ClearEnvironmentDeviceRelations
	@id int
AS
DELETE FROM DeviceEnvironmentRelations
WHERE environmentId = @id;
GO

CREATE OR ALTER PROC GetComponents AS 
SELECT componentId, serverId, componentTypeId, version, username, password FROM Components
GO

CREATE OR ALTER PROC UpdateComponent
	@id int,
	@version varchar(50) = NULL,
	@user varchar(50) = NULL,
	@pass varchar(50) = NULL
AS
UPDATE Components
SET version = @version,
	username = @user,
	password = @pass
WHERE componentId = @id;
GO

CREATE OR ALTER PROC UpdateComponentFromService
	@id int,
	@version varchar(50) = NULL,
	@user varchar(50) = NULL,
	@pass varchar(50) = NULL
AS
UPDATE Components
SET version = @version,
	username = @user,
	password = @pass
WHERE componentId = @id;
GO

CREATE OR ALTER PROC AddComponent
	@serverId int,
	@componentTypeId int
AS
INSERT INTO Components ( serverId, componentTypeId)
VALUES (@serverId, @componentTypeId);

SELECT SCOPE_IDENTITY();
GO

CREATE OR ALTER PROC DeleteComponent 
	@id int
AS
DELETE FROM Components
WHERE componentId = @id;
GO

CREATE OR ALTER PROC GetServers AS
SELECT server_id, environmentId, name, ip, fqdn, type, hasDB, dbType, isSQL, username, pass, port, SID, useSID FROM Servers
GO

CREATE OR ALTER PROC UpdateServer
	@id int,
	@name varchar(50),
	@ip varchar(50),
	@fqdn varchar(50),
	@type varchar(50),
	@hasDB bit,
	@dbType varchar(50),
	@isSQL bit,
	@username varchar(50),
	@pass varchar(50),
	@port int, 
	@sid varchar(50),
	@useSID bit
AS
UPDATE Servers
SET name= @name,
	ip = @ip,
	fqdn = @fqdn,
	type = @type,
	hasDB = @hasDB,
	dbType = @dbType,
	isSQL = @isSQL,
	username = @username,
	pass = @pass,
	port = @port,
	SID = @sid,
	useSID = @useSID
WHERE server_id = @id;
GO

CREATE OR ALTER PROC AddServer 
	@name varchar(50),
	@environmentId int
AS
INSERT INTO Servers (name, environmentId)
VALUES (@name, @environmentId);

SELECT SCOPE_IDENTITY();
GO

CREATE OR ALTER PROC DeleteServer
	@id int
AS
DELETE FROM Components
WHERE serverId = @id;

DELETE FROM Servers
WHERE server_id = @id;
GO

CREATE OR ALTER PROC GetCheckpoints AS
SELECT checkpointId, environmentId, cc_version FROM Checkpoints
GO

CREATE OR ALTER PROC UpdateCheckpoint
	@id int,
	@version varchar(50)
AS
UPDATE Checkpoints
SET cc_version = @version
WHERE checkpointId = @id;
GO

CREATE OR ALTER PROC AddCheckpoint 
	@version varchar(50),
	@environmentId int
AS
INSERT INTO Checkpoints (cc_version, environmentId)
VALUES (@version, @environmentId);

SELECT SCOPE_IDENTITY();
GO

CREATE OR ALTER PROC DeleteCheckpoint
	@id int
AS
DELETE FROM Checkpoints
WHERE checkpointId = @id;
GO