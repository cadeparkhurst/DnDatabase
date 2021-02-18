CREATE PROCEDURE addItem
	@CharacterID int,
	@ItemName varchar(50),
	@Quantity smallint
AS BEGIN
	IF NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID) BEGIN
		RAISERROR('Character Does not Exist',14,1)
		RETURN 1
	END

	IF NOT EXISTS (SELECT * FROM Item WHERE Name = @ItemName) BEGIN
		RAISERROR('No item with that name exists',14,2)
		RETURN 2
	END

	DECLARE @ItemID int
	SET @ItemID = (SELECT ItemID FROM Item WHERE Name = @ItemName)

	IF EXISTS (SELECT * FROM IsOwnedBy WHERE CharacterID=@CharacterID AND ItemID=@ItemID) BEGIN
		UPDATE IsOwnedBy
		SET Quantity = @Quantity + (SELECT Quantity FROM IsOwnedBy WHERE CharacterID=@CharacterID AND ItemID=@ItemID)
		WHERE CharacterID=@CharacterID AND ItemID=@ItemID 
	END ELSE BEGIN
		INSERT INTO IsOwnedBy(CharacterID, ItemID, Quantity)
		VALUES(@CharacterID,@ItemID,@Quantity)
	END
END

GRANT EXECUTE ON addItem
	TO [dndadabasefrontend]