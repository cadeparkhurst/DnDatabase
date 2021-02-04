CREATE TABLE Armor (
ItemID int PRIMARY KEY,
BaseAC int,
AddDex boolean,
Name varchar(20),
StrengthMin int,
DisAdvantageonStealth boolean,
ArmorWeightName ArmorWeightName
);

ALTER TABLE Armor
DROP COLUMN Name