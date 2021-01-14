USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE TABLE Knows_Language(
	CharacterID int,
	LanguageName languageName,
	PRIMARY KEY(CharacterID, LanguageName)
)

ALTER TABLE Knows_Language
ADD FOREIGN KEY (LanguageName) REFERENCES Language(Name)

ALTER TABLE Knows_Language
ADD FOREIGN KEY (CharacterID) REFERENCES Character(CharacterID)