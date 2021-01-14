CREATE TABLE OffersSkillProficiency (
ClassID INT,
SkillName NVARCHAR(20),
PRIMARY KEY(ClassID, SkillName)
)

ALTER TABLE OffersSkillProficiency
ADD FOREIGN KEY(SkillName) REFERENCES Skill([Name]);