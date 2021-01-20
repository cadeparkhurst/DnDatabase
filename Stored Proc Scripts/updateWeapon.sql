CREATE PROCEDURE updateWeapon
@ItemID int,
@Damage varchar(8),
@Properties varchar(20),
@WeaponTypeName weaponTypeName
AS
BEGIN
	if @ItemID is null BEGIN
		PRINT 'ERROR: ItemID cannot be null';
		RETURN (1)
	END
	if @WeaponTypeName is null BEGIN
		PRINT 'ERROR: WeaponTypeName cannot be null'
		RETURN (2)
	END
	if NOT EXISTS (SELECT * FROM WeaponType WHERE Name=@WeaponTypeName) BEGIN
		PRINT 'ERROR: WeaponTypeName is not a valid name'
		RETURN (3)
	END
	
	UPDATE Weapon
	SET Damage=@Damage, Properties=@Properties, WeaponTypeName=@WeaponTypeName
	WHERE ItemID = @ItemID 
END