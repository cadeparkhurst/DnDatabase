CREATE TABLE ChoseProficiency (
CharacterID INT,
SkillName NVARCHAR(20),
PRIMARY KEY(CharacterID, SkillName)
)

ALTER TABLE ChoseProficiency
ADD FOREIGN KEY(CharacterID) REFERENCES [Character](CharacterID);
ALTER TABLE ChoseProficiency
ADD FOREIGN KEY(SkillName) REFERENCES Skill([Name]);