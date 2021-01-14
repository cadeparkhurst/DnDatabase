USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE TABLE IsOwnedBy (
ItemID INT,
CharacterID INT,
Quantity INT,
PRIMARY KEY(ItemID, CharacterID)
)