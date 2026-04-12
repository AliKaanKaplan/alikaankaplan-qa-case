# QA Assignment - Ali Kaan Kaplan

Multi-module QA automation project covering UI testing, API testing, and load testing.

## Modules

| Module | Type | Target | Details |
|---|---|---|---|
| [ui-tests](ui-tests/) | Selenium + TestNG | [insiderone.com](https://insiderone.com) | End-to-end career page flow with POM framework |
| [api-tests](api-tests/) | REST Assured + TestNG | [Petstore API](https://petstore.swagger.io/v2) | CRUD operations with positive & negative coverage |
| [load-tests](load-tests/) | Apache JMeter | [n11.com](https://www.n11.com) | Search functionality smoke & data-driven tests |

## Prerequisites

- **Java 17**
- **Maven 3.6+**

## Quick Start

```bash
# UI tests (Chrome)
mvn test -pl ui-tests

# UI tests (Firefox)
mvn test -pl ui-tests -Dbrowser=firefox

# API tests
mvn test -pl api-tests

# Load tests (requires JMeter setup, see load-tests/README.md)
load-tests\jmeter\scripts\run-search-smoke.bat
```

## Project Structure

```
qa_alikaan_kaplan_case/
├── ui-tests/          # Selenium UI automation
├── api-tests/         # REST API automation
├── load-tests/        # JMeter load/performance tests
└── pom.xml            # Parent POM (Java 17, shared dependencies)
```

Each module has its own `README.md` with detailed setup, structure, and test coverage information.
