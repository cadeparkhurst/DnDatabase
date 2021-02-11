CREATE PROCEDURE getLanguages
	@CharacterID int
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END

	SELECT kl.LanguageName
	FROM Knows_Language kl
	WHERE kl.CharacterID = @CharacterID
END