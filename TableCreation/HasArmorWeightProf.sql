CREATE TABLE HasArmorWeightProf (
ClassID INT,
ArmorWeightName VARCHAR(30),
PRIMARY KEY(ClassID, ArmorWeightName)
)

ALTER TABLE HasArmorWeightProf
ADD FOREIGN KEY(ClassID) REFERENCES Class(ClassID)
ALTER TABLE HasArmorWeightProf
ADD FOREIGN KEY(ArmorWeightName) REFERENCES ArmorWeight([Name])