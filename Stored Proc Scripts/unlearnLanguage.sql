CREATE PROCEDURE unlearnLanguage
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
	IF(NOT EXISTS (SELECT * FROM Knows_Language WHERE CharacterID = @CharacterID AND LanguageName = @Language))
	BEGIN
		RAISERROR('The character does not know that language', 14, 3);
		RETURN 3;
	END

	DELETE FROM Knows_Language WHERE CharacterID = @CharacterID AND LanguageName = @Language;
END