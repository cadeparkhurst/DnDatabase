USE DnD_goodriat_oriansaj_parkhuca30
GO

CREATE TABLE [User](
Username varchar(50) PRIMARY KEY,
PasswordSalt varchar(50),
PasswordHash varchar(50)
);