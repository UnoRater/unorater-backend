/** Create database is one does not exist */
IF  NOT EXISTS (SELECT * FROM sys.databases WHERE name = N'unodatabase')
    BEGIN
        CREATE DATABASE [unodatabase]
    END;
GO

/** Specify database to use */
USE [unodatabase]
GO

/** Drop tables if they exist in the database */
DROP TABLE IF EXISTS dbo.[Reviews]
DROP TABLE IF EXISTS dbo.[Services]
DROP TABLE IF EXISTS dbo.[Departments]
DROP TABLE IF EXISTS dbo.[Users]

/** Create tables */
CREATE TABLE
    dbo.Departments
(
    departmentId BIGINT PRIMARY KEY NOT NULL IDENTITY(1,1),
    departmentName VARCHAR(50) NOT NULL,
)
GO

CREATE TABLE
    dbo.Users
(
    userId BIGINT PRIMARY KEY NOT NULL IDENTITY(1,1),
    userName VARCHAR(20) NOT NULL,
    passwordHash BINARY(64) NOT NULL,
    salt UNIQUEIDENTIFIER NOT NULL,
    email VARCHAR (20) NOT NULL,
    isAdmin BIT NOT NULL,
    dateCreated DATETIME NOT NULL
)
GO

CREATE TABLE
    dbo.[Services]
(
    serviceId BIGINT PRIMARY KEY NOT NULL IDENTITY(1,1),
    departmentId BIGINT NOT NULL,
    serviceName VARCHAR(50) NOT NULL,
    dateCreated DATETIME NOT NULL,
    FOREIGN KEY (departmentId) REFERENCES Departments(departmentId)
)
GO

CREATE TABLE
    dbo.Reviews
(
    reviewId BIGINT PRIMARY KEY NOT NULL IDENTITY(1,1),
    userId BIGINT NOT NULL,
    serviceId BIGINT NOT NULL,
    reviewText VARCHAR(500) NOT NULL,
    dateCreated DATETIME NOT NULL,
    dateModified DATETIME NULL,
    FOREIGN KEY (userId) REFERENCES Users(userId),
    FOREIGN KEY (serviceId) REFERENCES [Services](serviceId)
)
GO

/** Stored procedures for user authentication */
DROP PROCEDURE IF EXISTS dbo.spAddUser
GO

CREATE PROCEDURE dbo.spAddUser
    @puserName VARCHAR(20),
    @ppassword VARCHAR(50),
    @pemail VARCHAR(50),
    @pdateCreated DATETIME,
    @pisAdmin BIT,
    @responseMessage VARCHAR(250) OUTPUT
AS
BEGIN
    SET NOCOUNT ON
    DECLARE @salt UNIQUEIDENTIFIER = NEWID()
    BEGIN TRY
        INSERT INTO dbo.[Users] (userName, passwordHash, salt, email, isAdmin, dateCreated)
        VALUES(@puserName, HASHBYTES('SHA2_512', @ppassword+CAST(@salt AS VARCHAR(36))), @salt, @pemail, @pisAdmin, @pdateCreated)
        SET @responseMessage='Success'
    END TRY
    BEGIN CATCH
        SET @responseMessage=ERROR_MESSAGE()
    END CATCH
END
GO

DROP PROCEDURE IF EXISTS dbo.spLogin
GO
CREATE PROCEDURE dbo.spLogin
    @puserName VARCHAR(20),
    @ppassword VARCHAR(50),
    @responseMessage VARCHAR(250)='' OUTPUT
AS
BEGIN
    SET NOCOUNT ON
    DECLARE @userId INT
    IF EXISTS (SELECT TOP 1 userId FROM [dbo].[Users] WHERE user=@puserName)
        BEGIN
            SET @userID=(SELECT userId FROM [dbo].[Users] WHERE userName=@puserName AND passwordHash=HASHBYTES('SHA2_512', @ppassword+CAST(Salt AS VARCHAR(36))))

            IF(@userID IS NULL)
                SET @responseMessage='Incorrect password'
            ELSE
                SET @responseMessage='User successfully logged in'
        END
    ELSE
        SET @responseMessage='Invalid login'
END
GO

DROP PROCEDURE IF EXISTS dbo.spAdminLogin
GO
CREATE PROCEDURE dbo.spAdminLogin
    @puserName VARCHAR(20),
    @ppassword VARCHAR(50),
    @responseMessage VARCHAR(250)='' OUTPUT
AS
BEGIN
    SET NOCOUNT ON
    DECLARE @userId INT
    IF EXISTS (SELECT TOP 1 userId FROM [dbo].[Users] WHERE user=@puserName)
        BEGIN
            SET @userID=(SELECT userId FROM [dbo].[Users] WHERE userName=@puserName AND passwordHash=HASHBYTES('SHA2_512', @ppassword+CAST(Salt AS VARCHAR(36))) AND isAdmin = 1)

            IF(@userID IS NULL)
                SET @responseMessage='Incorrect password'
            ELSE
                SET @responseMessage='User successfully logged in'
        END
    ELSE
        SET @responseMessage='Invalid login'
END




