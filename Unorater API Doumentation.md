
# UNORATER BACKEND API DOCUMENTATION
This document provides detailed information about various API requests and responses available to the Unorater application backend. The requests use **Create Read Update Delete** operations synonymous to the **POST GET PUT DELETE** HTTP commands along with requests data in **JavaScript Object Notaion (JSON)** to send and recieve data to and from the backend server. 

Since most JSON elements are nested (objects inside objects, arrays inside arrays, arrays inside objects e.t.c) , elements nested inside another element are either indented in the table - if the objects are used only once, or linked to a table for the object type- if the object is reused (the latter option was used in this documentation). Also, note that the element names are case sensitive, something to keep in mind when making the API calls. To understand the JSON syntax, read more [here](https://www.tutorialspoint.com/json/json_syntax.htm).

The information about all endpoints and the HTTP methods they make use of are documented along with a table showing the details of the JSON format request/response body. The backend makes use of JSON Web Tokens([JWT](https://tools.ietf.org/html/rfc7519)) to authenticate users. The backend is engineered to categorize users of the system three into access levels: regular users, department administrators, and system administrators. Resources are restricted to users based on their "access level" or "roles". All resources except the authentication resource requires a JWT along with the right role to access. 

The columns of the tables:

**JSON RESPONSES**
1. *Element*: the key of the key/value pair
2. *Description*: a sentence fragment, usually a descriptive noun that describes the value
3. *Type*: number, string, boolean, array, or object
4. *Notes*: addtional information

**JSON REQUESTS**
The request table is the same as response table but includes an additional column  called *Required* that indicates whether you need to include the element. The field will be filled with either "Required" if it must be included in the request, "Optional" if it can be ignored, and "Not Needed" if the field's value is automatically generated hence should not be included at all. 
  

# API REQUESTS

<h2 id="auth-api">Authentication API</h2>

**Resource Name:** Authentication\
**Resource Description:** Provides API endpoints for login (includes regular users, system admins, and department admins) and signup for all users.\
**Resource Access:** Open to all

1. ***Endpoint Name:*** User Sign-up\
	***Endpoint Description:*** Signs up a user with REGULAR_USER role into the system.\
	***Endpoint Request Method:*** POST\
	***Endpoint Full URL Path:*** *localhost:8080/api/auth/signup*\
	***Endpoint Request Table:*** 
	<h3 id="signup-table">SIGN-UP REQUEST DATA OBJECT TABLE</h3>
	
	| Element|Description|Type|Required|Notes|
	|-----------|-------------|------|---------|--------|
	| userName| Username  of the user attempting to sign-up|String|Required|
	| email| Email of the user attempting to sign-up|String|Required|
	| password| Password of the user attempting to sign-up|String|Required|
	***Endpoint Request Sample  in JSON:***
	```json
	{
	  "userName": "John",
	  "email": "johndoe@gmail.com",
	  "password": "Doe1821731!@"
	}
	```
	***Endpoint Response Sample in JSON:*** **200 OK**
	```json
	{
	  "success":  false,
      "message":  "Email Address already in use!"
	}
	```
	<br>
2. ***Endpoint Name:*** Sign in\
	***Endpoint Description:*** Signs a user with REGULAR_USER role into the system. This endpoint will return an access token in the response body that will be used to identify the user.\
	***Endpoint Request Method:*** POST\
	***Endpoint Full URL Path:*** *localhost:8080/api/auth/signin*\
	***Endpoint Request Body Table:***


	<h3 id="signin-table">LOGIN REQUEST DATA OBJECT TABLE</h3>

	| Element|Description|Type|Required|Notes|
	|-----------|-------------|------|---------|--------|
	| userNameOrEmail| Username or Email of the user attempting to sign-in|String|Required|
	| password| Password of the user attempting to sign-in|String|Required|
	***Endpoint Request Sample  in JSON:***
	```json
	{
	  "userNameOrEmail": "John",
	  "password": "Doe1821731!@"
	}
	```
	***Endpoint Response Sample in JSON:*** **200 OK**
	```json
	{
	  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTkyNDcwNzE5LCJleHAiOjE1OTMwNzU1MTl9.6eYT244DB1313113WH8HXxR_opfxqnkddbVmkY-18-51h01RUOa-LbrRIJHZEesniU9q7tsID7VbpvhZmqBVDm-g",
	  "tokenType":  "Bearer"
   }
	```
	<br>
	
3.  ***Endpoint Name:*** System Administrator Sign-in\
	***Endpoint Description:*** Signs a user with SYSTEM_ADMIN role into the system.\
	***Endpoint Request Method:*** POST\
	***Endpoint Full URL Path:*** *localhost:8080/api/auth/adminsignin*\
	***Endpoint Request Table:***
	<a href="#signin-table">Login-In Request Data Object</a>
	<br>
	
4. ***Endpoint Name:*** Department Adminstrator Sign-in\
	***Endpoint Description:*** Signs a user with DEPARTMENT_ADMIN role into the system. This endpoint will return an access token in the response body that will be used to identify the user.\
	***Endpoint Request Method:*** POST\
	***Endpoint Full URL Path:*** *localhost:8080/api/auth/departmentadminsignin*\
	***Endpoint Request Table:***
	<a href="#signin-table">Login-In Request Data Object</a>
	<br>
	<br>
<h2 id="depadmin-api">Department Admin API</h2>

**Resource Name:** Department Adminstrator\
**Resource Description:** Provides API endpoints available to a Department Admin Users. A necessary precondition is that department admins have been assigned their respective department number before this resource is accessed. \
**Resource Access:** ROLE_DEPARTMENT_ADMIN only

1. ***Endpoint Name:*** Add Service
	***Endpoint Description:*** Adds a Service to the deaprtment
	***Endpoint Request Method:*** POST
	***Endpoint Full URL Path:*** *localhost:8080/api/departmentadmin/addservice*
	***Endpoint Body Request Table:***
	<h3 id="service-table">SERVICE DATA OBJECT TABLE</h3>
	
	| Element|Description|Type|Required|Notes|
	|-----------|-------------|------|---------|--------|
	| serviceID| ID of the service |String|Not Required|
	| serviceName| Name of the service being added|String|Required|
	| serviceDescription| Summary of the Service|String|Required|
	| reviews| Reviews associated with the service|<a href="#review-table"> Review</a>|Not Needed|
	| datedCreated| Date the Service was created|String|Not Needed|
	| publicService| Indicates if the service is public facing or not|boolean|Required|

	***Endpoint Request Sample  in JSON:***
	```json
	{
	  "serviceName"  :  "Dummy Service",
	  "serviceDescription"  :  "This is an API test",
	  "publicService" : true
	}
	```
	***Endpoint Response Sample in JSON:*** **200 OK**
	```json
	{
	  "success":  true,
      "message":  "Service Added Successfully"
	}
	```
	<br>
2. ***Endpoint Name:*** View Services\
	***Endpoint Description:*** Returns a Department data object with a list of all it's services\
	***Endpoint Request Method:*** GET\
	***Endpoint Full URL Path:*** *localhost:8080/api/departmentadmin/services*\
	***Endpoint Request Table:***
	<h3 id="department-table">DEPARTMENT DATA OBJECT TABLE</h3>
	
	| Element|Description|Type|Required|Notes|
	|-----------|-------------|------|---------|--------|
	| departmentID| ID of the department|String|Not Needed|
	| departmentName| Name of the department|String|Required|
	| services| List of all services available in the department|<a href="#service-table">Service</a>|Not Needed|
	***Endpoint Request  Body Sample  in JSON:*** N/A
	***Endpoint Response Sample in JSON:***
	```json
	{
	  {
		"departmentID":  1,
		"departmentName":  "Department of Computer Science and Cybersecurity (CSC)",
		"services":  [
			{
				"serviceID":  1,
				"departmentID":  1,
				"serviceName":  "ICS 499-02 Software Engineering and Capstone Project (SUMMER 2020)",
				"serviceDescription":  "This course focuses on the theory and practice of effectively and efficiently building software systems that satisfy the requirements placed upon them by customers. This course gives an overview of the software lifecycle and introduces various process models used to develop software.",
				"reviews":  [
					{
						"reviewID":  1,
						"userID":  1,
						"serviceID":  1,
						"serviceName":  "ICS 499-02 Software Engineering and Capstone Project (SUMMER 2020)",
						"reviewText":  "This course was very informative and interesting. The professor, Prof. Ismail was very professional",
						"dateCreated":  "2020-06-12T05:00:00.000+00:00",
						"dateModified":  null,
					}
				],
				"dateCreated":  "2020-06-12T05:00:00.000+00:00"
			},
			{
				"serviceID":  8,
				"departmentID":  1,
				"serviceName":  "Dummy Service",
				"serviceDescription":  "This is an API test",
				"reviews":  [],
				"dateCreated":  "2020-06-18T10:13:59.500+00:00"
			}
		]
	}
	```

<br>


3. ***Endpoint Name:*** Update Service.\
	***Endpoint Description:*** Updates the service name and description\
	***Endpoint Request Method:*** PUT\
	***Endpoint Full URL Path:*** *localhost:8080/api/departmentadmin/update*\
	***Endpoint Request Table:*** <a href="#service-table">Service Data Object</a> (*serviceID* required for call)

	***Endpoint Request  Body Sample  in JSON:*** 
	```json
	{
	  "serviceID"    :  8,
	  "serviceName"  :  "Dummy Service Updated",
	  "serviceDescription" : "This is an API test Updated"
	}
	```
	***Endpoint Response Sample in JSON:*** ***200 OK***
	```json
	{
	  "success":  true,
	  "message":  "Service 8 successfully updated"
	}
	```
	<br>
4.  ***Endpoint Name:*** Delete Service\
	***Endpoint Description:*** Deletes a service and reviews associted with in from the system.\
	***Endpoint Request Method:*** DELETE\
	***Endpoint Full URL Path:*** *localhost:8080/api/departmentadmin/delete/{serviceID}*\
	***Endpoint Request Body Table:*** N/A\
	***Endpoint Request  Body Sample  in JSON:*** N/A\
	***Endpoint Response Sample in JSON:*** **200 OK**
	```json
	{
	  "success":  true,
	  "message":  "Service 8 successfully deleted"
	}
	```
5. ***Endpoint Name:*** Search Services\
	***Endpoint Description:*** Searches for services that match the name specified.\
	  ***Endpoint Request Method:*** GET\
	***Endpoint Full URL Path:*** *localhost:8080/api/departmentadmin/search/{serviceName}*\
    ***Endpoint Request Body Table:*** N/A \
***Endpoint Request  Body Sample  in JSON:*** N/A \
***Endpoint Response Sample:*** **200 OK**
 A List of <a href="#service-table">Service Data Object</a>
	<br>
	<br>
<h2 id="depadmin-api">Regular User API</h2>

**Resource Name:** Regular User \
**Resource Description:** Provides API endpoints available to a Regular User\
**Resource Access:** ROLE_REGULAR_USER (Default role for all users)

1. ***Endpoint Name:*** User Summary\
	***Endpoint Description:*** Gets the currently authenticated user summary\
	***Endpoint Request Method:*** GET\
	***Endpoint Full URL Path:*** *localhost:8080/api/user/me*\
	***Endpoint Request Body Table:*** N.A\
	***Endpoint Response Body Table:***
	<h3 id="usersummary-table">USER SUMMARY DATA OBJECT TABLE</h3>
	
	| Element|Description|Type|Required|Notes|
	|-----------|-------------|------|---------|--------|
	| id| ID of the User|String|Not Required|
	| userName| Name of the user|String|Not Required|
	| email| Email of the user|String|Not Required|

	
	***Endpoint Request  Body Sample  in JSON:*** N/A
	
	
	***Endpoint Response Sample in JSON:*** **200 OK**
	```json
	{
	  "id":  1,
	  "userName":  "John",
	  "email":  "john.doe@my.metrostate.edu"
	}
	```
	
	<br>
	
2. ***Endpoint Name:*** View Public Services\
	***Endpoint Description:*** Gets all the public facing services available for a user to review. The reviews attached to these services can be used to populate the reviews section of the frontend.\
	***Endpoint Request Method:*** GET\
	***Endpoint Full URL Path:*** *localhost:8080/api/user/viewpublicservices*\
	***Endpoint Request Body Table:*** N/A
	***Endpoint Response Body Table:*** 
	<a href="#service-table">Service Data Object</a>
	***Endpoint Request  Body Sample  in JSON:*** N/A\
	***Endpoint Response Sample in JSON:*** **200 OK** \
		See *Service* Objects List  in JSON Response : 	<a href="#department-table">Department Data Object</a>
	<br>
	
3. ***Endpoint Name:*** View All Services\
	***Endpoint Description:*** Gets all the services available for a user to review. The reviews attached to these services should not be used to populate the reviews section of the frontend, use the call above instead.\
	***Endpoint Request Method:*** GET\
	***Endpoint Full URL Path:*** *localhost:8080/api/user/viewallservices*\
	***Endpoint Request Body Table:*** N/A
	***Endpoint Response Body Table:*** 
	<a href="#service-table">Service Data Object</a>
	***Endpoint Request  Body Sample  in JSON:*** N/A\
	***Endpoint Response Sample in JSON:*** **200 OK** \
		See *Service* Objects List  in JSON Response : 	<a href="#department-table">Department Data Object</a>
	<br>

4. ***Endpoint Name:*** Check Username Availablilty\
	***Endpoint Description:*** Returns true if a username is avaliable.\
	***Endpoint Request Method:*** GET\
	***Endpoint Full URL Path:*** *localhost:8080/api/user/checkUsernameAvailability?username={name}*\
	***Endpoint Request Body Table:*** N/A
	***Endpoint Response Body Table:*** 
	<h3 id="useridava-table">UserIdentityAvailability Data Object Table </h3>
	
	| Element|Description|Type|Required|Notes|
	|-----------|-------------|------|---------|--------|
	| available | Indicates if the given username/email is available or not|boolean|Not Required|

	***Endpoint Request  Body Sample  in JSON:*** N/A
	***Endpoint Response Sample in JSON:*** **200 OK**
	```json
	{
	  "availabe": true
	}
	```
	<br>
	
5. ***Endpoint Name:*** Check Email Availablilty\
	   ***Endpoint Description:*** Returns true if an email is avaliable.\
	***Endpoint Request Method:*** GET\
	***Endpoint Full URL Path:*** *localhost:8080/api/user/checkEmailAvailability?email={email_address}*\
    ***Endpoint Request Body Table:*** N/A
	***Endpoint Response Body Table:*** 
	<a href="#useridava-table">UserIdentity Data Object</a>
<br>
6. ***Endpoint Name:*** Get User Profile\
	   ***Endpoint Description:*** Returns the currently authenticated user's profile which includes the user id, username, number user reviews, and approved reviews .\
	***Endpoint Request Method:*** GET\
	***Endpoint Full URL Path:*** *localhost:8080/api/user/me/profile*\
    ***Endpoint Request Body Table:*** N/A
	***Endpoint Response Body Table:*** 
	<h3 id="usersummary-table">USER PROFILE DATA OBJECT TABLE</h3>

	| Element|Description|Type|Required|Notes|
	| -----------|-------------|------|---------|--------|
	| id| ID of the User|String|Not Required|
	| userName| Name of the user|String|Not Required|
	| numOfReviews| Number of reviews|String|Not Required|
	| allApprovedReviews| List of approved reviews|<a href="#review-table"> Review</a>|Not Required|

	***Endpoint Request  Body Sample  in JSON:*** N/A\
	***Endpoint Response Sample in JSON:*** **200 OK**
	```json
	{
	    "id":  1,
		"numOfReviews":  1,
		"allApprovedReviews":  [
			{
				"reviewID":  1,
				"userID":  1,
				"serviceID":  1,
				"serviceName":  "ICS 499-02 Software Engineering and Capstone Project (SUMMER 2020)",
				"reviewText":  "This course was very informative and interesting. The professor, Prof. Ismail was very professional",
				"dateCreated":  "2020-06-12T05:00:00.000+00:00",
				"dateModified":  null,
				"score": "5"
			}
		],
		"username":  "John"
	}
<br>

7. ***Endpoint Name:*** Post Review\
	***Endpoint Description:*** Posts a currently authenticated user's review.\
	  ***Endpoint Request Method:*** POST\
	***Endpoint Full URL Path:*** *localhost:8080/api/user/me/postreview*\
    ***Endpoint Request Body Table:***
	<h3 id="review-table">Review Data Object Table</h3>

	| Element|Description|Type|Required|Notes|
	| -----------|-------------|------|---------|--------|
	| reviewID| ID of the Review|String|Not Needed|
	| userID| ID of user posting review|String|Not Required|
	| serviceID| ID of service being reviewed |String|Required|
	| serviceName| Name of service being reviewed|String|Not Required|
	| reviewText| The review text|String|Required|
	| dateCreated| Date the review was created |Date|Not Required|
	| dateModified| Date the review was modified|Date|Not Required|
	| score| Rating score|String|Required|

	
    
	***Endpoint Response Body Table:*** N/A

	***Endpoint Request  Body Sample  in JSON:*** 
	```json
	{
	  "serviceID":  2,
	  "reviewText":"I really understood the course content",
	  "score": "4.5"
	}
	```
	***Endpoint Response Sample:*** **200 OK**
	```Review Created : Thu Jun 18  12:  30:  07 CDT 2020```
<br>

8. ***Endpoint Name:*** Update Review\
	***Endpoint Description:*** Updates a currently authenticated user's review.\
	  ***Endpoint Request Method:*** PUT\
	***Endpoint Full URL Path:*** *localhost:8080/api/user/me/updatereview*\
    ***Endpoint Request Body Table:***
    <a href="#review-table"> Review Data Object </a>(Required: *reviewID, reviewText*)
***Endpoint Request  Body Sample  in JSON:*** See Review Data Object Sample\
	***Endpoint Response Sample:*** **200 OK**
	```Review Modified: Thu Jun 18  12:  30:  07 CDT 2020```
<br>

9. ***Endpoint Name:*** Delete Review\
	***Endpoint Description:*** Deletes a currently authenticated user's review.\
	  ***Endpoint Request Method:*** DELETE\
	***Endpoint Full URL Path:*** *localhost:8080/api/user/me/deletereview/{reviewID}*\
    ***Endpoint Request Body Table:*** N/A \
***Endpoint Request  Body Sample  in JSON:*** N/A \
***Endpoint Response Sample:*** **200 OK**

10. ***Endpoint Name:*** Search Services\
	***Endpoint Description:*** Searches for services that match the name specified.\
	  ***Endpoint Request Method:*** GET\
	***Endpoint Full URL Path:*** *localhost:8080/api/regularuser/search/{serviceName}*\
    ***Endpoint Request Body Table:*** N/A \
***Endpoint Request  Body Sample  in JSON:*** N/A \
***Endpoint Response Sample:*** **200 OK**
 A List of <a href="#service-table">Service Data Object</a>

<br>
<br>
<h2 id="depadmin-api">System Admin API</h2>

**Resource Name:** System Administrator\
**Resource Description:** Provides API endpoints available to a System Admin User\
**Resource Access:** ROLE_SYSTEM_ADMIN

1. ***Endpoint Name:*** Grant Admin Role  \
	***Endpoint Description:*** Grants system administrator role to a specified user \
***Endpoint Request Method:*** POST \
***Endpoint Full URL Path:*** *localhost:8080/api/grantsysadminrole/{userID}*  \
***Endpoint Request Body Table:*** N/A  \
***Endpoint Response Body Table:*** N/A \
	***Endpoint Request  Body Sample  in JSON:*** N/A \
	***Endpoint Response Sample:*** **200 OK**
	```json
	{
	  "success":  true,
	  "reviewText":"Role ROLE_SYSTEM_ADMIN Successfully Assigned to User John"
	}
	```
	<br>
2. ***Endpoint Name:*** Revoke Admin Role  \
	***Endpoint Description:*** Removes system administrator role from specified user \
***Endpoint Request Method:*** POST \
***Endpoint Full URL Path:*** *localhost:8080/api/removesysadminrole/{userID}*  \
***Endpoint Request Body Table:*** N/A  \
***Endpoint Response Body Table:*** N/A \
	***Endpoint Request  Body Sample  in JSON:*** N/A \
	***Endpoint Response Sample:*** **200 OK**
	```json
	{
	  "success":  true,
	  "reviewText":"Role ROLE_SYSTEM_ADMIN Successfully Removed from User John"
	}
	```
	<br>
3. ***Endpoint Name:*** Grant Department Admin Role  \
	***Endpoint Description:*** Grants department administrator role to specified user \
***Endpoint Request Method:*** POST \
***Endpoint Full URL Path:*** *localhost:8080/api/grantdepadminrole/{userID}/{departmentID}*  \
***Endpoint Request Body Table:*** N/A  \
***Endpoint Response Body Table:*** N/A \
	***Endpoint Request  Body Sample  in JSON:*** N/A \
	***Endpoint Response Sample:*** **200 OK**
	```json
	{
	  "success": true,
	  "reviewText": "Role ROLE_DEPARTMENT_ADMIN Successfully Assigned to User John"
	}
	```
	<br>
4. ***Endpoint Name:*** Remove Department Admin Role  \
	***Endpoint Description:*** Removes department administrator role from specified user \
***Endpoint Request Method:*** POST \
***Endpoint Full URL Path:*** *localhost:8080/api/removedepadminrole/{userID}*  \
***Endpoint Request Body Table:*** N/A  \
***Endpoint Response Body Table:*** N/A\
	***Endpoint Request  Body Sample  in JSON:*** N/A\
	***Endpoint Response Sample:*** **200 OK**
	```json
	{
	  "success":  true,
	  "reviewText":"Role ROLE_DEPARTMENT_ADMIN Successfully Removed from User John"
	}
	```
	<br>
5. ***Endpoint Name:*** Get Departments  \
	***Endpoint Description:*** Retrieves a list of all departments \
	***Endpoint Request Method:*** GET\
	***Endpoint Full URL Path:*** *localhost:8080/api/systemadmin/departments* \
	***Endpoint Request Body Table:*** N/A \
	***Endpoint Response Body Table:*** 	N/A \
	***Endpoint Request  Body Sample  in JSON:*** N/A \
	***Endpoint Response Sample:*** **200 OK**
	<a href="#department-table">Department Data Object</a>
<br>

6. ***Endpoint Name:*** Create Department  \
	***Endpoint Description:*** Adds a department to the system \
***Endpoint Request Method:*** POST \
***Endpoint Full URL Path:*** *localhost:8080/api/systemadmin/createdepartment*  \
***Endpoint Request Body Table:*** <a href="#department-table">Department Data Object</a>
***Endpoint Response Body Table:*** N/A\
	***Endpoint Request  Body Sample  in JSON:*** 
	```json
	{
	  "departmentID":  1,
	  "departmentName": "Department of Social Sciences"
	}
	```
	***Endpoint Response Sample:*** **200 CREATED**
	```json
	{
	  "success":  true,
	  "reviewText": "Department Added Successfully"
	}
	```
	<br>
7.  ***Endpoint Name:*** Delete Department  \
	***Endpoint Description:*** Deletes a department along with services and reviews associated with it from the system\
***Endpoint Request Method:*** DELETE\
***Endpoint Full URL Path:*** *localhost:8080/api/systemadmin/delete/{departmentID}*  \
***Endpoint Request Body Table:*** N/A \
***Endpoint Response Body Table:*** N/A\
	***Endpoint Request  Body Sample  in JSON:*** N/A\
	***Endpoint Response Sample:*** **200 OK**
	<br>
8. 	***Endpoint Name:*** Update Department  \
	***Endpoint Description:*** Updates a department's name \
***Endpoint Request Method:*** PUT\
***Endpoint Full URL Path:*** *localhost:8080/api/systemadmin/department/rename/{departmentID}* \
***Endpoint Request Body Table:*** N/A \
***Endpoint Response Body Table:*** N/A\
	***Endpoint Request  Body Sample  in JSON:*** N/A\
***Endpoint Response Sample:*** **200 OK**
	```json
	{
	  "departmentName": "Department of Social Justices(formely Sciences)"
	}
	```
	<br>
9.  ***Endpoint Name:*** Get users\
	***Endpoint Description:*** Retrieves a list of all the users\
	***Endpoint Request Method:*** GET\
	***Endpoint Full URL Path:*** *localhost:8080/api/systemadmin/users*  \
	***Endpoint Request Body Table:***

	<h3 id="user-table">FULL USER DATA OBJECT	TABLE</h3>
		
	 | Element|Description|Type|Required|Notes|
	 | -----------|-------------|------|---------|--------|
	 | userID| ID of the User|String|Not Required|
	 | userName| Account name of the user|String| Required|
	 | email| Email of the user|String|Required|
	 | password| Password of the user|String| Required|
	 | reviews| List of all reviews|<a href="#review-table"> Review</a>|Not Required|

	
	***Endpoint Response Body Table:*** N/A\
	***Endpoint Request  Body Sample  in JSON:*** N/A\
	***Endpoint Response Sample:*** **200 OK**
	```json
	[
		{
			"userID":  4,
			"userName":  "John",
			"email":  "john.doe@yahoo.com",
			"reviews":  [
				{
					"reviewID":  6,
					"userID":  4,
					"serviceID":  2,
					"serviceName":  "Linear Algebra and Applications(SUMMER 2020)",
					"reviewText":  "This course was challenging but interesting",
					"dateCreated":  "2020-06-17T18:25:21.570+00:00",
					"dateModified":  null,
					"score": "4"

				},
				{
					"reviewID":  9,
					"userID":  4,
					"serviceID":  2,
					"serviceName":  "Linear Algebra and Applications(SUMMER 2020)",
					"reviewText":  "The course was challenging but interesting",
					"dateCreated":  "2020-06-19T02:19:35.800+00:00",
					"dateModified":  null,
					"score": "2.4"
				}
			],
				"roles":  [
					{
						"id":  2,
						"name":  "ROLE_REGULAR_USER"
					}
				],
			"dateCreated":  "2020-06-16T21:47:51.520+00:00",
			"departmentNum":  0
		}
	]
	  ```
<br>

10. ***Endpoint Name:*** Delete User\
	***Endpoint Description:*** Deletes a user from the system \
***Endpoint Request Method:*** DELETE\
***Endpoint Full URL Path:*** *localhost:8080/api/systemadmin/user/delete/{userID}*  \
***Endpoint Request Body Table:*** N/A \
***Endpoint Response Body Table:*** N/A\
	***Endpoint Request  Body Sample  in JSON:*** N/A\
	***Endpoint Response Sample:*** **200 OK**
<br>

11. ***Endpoint Name:*** Search User\
	***Endpoint Description:*** Searches for users that match the name specified. This will return a list of users matching the name parameter supplied.\
	  ***Endpoint Request Method:*** GET\
	***Endpoint Full URL Path:*** *localhost:8080/api/regularuser/search/{userName}*\
    ***Endpoint Request Body Table:*** N/A \
***Endpoint Request  Body Sample  in JSON:*** N/A \
***Endpoint Response Sample:*** **200 OK**
 A List of <a href="#user-table">Full User Data Object</a>
