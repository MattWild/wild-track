USE EnvironmentManagement
GO

CREATE OR ALTER PROC GetSockets AS
SELECT socketId, Sockets.ID_Field, Socket_Form, Load_No_Load, Location FROM Sockets
GO

CREATE OR ALTER PROC AddSocket
	@idField varchar(50),
	@form varchar(50),
	@nload varchar(50),
	@location varchar(50)
AS
INSERT INTO Sockets (ID_Field, Socket_Form, Load_No_Load, Location) VALUES (@idField, @form, @nload, @location);

SELECT SCOPE_IDENTITY();
GO

CREATE OR ALTER PROC DeleteSocket
	@id varchar(50)
AS
DELETE FROM Sockets WHERE socketId = @id;
GO

CREATE OR ALTER PROC UpdateSocket
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