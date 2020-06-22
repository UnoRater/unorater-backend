/** Create database if one does not exist */
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
    departmentid BIGINT PRIMARY KEY NOT NULL IDENTITY(1,1),
    departmentname VARCHAR(800) NOT NULL
)
GO

CREATE TABLE
    dbo.Roles
(
    roleid BIGINT PRIMARY KEY NOT NULL IDENTITY(1,1),
    rolename VARCHAR(50)
)
GO

CREATE TABLE
    dbo.Users
(
    userid BIGINT PRIMARY KEY NOT NULL IDENTITY(1,1),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR (255) NOT NULL,
    departmentno BIGINT NULL,
    datecreated DATETIME NOT NULL,
    UNIQUE (email)
)
GO

CREATE TABLE
    dbo.UsersRoles
(
    userid BIGINT NOT NULL,
    roleid BIGINT NOT NULL,
    FOREIGN KEY (userid) REFERENCES Users(userid),
    FOREIGN KEY (roleid) REFERENCES Roles(roleid)
)
GO

CREATE TABLE
    dbo.[Services]
(
    serviceid BIGINT PRIMARY KEY NOT NULL IDENTITY(1,1),
    departmentid BIGINT NOT NULL,
    servicename VARCHAR(800) NOT NULL,
    servicedescription VARCHAR(800) NULL,
    datecreated DATETIME NOT NULL,
    publicservice BIT NOT NULL,
    FOREIGN KEY (departmentid) REFERENCES Departments(departmentid)
)
GO

CREATE TABLE
    dbo.Reviews
(
    reviewid BIGINT PRIMARY KEY NOT NULL IDENTITY(1,1),
    userid BIGINT NOT NULL,
    serviceid BIGINT NOT NULL,
    reviewtext VARCHAR(500) NOT NULL,
    datecreated DATETIME NOT NULL,
    datemodified DATETIME NULL,
    FOREIGN KEY (userid) REFERENCES Users(userid),
    FOREIGN KEY (serviceid) REFERENCES [Services](serviceid)
)
GO

SET IDENTITY_INSERT dbo.Users ON
GO
INSERT INTO [dbo].[Users]
           ([userid]
           ,[username]
           ,[password]
           ,[email]
           ,[datecreated])
     VALUES
           (1
           ,N'Seyi'
           ,N'$2a$10$Ys3mpRhR.z.u1SfVAHqiNOfxi.KzgyoNXU6D2mijuKRQBaK3P7D2u'
           ,N'oluwaseyi.ola@my.metrostate.edu'
           ,CAST(N'2020-06-12' AS DATETIME))
GO

INSERT INTO [dbo].[Users]
        ([userid]
        ,[username]
        ,[password]
        ,[email]
        ,[datecreated]
        ,[departmentno])
    VALUES
        (3
        ,N'Department1_Admin'
        ,N'$2a$10$Ys3mpRhR.z.u1SfVAHqiNOfxi.KzgyoNXU6D2mijuKRQBaK3P7D2u'
        ,N'depart1Admin@gmail.com'
        ,CAST(N'2020-06-13' AS DATETIME)
        ,1)
GO

SET IDENTITY_INSERT dbo.Users OFF
GO

SET IDENTITY_INSERT dbo.Roles ON
GO
INSERT INTO [dbo].[Roles] ([roleid], [rolename]) VALUES (1, N'ROLE_SYSTEM_ADMIN')
GO
INSERT INTO [dbo].[Roles] ([roleid], [rolename]) VALUES (2, N'ROLE_REGULAR_USER')
GO
INSERT INTO [dbo].[Roles] ([roleid], [rolename]) VALUES (3, N'ROLE_DEPARTMENT_ADMIN')
GO
SET IDENTITY_INSERT dbo.Roles OFF
GO


INSERT INTO [dbo].[UsersRoles] ([userid] ,[roleid]) VALUES (1, 1)
GO
INSERT INTO [dbo].[UsersRoles] ([userid] ,[roleid]) VALUES (1, 2)
GO
INSERT INTO [dbo].[UsersRoles] ([userid] ,[roleid]) VALUES (1, 3)
GO
INSERT INTO [dbo].[UsersRoles] ([userid] ,[roleid]) VALUES (3, 3)
GO

SET IDENTITY_INSERT [dbo].[Departments] ON
GO
INSERT INTO [dbo].[Departments] ([departmentid], [departmentname]) VALUES (1, N'Department of Computer Science and Cybersecurity (CSC)')
GO
INSERT INTO [dbo].[Departments] ([departmentid], [departmentname]) VALUES (2, N'Department of Mathematics & Statistics')
GO

SET IDENTITY_INSERT [dbo].[Departments] OFF
GO

SET IDENTITY_INSERT dbo.Services ON
GO
INSERT INTO [dbo].[Services]
            ([serviceid]
            ,[departmentid]
            ,[servicename]
            ,[servicedescription]
            ,[datecreated]
            ,[publicservice])
        VALUES
            (1
            ,1
            ,N'ICS 499-02 Software Engineering and Capstone Project (SUMMER 2020)'
            ,N'This course focuses on the theory and practice of effectively and efficiently building ' +
             N'software systems that satisfy the requirements placed upon them by customers. This course gives an ' +
             N'overview of the software lifecycle and introduces various process models used to develop software.'
            ,CAST(N'2020-06-12' AS DATETIME)
            ,1)
GO

INSERT INTO [dbo].[Services]
        ([serviceid]
        ,[departmentid]
        ,[servicename]
        ,[servicedescription]
        ,[datecreated]
        ,[publicservice])
    VALUES
        (2
        ,2
        ,N'Linear Algebra and Applications(SUMMER 2020)'
        ,'The need to solve systems of linear equations frequently arises in mathematics, ' +
         'the physical sciences, engineering and economics. In this course we study these ' +
         'systems from an algebraic and geometric viewpoint. Topics include systems of linear equations, ' +
         'matrix algebra, Euclidean vector spaces, linear transformations, linear independence, dimension, ' +
         'eigenvalues and eigenvectors.'
        ,CAST(N'2020-06-14' AS DATETIME)
        ,1)
GO

SET IDENTITY_INSERT dbo.Services OFF
GO

SET IDENTITY_INSERT dbo.Reviews ON
GO
INSERT INTO [dbo].[Reviews]
            ([reviewid]
            ,[userid]
            ,[serviceid]
            ,[reviewtext]
            ,[datecreated]
            ,[datemodified])
        VALUES
            (1
            ,1
            ,1
            ,N'This course was very informative and interesting. The professor, Prof. Ismail was very professional'
            ,CAST(N'2020-06-12' AS DATETIME)
            ,NULL)
GO
INSERT INTO [dbo].[Reviews]
            ([reviewid]
            ,[userid]
            ,[serviceid]
            ,[reviewtext]
            ,[datecreated]
            ,[datemodified])
        VALUES
        (2
        ,1
        ,2
        ,N'This course was a bit challenging but hey, its maths'
        ,CAST(N'2020-06-12' AS DATETIME)
        ,NULL)
GO
SET IDENTITY_INSERT dbo.Reviews OFF
GO












