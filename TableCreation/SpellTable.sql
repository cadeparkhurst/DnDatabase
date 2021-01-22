CREATE TABLE Spell (
SpellID int PRIMARY KEY,
CastingTime int,
Range int,
V boolean,
S boolean,
M varchar(20),
Duration int,
Concentration boolean
);
 
ALTER TABLE Spell
ADD [Name] varchar(50)