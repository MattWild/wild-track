USE EnvironmentManagement;
GO

CREATE OR ALTER PROC GetVersionAliases AS
SELECT versionAliasId, version, alias FROM VersionAliases;
GO

CREATE OR ALTER PROC UpdateVersionAlias
	@id int,
	@version varchar(50),
	@alias varchar(50)
AS
UPDATE VersionAliases
SET version = @version,
	alias = @alias
WHERE versionAliasId = @id;
GO

CREATE OR ALTER PROC AddVersionAlias
	@version varchar(50),
	@alias varchar(50)
AS
INSERT INTO VersionAliases (version, alias)
VALUES (@version, @alias);

SELECT SCOPE_IDENTITY();
GO

CREATE OR ALTER PROC DeleteVersionAlias
	@id int
AS
DELETE FROM VersionAliases
WHERE versionAliasId = @id;
GO