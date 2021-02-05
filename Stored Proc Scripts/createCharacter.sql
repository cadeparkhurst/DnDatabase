USE DnD_goodriat_oriansaj_parkhuca30
GO

ALTER Procedure AddCharacter
	@str smallint,
	@dex smallint,
	@intel smallint,
	@wis smallint,
	@cha smallint,
	@con smallint,
	@alignment varchar(25),
	@hp smallint,
	@maxhp smallint,
	@classId int,
	@backgroundName dbo.backgroundName,
	@raceName dbo.raceName,
	@name varchar(25)
AS
BEGIN
	IF(@str IS NOT NULL AND (@str < 3 OR @str > 20))
	BEGIN
		RAISERROR('Strength must be between 3 and 20.', 14, 1);
		RETURN 1;
	END
	IF(@dex IS NOT NULL AND (@dex < 3 OR @dex > 20))
	BEGIN
		RAISERROR('Dexterity must be between 3 and 20.', 14, 2);
		RETURN 2;
	END
	IF(@intel IS NOT NULL AND (@intel < 3 OR @intel > 20))
	BEGIN
		RAISERROR('Intelligence must be between 3 and 20.', 14, 3);
		RETURN 3;
	END
	IF(@wis IS NOT NULL AND (@wis < 3 OR @wis > 20))
	BEGIN
		RAISERROR('Wisdom must be between 3 and 20.', 14, 4);
		RETURN 4;
	END
	IF(@cha IS NOT NULL AND (@cha < 3 OR @cha > 20))
	BEGIN
		RAISERROR('Charisma must be between 3 and 20.', 14, 5);
		RETURN 5;
	END
	IF(@con IS NOT NULL AND (@con < 3 OR @con > 20))
	BEGIN
		RAISERROR('Constitution must be between 3 and 20.', 14, 6);
		RETURN 6;
	END
	IF(NOT EXISTS (SELECT * FROM Class WHERE ClassID = @classId))
	BEGIN
		RAISERROR('Class must exist in the Class table.', 14, 7);
		RETURN 7;
	END
	IF(NOT EXISTS (SELECT * FROM Background WHERE [Name] = @backgroundName))
	BEGIN
		RAISERROR('Background must exist in the Background table.', 14, 8);
		RETURN 8;
	END
	IF(NOT EXISTS (SELECT * FROM Race WHERE [Name] = @raceName))
	BEGIN
		RAISERROR('Race must exist in the Race table.', 14, 9);
		RETURN 9;
	END
	IF(@name IS NULL)
	BEGIN
		RAISERROR('Name must not be null', 14, 10)
		RETURN 10;
	END

	DECLARE @newid int;
	SET @newid = (SELECT MAX(CharacterID) FROM [Character]) + 1;

	INSERT INTO Character(CharacterID, [Str], Dex, [Int], Wis, Cha, Alignment, HP, MaxHP, Con, Background, Race, [Name])
	VALUES(@newid, @str, @dex, @intel, @wis, @cha, @alignment, @hp, @maxhp, @con, @backgroundName, @raceName, @name);

--	DECLARE @newid int;
--	SET @newid = SCOPE_IDENTITY();

	INSERT INTO Has_Levels_In(CharacterID, ClassID, NumLevels)
	VALUES(@newid, @classId, 1);

	INSERT INTO IsOwnedBy(ItemID, CharacterID, Quantity)
	SELECT sw.ItemID, @newid, sw.Quantity
	FROM StartsWith sw
	WHERE sw.ClassID = @classId

	INSERT INTO Knows_Language(CharacterID, LanguageName)
	SELECT @newid, klr.LanguageName
	FROM Knows_Language_From_Race klr
	WHERE klr.RaceName = @raceName
END