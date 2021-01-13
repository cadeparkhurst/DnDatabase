CREATE TABLE Has_Levels_In(
	CharacterID int,
	ClassID int,
	NumLevels smallint
	PRIMARY KEY(CharacterID, ClassID)
)

ALTER TABLE Has_Levels_In