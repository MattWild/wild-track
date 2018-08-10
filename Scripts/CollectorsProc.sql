USE EnvironmentManagement
GO

CREATE OR ALTER PROC GetCollectors AS
SELECT collectorId, Collector_IP, Radios, Network_ID, Collector_App, Collector_Type, Location, Comments, Collectors.[user], Collectors.pass FROM Collectors
GO

CREATE OR ALTER PROC AddCollector
	@ip varchar(50),
	@radios varchar(50),
	@netID int,
	@app varchar(50),
	@type varchar(50),
	@location varchar(50),
	@note varchar(50),
	@user varchar(50),
	@pass varchar(50)
AS
INSERT INTO Collectors (Collector_IP, Radios, Network_ID, Collector_App, Collector_Type, Location, Comments, [user], pass) VALUES (@ip, @radios, @netID, @app, @type, @location, @note, @user, @pass);

SELECT SCOPE_IDENTITY();
GO

CREATE OR ALTER PROC DeleteCollector
	@id int
AS
DELETE FROM Collectors WHERE collectorId = @id;
GO

CREATE OR ALTER PROC UpdateCollector
	@id int,
	@ip varchar(50),
	@radios varchar(50),
	@netID int,
	@app varchar(50),
	@type varchar(50),
	@location varchar(50),
	@note varchar(50),
	@user varchar(50),
	@pass varchar(50)
AS
UPDATE Collectors
SET Collector_IP = @ip,
	Radios = @radios,
	Network_ID = @netID,
	Collector_App = @app,
	Collector_Type = @type,
	Location = @location,
	Comments = @note,
	[user] = @user,
	pass = @pass
WHERE collectorId = @id
GO