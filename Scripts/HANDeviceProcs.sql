USE EnvironmentManagement
GO

CREATE OR ALTER PROC GetHANDevices AS
SELECT hanId, Unit_ID, Device_Name, Installation_Code, MAC_Address, Location, Notes FROM HANDevices
GO

CREATE OR ALTER PROC AddHANDevice
	@unitID varchar(50),
	@name varchar(50),
	@install int,
	@mac varchar(50),
	@location varchar(50),
	@note varchar(50)
AS
INSERT INTO HANDevices (Unit_ID, Device_Name, Installation_Code, MAC_Address, Location, Notes) VALUES (@unitID, @name, @install, @mac, @location, @note);

SELECT SCOPE_IDENTITY();
GO

CREATE OR ALTER PROC DeleteHANDevice
	@id int
AS
DELETE FROM HANDevices WHERE hanID = @id;
GO

CREATE OR ALTER PROC UpdateHANDevice 
	@id int,
	@unitID varchar(50),
	@name varchar(50),
	@install int,
	@mac varchar(50),
	@location varchar(50),
	@note varchar(50)
AS
UPDATE HANDevices
SET Unit_ID = @unitID,
	Device_Name = @name,
	Installation_Code = @install,
	MAC_Address = @mac,
	Location = @location,
	Notes = @note
WHERE hanId = @id
GO