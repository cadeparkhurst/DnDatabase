ALTER PROCEDURE getTraits
	@CharacterID int
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END

	SELECT t.Name AS Trait
	FROM Character c
	JOIN Background b ON c.Background = b.Name
	JOIN Trait t ON t.TraitID = b.Feature
	WHERE c.CharacterID = @CharacterID
	UNION
	SELECT t.Name AS Trait
	FROM Character c
	JOIN Has_Levels_In hli ON hli.CharacterID = c.CharacterID
	JOIN GivesClassFeature gcf ON gcf.ClassID = hli.ClassID
	JOIN Trait t ON t.TraitID = gcf.ClassFeatureID
	WHERE c.CharacterID = @CharacterID
	UNION
	SELECT t.Name AS Trait
	FROM Character c
	JOIN GivesRaceFeature grf ON grf.RaceName = c.Race
	JOIN Trait t ON t.TraitID = grf.FeatureID
	WHERE c.CharacterID = @CharacterID
END