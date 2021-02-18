ALTER PROCEDURE getAllClassLevels
	@CharacterID int
AS BEGIN
	IF NOT EXISTS(SELECT * FROM Character WHERE CharacterID = @CharacterID)BEGIN
		RAISERROR('No such character exists',14,1)
		RETURN 1
	END

	SELECT [Class Name], [Level]
	FROM [dbo].[characterClassNamesAndLevels]
	WHERE characterID = @CharacterID
END