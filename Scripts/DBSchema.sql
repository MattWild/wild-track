USE [EnvironmentManagement]
GO
/****** Object:  User [EscalationsTeam]    Script Date: 8/10/2018 9:41:02 AM ******/
CREATE USER [EscalationsTeam] FOR LOGIN [EscalationsTeam] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_securityadmin] ADD MEMBER [EscalationsTeam]
GO
ALTER ROLE [db_backupoperator] ADD MEMBER [EscalationsTeam]
GO
ALTER ROLE [db_datareader] ADD MEMBER [EscalationsTeam]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [EscalationsTeam]
GO
/****** Object:  Table [dbo].[Checkpoints]    Script Date: 8/10/2018 9:41:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Checkpoints](
	[checkpointId] [int] IDENTITY(1,1) NOT NULL,
	[environmentId] [int] NOT NULL,
	[cc_version] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Checkpoints] PRIMARY KEY CLUSTERED 
(
	[checkpointId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Collectors]    Script Date: 8/10/2018 9:41:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Collectors](
	[collectorId] [int] IDENTITY(1,1) NOT NULL,
	[Collector_IP] [varchar](50) NOT NULL,
	[Radios] [varchar](50) NULL,
	[Network_ID] [int] NULL,
	[Collector_App] [varchar](50) NULL,
	[Collector_Type] [varchar](50) NULL,
	[Location] [varchar](50) NULL,
	[Comments] [varchar](50) NULL,
	[user] [varchar](50) NULL,
	[pass] [varchar](50) NULL,
 CONSTRAINT [PK_Collectors] PRIMARY KEY CLUSTERED 
(
	[collectorId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Components]    Script Date: 8/10/2018 9:41:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Components](
	[componentId] [int] IDENTITY(1,1) NOT NULL,
	[componentTypeId] [int] NOT NULL,
	[serverId] [int] NOT NULL,
	[version] [varchar](50) NULL,
	[username] [varchar](50) NULL,
	[password] [varchar](50) NULL,
 CONSTRAINT [PK_Components] PRIMARY KEY CLUSTERED 
(
	[componentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ComponentTypes]    Script Date: 8/10/2018 9:41:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ComponentTypes](
	[componentTypeId] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
 CONSTRAINT [PK_ComponentTypes] PRIMARY KEY CLUSTERED 
(
	[componentTypeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DeviceEnvironmentRelations]    Script Date: 8/10/2018 9:41:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DeviceEnvironmentRelations](
	[deviceId] [int] NOT NULL,
	[deviceTypeId] [int] NOT NULL,
	[environmentId] [int] NOT NULL,
	[latestReadDate] [smalldatetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DeviceTypes]    Script Date: 8/10/2018 9:41:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DeviceTypes](
	[deviceTypeId] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
 CONSTRAINT [PK_DeviceTypes] PRIMARY KEY CLUSTERED 
(
	[deviceTypeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Environments]    Script Date: 8/10/2018 9:41:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Environments](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[collectorId] [int] NULL,
	[crc] [int] NULL,
	[notes] [varchar](max) NULL,
 CONSTRAINT [PK_Environments] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HANDevices]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HANDevices](
	[hanID] [int] IDENTITY(1,1) NOT NULL,
	[Unit_ID] [varchar](50) NOT NULL,
	[Device_Name] [varchar](50) NULL,
	[Installation_Code] [varchar](50) NULL,
	[MAC_Address] [varchar](50) NULL,
	[Location] [varchar](50) NULL,
	[Notes] [varchar](50) NULL,
 CONSTRAINT [PK_HANDevices] PRIMARY KEY CLUSTERED 
(
	[hanID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Meters]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Meters](
	[meterId] [int] IDENTITY(1,1) NOT NULL,
	[LAN_Address] [varchar](max) NOT NULL,
	[Meter_Name] [varchar](max) NULL,
	[Meter_Type] [varchar](max) NULL,
	[Location] [varchar](50) NULL,
	[Socket] [varchar](max) NULL,
	[Comments] [varchar](max) NULL,
 CONSTRAINT [PK_Meters] PRIMARY KEY CLUSTERED 
(
	[meterId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Routers]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Routers](
	[routerID] [int] IDENTITY(1,1) NOT NULL,
	[LAN_Address] [varchar](50) NOT NULL,
	[Radio_Type] [varchar](50) NULL,
	[Location] [varchar](50) NULL,
	[Comments] [varchar](max) NULL,
 CONSTRAINT [PK_Routers_1] PRIMARY KEY CLUSTERED 
(
	[routerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Servers]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Servers](
	[server_id] [int] IDENTITY(1,1) NOT NULL,
	[environmentId] [int] NOT NULL,
	[name] [varchar](50) NULL,
	[ip] [varchar](50) NULL,
	[fqdn] [varchar](50) NULL,
	[type] [varchar](50) NULL,
	[hasDB] [bit] NULL,
	[dbType] [varchar](50) NULL,
	[isSQL] [bit] NULL,
	[username] [varchar](50) NULL,
	[pass] [varchar](50) NULL,
	[port] [int] NULL,
	[SID] [varchar](50) NULL,
	[useSID] [bit] NULL,
 CONSTRAINT [PK_Servers] PRIMARY KEY CLUSTERED 
(
	[server_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Sockets]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sockets](
	[socketId] [int] IDENTITY(1,1) NOT NULL,
	[ID_Field] [int] NOT NULL,
	[Socket_Form] [varchar](50) NULL,
	[Load_No_Load] [varchar](50) NULL,
	[Location] [varchar](50) NULL,
 CONSTRAINT [PK_Sockets] PRIMARY KEY CLUSTERED 
(
	[socketId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[VersionAliases]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[VersionAliases](
	[versionAliasId] [int] IDENTITY(1,1) NOT NULL,
	[version] [varchar](50) NULL,
	[alias] [varchar](50) NULL,
 CONSTRAINT [PK_VersionAliases] PRIMARY KEY CLUSTERED 
(
	[versionAliasId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Checkpoints]  WITH CHECK ADD  CONSTRAINT [FK_Checkpoints_Environments] FOREIGN KEY([environmentId])
REFERENCES [dbo].[Environments] ([id])
GO
ALTER TABLE [dbo].[Checkpoints] CHECK CONSTRAINT [FK_Checkpoints_Environments]
GO
ALTER TABLE [dbo].[Components]  WITH CHECK ADD  CONSTRAINT [FK_Components_ComponentTypes] FOREIGN KEY([componentTypeId])
REFERENCES [dbo].[ComponentTypes] ([componentTypeId])
GO
ALTER TABLE [dbo].[Components] CHECK CONSTRAINT [FK_Components_ComponentTypes]
GO
ALTER TABLE [dbo].[Components]  WITH CHECK ADD  CONSTRAINT [FK_Components_Servers] FOREIGN KEY([serverId])
REFERENCES [dbo].[Servers] ([server_id])
GO
ALTER TABLE [dbo].[Components] CHECK CONSTRAINT [FK_Components_Servers]
GO
ALTER TABLE [dbo].[DeviceEnvironmentRelations]  WITH CHECK ADD  CONSTRAINT [FK_DeviceEnvironmentRelations_DeviceTypes] FOREIGN KEY([deviceTypeId])
REFERENCES [dbo].[DeviceTypes] ([deviceTypeId])
GO
ALTER TABLE [dbo].[DeviceEnvironmentRelations] CHECK CONSTRAINT [FK_DeviceEnvironmentRelations_DeviceTypes]
GO
ALTER TABLE [dbo].[DeviceEnvironmentRelations]  WITH CHECK ADD  CONSTRAINT [FK_DeviceEnvironmentRelations_Environments] FOREIGN KEY([environmentId])
REFERENCES [dbo].[Environments] ([id])
GO
ALTER TABLE [dbo].[DeviceEnvironmentRelations] CHECK CONSTRAINT [FK_DeviceEnvironmentRelations_Environments]
GO
ALTER TABLE [dbo].[Servers]  WITH CHECK ADD  CONSTRAINT [FK_Servers_Servers] FOREIGN KEY([server_id])
REFERENCES [dbo].[Servers] ([server_id])
GO
ALTER TABLE [dbo].[Servers] CHECK CONSTRAINT [FK_Servers_Servers]
GO
/****** Object:  StoredProcedure [dbo].[AddCheckpoint]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[AddCheckpoint] 
	@version varchar(50),
	@environmentId int
AS
INSERT INTO Checkpoints (cc_version, environmentId)
VALUES (@version, @environmentId);

SELECT SCOPE_IDENTITY();
GO
/****** Object:  StoredProcedure [dbo].[AddCollector]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[AddCollector]
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
/****** Object:  StoredProcedure [dbo].[AddComponent]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[AddComponent]
	@serverId int,
	@componentTypeId int
AS
INSERT INTO Components ( serverId, componentTypeId)
VALUES (@serverId, @componentTypeId);

SELECT SCOPE_IDENTITY();
GO
/****** Object:  StoredProcedure [dbo].[AddEnvironment]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[AddEnvironment]
	@name varchar(50)
AS
INSERT INTO Environments (name)
VALUES (@name);

SELECT SCOPE_IDENTITY();
GO
/****** Object:  StoredProcedure [dbo].[AddHANDevice]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[AddHANDevice]
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
/****** Object:  StoredProcedure [dbo].[AddMeter]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[AddMeter]
	@lan varchar(50),
	@loc varchar(50),
	@sock varchar(50),
	@notes varchar(max)
AS
INSERT INTO Meters (LAN_Address, Location, Socket, Comments) VALUES (@lan, @loc, @sock, @notes);

SELECT SCOPE_IDENTITY();
GO
/****** Object:  StoredProcedure [dbo].[AddRouter]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[AddRouter]
	@lan varchar(50),
	@loc varchar(50),
	@notes varchar(50)
AS
INSERT INTO Routers (LAN_Address,Location,Comments) VALUES (@lan, @loc,@notes);

SELECT SCOPE_IDENTITY();
GO
/****** Object:  StoredProcedure [dbo].[AddServer]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[AddServer] 
	@name varchar(50),
	@environmentId int
AS
INSERT INTO Servers (name, environmentId)
VALUES (@name, @environmentId);

SELECT SCOPE_IDENTITY();
GO
/****** Object:  StoredProcedure [dbo].[AddSocket]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[AddSocket]
	@idField varchar(50),
	@form varchar(50),
	@nload varchar(50),
	@location varchar(50)
AS
INSERT INTO Sockets (ID_Field, Socket_Form, Load_No_Load, Location) VALUES (@idField, @form, @nload, @location);

SELECT SCOPE_IDENTITY();
GO
/****** Object:  StoredProcedure [dbo].[AddVersionAlias]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[AddVersionAlias]
	@version varchar(50),
	@alias varchar(50)
AS
INSERT INTO VersionAliases (version, alias)
VALUES (@version, @alias);

SELECT SCOPE_IDENTITY();
GO
/****** Object:  StoredProcedure [dbo].[ClearEnvironmentDeviceRelations]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[ClearEnvironmentDeviceRelations]
	@id int
AS
DELETE FROM DeviceEnvironmentRelations
WHERE environmentId = @id;
GO
/****** Object:  StoredProcedure [dbo].[ClearEnvironmentRelations]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[ClearEnvironmentRelations] AS
DELETE FROM AltEnvironmentRelations
GO
/****** Object:  StoredProcedure [dbo].[DeleteCheckpoint]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[DeleteCheckpoint]
	@id int
AS
DELETE FROM Checkpoints
WHERE checkpointId = @id;
GO
/****** Object:  StoredProcedure [dbo].[DeleteCollector]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[DeleteCollector]
	@id int
AS
DELETE FROM Collectors WHERE collectorId = @id;
GO
/****** Object:  StoredProcedure [dbo].[DeleteComponent]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[DeleteComponent] 
	@id int
AS
DELETE FROM Components
WHERE componentId = @id;
GO
/****** Object:  StoredProcedure [dbo].[DeleteEnvironment]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[DeleteEnvironment]
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
/****** Object:  StoredProcedure [dbo].[DeleteHANDevice]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[DeleteHANDevice]
	@id int
AS
DELETE FROM HANDevices WHERE hanID = @id;
GO
/****** Object:  StoredProcedure [dbo].[DeleteMeter]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[DeleteMeter]
	@id int
AS
DELETE FROM Meters WHERE meterId = @id;

DELETE FROM DeviceEnvironmentRelations 
WHERE deviceTypeId = 1
AND deviceId = @id;
GO
/****** Object:  StoredProcedure [dbo].[DeleteRouter]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[DeleteRouter]
	@id int
AS
DELETE FROM Routers WHERE routerId = @id;

DELETE FROM DeviceEnvironmentRelations 
WHERE deviceTypeId = 3
AND deviceId = @id;
GO
/****** Object:  StoredProcedure [dbo].[DeleteServer]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[DeleteServer]
	@id int
AS
DELETE FROM Components
WHERE serverId = @id;

DELETE FROM Servers
WHERE server_id = @id;
GO
/****** Object:  StoredProcedure [dbo].[DeleteSocket]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[DeleteSocket]
	@id varchar(50)
AS
DELETE FROM Sockets WHERE socketId = @id;
GO
/****** Object:  StoredProcedure [dbo].[DeleteVersionAlias]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[DeleteVersionAlias]
	@id int
AS
DELETE FROM VersionAliases
WHERE versionAliasId = @id;
GO
/****** Object:  StoredProcedure [dbo].[EnvironmentConnectionParameters]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[EnvironmentConnectionParameters] AS
Select isSQL, ip, Components.username, Components.password, port, SID, useSID from Components
inner join Servers on Components.serverId = Servers.server_id
inner join Environments on Environments.id = Servers.environmentId
Where componentTypeId = 4;
GO
/****** Object:  StoredProcedure [dbo].[GetCheckpoints]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[GetCheckpoints] AS
SELECT checkpointId, environmentId, cc_version FROM Checkpoints
GO
/****** Object:  StoredProcedure [dbo].[GetCollectors]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[GetCollectors] AS
SELECT collectorId, Collector_IP, Radios, Network_ID, Collector_App, Collector_Type, Location, Comments, Collectors.[user], Collectors.pass FROM Collectors
GO
/****** Object:  StoredProcedure [dbo].[GetComponents]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[GetComponents] AS 
SELECT componentId, serverId, componentTypeId, version, username, password FROM Components
GO
/****** Object:  StoredProcedure [dbo].[GetDeviceEnvironmentRelations]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[GetDeviceEnvironmentRelations] AS
SELECT deviceId, deviceTypeID, environmentId, latestReadDate FROM DeviceEnvironmentRelations;
GO
/****** Object:  StoredProcedure [dbo].[GetEnvironments]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[GetEnvironments] AS
SELECT id, name, collectorId, crc, notes  FROM Environments
GO
/****** Object:  StoredProcedure [dbo].[GetHANDevices]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[GetHANDevices] AS
SELECT hanId, Unit_ID, Device_Name, Installation_Code, MAC_Address, Location, Notes FROM HANDevices
GO
/****** Object:  StoredProcedure [dbo].[GetMeterEnvironmentRelationships]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[GetMeterEnvironmentRelationships] 
	@id int
AS
SELECT environmentId, latestReadDate FROM DeviceEnvironmentRelations
WHERE deviceTypeId = 1
AND deviceId = @id;
GO
/****** Object:  StoredProcedure [dbo].[GetMeters]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[GetMeters] AS
SELECT meterId, Meters.LAN_Address, Meter_Name, Meter_Type, Location, Socket, Comments FROM Meters
GO
/****** Object:  StoredProcedure [dbo].[GetRouterEnvironmentRelationships]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[GetRouterEnvironmentRelationships] 
	@id int
AS
SELECT environmentId, latestReadDate FROM DeviceEnvironmentRelations
WHERE deviceTypeId = 3
AND deviceId = @id;
GO
/****** Object:  StoredProcedure [dbo].[GetRouters]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[GetRouters] AS
SELECT routerId, Routers.LAN_Address, Radio_Type, Location, Comments FROM Routers
GO
/****** Object:  StoredProcedure [dbo].[GetServers]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[GetServers] AS
SELECT server_id, environmentId, name, ip, fqdn, type, hasDB, dbType, isSQL, username, pass, port, SID, useSID FROM Servers
GO
/****** Object:  StoredProcedure [dbo].[GetSockets]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[GetSockets] AS
SELECT socketId, Sockets.ID_Field, Socket_Form, Load_No_Load, Location FROM Sockets
GO
/****** Object:  StoredProcedure [dbo].[GetVersionAliases]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[GetVersionAliases] AS
SELECT versionAliasId, version, alias FROM VersionAliases;
GO
/****** Object:  StoredProcedure [dbo].[UpdateCheckpoint]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[UpdateCheckpoint]
	@id int,
	@version varchar(50)
AS
UPDATE Checkpoints
SET cc_version = @version
WHERE checkpointId = @id;
GO
/****** Object:  StoredProcedure [dbo].[UpdateCollector]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[UpdateCollector]
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
/****** Object:  StoredProcedure [dbo].[UpdateComponent]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[UpdateComponent]
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
/****** Object:  StoredProcedure [dbo].[UpdateComponentFromService]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[UpdateComponentFromService]
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
/****** Object:  StoredProcedure [dbo].[UpdateEnvironment]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[UpdateEnvironment] 
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
/****** Object:  StoredProcedure [dbo].[UpdateHANDevice]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[UpdateHANDevice] 
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
/****** Object:  StoredProcedure [dbo].[UpdateMeter]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[UpdateMeter]
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
/****** Object:  StoredProcedure [dbo].[UpdateMeterFromService]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[UpdateMeterFromService]
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
/****** Object:  StoredProcedure [dbo].[UpdateRouter]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE   PROC [dbo].[UpdateRouter] 
	@id	int,
	@loc varchar(50),
	@notes varchar(max)
AS
UPDATE Routers
SET Location = @loc,
	Comments = @notes
WHERE routerId = @id;
GO
/****** Object:  StoredProcedure [dbo].[UpdateRouterFromService]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[UpdateRouterFromService]
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
/****** Object:  StoredProcedure [dbo].[UpdateServer]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[UpdateServer]
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
/****** Object:  StoredProcedure [dbo].[UpdateSocket]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[UpdateSocket]
	@id int,
	@idField varchar(50),
	@form varchar(50),
	@nload varchar(50),
	@location varchar(50)
AS
UPDATE Sockets
SET ID_Field = @idField,
	Socket_Form = @form,
	Load_No_Load = @nload,
	Location = @location
WHERE ID_Field = @idField
GO
/****** Object:  StoredProcedure [dbo].[UpdateVersionAlias]    Script Date: 8/10/2018 9:41:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROC [dbo].[UpdateVersionAlias]
	@id int,
	@version varchar(50),
	@alias varchar(50)
AS
UPDATE VersionAliases
SET version = @version,
	alias = @alias
WHERE versionAliasId = @id;
GO
