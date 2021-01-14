CREATE TABLE OffersSkillProficiency (
ClassID INT,
SkillName dbo.skillName,
PRIMARY KEY(ClassID, SkillName)
)

ALTER TABLE OffersSkillProficiency
ADD FOREIGN KEY(SkillName) REFERENCES Skill([Name]);
ALTER TABLE OffersSkillProficiency
ADD FOREIGN KEY(ClassID) REFERENCES Class(ClassID)