CREATE PROCEDURE updateItem
@ItemID int,
@Cost int = null,
@Weight int = null,
@Name varchar(20) = null
AS
BEGIN
	if @ItemID is null BEGIN
		PRINT 'ERROR: ItemID cannot be null';
		RETURN (1)
	END
	
	UPDATE dbo.Item
	SET Cost=@Cost, Mass=@Weight, Name=@Name
	WHERE ItemID = @ItemID 
END