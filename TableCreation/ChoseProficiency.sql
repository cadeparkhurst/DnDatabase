CREATE TABLE ChoseProficiency (
CharacterID INT,
SkillName dbo.skillName,
PRIMARY KEY(CharacterID, SkillName)
)

ALTER TABLE ChoseProficiency
ADD FOREIGN KEY(CharacterID) REFERENCES [Character](CharacterID);
ALTER TABLE ChoseProficiency
ADD FOREIGN KEY(SkillName) REFERENCES Skill([Name]);