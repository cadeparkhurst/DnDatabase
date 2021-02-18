CREATE PROCEDURE removeItemByQuant
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

	DECLARE @newQuant int
	SET @newQuant = (SELECT quantity FROM IsOwnedBy WHERE CharacterID=@CharacterID AND ItemID=@ItemID) - @Quantity

	IF @newQuant <= 0 BEGIN
		DELETE IsOwnedBy
		WHERE CharacterID=@CharacterID AND ItemID=@ItemID
	END ELSE BEGIN
		UPDATE IsOwnedBy
		SET Quantity=@newQuant
		WHERE CharacterID=@CharacterID AND ItemID=@ItemID
	END
END