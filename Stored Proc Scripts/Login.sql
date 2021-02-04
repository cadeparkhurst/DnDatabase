USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE PROCEDURE LOGIN
@userName varchar(30)
AS
BEGIN
	IF NOT EXISTS (SELECT * FROM [User] WHERE Username=@userName) BEGIN
		RAISERROR('Invalid Login',14,1)
		RETURN(1)
	END

	SELECT PasswordSalt, PasswordHash
	FROM [User]
	WHERE Username=@userName
END