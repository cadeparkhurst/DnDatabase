ALTER PROCEDURE getCharNames
AS
BEGIN
	SELECT Name, CharacterID
	FROM Character
END