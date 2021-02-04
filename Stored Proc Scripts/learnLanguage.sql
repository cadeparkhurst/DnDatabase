CREATE PROCEDURE learnLanguage
	@CharacterID int,
	@Language dbo.languageName
AS
BEGIN
	IF(@CharacterID IS NULL OR NOT EXISTS (SELECT * FROM [Character] WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('Character must not be null and must exist in the Character table', 14, 1);
		RETURN 1;
	END
	IF(@Language IS NULL OR NOT EXISTS (SELECT * FROM [Language] WHERE [Name] = @Language))
	BEGIN
		RAISERROR('lanuage must not be null and must exist in the Language table', 14, 2);
		RETURN 2;
	END
	IF(EXISTS (SELECT * FROM Knows_Language WHERE CharacterID = @CharacterID AND LanguageName = @Language))
	BEGIN
		RAISERROR('The character already knows that language', 14, 3);
		RETURN 3;
	END

	INSERT INTO Knows_Language(CharacterID, LanguageName)
	VALUES(@CharacterID, @Language)
END