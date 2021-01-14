USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE TABLE Background (
	Name backgroundName,
	Feature int,
	NumLanguagesGained smallint
	PRIMARY KEY(Name)
)

ALTER TABLE Background
ADD FOREIGN KEY (Feature) REFERENCES Trait(TraitID)