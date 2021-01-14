USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE TABLE Race_Tool_Prof(
	RaceName raceName,
	ItemID  int,
	PRIMARY KEY(RaceName,  ItemID)
)

ALTER TABLE Race_Tool_Prof
ADD FOREIGN KEY (RaceName) REFERENCES Race(Name)

ALTER TABLE Race_Tool_Prof
ADD FOREIGN KEY (ItemID) REFERENCES Item(ItemID)