USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE TABLE GivesRaceFeature(
	FeatureID int,
	RaceName raceName,
	PRIMARY KEY(FeatureID,  RaceName)
)

ALTER TABLE GivesRaceFeature
ADD FOREIGN KEY (FeatureID) REFERENCES Trait(TraitID)

ALTER TABLE GivesRaceFeature
ADD FOREIGN KEY (Racename) REFERENCES Race(Name)