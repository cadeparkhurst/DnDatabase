CREATE TABLE KnowsSpell (
CharacterID INT,
SpellID INT,
PRIMARY KEY(CharacterID, SpellID)
)

ALTER TABLE KnowsSpell
ADD FOREIGN KEY(CharacterID) REFERENCES [Character](CharacterID);