USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE TABLE Has_Levels_In(
	CharacterID int,
	ClassID int,
	NumLevels smallint
	PRIMARY KEY(CharacterID, ClassID)
)

ALTER TABLE Has_Levels_In
ADD FOREIGN KEY (CharacterID) REFERENCES Character(CharacterID)

ALTER TABLE Has_Levels_In
ADD FOREIGN KEY (ClassID) REFERENCES Class(ClassID)