CREATE PROCEDURE updateRace
@Name raceName,
@AbilityScore varchar(20),
@NumIncreasedBy smallint,
@SubRace raceName
AS
BEGIN
	if @Name is null BEGIN
		PRINT 'ERROR: Name cannot be null';
		RETURN (1)
	END
	if @SubRace is null BEGIN
		PRINT 'ERROR: SubRace cannot be null';
		RETURN (2)
	END
	if NOT EXISTS (SELECT * FROM Race WHERE Name=@SubRace) BEGIN
		PRINT 'ERROR: SubRace is not a valid race'
		RETURN (3)
	END

	
	UPDATE dbo.Race
	SET AbilityScore=@AbilityScore, NumIncreasedBy=@NumIncreasedBy, SubRace=@SubRace
	WHERE Name = @Name
END