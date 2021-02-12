CREATE PROCEDURE getOfferedProfs
	@classID int
AS
BEGIN
	IF NOT EXISTS (SELECT * FROM Class WHERE ClassID = @classID) BEGIN
		RAISERROR('That class does not exists',14,1)
		RETURN 1
	END

	SELECT SkillName
	FROM OffersSkillProficiency
	WHERE ClassID = @classID
END

GRANT EXECUTE ON getOfferedProfs
	TO [dndadabasefrontend]