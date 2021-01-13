CREATE TABLE Race (
	Name dbo.raceName PRIMARY KEY,
	AbilityScore varchar(20),
	NumIncreasedBy smallint,
	SubRace dbo.raceName,
)

ALTER TABLE Race
