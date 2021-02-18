ALTER VIEW characterClassNamesAndLevels
AS
	SELECT hli.CharacterID, c.Name AS 'Class Name', hli.NumLevels AS 'Level'
	From Has_Levels_In hli
	JOIN Class c ON hli.ClassID = c.ClassID