# Chubb Claims Backend

A simple Spring Boot backend for motor and property claims handling with REST endpoints for claim submission, status updates, and activity tracking.

## What is included
- Claim submission for claimants
- Claim retrieval by id and status
- Staff workflow to update claim status
- Activity timeline for follow-up notes
- Embedded H2 database by default for easy local startup
- Optional MySQL profile for local MySQL usage

## Local prerequisites
1. Install Java 17 or later.
2. Install Maven or use the local Maven distribution already included in this workspace under [maven-dist](maven-dist).
3. Optional: install MySQL 8.x locally and create a database named `chubb_claims`.

## Run with the default embedded database
From the project root:

```bash
mvn test
mvn spring-boot:run
```

The app will be available at:
- http://localhost:8080/api/claims
- H2 console: http://localhost:8080/h2-console

## Run with MySQL
1. Make sure MySQL is running locally.
2. Create a database named `chubb_claims`.
3. Update the credentials in [src/main/resources/application-mysql.properties](src/main/resources/application-mysql.properties) if needed.
4. Start the app with:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## API endpoints
### Create claim
POST /api/claims

Example JSON:
```json
{
  "claimantName": "Jane Doe",
  "claimantEmail": "jane@example.com",
  "market": "Singapore",
  "lossType": "MOTOR",
  "description": "Rear bumper damage after collision",
  "estimatedLoss": 1500
}
```

### Get all claims
GET /api/claims

### Get claim by id
GET /api/claims/{id}

### Get claims by status
GET /api/claims/status/{status}

### Update claim status
PATCH /api/claims/{id}/status

Example JSON:
```json
{
  "status": "UNDER_REVIEW",
  "actor": "claims-staff",
  "message": "Claim accepted for assessment"
}
```

### Add activity
POST /api/claims/{id}/activities

Example JSON:
```json
{
  "actor": "claimant",
  "message": "I have uploaded the repair invoice"
}
```

## Project structure
- [src/main/java/com/chubb/claims/ClaimsBackendApplication.java](src/main/java/com/chubb/claims/ClaimsBackendApplication.java) - Spring Boot entry point
- [src/main/java/com/chubb/claims/controller/ClaimController.java](src/main/java/com/chubb/claims/controller/ClaimController.java) - REST controller
- [src/main/java/com/chubb/claims/service/ClaimService.java](src/main/java/com/chubb/claims/service/ClaimService.java) - business logic
- [src/main/java/com/chubb/claims/repository](src/main/java/com/chubb/claims/repository) - JPA repositories
- [src/main/java/com/chubb/claims/entity](src/main/java/com/chubb/claims/entity) - JPA entities
- [src/main/java/com/chubb/claims/dto](src/main/java/com/chubb/claims/dto) - request and response DTOs
- [src/main/resources/application.properties](src/main/resources/application.properties) - default H2 configuration
- [src/main/resources/application-mysql.properties](src/main/resources/application-mysql.properties) - MySQL configuration
- [src/test/java/com/chubb/claims/controller/ClaimControllerTest.java](src/test/java/com/chubb/claims/controller/ClaimControllerTest.java) - basic API test
