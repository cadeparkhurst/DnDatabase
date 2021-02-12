CREATE PROCEDURE getSpellID
	@Name varchar(50)
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Spell WHERE Name = @Name))
	BEGIN
		RAISERROR('No spell with that name exists', 14, 1);
		RETURN 1;
	END

	SELECT SpellID FROM Spell WHERE Name = @Name
END