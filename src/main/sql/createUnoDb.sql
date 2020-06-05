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
DROP TABLE IF EXISTS dbo.[UsersRoles]
DROP TABLE IF EXISTS dbo.[Roles]
DROP TABLE IF EXISTS dbo.[Users]

/** Create tables */
CREATE TABLE
    dbo.Departments
(
    departmentId BIGINT PRIMARY KEY NOT NULL IDENTITY(1,1),
    departmentName VARCHAR(50) NOT NULL
)
GO

CREATE TABLE
    dbo.Roles
(
    roleId BIGINT PRIMARY KEY NOT NULL IDENTITY(1,1),
    roleName VARCHAR(20)
)
GO

CREATE TABLE
    dbo.Users
(
    userId BIGINT PRIMARY KEY NOT NULL IDENTITY(1,1),
    userName VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR (255) NOT NULL,
    isAdmin BIT NOT NULL,
    dateCreated DATETIME NOT NULL,
    UNIQUE (email)
)
GO

CREATE TABLE
    dbo.UsersRoles
(
    userId BIGINT NOT NULL,
    roleId BIGINT NOT NULL,
    FOREIGN KEY (userId) REFERENCES Users(userId),
    FOREIGN KEY (roleId) REFERENCES Roles(roleId)
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



