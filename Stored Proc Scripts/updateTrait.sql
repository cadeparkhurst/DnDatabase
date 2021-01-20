CREATE PROCEDURE updateTrait
@TraitID int,
@Description nvarchar(200),
@Type nvarchar(30)
AS
BEGIN
	if @TraitID is null BEGIN
		PRINT 'ERROR: TraitID cannot be null';
		RETURN (1)
	END
	
	UPDATE dbo.Trait
	SET Description=@Description, Type=@Type
	WHERE TraitID = @TraitID 
END