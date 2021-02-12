CREATE PROCEDURE addProficiency
	@CharacterID int,
	@Skill dbo.skillName
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table',14,1);
		RETURN 1;
	END
	IF(NOT EXISTS (SELECT * FROM Skill WHERE Name = @Skill))
	BEGIN
		RAISERROR('Skill does not exist in the skills table',14,2);
		RETURN 2;
	END
	
	INSERT INTO ChoseProficiency(CharacterID, SkillName) VALUES (@CharacterID, @Skill);
END