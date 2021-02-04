CREATE TABLE Class (
ClassID int PRIMARY KEY,
HitDice varchar(20),
NumProfieciencies int
);

ALTER TABLE Class
ADD NumSkillProfs smallint

ALTER TABLE Class
DROP COLUMN Range

ALTER TABLE Class
ADD Name varchar(20)

ALTER TABLE Class
ALTER COLUMN HitDice int