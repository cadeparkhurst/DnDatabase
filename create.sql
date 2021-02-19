USE [master]
GO
/****** Object:  Database [DnD_goodriat_oriansaj_parkhuca31]    Script Date: 2/18/2021 11:18:22 PM ******/
CREATE DATABASE [DnD_goodriat_oriansaj_parkhuca31]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DnD_goodriat_oriansaj_parkhuca31', FILENAME = N'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\DnD_goodriat_oriansaj_parkhuca31.mdf' , SIZE = 8192KB , MAXSIZE = 102400KB , FILEGROWTH = 10%)
 LOG ON 
( NAME = N'DnD_goodriat_oriansaj_parkhuca31_log', FILENAME = N'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\DnD_goodriat_oriansaj_parkhuca31_log.ldf' , SIZE = 1024KB , MAXSIZE = 71680KB , FILEGROWTH = 10%)
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO

USE [DnD_goodriat_oriansaj_parkhuca31]
GO
/****** Object:  User [parkhuca]    Script Date: 2/18/2021 11:18:22 PM ******/
CREATE USER [parkhuca] FOR LOGIN [parkhuca] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [goodriat]    Script Date: 2/18/2021 11:18:22 PM ******/
CREATE USER [goodriat] FOR LOGIN [goodriat] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [dndatabasefrontend]    Script Date: 2/18/2021 11:18:22 PM ******/
CREATE USER [dndatabasefrontend] FOR LOGIN [dndatabasefrontend] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [parkhuca]
GO
ALTER ROLE [db_owner] ADD MEMBER [goodriat]
GO
ALTER ROLE [db_datareader] ADD MEMBER [goodriat]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [goodriat]
GO
ALTER ROLE [db_datareader] ADD MEMBER [dndatabasefrontend]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [dndatabasefrontend]
GO
GRANT EXEC TO [dndatabasefrontend]
GO
/****** Object:  UserDefinedDataType [dbo].[armorWeightName]    Script Date: 2/18/2021 11:18:22 PM ******/
CREATE TYPE [dbo].[armorWeightName] FROM [varchar](30) NOT NULL
GO
/****** Object:  UserDefinedDataType [dbo].[backgroundName]    Script Date: 2/18/2021 11:18:22 PM ******/
CREATE TYPE [dbo].[backgroundName] FROM [varchar](30) NOT NULL
GO
/****** Object:  UserDefinedDataType [dbo].[languageName]    Script Date: 2/18/2021 11:18:22 PM ******/
CREATE TYPE [dbo].[languageName] FROM [varchar](20) NOT NULL
GO
/****** Object:  UserDefinedDataType [dbo].[raceName]    Script Date: 2/18/2021 11:18:22 PM ******/
CREATE TYPE [dbo].[raceName] FROM [varchar](30) NOT NULL
GO
/****** Object:  UserDefinedDataType [dbo].[savingThrowName]    Script Date: 2/18/2021 11:18:22 PM ******/
CREATE TYPE [dbo].[savingThrowName] FROM [varchar](30) NULL
GO
/****** Object:  UserDefinedDataType [dbo].[skillName]    Script Date: 2/18/2021 11:18:22 PM ******/
CREATE TYPE [dbo].[skillName] FROM [varchar](30) NULL
GO
/****** Object:  UserDefinedDataType [dbo].[weaponTypeName]    Script Date: 2/18/2021 11:18:22 PM ******/
CREATE TYPE [dbo].[weaponTypeName] FROM [varchar](30) NOT NULL
GO
/****** Object:  Table [dbo].[Has_Levels_In]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Has_Levels_In](
	[CharacterID] [int] NOT NULL,
	[ClassID] [int] NOT NULL,
	[NumLevels] [smallint] NULL,
PRIMARY KEY CLUSTERED 
(
	[CharacterID] ASC,
	[ClassID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Class]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Class](
	[HitDice] [int] NULL,
	[ClassID] [int] NOT NULL,
	[Name] [varchar](20) NULL,
	[NumSkillProfs] [smallint] NULL,
PRIMARY KEY CLUSTERED 
(
	[ClassID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[characterClassNamesAndLevels]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[characterClassNamesAndLevels]
AS
	SELECT hli.CharacterID, c.Name AS 'Class Name', hli.NumLevels AS 'Level'
	From Has_Levels_In hli
	JOIN Class c ON hli.ClassID = c.ClassID
GO
/****** Object:  Table [dbo].[Armor]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Armor](
	[BaseAC] [int] NULL,
	[ItemID] [int] NOT NULL,
	[AddDex] [int] NULL,
	[StrengthMin] [int] NULL,
	[DisAdvantageOnStealth] [int] NULL,
	[ArmorWeightName] [dbo].[armorWeightName] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ItemID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ArmorWeight]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ArmorWeight](
	[Name] [dbo].[armorWeightName] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Background]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Background](
	[Name] [dbo].[backgroundName] NOT NULL,
	[Feature] [int] NULL,
	[NumLanguagesGained] [smallint] NULL,
PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Background_Skill_Prof]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Background_Skill_Prof](
	[BackgroundName] [dbo].[backgroundName] NOT NULL,
	[SkillName] [dbo].[skillName] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[BackgroundName] ASC,
	[SkillName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Background_Tool_Prof]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Background_Tool_Prof](
	[BackgroundName] [dbo].[backgroundName] NOT NULL,
	[ItemID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[BackgroundName] ASC,
	[ItemID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CanLearnSpell]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CanLearnSpell](
	[ClassID] [int] NOT NULL,
	[SpellID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ClassID] ASC,
	[SpellID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Character]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Character](
	[CharacterID] [int] NOT NULL,
	[Str] [smallint] NULL,
	[Dex] [smallint] NULL,
	[Int] [smallint] NULL,
	[Wis] [smallint] NULL,
	[Cha] [smallint] NULL,
	[Alignment] [varchar](25) NULL,
	[HP] [smallint] NULL,
	[MaxHP] [smallint] NULL,
	[Con] [smallint] NULL,
	[Background] [dbo].[backgroundName] NOT NULL,
	[Race] [dbo].[raceName] NOT NULL,
	[Name] [varchar](25) NOT NULL,
 CONSTRAINT [PK__Characte__757BCA400EFA8E46] PRIMARY KEY CLUSTERED 
(
	[CharacterID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChoseProficiency]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChoseProficiency](
	[CharacterID] [int] NOT NULL,
	[SkillName] [dbo].[skillName] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[CharacterID] ASC,
	[SkillName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GivesClassFeature]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GivesClassFeature](
	[ClassID] [int] NOT NULL,
	[ClassFeatureID] [int] NOT NULL,
	[GainedAtLevel] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ClassFeatureID] ASC,
	[ClassID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GivesRaceFeature]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GivesRaceFeature](
	[FeatureID] [int] NOT NULL,
	[RaceName] [dbo].[raceName] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[FeatureID] ASC,
	[RaceName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HasArmorWeightProf]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HasArmorWeightProf](
	[ClassID] [int] NOT NULL,
	[ArmorWeightName] [varchar](30) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ClassID] ASC,
	[ArmorWeightName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HasSavingThrowProf]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HasSavingThrowProf](
	[ClassID] [int] NOT NULL,
	[SavingThrowName] [nvarchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ClassID] ASC,
	[SavingThrowName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HasWeaponProf]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HasWeaponProf](
	[ClassID] [int] NOT NULL,
	[WeaponID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ClassID] ASC,
	[WeaponID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HasWeaponTypeProf]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HasWeaponTypeProf](
	[ClassID] [int] NOT NULL,
	[WeaponTypeName] [varchar](30) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ClassID] ASC,
	[WeaponTypeName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[IsOwnedBy]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[IsOwnedBy](
	[ItemID] [int] NOT NULL,
	[CharacterID] [int] NOT NULL,
	[Quantity] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ItemID] ASC,
	[CharacterID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Item]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Item](
	[ItemID] [int] NOT NULL,
	[Cost] [varchar](10) NULL,
	[Mass] [float] NULL,
	[Name] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[ItemID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Knows_Language]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Knows_Language](
	[CharacterID] [int] NOT NULL,
	[LanguageName] [dbo].[languageName] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[CharacterID] ASC,
	[LanguageName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Knows_Language_From_Race]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Knows_Language_From_Race](
	[RaceName] [dbo].[raceName] NOT NULL,
	[LanguageName] [dbo].[languageName] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[RaceName] ASC,
	[LanguageName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KnowsSpell]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KnowsSpell](
	[CharacterID] [int] NOT NULL,
	[SpellID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[CharacterID] ASC,
	[SpellID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Language]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Language](
	[Name] [dbo].[languageName] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OffersSkillProficiency]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OffersSkillProficiency](
	[ClassID] [int] NOT NULL,
	[SkillName] [dbo].[skillName] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ClassID] ASC,
	[SkillName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Race]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Race](
	[Name] [dbo].[raceName] NOT NULL,
	[SubRace] [dbo].[raceName] NULL,
 CONSTRAINT [PK__Race__737584F7C2C29D8A] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Race_Tool_Prof]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Race_Tool_Prof](
	[RaceName] [dbo].[raceName] NOT NULL,
	[ItemID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[RaceName] ASC,
	[ItemID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Race_Weapon_Prof]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Race_Weapon_Prof](
	[RaceName] [dbo].[raceName] NOT NULL,
	[WeaponID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[RaceName] ASC,
	[WeaponID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SavingThrow]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SavingThrow](
	[Name] [nvarchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Skill]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Skill](
	[Name] [dbo].[skillName] NOT NULL,
	[relatedStat] [varchar](3) NULL,
PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Spell]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Spell](
	[Range] [varchar](20) NULL,
	[CastingTime] [varchar](20) NULL,
	[SpellID] [int] NOT NULL,
	[V] [int] NULL,
	[S] [int] NULL,
	[M] [varchar](150) NULL,
	[Duration] [varchar](50) NULL,
	[Concentration] [int] NULL,
	[Name] [varchar](50) NULL,
	[School] [varchar](20) NULL,
	[Level] [smallint] NULL,
PRIMARY KEY CLUSTERED 
(
	[SpellID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[StartsWith]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[StartsWith](
	[ClassID] [int] NOT NULL,
	[ItemID] [int] NOT NULL,
	[Quantity] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ItemID] ASC,
	[ClassID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Trait]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Trait](
	[TraitID] [int] NOT NULL,
	[Description] [nvarchar](200) NULL,
	[Name] [nvarchar](30) NULL,
PRIMARY KEY CLUSTERED 
(
	[TraitID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[Username] [varchar](50) NOT NULL,
	[PasswordSalt] [varchar](50) NULL,
	[PasswordHash] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Weapon]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Weapon](
	[ItemID] [int] NOT NULL,
	[Damage] [varchar](20) NULL,
	[Properties] [varchar](90) NULL,
	[WeaponTypeName] [dbo].[weaponTypeName] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ItemID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[WeaponType]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[WeaponType](
	[Name] [dbo].[weaponTypeName] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Armor]  WITH CHECK ADD  CONSTRAINT [FK_Armor_ArmorWeight] FOREIGN KEY([ArmorWeightName])
REFERENCES [dbo].[ArmorWeight] ([Name])
GO
ALTER TABLE [dbo].[Armor] CHECK CONSTRAINT [FK_Armor_ArmorWeight]
GO
ALTER TABLE [dbo].[Armor]  WITH CHECK ADD  CONSTRAINT [FK_Armor_Item] FOREIGN KEY([ItemID])
REFERENCES [dbo].[Item] ([ItemID])
GO
ALTER TABLE [dbo].[Armor] CHECK CONSTRAINT [FK_Armor_Item]
GO
ALTER TABLE [dbo].[Background]  WITH CHECK ADD FOREIGN KEY([Feature])
REFERENCES [dbo].[Trait] ([TraitID])
GO
ALTER TABLE [dbo].[Background_Skill_Prof]  WITH CHECK ADD FOREIGN KEY([BackgroundName])
REFERENCES [dbo].[Background] ([Name])
GO
ALTER TABLE [dbo].[Background_Skill_Prof]  WITH CHECK ADD FOREIGN KEY([SkillName])
REFERENCES [dbo].[Skill] ([Name])
GO
ALTER TABLE [dbo].[Background_Tool_Prof]  WITH CHECK ADD FOREIGN KEY([BackgroundName])
REFERENCES [dbo].[Background] ([Name])
GO
ALTER TABLE [dbo].[Background_Tool_Prof]  WITH CHECK ADD FOREIGN KEY([ItemID])
REFERENCES [dbo].[Item] ([ItemID])
GO
ALTER TABLE [dbo].[CanLearnSpell]  WITH CHECK ADD FOREIGN KEY([ClassID])
REFERENCES [dbo].[Class] ([ClassID])
GO
ALTER TABLE [dbo].[CanLearnSpell]  WITH CHECK ADD FOREIGN KEY([SpellID])
REFERENCES [dbo].[Spell] ([SpellID])
GO
ALTER TABLE [dbo].[Character]  WITH CHECK ADD  CONSTRAINT [FK__Character__Backg__339FAB6E] FOREIGN KEY([Background])
REFERENCES [dbo].[Background] ([Name])
GO
ALTER TABLE [dbo].[Character] CHECK CONSTRAINT [FK__Character__Backg__339FAB6E]
GO
ALTER TABLE [dbo].[Character]  WITH CHECK ADD  CONSTRAINT [FK__Character__Race__3493CFA7] FOREIGN KEY([Race])
REFERENCES [dbo].[Race] ([Name])
GO
ALTER TABLE [dbo].[Character] CHECK CONSTRAINT [FK__Character__Race__3493CFA7]
GO
ALTER TABLE [dbo].[ChoseProficiency]  WITH CHECK ADD  CONSTRAINT [FK__ChoseProf__Chara__1AD3FDA4] FOREIGN KEY([CharacterID])
REFERENCES [dbo].[Character] ([CharacterID])
GO
ALTER TABLE [dbo].[ChoseProficiency] CHECK CONSTRAINT [FK__ChoseProf__Chara__1AD3FDA4]
GO
ALTER TABLE [dbo].[ChoseProficiency]  WITH CHECK ADD FOREIGN KEY([SkillName])
REFERENCES [dbo].[Skill] ([Name])
GO
ALTER TABLE [dbo].[GivesClassFeature]  WITH CHECK ADD  CONSTRAINT [FK_GivesClassFeature_Class] FOREIGN KEY([ClassID])
REFERENCES [dbo].[Class] ([ClassID])
GO
ALTER TABLE [dbo].[GivesClassFeature] CHECK CONSTRAINT [FK_GivesClassFeature_Class]
GO
ALTER TABLE [dbo].[GivesClassFeature]  WITH CHECK ADD  CONSTRAINT [FK_GivesClassFeature_Trait] FOREIGN KEY([ClassFeatureID])
REFERENCES [dbo].[Trait] ([TraitID])
GO
ALTER TABLE [dbo].[GivesClassFeature] CHECK CONSTRAINT [FK_GivesClassFeature_Trait]
GO
ALTER TABLE [dbo].[GivesRaceFeature]  WITH CHECK ADD FOREIGN KEY([FeatureID])
REFERENCES [dbo].[Trait] ([TraitID])
GO
ALTER TABLE [dbo].[GivesRaceFeature]  WITH CHECK ADD  CONSTRAINT [FK__GivesRace__RaceN__2645B050] FOREIGN KEY([RaceName])
REFERENCES [dbo].[Race] ([Name])
GO
ALTER TABLE [dbo].[GivesRaceFeature] CHECK CONSTRAINT [FK__GivesRace__RaceN__2645B050]
GO
ALTER TABLE [dbo].[Has_Levels_In]  WITH CHECK ADD  CONSTRAINT [FK__Has_Level__Chara__2739D489] FOREIGN KEY([CharacterID])
REFERENCES [dbo].[Character] ([CharacterID])
GO
ALTER TABLE [dbo].[Has_Levels_In] CHECK CONSTRAINT [FK__Has_Level__Chara__2739D489]
GO
ALTER TABLE [dbo].[Has_Levels_In]  WITH CHECK ADD FOREIGN KEY([ClassID])
REFERENCES [dbo].[Class] ([ClassID])
GO
ALTER TABLE [dbo].[HasArmorWeightProf]  WITH CHECK ADD FOREIGN KEY([ArmorWeightName])
REFERENCES [dbo].[ArmorWeight] ([Name])
GO
ALTER TABLE [dbo].[HasArmorWeightProf]  WITH CHECK ADD FOREIGN KEY([ClassID])
REFERENCES [dbo].[Class] ([ClassID])
GO
ALTER TABLE [dbo].[HasSavingThrowProf]  WITH CHECK ADD FOREIGN KEY([ClassID])
REFERENCES [dbo].[Class] ([ClassID])
GO
ALTER TABLE [dbo].[HasSavingThrowProf]  WITH CHECK ADD FOREIGN KEY([SavingThrowName])
REFERENCES [dbo].[SavingThrow] ([Name])
GO
ALTER TABLE [dbo].[HasWeaponProf]  WITH CHECK ADD FOREIGN KEY([ClassID])
REFERENCES [dbo].[Class] ([ClassID])
GO
ALTER TABLE [dbo].[HasWeaponProf]  WITH CHECK ADD FOREIGN KEY([ClassID])
REFERENCES [dbo].[Class] ([ClassID])
GO
ALTER TABLE [dbo].[HasWeaponProf]  WITH CHECK ADD FOREIGN KEY([WeaponID])
REFERENCES [dbo].[Weapon] ([ItemID])
GO
ALTER TABLE [dbo].[HasWeaponTypeProf]  WITH CHECK ADD FOREIGN KEY([ClassID])
REFERENCES [dbo].[Class] ([ClassID])
GO
ALTER TABLE [dbo].[HasWeaponTypeProf]  WITH CHECK ADD FOREIGN KEY([WeaponTypeName])
REFERENCES [dbo].[WeaponType] ([Name])
GO
ALTER TABLE [dbo].[IsOwnedBy]  WITH CHECK ADD  CONSTRAINT [FK__IsOwnedBy__Chara__5535A963] FOREIGN KEY([CharacterID])
REFERENCES [dbo].[Character] ([CharacterID])
GO
ALTER TABLE [dbo].[IsOwnedBy] CHECK CONSTRAINT [FK__IsOwnedBy__Chara__5535A963]
GO
ALTER TABLE [dbo].[IsOwnedBy]  WITH CHECK ADD FOREIGN KEY([ItemID])
REFERENCES [dbo].[Item] ([ItemID])
GO
ALTER TABLE [dbo].[Knows_Language]  WITH CHECK ADD  CONSTRAINT [FK__Knows_Lan__Chara__2A164134] FOREIGN KEY([CharacterID])
REFERENCES [dbo].[Character] ([CharacterID])
GO
ALTER TABLE [dbo].[Knows_Language] CHECK CONSTRAINT [FK__Knows_Lan__Chara__2A164134]
GO
ALTER TABLE [dbo].[Knows_Language]  WITH CHECK ADD FOREIGN KEY([LanguageName])
REFERENCES [dbo].[Language] ([Name])
GO
ALTER TABLE [dbo].[Knows_Language_From_Race]  WITH CHECK ADD  CONSTRAINT [FK__Knows_Lan__RaceN__2BFE89A6] FOREIGN KEY([RaceName])
REFERENCES [dbo].[Race] ([Name])
GO
ALTER TABLE [dbo].[Knows_Language_From_Race] CHECK CONSTRAINT [FK__Knows_Lan__RaceN__2BFE89A6]
GO
ALTER TABLE [dbo].[KnowsSpell]  WITH CHECK ADD  CONSTRAINT [FK__KnowsSpel__Chara__571DF1D5] FOREIGN KEY([CharacterID])
REFERENCES [dbo].[Character] ([CharacterID])
GO
ALTER TABLE [dbo].[KnowsSpell] CHECK CONSTRAINT [FK__KnowsSpel__Chara__571DF1D5]
GO
ALTER TABLE [dbo].[KnowsSpell]  WITH CHECK ADD FOREIGN KEY([SpellID])
REFERENCES [dbo].[Spell] ([SpellID])
GO
ALTER TABLE [dbo].[OffersSkillProficiency]  WITH CHECK ADD FOREIGN KEY([ClassID])
REFERENCES [dbo].[Class] ([ClassID])
GO
ALTER TABLE [dbo].[OffersSkillProficiency]  WITH CHECK ADD FOREIGN KEY([SkillName])
REFERENCES [dbo].[Skill] ([Name])
GO
ALTER TABLE [dbo].[Race]  WITH CHECK ADD  CONSTRAINT [FK__Race__SubRace__22751F6C] FOREIGN KEY([SubRace])
REFERENCES [dbo].[Race] ([Name])
GO
ALTER TABLE [dbo].[Race] CHECK CONSTRAINT [FK__Race__SubRace__22751F6C]
GO
ALTER TABLE [dbo].[Race_Tool_Prof]  WITH CHECK ADD FOREIGN KEY([ItemID])
REFERENCES [dbo].[Item] ([ItemID])
GO
ALTER TABLE [dbo].[Race_Tool_Prof]  WITH CHECK ADD  CONSTRAINT [FK__Race_Tool__RaceN__1DB06A4F] FOREIGN KEY([RaceName])
REFERENCES [dbo].[Race] ([Name])
GO
ALTER TABLE [dbo].[Race_Tool_Prof] CHECK CONSTRAINT [FK__Race_Tool__RaceN__1DB06A4F]
GO
ALTER TABLE [dbo].[Race_Weapon_Prof]  WITH CHECK ADD  CONSTRAINT [FK__Race_Weap__RaceN__236943A5] FOREIGN KEY([RaceName])
REFERENCES [dbo].[Race] ([Name])
GO
ALTER TABLE [dbo].[Race_Weapon_Prof] CHECK CONSTRAINT [FK__Race_Weap__RaceN__236943A5]
GO
ALTER TABLE [dbo].[Race_Weapon_Prof]  WITH CHECK ADD FOREIGN KEY([WeaponID])
REFERENCES [dbo].[Weapon] ([ItemID])
GO
ALTER TABLE [dbo].[StartsWith]  WITH CHECK ADD  CONSTRAINT [FK_StartsWith_Class] FOREIGN KEY([ClassID])
REFERENCES [dbo].[Class] ([ClassID])
GO
ALTER TABLE [dbo].[StartsWith] CHECK CONSTRAINT [FK_StartsWith_Class]
GO
ALTER TABLE [dbo].[StartsWith]  WITH CHECK ADD  CONSTRAINT [FK_StartsWith_Item] FOREIGN KEY([ItemID])
REFERENCES [dbo].[Item] ([ItemID])
GO
ALTER TABLE [dbo].[StartsWith] CHECK CONSTRAINT [FK_StartsWith_Item]
GO
ALTER TABLE [dbo].[Weapon]  WITH CHECK ADD  CONSTRAINT [FK_Weapon_Item] FOREIGN KEY([ItemID])
REFERENCES [dbo].[Item] ([ItemID])
GO
ALTER TABLE [dbo].[Weapon] CHECK CONSTRAINT [FK_Weapon_Item]
GO
ALTER TABLE [dbo].[Weapon]  WITH CHECK ADD  CONSTRAINT [FK_Weapon_WeaponType] FOREIGN KEY([WeaponTypeName])
REFERENCES [dbo].[WeaponType] ([Name])
GO
ALTER TABLE [dbo].[Weapon] CHECK CONSTRAINT [FK_Weapon_WeaponType]
GO
/****** Object:  StoredProcedure [dbo].[acquireItem]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[acquireItem]
	@ItemID int,
	@CharacterID int,
	@Quantity int
AS
BEGIN
	IF(@CharacterID IS NULL OR NOT EXISTS (SELECT * FROM [Character] WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('Character must not be null and must exist in the Character table', 14, 1);
		RETURN 1;
	END
	IF(@ItemID IS NULL OR NOT EXISTS (SELECT * FROM Item WHERE ItemID = @ItemID))
	BEGIN
		RAISERROR('Item must not be null and must exist in the Item table', 14, 2);
		RETURN 2;
	END
	IF(EXISTS (SELECT * FROM IsOwnedBy WHERE CharacterID = @CharacterID AND ItemID = @ItemID))
	BEGIN
		RAISERROR('The character already owns that item', 14, 3);
		RETURN 3;
	END
	IF(@Quantity < 1)
	BEGIN
		RAISERROR('Quantity must be greater than 0', 14, 4);
		RETURN 4;
	END

	INSERT INTO IsOwnedBy(ItemID, CharacterID, Quantity)
	VALUES(@ItemID, @CharacterID, @Quantity)
END
GRANT EXECUTE ON acquireItem TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[AddCharacter]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE Procedure [dbo].[AddCharacter]
	@str smallint,
	@dex smallint,
	@intel smallint,
	@wis smallint,
	@cha smallint,
	@con smallint,
	@alignment varchar(25),
	@hp smallint,
	@maxhp smallint,
	@classId int,
	@backgroundName dbo.backgroundName,
	@raceName dbo.raceName,
	@name varchar(25)
AS
BEGIN
	IF(@str IS NOT NULL AND (@str < 3 OR @str > 20))
	BEGIN
		RAISERROR('Strength must be between 3 and 20.', 14, 1);
		RETURN 1;
	END
	IF(@dex IS NOT NULL AND (@dex < 3 OR @dex > 20))
	BEGIN
		RAISERROR('Dexterity must be between 3 and 20.', 14, 2);
		RETURN 2;
	END
	IF(@intel IS NOT NULL AND (@intel < 3 OR @intel > 20))
	BEGIN
		RAISERROR('Intelligence must be between 3 and 20.', 14, 3);
		RETURN 3;
	END
	IF(@wis IS NOT NULL AND (@wis < 3 OR @wis > 20))
	BEGIN
		RAISERROR('Wisdom must be between 3 and 20.', 14, 4);
		RETURN 4;
	END
	IF(@cha IS NOT NULL AND (@cha < 3 OR @cha > 20))
	BEGIN
		RAISERROR('Charisma must be between 3 and 20.', 14, 5);
		RETURN 5;
	END
	IF(@con IS NOT NULL AND (@con < 3 OR @con > 20))
	BEGIN
		RAISERROR('Constitution must be between 3 and 20.', 14, 6);
		RETURN 6;
	END
	IF(NOT EXISTS (SELECT * FROM Class WHERE ClassID = @classId))
	BEGIN
		RAISERROR('Class must exist in the Class table.', 14, 7);
		RETURN 7;
	END
	IF(NOT EXISTS (SELECT * FROM Background WHERE [Name] = @backgroundName))
	BEGIN
		RAISERROR('Background must exist in the Background table.', 14, 8);
		RETURN 8;
	END
	IF(NOT EXISTS (SELECT * FROM Race WHERE [Name] = @raceName))
	BEGIN
		RAISERROR('Race must exist in the Race table.', 14, 9);
		RETURN 9;
	END
	IF(@name IS NULL)
	BEGIN
		RAISERROR('Name must not be null', 14, 10)
		RETURN 10;
	END

	DECLARE @newid int;
	SET @newid = (SELECT MAX(CharacterID) FROM [Character]) + 1;

	INSERT INTO Character(CharacterID, [Str], Dex, [Int], Wis, Cha, Alignment, HP, MaxHP, Con, Background, Race, [Name])
	VALUES(@newid, @str, @dex, @intel, @wis, @cha, @alignment, @hp, @maxhp, @con, @backgroundName, @raceName, @name);

--	DECLARE @newid int;
--	SET @newid = SCOPE_IDENTITY();

	INSERT INTO Has_Levels_In(CharacterID, ClassID, NumLevels)
	VALUES(@newid, @classId, 1);

	INSERT INTO IsOwnedBy(ItemID, CharacterID, Quantity)
	SELECT sw.ItemID, @newid, sw.Quantity
	FROM StartsWith sw
	WHERE sw.ClassID = @classId

	INSERT INTO Knows_Language(CharacterID, LanguageName)
	SELECT @newid, klr.LanguageName
	FROM Knows_Language_From_Race klr
	WHERE klr.RaceName = @raceName

	SELECT CharacterID FROM Character WHERE CharacterID = @newid
END
GRANT EXECUTE ON AddCharacter TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[addItem]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[addItem]
	@CharacterID int,
	@ItemName varchar(50),
	@Quantity smallint
AS BEGIN
	IF NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID) BEGIN
		RAISERROR('Character Does not Exist',14,1)
		RETURN 1
	END

	IF NOT EXISTS (SELECT * FROM Item WHERE Name = @ItemName) BEGIN
		RAISERROR('No item with that name exists',14,2)
		RETURN 2
	END

	DECLARE @ItemID int
	SET @ItemID = (SELECT ItemID FROM Item WHERE Name = @ItemName)

	IF EXISTS (SELECT * FROM IsOwnedBy WHERE CharacterID=@CharacterID AND ItemID=@ItemID) BEGIN
		UPDATE IsOwnedBy
		SET Quantity = @Quantity + (SELECT Quantity FROM IsOwnedBy WHERE CharacterID=@CharacterID AND ItemID=@ItemID)
		WHERE CharacterID=@CharacterID AND ItemID=@ItemID 
	END ELSE BEGIN
		INSERT INTO IsOwnedBy(CharacterID, ItemID, Quantity)
		VALUES(@CharacterID,@ItemID,@Quantity)
	END


END
GRANT EXECUTE ON addItem TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[addLevelIn]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[addLevelIn]
	@CharacterID int,
	@ClassName	varchar(30),
	@DiceRolled smallint
AS BEGIN
	IF NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID) BEGIN
		RAISERROR('THAT CHARACTER DOES NOT EXIST',14,1)
		RETURN 1
	END

	IF NOT EXISTS (SELECT * FROM Class c WHERE c.Name = @ClassName) BEGIN
		RAISERROR('That class does not exist',14,2)
		RETURN 2
	END

	DECLARE @ClassID int
	SET @ClassID = (SELECT ClassID FROM Class c WHERE c.Name = @ClassName)

	UPDATE Character
	SET MaxHP = MaxHP+@DiceRolled, HP=HP+@DiceRolled
	WHERE CharacterID=@CharacterID

	IF NOT EXISTS (SELECT * FROM Has_Levels_In WHERE CharacterID=@CharacterID AND ClassID=@ClassID) BEGIN
		INSERT INTO Has_Levels_In(CharacterID, ClassID,  NumLevels)
		VALUES (@CharacterID, @ClassID, 1)
	END ELSE BEGIN
		DECLARE @currentLevel smallint
		SET @currentLevel = (SELECT NumLevels FROM Has_Levels_In WHERE CharacterID=@CharacterID AND ClassID=@ClassID)

		UPDATE Has_Levels_In
		SET NumLevels=@currentLevel+1
		WHERE CharacterID=@CharacterID AND ClassID=@ClassID

	END

END

GRANT EXECUTE ON addLevelIn
	TO [dndatabasefrontend]
GO
/****** Object:  StoredProcedure [dbo].[addProficiency]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[addProficiency]
	@CharacterID int,
	@Skill dbo.skillName
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table',14,1);
		RETURN 1;
	END
	IF(NOT EXISTS (SELECT * FROM Skill WHERE Name = @Skill))
	BEGIN
		RAISERROR('Skill does not exist in the skills table',14,2);
		RETURN 2;
	END
	
	INSERT INTO ChoseProficiency(CharacterID, SkillName) VALUES (@CharacterID, @Skill);
END
GRANT EXECUTE ON addProficiency TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[deleteCharacter]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[deleteCharacter]
	@CharacterID int
AS
BEGIN
	BEGIN TRANSACTION
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END
	DELETE FROM ChoseProficiency WHERE CharacterID = @CharacterID
	IF(@@ERROR <> 0)
	BEGIN
		RAISERROR('Could not delete proficiencies',14,2);
		ROLLBACK TRANSACTION;
	END
	DELETE FROM Has_Levels_In WHERE CharacterID = @CharacterID
	IF(@@ERROR <> 0)
	BEGIN
		RAISERROR('Could not delete class levels',14,3);
		ROLLBACK TRANSACTION;
	END
	DELETE FROM IsOwnedBy WHERE CharacterID = @CharacterID
	IF(@@ERROR <> 0)
	BEGIN
		RAISERROR('Could not delete items',14,4);
		ROLLBACK TRANSACTION;
	END
	DELETE FROM Knows_Language WHERE CharacterID = @CharacterID
	IF(@@ERROR <> 0)
	BEGIN
		RAISERROR('Could not delete languages',14,5);
		ROLLBACK TRANSACTION;
	END
	DELETE FROM KnowsSpell WHERE CharacterID = @CharacterID
	IF(@@ERROR <> 0)
	BEGIN
		RAISERROR('Could not delete spells',14,6);
		ROLLBACK TRANSACTION;
	END
	DELETE FROM Character WHERE CharacterID = @CharacterID
	IF(@@ERROR <> 0)
	BEGIN
		RAISERROR('Could not delete character',14,7);
		ROLLBACK TRANSACTION;
	END
	COMMIT TRANSACTION;
END
GRANT EXECUTE ON deleteCharacter TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[getAllClassLevels]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getAllClassLevels]
	@CharacterID int
AS BEGIN
	IF NOT EXISTS(SELECT * FROM Character WHERE CharacterID = @CharacterID)BEGIN
		RAISERROR('No such character exists',14,1)
		RETURN 1
	END

	SELECT [Class Name], [Level]
	FROM [dbo].[characterClassNamesAndLevels]
	WHERE characterID = @CharacterID
END
GRANT EXECUTE ON getAllClassLevels TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[getAllSkillsProfs]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getAllSkillsProfs]
	@CharacterID int
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END

	SELECT s.Name, s.relatedStat, cp.CharacterID
	FROM Skill s
	LEFT JOIN ChoseProficiency cp ON cp.SkillName = s.Name AND cp.CharacterID = @CharacterID
END
GRANT EXECUTE ON getAllSkillsProfs TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[getCharacterInfo]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[getCharacterInfo]
	@CharacterID int
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END

	SELECT c.[Name], b.[Name] AS Background, r.[Name] AS Race, Dex, [Int], Wis, Cha, Con, [Str],  Alignment, HP, MaxHP, SUM(l.NumLevels) AS Level
	FROM Character c
	JOIN Background b ON b.Name = c.Background
	JOIN Race r ON r.Name = c.Race
	JOIN Has_Levels_In l ON l.CharacterID = c.CharacterID
	WHERE c.CharacterID = @CharacterID
	GROUP BY l.CharacterID, c.[Name], b.[Name], r.[Name], Dex, [Int], Wis, Cha, Con, [Str],  Alignment, HP, MaxHP
END
GRANT EXECUTE ON getCharacterInfo TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[getCharNames]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getCharNames]
AS
BEGIN
	SELECT Name, CharacterID
	FROM Character
END
GRANT EXECUTE ON getCharNames TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[getClassID]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getClassID]
	@Name varchar(20)
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Class WHERE Name = @Name))
	BEGIN
		RAISERROR('No class with that name exists', 14, 1);
		RETURN 1;
	END

	SELECT ClassID FROM Class WHERE Name = @Name;
END
GRANT EXECUTE ON getClassID TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[getClassLevels]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getClassLevels]
	@CharacterID int,
	@ClassID int
AS
BEGIN
	IF(@CharacterID IS NULL OR NOT EXISTS (SELECT * FROM [Character] WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('Character must not be null and must exist in the Character table', 14, 1);
		RETURN 1;
	END
	IF(@ClassID IS NULL OR NOT EXISTS (SELECT * FROM Class WHERE ClassID = @ClassID))
	BEGIN
		RAISERROR('Class must not be null and must exist in the Class table', 14, 2);
		RETURN 2;
	END
	IF(NOT EXISTS (SELECT * FROM Has_Levels_In WHERE CharacterID = @CharacterID AND ClassID = @ClassID))
	BEGIN
		RAISERROR('The character must already have levels in the given class', 14, 3);
		RETURN 3;
	END

	SELECT NumLevels
	FROM Has_Levels_In
	WHERE CharacterID = @CharacterID AND ClassID = @ClassID
END
GRANT EXECUTE ON getClassLevels TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[getItems]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getItems]
	@CharacterID int
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END

	SELECT i.Name AS Item, iob.Quantity
	FROM IsOwnedBy iob
	JOIN Item i ON iob.ItemID = i.ItemID
	WHERE iob.CharacterID = @CharacterID
END
GRANT EXECUTE ON getItems TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[getLanguages]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getLanguages]
	@CharacterID int
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END

	SELECT kl.LanguageName
	FROM Knows_Language kl
	WHERE kl.CharacterID = @CharacterID
END
GRANT EXECUTE ON getLanguages TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[getNumLangs]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getNumLangs]
	@CharacterID int
AS
BEGIN
	IF NOT EXISTS (SELECT * FROM Character WHERE  CharacterID=@CharacterID) BEGIN
		RAISERROR('That character does not exist',14,1)
		RETURN 1 
	END

	SELECT b.NumLanguagesGained 
	FROM Character c 
	JOIN Background b ON b.Name = c.Background
	WHERE c.CharacterID = @CharacterID
END
GRANT EXECUTE ON getNumLangs TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[getNumSkillProfs]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getNumSkillProfs]
	@classID int
AS
BEGIN
	IF NOT EXISTS (SELECT * FROM Class WHERE ClassID=@classID)BEGIN
		RAISERROR('No class exists with that ID',14,1)
		RETURN 1
	END

	SELECT NumSkillProfs FROM Class WHERE ClassID=@classID
END

GRANT EXECUTE ON  getNumSkillProfs
	TO [dndatabasefrontend]
GO
/****** Object:  StoredProcedure [dbo].[getOfferedProfs]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getOfferedProfs]
	@classID int
AS
BEGIN
	IF NOT EXISTS (SELECT * FROM Class WHERE ClassID = @classID) BEGIN
		RAISERROR('That class does not exists',14,1)
		RETURN 1
	END

	SELECT SkillName
	FROM OffersSkillProficiency
	WHERE ClassID = @classID
END
GRANT EXECUTE ON getOfferedProfs TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[getSkills]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getSkills]
	@CharacterID int
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END

	SELECT cp.SkillName
	FROM ChoseProficiency cp
	WHERE cp.CharacterID = @CharacterID
END
GRANT EXECUTE ON getSkills TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[getSpellID]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getSpellID]
	@Name varchar(50)
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Spell WHERE Name = @Name))
	BEGIN
		RAISERROR('No spell with that name exists', 14, 1);
		RETURN 1;
	END

	SELECT SpellID FROM Spell WHERE Name = @Name
END
GRANT EXECUTE ON getSpellID TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[getSpells]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getSpells]
	@CharacterID int
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END

	SELECT s.Name, s.Level, s.Duration, s.Range, s.CastingTime, s.Concentration, s.School, s.M, s.V, s.S
	FROM KnowsSpell ks
	JOIN Spell s ON s.SpellID = ks.SpellID
	WHERE ks.CharacterID = @CharacterID
END
GRANT EXECUTE ON getSpells TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[getTraits]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getTraits]
	@CharacterID int
AS
BEGIN
	IF(NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('CharacterID does not exist in the Characters table', 14, 1)
		RETURN 1;
	END

	SELECT t.Name AS Trait, t.Description
	FROM Character c
	JOIN Background b ON c.Background = b.Name
	JOIN Trait t ON t.TraitID = b.Feature
	WHERE c.CharacterID = @CharacterID
	UNION
	SELECT t.Name AS Trait, t.Description
	FROM Character c
	JOIN Has_Levels_In hli ON hli.CharacterID = c.CharacterID
	JOIN GivesClassFeature gcf ON gcf.ClassID = hli.ClassID
	JOIN Trait t ON t.TraitID = gcf.ClassFeatureID
	WHERE c.CharacterID = @CharacterID
	UNION
	SELECT t.Name AS Trait, t.Description
	FROM Character c
	JOIN GivesRaceFeature grf ON grf.RaceName = c.Race
	JOIN Trait t ON t.TraitID = grf.FeatureID
	WHERE c.CharacterID = @CharacterID
END
GRANT EXECUTE ON getTraits TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[learnLanguage]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[learnLanguage]
	@CharacterID int,
	@Language dbo.languageName
AS
BEGIN
	IF(@CharacterID IS NULL OR NOT EXISTS (SELECT * FROM [Character] WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('Character must not be null and must exist in the Character table', 14, 1);
		RETURN 1;
	END
	IF(@Language IS NULL OR NOT EXISTS (SELECT * FROM [Language] WHERE [Name] = @Language))
	BEGIN
		RAISERROR('lanuage must not be null and must exist in the Language table', 14, 2);
		RETURN 2;
	END
	IF(EXISTS (SELECT * FROM Knows_Language WHERE CharacterID = @CharacterID AND LanguageName = @Language))
	BEGIN
		RAISERROR('The character already knows that language', 14, 3);
		RETURN 3;
	END

	INSERT INTO Knows_Language(CharacterID, LanguageName)
	VALUES(@CharacterID, @Language)
END
GRANT EXECUTE ON learnLanguage TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[learnSpell]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[learnSpell]
	@CharacterID int,
	@SpellID int
AS
BEGIN
	IF(@CharacterID IS NULL OR NOT EXISTS (SELECT * FROM [Character] WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('Character must not be null and must exist in the Character table', 14, 1);
		RETURN 1;
	END
	IF(@SpellID IS NULL OR NOT EXISTS (SELECT * FROM Spell WHERE SpellID = @SpellID))
	BEGIN
		RAISERROR('Spell must not be null and must exist in the Spell table', 14, 2);
		RETURN 2;
	END
	IF(EXISTS (SELECT * FROM KnowsSpell WHERE CharacterID = @CharacterID AND SpellID = @SpellID))
	BEGIN
		RAISERROR('The character already knows that spell', 14, 3);
		RETURN 3;
	END
	IF(NOT EXISTS (SELECT * FROM CanLearnSpell WHERE SpellID = @SpellID AND ClassID IN (SELECT ClassID FROM Has_Levels_In WHERE CharacterID = @CharacterID)))
	BEGIN
		RAISERROR('The character cannot learn that spell', 14, 4);
		RETURN 4;
	END

	INSERT INTO KnowsSpell(CharacterID, SpellID)
	VALUES(@CharacterID, @SpellID)
END
GRANT EXECUTE ON learnSpell TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[LOGIN]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[LOGIN]
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
GRANT EXECUTE ON [LOGIN] TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[Register]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Register]
	@Username nvarchar(50),
	@PasswordSalt varchar(50),
	@PasswordHash varchar(50)
AS
BEGIN
	if @Username is null or @Username = ''
	BEGIN
		Print 'Username cannot be null or empty.';
		RETURN (1)
	END
	if @PasswordSalt is null or @PasswordSalt = ''
	BEGIN
		Print 'PasswordSalt cannot be null or empty.';
		RETURN (2)
	END
	if @PasswordHash is null or @PasswordHash = ''
	BEGIN
		Print 'PasswordHash cannot be null or empty.';
		RETURN (3)
	END
	IF (SELECT COUNT(*) FROM [User]
          WHERE Username = @Username) = 1
	BEGIN
      PRINT 'ERROR: Username already exists.';
	  RETURN(4)
	END
	INSERT INTO [User](Username, PasswordSalt, PasswordHash)
	VALUES (@username, @passwordSalt, @passwordHash)
END
GRANT EXECUTE ON Register TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[removeItem]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[removeItem]
	@CharacterID int,
	@ItemID int
AS
BEGIN
	IF(@CharacterID IS NULL OR NOT EXISTS (SELECT * FROM [Character] WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('Character must not be null and must exist in the Character table', 14, 1);
		RETURN 1;
	END
	IF(@ItemID IS NULL OR NOT EXISTS (SELECT * FROM Item WHERE ItemID = @ItemID))
	BEGIN
		RAISERROR('Item must not be null and must exist in the Item table', 14, 2);
		RETURN 2;
	END
	IF(NOT EXISTS (SELECT * FROM IsOwnedBy WHERE CharacterID = @CharacterID AND ItemID = @ItemID))
	BEGIN
		RAISERROR('The character does not own that item', 14, 3);
		RETURN 3;
	END

	DELETE FROM IsOwnedBy WHERE CharacterID = @CharacterID AND ItemID = @ItemID
END
GRANT EXECUTE ON removeItem TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[removeItemByQuant]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[removeItemByQuant]
	@CharacterID int,
	@ItemName varchar(50),
	@Quantity smallint
AS BEGIN
	IF NOT EXISTS (SELECT * FROM Character WHERE CharacterID = @CharacterID) BEGIN
		RAISERROR('Character Does not Exist',14,1)
		RETURN 1
	END

	IF NOT EXISTS (SELECT * FROM Item WHERE Name = @ItemName) BEGIN
		RAISERROR('No item with that name exists',14,2)
		RETURN 2
	END

	DECLARE @ItemID int
	SET @ItemID = (SELECT ItemID FROM Item WHERE Name = @ItemName)

	DECLARE @newQuant int
	SET @newQuant = (SELECT quantity FROM IsOwnedBy WHERE CharacterID=@CharacterID AND ItemID=@ItemID) - @Quantity

	IF @newQuant <= 0 BEGIN
		DELETE IsOwnedBy
		WHERE CharacterID=@CharacterID AND ItemID=@ItemID
	END ELSE BEGIN
		UPDATE IsOwnedBy
		SET Quantity=@newQuant
		WHERE CharacterID=@CharacterID AND ItemID=@ItemID
	END
END
GRANT EXECUTE ON removeItemByQuant TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[unlearnLanguage]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[unlearnLanguage]
	@CharacterID int,
	@Language dbo.languageName
AS
BEGIN
	IF(@CharacterID IS NULL OR NOT EXISTS (SELECT * FROM [Character] WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('Character must not be null and must exist in the Character table', 14, 1);
		RETURN 1;
	END
	IF(@Language IS NULL OR NOT EXISTS (SELECT * FROM [Language] WHERE [Name] = @Language))
	BEGIN
		RAISERROR('lanuage must not be null and must exist in the Language table', 14, 2);
		RETURN 2;
	END
	IF(NOT EXISTS (SELECT * FROM Knows_Language WHERE CharacterID = @CharacterID AND LanguageName = @Language))
	BEGIN
		RAISERROR('The character does not know that language', 14, 3);
		RETURN 3;
	END

	DELETE FROM Knows_Language WHERE CharacterID = @CharacterID AND LanguageName = @Language;
END
GRANT EXECUTE ON unlearnLanguage TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[unlearnSpell]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[unlearnSpell]
	@CharacterID int,
	@SpellID int
AS
BEGIN
	IF(@CharacterID IS NULL OR NOT EXISTS (SELECT * FROM [Character] WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('Character must not be null and must exist in the Character table', 14, 1);
		RETURN 1;
	END
	IF(@SpellID IS NULL OR NOT EXISTS (SELECT * FROM Spell WHERE [Name] = @SpellID))
	BEGIN
		RAISERROR('Spell must not be null and must exist in the Spell table', 14, 2);
		RETURN 2;
	END
	IF(NOT EXISTS (SELECT * FROM KnowsSpell WHERE CharacterID = @CharacterID AND SpellID = @SpellID))
	BEGIN
		RAISERROR('The character does not know that spell', 14, 3);
		RETURN 3;
	END

	DELETE FROM KnowsSpell WHERE CharacterID = @CharacterID AND SpellID = @SpellID;
END
GRANT EXECUTE ON unlearnSpell TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[UpdateClassLevel]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UpdateClassLevel]
	@CharacterID int,
	@ClassID int,
	@NumLevels smallint
AS
BEGIN
	IF(@CharacterID IS NULL OR NOT EXISTS (SELECT * FROM [Character] WHERE CharacterID = @CharacterID))
	BEGIN
		RAISERROR('Character must not be null and must exist in the Character table', 14, 1);
		RETURN 1;
	END
	IF(@ClassID IS NULL OR NOT EXISTS (SELECT * FROM Class WHERE ClassID = @ClassID))
	BEGIN
		RAISERROR('Class must not be null and must exist in the Class table', 14, 2);
		RETURN 2;
	END
	IF(NOT EXISTS (SELECT * FROM Has_Levels_In WHERE CharacterID = @CharacterID AND ClassID = @ClassID))
	BEGIN
		RAISERROR('The character must already have levels in the given class', 14, 3);
		RETURN 3;
	END
	IF(@NumLevels IS NULL OR @NumLevels < 0)
	BEGIN
		RAISERROR('NumLevels must be non-null and positive', 14, 4);
		RETURN 4;
	END

	UPDATE Has_Levels_In
	SET NumLevels = @NumLevels
	WHERE CharacterID = @CharacterID AND ClassID = @ClassID
END
GRANT EXECUTE ON UpdateClassLevel TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[updateItem]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[updateItem]
@ItemID int,
@Cost int = null,
@Weight int = null,
@Name varchar(20) = null
AS
BEGIN
	if @ItemID is null BEGIN
		PRINT 'ERROR: ItemID cannot be null';
		RETURN (1)
	END
	
	UPDATE dbo.Item
	SET Cost=@Cost, Mass=@Weight, Name=@Name
	WHERE ItemID = @ItemID 
END
GRANT EXECUTE ON updateItem TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[updateSpell]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[updateSpell]
@SpellID int,
@Range int,
@CastingTime int,
@V int=0,
@S int=0,
@M int=0,
@Duration int,
@Concentration int = 0
AS
BEGIN
	if @SpellID is null BEGIN
		PRINT 'ERROR: SpellID cannot be null';
		RETURN (1)
	END
	
	UPDATE dbo.Spell
	SET Range=@Range, CastingTime=@CastingTime,
		V=@V, S=@S, M=@M, Duration=@Duration, Concentration=@Concentration
	WHERE SpellID = @SpellID 
END
GRANT EXECUTE ON updateSpell TO dndatabasefrontend
GO
/****** Object:  StoredProcedure [dbo].[updateWeapon]    Script Date: 2/18/2021 11:18:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[updateWeapon]
@ItemID int,
@Damage varchar(8),
@Properties varchar(20),
@WeaponTypeName weaponTypeName
AS
BEGIN
	if @ItemID is null BEGIN
		PRINT 'ERROR: ItemID cannot be null';
		RETURN (1)
	END
	if @WeaponTypeName is null BEGIN
		PRINT 'ERROR: WeaponTypeName cannot be null'
		RETURN (2)
	END
	if NOT EXISTS (SELECT * FROM WeaponType WHERE Name=@WeaponTypeName) BEGIN
		PRINT 'ERROR: WeaponTypeName is not a valid name'
		RETURN (3)
	END
	
	UPDATE Weapon
	SET Damage=@Damage, Properties=@Properties, WeaponTypeName=@WeaponTypeName
	WHERE ItemID = @ItemID 
END
GRANT EXECUTE ON updateWeapon TO dndatabasefrontend
GO
CREATE INDEX charOwns ON IsOwnedBy(CharacterID);
CREATE INDEX charLevelsIn ON Has_Levels_In(CharacterID);
CREATE INDEX charProf ON ChoseProficiency(CharacterID);
CREATE INDEX charLang ON Knows_Language(CharacterID);
CREATE INDEX charSpell ON KnowsSpell(CharacterID);
GO
USE [master]
GO
ALTER DATABASE [DnD_goodriat_oriansaj_parkhuca31] SET  READ_WRITE 
GO
