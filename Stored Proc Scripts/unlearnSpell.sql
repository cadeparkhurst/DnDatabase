CREATE PROCEDURE unlearnSpell
	@CharacterID int,
	@SpellID int
AS
BEGIN
	IF(@CharacterID IS NULL OR NOT EXISTS (SELECT * FROM [Character] WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('Character must not be null and must exist in the Character table', 14, 1);
		RETURN 1;
	END
	IF(@SpellID IS NULL OR NOT EXISTS (SELECT * FROM Spell WHERE [Name] = @SpellID))
	BEGIN
		RAISERROR('Spell must not be null and must exist in the Spell table', 14, 2);
		RETURN 2;
	END
	IF(NOT EXISTS (SELECT * FROM KnowsSpell WHERE CharacterID = @CharacterID AND SpellID = @SpellID))
	BEGIN
		RAISERROR('The character does not know that spell', 14, 3);
		RETURN 3;
	END

	DELETE FROM KnowsSpell WHERE CharacterID = @CharacterID AND SpellID = @SpellID;
END