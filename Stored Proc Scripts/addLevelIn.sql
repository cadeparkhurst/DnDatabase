ALTER PROCEDURE addLevelIn
	@CharacterID int,
	@ClassName	varchar(30),
	@DiceRolled smallint
AS BEGIN
	IF NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID) BEGIN
		RAISERROR('THAT CHARACTER DOES NOT EXIST',14,1)
		RETURN 1
	END

	IF NOT EXISTS (SELECT * FROM Class c WHERE c.Name = @ClassName) BEGIN
		RAISERROR('That class does not exist',14,2)
		RETURN 2
	END

	DECLARE @ClassID int
	SET @ClassID = (SELECT ClassID FROM Class c WHERE c.Name = @ClassName)

	UPDATE Character
	SET MaxHP = MaxHP+@DiceRolled, HP=HP+@DiceRolled
	WHERE CharacterID=@CharacterID

	IF NOT EXISTS (SELECT * FROM Has_Levels_In WHERE CharacterID=@CharacterID AND ClassID=@ClassID) BEGIN
		INSERT INTO Has_Levels_In(CharacterID, ClassID,  NumLevels)
		VALUES (@CharacterID, @ClassID, 1)
	END ELSE BEGIN
		DECLARE @currentLevel smallint
		SET @currentLevel = (SELECT NumLevels FROM Has_Levels_In WHERE CharacterID=@CharacterID AND ClassID=@ClassID)

		UPDATE Has_Levels_In
		SET NumLevels=@currentLevel+1
		WHERE CharacterID=@CharacterID AND ClassID=@ClassID

	END

END

GRANT EXECUTE ON addLevelIn
	TO [dndadabasefrontend]