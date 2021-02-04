CREATE PROCEDURE acquireItem
	@ItemID int,
	@CharacterID int,
	@Quantity int
AS
BEGIN
	IF(@CharacterID IS NULL OR NOT EXISTS (SELECT * FROM [Character] WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('Character must not be null and must exist in the Character table', 14, 1);
		RETURN 1;
	END
	IF(@ItemID IS NULL OR NOT EXISTS (SELECT * FROM Item WHERE ItemID = @ItemID))
	BEGIN
		RAISERROR('Item must not be null and must exist in the Item table', 14, 2);
		RETURN 2;
	END
	IF(EXISTS (SELECT * FROM IsOwnedBy WHERE CharacterID = @CharacterID AND ItemID = @ItemID))
	BEGIN
		RAISERROR('The character already owns that item', 14, 3);
		RETURN 3;
	END
	IF(@Quantity < 1)
	BEGIN
		RAISERROR('Quantity must be greater than 0', 14, 4);
		RETURN 4;
	END

	INSERT INTO IsOwnedBy(ItemID, CharacterID, Quantity)
	VALUES(@ItemID, @CharacterID, @Quantity)
END