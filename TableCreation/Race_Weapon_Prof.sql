USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE TABLE  Race_Weapon_Prof (
	RaceName raceName,
	WeaponID int,
	PRIMARY KEY(RaceName, WeaponID)
)

ALTER TABLE Race_Weapon_Prof
ADD FOREIGN KEY (RaceName) REFERENCES Race(Name)

ALTER TABLE Race_Weapon_Prof
ADD FOREIGN KEY (WeaponID) REFERENCES Weapon(ItemID)