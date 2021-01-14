CREATE TABLE HasWeaponTypeProf (
ClassID INT,
WeaponTypeName VARCHAR(30),
PRIMARY KEY(ClassID, WeaponTypeName)
)

ALTER TABLE HasWeaponTypeProf
ADD FOREIGN KEY(ClassID) REFERENCES Class(ClassID)
ALTER TABLE HasWeaponTypeProf
ADD FOREIGN KEY(WeaponTypeName) REFERENCES WeaponType([Name])