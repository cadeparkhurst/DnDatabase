CREATE PROCEDURE getItems
	@CharacterID int
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END

	SELECT i.Name AS Item, iob.Quantity
	FROM IsOwnedBy iob
	JOIN Item i ON iob.ItemID = i.ItemID
	WHERE iob.CharacterID = @CharacterID
END