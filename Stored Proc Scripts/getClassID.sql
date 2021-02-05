CREATE PROCEDURE getClassID
	@Name varchar(20)
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Class WHERE Name = @Name))
	BEGIN
		RAISERROR('No class with that name exists', 14, 1);
		RETURN 1;
	END

	SELECT ClassID FROM Class WHERE Name = @Name;
END