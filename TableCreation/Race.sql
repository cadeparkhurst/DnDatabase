USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE TABLE Race (
	Name dbo.raceName PRIMARY KEY,
	AbilityScore varchar(20),
	NumIncreasedBy smallint,
	SubRace dbo.raceName,
)

ALTER TABLE Race
ADD FOREIGN KEY (SubRace) REFERENCES Race(Name)