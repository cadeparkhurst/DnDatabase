CREATE PROCEDURE getSpells
	@CharacterID int
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END

	SELECT s.Name, s.Level, s.Duration, s.Range, s.CastingTime, s.Concentration, s.School, s.M, s.V, s.S
	FROM KnowsSpell ks
	JOIN Spell s ON s.SpellID = ks.SpellID
	WHERE ks.CharacterID = @CharacterID
END