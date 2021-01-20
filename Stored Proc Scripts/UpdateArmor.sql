CREATE PROCEDURE updateArmor
@ItemID int,
@BaseAC int = null,
@AddDex int = 0,
@StrengthMin int = 0,
@DisAdvantageOnStealth int = 0,
@Name varchar(20) = null,
@ArmorWeightName armorWeightname
AS
BEGIN
	if @ItemID is null BEGIN
		PRINT 'ERROR: ItemID cannot be null';
		RETURN (1)
	END
	if @ArmorWeightName is null BEGIN
		PRINT 'ERROR: ArmorWeightName cannot be null'
		RETURN (2)
	END
	if NOT EXISTS (SELECT * FROM ArmorWeight WHERE Name=@ArmorWeightName) BEGIN
		PRINT 'ERROR: ArmorWeightName is not a valid name'
		RETURN (3)
	END
	
	UPDATE Armor
	SET Name=@Name, BaseAC=@BaseAC, AddDex=@AddDex, StrengthMin=@StrengthMin,
		DisAdvantageOnStealth=@DisAdvantageOnStealth, ArmorWeightName=@ArmorWeightName
	WHERE ItemID = @ItemID 
END