/*
** Author : Subodh Shakya
** Project : Geospatial Itinerary Intelligence
** Script Description : SQL Script to create all tables and relationships in database for Geospatial Itinerary Intelligence
** Date : 03/10/2014
*/

USE [GII]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO
-- Create User Table and Relationships
CREATE TABLE [dbo].[User](
	[UserId] [int] IDENTITY(1,1) NOT NULL,
	[FirstName] [varchar](50) NOT NULL,
	[MiddleName] [varchar](50) NULL,
	[LastName] [varchar](50) NOT NULL,
	[Email] [varchar](100) NOT NULL UNIQUE,
	[Password] [varchar](100) NOT NULL,
	[Street] [varchar](100) NULL,
	[City] [varchar](50) NULL,
	[State] [varchar](50) NULL,
	[Country] [varchar](50) NULL,
	[ZipCode] [int] NULL,
	[NoOfTraveler] [int] NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[UserId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

-- Create Sector Table and Relationships
CREATE TABLE [dbo].[Sector](
	[SectorId] [int] IDENTITY(1,1) NOT NULL,
	[OriginCityId] [int] NOT NULL,
	[DestinationCityId] [int] NOT NULL,
	[PlanDate] [date] NOT NULL,
	[Cost] [float] NOT NULL,
	[Distance] [float] NULL,
	[NoOfSegments] [int] NOT NULL,
	[UserId] [int] NOT NULL,
	[Completed] [bit]  NOT NULL,
 CONSTRAINT [PK_Sector] PRIMARY KEY CLUSTERED 
(
	[SectorId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[Sector]  WITH CHECK ADD  CONSTRAINT [FK_Sector_User] FOREIGN KEY([UserId])
REFERENCES [dbo].[User] ([UserId])
GO

ALTER TABLE [dbo].[Sector] CHECK CONSTRAINT [FK_Sector_User]
GO

-- City Table and Relationships
CREATE TABLE [dbo].[City](
	[CityId] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](100) NOT NULL,
	[ZipCode] [int] NULL,
	[CountryId] [int] NOT NULL,
 CONSTRAINT [PK_City] PRIMARY KEY CLUSTERED 
(
	[CityId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

-- Country Table and Relationships
CREATE TABLE [dbo].[Country](
	[CountryId] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](100) NOT NULL,
	[CapitalCityId] [int] NULL,
 CONSTRAINT [PK_Country] PRIMARY KEY CLUSTERED 
(
	[CountryId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

-- Relationship of country with city
ALTER TABLE [dbo].[Country]  WITH CHECK ADD  CONSTRAINT [FK_Country_Capital_City] FOREIGN KEY([CapitalCityId])
REFERENCES [dbo].[City] ([CityId])
GO

ALTER TABLE [dbo].[Country] CHECK CONSTRAINT [FK_Country_Capital_City]
GO

-- Relationship of city with country
ALTER TABLE [dbo].[City]  WITH CHECK ADD  CONSTRAINT [FK_City_Country] FOREIGN KEY([CountryId])
REFERENCES [dbo].[Country] ([CountryId])
GO

ALTER TABLE [dbo].[City] CHECK CONSTRAINT [FK_City_Country]
GO

-- Create Segment Table and Relationships
CREATE TABLE [dbo].[Segment](
	[SegmentId] [int] IDENTITY(1,1) NOT NULL,
	[OriginCityId] [int] NOT NULL,
	[DestinationCityId] [int] NOT NULL,
	[StartDate] [date] NOT NULL,
	[EndDate] [date] NOT NULL,
	[Distance] [float] NULL,
	[Cost] [float] NULL,
	[SectorId] [int] NULL,
	[ReviewId] [int] NULL,
	[NextSegmentId] [int] NULL,
 CONSTRAINT [PK_Segment] PRIMARY KEY CLUSTERED 
(
	[SegmentId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[Segment]  WITH CHECK ADD  CONSTRAINT [FK_Segment_Destination_City] FOREIGN KEY([DestinationCityId])
REFERENCES [dbo].[City] ([CityId])
GO

ALTER TABLE [dbo].[Segment] CHECK CONSTRAINT [FK_Segment_Destination_City]
GO

ALTER TABLE [dbo].[Segment]  WITH CHECK ADD  CONSTRAINT [FK_Segment_Origin_City] FOREIGN KEY([OriginCityId])
REFERENCES [dbo].[City] ([CityId])
GO

ALTER TABLE [dbo].[Segment] CHECK CONSTRAINT [FK_Segment_Origin_City]
GO

ALTER TABLE [dbo].[Segment]  WITH CHECK ADD  CONSTRAINT [FK_Segment_Sector] FOREIGN KEY([SectorId])
REFERENCES [dbo].[Sector] ([SectorId])
GO

ALTER TABLE [dbo].[Segment] CHECK CONSTRAINT [FK_Segment_Sector]
GO

ALTER TABLE [dbo].[Segment]  WITH CHECK ADD  CONSTRAINT [FK_Segment_Segment] FOREIGN KEY([SegmentId])
REFERENCES [dbo].[Segment] ([SegmentId])
GO

ALTER TABLE [dbo].[Segment] CHECK CONSTRAINT [FK_Segment_Segment]
GO

-- CoTraveler Table and Relationships
CREATE TABLE [dbo].[CoTraveler](
	[UserId] [int] NOT NULL,
	[CoTravelerId] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](100) NOT NULL,
	[Relationship] [varchar](100) NOT NULL,
 CONSTRAINT [PK_CoTraveler_1] PRIMARY KEY CLUSTERED 
(
	[CoTravelerId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[CoTraveler]  WITH CHECK ADD  CONSTRAINT [FK_CoTraveler_User] FOREIGN KEY([UserId])
REFERENCES [dbo].[User] ([UserId])
GO

ALTER TABLE [dbo].[CoTraveler] CHECK CONSTRAINT [FK_CoTraveler_User]
GO


-- Create Place Table and Relationships
CREATE TABLE [dbo].[Place](
	[PlaceId] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](100) NOT NULL,
	[CityId] [int] NOT NULL,
	[XCoordinate] [float] NULL,
	[YCoordinate] [float] NULL,
 CONSTRAINT [PK_Place] PRIMARY KEY CLUSTERED 
(
	[PlaceId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[Place]  WITH CHECK ADD  CONSTRAINT [FK_Place_City] FOREIGN KEY([CityId])
REFERENCES [dbo].[City] ([CityId])
GO

ALTER TABLE [dbo].[Place] CHECK CONSTRAINT [FK_Place_City]
GO


-- Create Photo Table and Relationships
CREATE TABLE [dbo].[Photo](
	[PhotoId] [int] IDENTITY(1,1) NOT NULL,
	[Image] [image] NOT NULL,
	[PlaceId] [int] NULL,
	[UserId] [int] NULL,
	[UploadDate] [date] NULL,
 CONSTRAINT [PK_Photo] PRIMARY KEY CLUSTERED 
(
	[PhotoId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

ALTER TABLE [dbo].[Photo]  WITH CHECK ADD  CONSTRAINT [FK_Photo_Place] FOREIGN KEY([PlaceId])
REFERENCES [dbo].[Place] ([PlaceId])
GO

ALTER TABLE [dbo].[Photo] CHECK CONSTRAINT [FK_Photo_Place]
GO

ALTER TABLE [dbo].[Photo]  WITH CHECK ADD  CONSTRAINT [FK_Photo_User] FOREIGN KEY([UserId])
REFERENCES [dbo].[User] ([UserId])
GO

ALTER TABLE [dbo].[Photo] CHECK CONSTRAINT [FK_Photo_User]
GO

-- Create Review Table and Relationships
CREATE TABLE [dbo].[Review](
	[ReviewId] [int] IDENTITY(1,1) NOT NULL,
	[Rating] [float] NULL,
	[Comment] [varchar](100) NOT NULL,
	[ReviewDate] [date] NOT NULL,
	[UserId] [int] NOT NULL,
	[SegmentId] [int] NULL,
	[PlaceId] [int] NULL,
	[SectorId] [int] NULL,
 CONSTRAINT [PK_Review1] PRIMARY KEY CLUSTERED 
(
	[ReviewId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[Review]  WITH CHECK ADD  CONSTRAINT [FK_Review_Place] FOREIGN KEY([PlaceId])
REFERENCES [dbo].[Place] ([PlaceId])
GO

ALTER TABLE [dbo].[Review] CHECK CONSTRAINT [FK_Review_Place]
GO

ALTER TABLE [dbo].[Review]  WITH CHECK ADD  CONSTRAINT [FK_Review_Sector] FOREIGN KEY([SectorId])
REFERENCES [dbo].[Sector] ([SectorId])
GO

ALTER TABLE [dbo].[Review] CHECK CONSTRAINT [FK_Review_Sector]
GO

ALTER TABLE [dbo].[Review]  WITH CHECK ADD  CONSTRAINT [FK_Review_User] FOREIGN KEY([UserId])
REFERENCES [dbo].[User] ([UserId])
GO

ALTER TABLE [dbo].[Review] CHECK CONSTRAINT [FK_Review_User]
GO

SET ANSI_PADDING OFF
GO

