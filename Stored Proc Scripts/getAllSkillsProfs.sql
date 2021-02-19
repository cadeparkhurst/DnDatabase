CREATE PROCEDURE getAllSkillsProfs
	@CharacterID int
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END

	SELECT s.Name, s.relatedStat, cp.CharacterID
	FROM Skill s
	LEFT JOIN ChoseProficiency cp ON cp.SkillName = s.Name AND cp.CharacterID = @CharacterID
END