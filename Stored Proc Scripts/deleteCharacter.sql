CREATE PROCEDURE deleteCharacter
	@CharacterID int
AS
BEGIN
	BEGIN TRANSACTION
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END
	DELETE FROM ChoseProficiency WHERE CharacterID = @CharacterID
	IF(@@ERROR <> 0)
	BEGIN
		RAISERROR('Could not delete proficiencies',14,2);
		ROLLBACK TRANSACTION;
	END
	DELETE FROM Has_Levels_In WHERE CharacterID = @CharacterID
	IF(@@ERROR <> 0)
	BEGIN
		RAISERROR('Could not delete class levels',14,3);
		ROLLBACK TRANSACTION;
	END
	DELETE FROM IsOwnedBy WHERE CharacterID = @CharacterID
	IF(@@ERROR <> 0)
	BEGIN
		RAISERROR('Could not delete items',14,4);
		ROLLBACK TRANSACTION;
	END
	DELETE FROM Knows_Language WHERE CharacterID = @CharacterID
	IF(@@ERROR <> 0)
	BEGIN
		RAISERROR('Could not delete languages',14,5);
		ROLLBACK TRANSACTION;
	END
	DELETE FROM KnowsSpell WHERE CharacterID = @CharacterID
	IF(@@ERROR <> 0)
	BEGIN
		RAISERROR('Could not delete spells',14,6);
		ROLLBACK TRANSACTION;
	END
	DELETE FROM Character WHERE CharacterID = @CharacterID
	IF(@@ERROR <> 0)
	BEGIN
		RAISERROR('Could not delete character',14,7);
		ROLLBACK TRANSACTION;
	END
	COMMIT TRANSACTION;
END