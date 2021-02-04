CREATE PROCEDURE getClassLevels
	@CharacterID int,
	@ClassID int
AS
BEGIN
	IF(@CharacterID IS NULL OR NOT EXISTS (SELECT * FROM [Character] WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('Character must not be null and must exist in the Character table', 14, 1);
		RETURN 1;
	END
	IF(@ClassID IS NULL OR NOT EXISTS (SELECT * FROM Class WHERE ClassID = @ClassID))
	BEGIN
		RAISERROR('Class must not be null and must exist in the Class table', 14, 2);
		RETURN 2;
	END
	IF(NOT EXISTS (SELECT * FROM Has_Levels_In WHERE CharacterID = @CharacterID AND ClassID = @ClassID))
	BEGIN
		RAISERROR('The character must already have levels in the given class', 14, 3);
		RETURN 3;
	END

	SELECT NumLevels
	FROM Has_Levels_In
	WHERE CharacterID = @CharacterID AND ClassID = @ClassID
END