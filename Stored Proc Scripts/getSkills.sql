CREATE PROCEDURE getSkills
	@CharacterID int
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END

	SELECT cp.SkillName
	FROM ChoseProficiency cp
	WHERE cp.CharacterID = @CharacterID
END