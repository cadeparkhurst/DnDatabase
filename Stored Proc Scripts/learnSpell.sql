CREATE PROCEDURE learnSpell
	@CharacterID int,
	@SpellID int
AS
BEGIN
	IF(@CharacterID IS NULL OR NOT EXISTS (SELECT * FROM [Character] WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('Character must not be null and must exist in the Character table', 14, 1);
		RETURN 1;
	END
	IF(@SpellID IS NULL OR NOT EXISTS (SELECT * FROM Spell WHERE SpellID = @SpellID))
	BEGIN
		RAISERROR('Spell must not be null and must exist in the Spell table', 14, 2);
		RETURN 2;
	END
	IF(EXISTS (SELECT * FROM KnowsSpell WHERE CharacterID = @CharacterID AND SpellID = @SpellID))
	BEGIN
		RAISERROR('The character already knows that spell', 14, 3);
		RETURN 3;
	END
	IF(NOT EXISTS (SELECT * FROM CanLearnSpell WHERE SpellID = @SpellID AND ClassID IN (SELECT ClassID FROM Has_Levels_In WHERE CharacterID = @CharacterID)))
	BEGIN
		RAISERROR('The character cannot learn that spell', 14, 4);
		RETURN 4;
	END

	INSERT INTO KnowsSpell(CharacterID, SpellID)
	VALUES(@CharacterID, @SpellID)
END