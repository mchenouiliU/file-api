# Read Me First


# Getting Started

To start the project, first build it
1. ./gradlew build
2. docker build -t chencorp/file-api .
3. docker-compose up

### Reference Documentation
1. Create a file in the api  
PUT http://localhost:8080/file?name=test.txt&category=test  
Body: place the binary/text data  
  

2. to upload a new version  
POST http://localhost:8080/file/1  
Body: place the binary/text data
  

3. Read a file metadata & the version available
GET http://localhost:8080/file/1
  

4. get the file in a browser  
GET http://localhost:8080/file-data/1 
  

5. Search by Name & category  
GET http://localhost:8080/files?name=<fileName>&category=<category> to search for files
  

6. Delete the file and all its version  
DELETE http://localhost:8080/file/1

### Whats was done

- CRUD for File API
- Versioning for file 
- Exception Handler
- Flyway for database migration
- Auditable with security user (altough security not implemented)
- data is stored in database (could be in filesystem)


### Nice to have

- Hateoas to be added (easier for resource management)
- Security to added with Oauth2 (my preference)
- much more complex model needed for production 
  - who has access to this file RBAC 
  - add hashing to avoid same version added twice for same file
  - logical delete
- UI to visualize the API with html form
- Audit table to follow up on who has done what and when
- ... 