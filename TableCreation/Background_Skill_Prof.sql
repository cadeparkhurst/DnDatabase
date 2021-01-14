USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE TABLE Background_Skill_Prof (
	BackgroundName backgroundName,
	SkillName skillName,
	PRIMARY KEY(BackgroundName, SkillName)
)

ALTER TABLE Background_Skill_Prof
ADD FOREIGN KEY(BackgroundName) REFERENCES Background(Name)

ALTER TABLE Background_Skill_Prof
ADD FOREIGN KEY (SkillName) REFERENCES Skill(Name)