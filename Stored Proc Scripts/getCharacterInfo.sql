USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE PROCEDURE getCharacterInfo
	@CharacterID int
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END

	SELECT c.[Name], b.[Name] AS Background, r.[Name] AS Race, Dex, [Int], Wis, Cha, Con, Alignment, HP, MaxHP
	FROM Character c
	JOIN Background b ON b.Name = c.Background
	JOIN Race r ON r.Name = c.Race
	WHERE c.CharacterID = @CharacterID
END