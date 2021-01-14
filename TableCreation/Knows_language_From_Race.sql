USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE TABLE Knows_Language_From_Race(
	RaceName varchar(20),
	LanguageName varchar(20),
	PRIMARY KEY(RaceName, LanguageName)
)

ALTER TABLE Knows_Language_From_Race
ALTER COLUMN RaceName raceName

ALTER TABLE Knows_Language_From_Race
ALTER COLUMN LanguageName languageName

ALTER TABLE Knows_Language_From_Race
ADD FOREIGN KEY (RaceName) REFERENCES Race(Name)