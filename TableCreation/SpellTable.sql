CREATE TABLE Spell (
SpellID int PRIMARY KEY,
CastingTime int,
Range int,
V int,
S int,
M varchar(20),
Duration int,
Concentration int
);
 
ALTER TABLE Spell
ADD [Name] varchar(50)

ALTER TABLE Spell
ADD School varchar(20)

ALTER TABLE Spell
ADD Level smallint

ALTER TABLE Spell
ALTER COLUMN CastingTime varchar(20)

ALTER TABLE Spell
ALTER COLUMN Range varchar(20)

ALTER TABLE Spell
ALTER COLUMN Duration varchar(20)

ALTER TABLE Spell
ALTER COLUMN M varchar(150)

DELETE Spell