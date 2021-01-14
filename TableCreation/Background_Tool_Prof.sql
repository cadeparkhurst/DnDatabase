USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE TABLE Background_Tool_Prof (
	BackgroundName backgroundName,
	ItemID int,
	PRIMARY KEY(BackgroundName, ItemID)
)

ALTER TABLE Background_Tool_Prof
ADD FOREIGN KEY (BackgroundName) REFERENCES Background(Name)

ALTER TABLE Background_Tool_Prof
ADD FOREIGN KEY (ItemID) REFERENCES Item(ItemID)