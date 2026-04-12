# API Tests

Automated REST API test suite for the [Petstore Swagger API](https://petstore.swagger.io/v2) using REST Assured, TestNG, and ExtentReports.

## Tech Stack

| Tool | Purpose |
|---|---|
| REST Assured | HTTP client & assertion DSL |
| TestNG | Test runner & grouping |
| Jackson | JSON serialization |
| Lombok | Boilerplate reduction (models) |
| JavaFaker | Random test data generation |
| ExtentReports | HTML test reporting |
| Log4j 2 + SLF4J | Logging |

## Project Structure

```
api-tests/
├── src/
│   ├── main/java/framework/api/
│   │   ├── config/          # Base URI & endpoint constants
│   │   ├── model/           # POJO models (Pet, Category, Tag)
│   │   ├── request/         # Request layer (PetRequests)
│   │   └── datafactory/     # Random test data builders
│   └── test/
│       ├── java/framework/api/
│       │   ├── tests/       # Test classes & TestBase
│       │   ├── listeners/   # TestNG listener (ApiTestListener)
│       │   └── report/      # ExtentReport configuration
│       └── resources/
│           └── log4j2.xml
├── testng.xml
└── pom.xml
```

## Test Coverage

### Positive Tests

| Test | Endpoint | Description |
|---|---|---|
| `createPet` | `POST /pet` | Create a new pet and verify response |
| `getPetById` | `GET /pet/{petId}` | Retrieve a pet by ID after creation |
| `updatePet` | `PUT /pet` | Update pet status and verify via GET |
| `deletePet` | `DELETE /pet/{petId}` | Delete a pet and verify 404 on GET |
| `uploadPetImage` | `POST /pet/{petId}/uploadImage` | Upload an image for an existing pet |
| `findByStatusAvailable` | `GET /pet/findByStatus` | Filter pets by status=available |
| `findByStatusPending` | `GET /pet/findByStatus` | Filter pets by status=pending |
| `findByStatusSold` | `GET /pet/findByStatus` | Filter pets by status=sold |

### Negative Tests

| Test | Endpoint | Description |
|---|---|---|
| `getNonExistentPet` | `GET /pet/{petId}` | Returns 404 for non-existent ID |
| `getWithInvalidIdFormat` | `GET /pet/{petId}` | Returns 400/404 for invalid ID format |
| `getWithNegativeId` | `GET /pet/{petId}` | Returns 400/404 for negative ID |
| `deleteNonExistentPet` | `DELETE /pet/{petId}` | Returns 404 for non-existent pet |
| `uploadPetImageNonExistentPet` | `POST /pet/{petId}/uploadImage` | Returns 404 for non-existent pet |
| `findByInvalidStatus` | `GET /pet/findByStatus` | Returns empty list for invalid status |

## Prerequisites

- **Java 11+**
- **Maven 3.6+**

## Running Tests

From the project root:

```bash
# Run all tests
mvn test -pl api-tests

# Run only positive tests
mvn test -pl api-tests -Dgroups=positive

# Run only negative tests
mvn test -pl api-tests -Dgroups=negative
```

## Reports

After execution, the ExtentReport HTML file is generated under `api-tests/reports/`. Open it in a browser to view detailed results with pass/fail/skip status per test.
