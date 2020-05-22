# Unorater-backend
* Spring boot application for Unorater backend

# Clone this repository (Windows OS)
* Download and install git here: https://gitforwindows.org/
* Open git bash
* Clone repo into preferred location

# Create application.properties file
* In your IDE, navigate to the resources folder within the unorater-backend folder: src/main/resources
* Create a file named "application.properties" in the resources folder
* Copy the contents of the application.properties.template into the file
* Assign secret values supplied in the "secrets" channel on discord to the required variables

# Test the Spring Boot Hello Controller endpoint
* Run your spring boot application
* On your web browser, go to http://localhost:8080/hello/[your_name_here]

# PULL REQUESTS
When making a PR please:
* Checkout a new branch like so: checkout -b DEV_#[issue_number_here]
  (Issue number is the number attached to the task assigned to you,
  this is found in the project section). See also below about linking your PR with issue numbers

# COMMITING CODE WITH LINK TO ISSUE NUMBER
* Please read about linking issue numbers with your PR here: https://help.github.com/en/github/managing-your-work-on-github/linking-a-pull-request-to-an-issue

# SQL Server Management Studio download and installation (Windows OS)
* Download and install SQL Server Management Studio here: https://docs.microsoft.com/en-us/sql/ssms/download-sql-server-management-studio-ssms?view=sql-server-ver15

# Instructions for installing MSSQL Server (Windows OS)
*	Go to https://www.microsoft.com/en-us/sql-server/sql-server-downloads
*	Scroll down, download 2019 Express Edition, and Install (choose basic option for installation)

# Setting up connection to your local database engine on SQL Server Management Studio (Windows OS)
*	In the Object Explorer, click on *Connect*
*	Select *Database Engine*, select the drop-down on *Server name* field
* Select *Browse for more*
* Select the *Local Servers* tab
* Expand *Database Engine* in the pane
* Click on your local mssql database server (see above for setup)
*	For the Authentication field, select *Windows Authentication*
*	Click *Connect*

# Setting up a local SQL Server with Azure Data Studio (Mac OS)
* Follow this article: https://www.freecodecamp.org/news/cjn-how-to-connect-your-microsoft-sql-server-docker-container-with-azure-data-studio/

# Create and populate the local database in your local database engine (Windows OS & Mac OS)
* Right on the database engine and select *New Query* on respective GUIs(MSSMS & Azure Data Studio)
* Copy the contents(query script) of the "createUnoDb" file located in src\main\sql folder of the 
  unorater-backend into the editor, Click on *Execute* (*Run* for Azure Data Studio) to create and populate the database

# IntelliJ Ultimate Edition for students link
* https://www.jetbrains.com/shop/eform/students
