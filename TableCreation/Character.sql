USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE TABLE Character(
	CharacterID int PRIMARY KEY,
	[Str] smallint,
	Dex smallint,
	[Int] smallint,
	Wis smallint,
	Cha smallint,
	Alignment varchar(25),
	HP smallint,
	MaxHP smallint
)