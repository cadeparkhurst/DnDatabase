CREATE TABLE Knows_Language_From_Race(
	RaceName varchar(20),
	LanguageName varchar(20),
	PRIMARY KEY(RaceName, LanguageName)
)

ALTER TABLE Knows_Language_From_Race
ALTER COLUMN LanguageName languageName