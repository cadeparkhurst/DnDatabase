CREATE PROCEDURE getNumLangs
	@CharacterID int
AS
BEGIN
	IF NOT EXISTS (SELECT * FROM Character WHERE  CharacterID=@CharacterID) BEGIN
		RAISERROR('That character does not exist',14,1)
		RETURN 1 
	END

	SELECT b.NumLanguagesGained 
	FROM Character c 
	JOIN Background b ON b.Name = c.Background
	WHERE c.CharacterID = @CharacterID
END