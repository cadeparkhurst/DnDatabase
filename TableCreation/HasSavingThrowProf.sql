CREATE TABLE HasSavingThrowProf (
ClassID INT,
SavingThrowName NVARCHAR(20),
PRIMARY KEY(ClassID, SavingThrowName)
)

ALTER TABLE HasSavingThrowProf
ADD FOREIGN KEY(SavingThrowName) REFERENCES SavingThrow([Name]);
ALTER TABLE HasSavingThrowProf
ADD FOREIGN KEY(ClassID) REFERENCES Class(ClassID)