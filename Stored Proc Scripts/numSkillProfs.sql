CREATE PROCEDURE getNumSkillProfs
	@classID int
AS
BEGIN
	IF NOT EXISTS (SELECT * FROM Class WHERE ClassID=@classID)BEGIN
		RAISERROR('No class exists with that ID',14,1)
		RETURN 1
	END

	SELECT NumSkillProfs FROM Class WHERE ClassID=@classID
END

GRANT EXECUTE ON  getNumSkillProfs
	TO [dndadabasefrontend]