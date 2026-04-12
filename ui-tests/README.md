# UI Tests

Selenium-based end-to-end test suite that validates the Insider careers page flow: from the home page through QA job listings on Lever to the application form.

## Tech Stack

| Tool | Purpose |
|---|---|
| Selenium 4 | Browser automation |
| TestNG | Test runner |
| WebDriverManager | Automatic driver binary management |
| ExtentReports | HTML test reporting with screenshots |
| Log4j 2 + SLF4J | Logging |

## Test Scenario

| Step | Action | Validation |
|---|---|---|
| 1 | Visit insiderone.com | All main sections are visible (Hero, Social Proof, Capabilities, etc.) |
| 2 | Navigate to Careers > Open Roles | "Explore open roles" section loads |
| 3 | Click "See all teams" | All team categories are listed including Quality Assurance |
| 4 | Click QA open positions | Redirects to Lever job board |
| 5 | Validate job listings page | Insider logo, filter bar, and "Quality Assurance" group title visible |
| 6 | Filter by Istanbul, Turkiye | All postings have QA-related titles and correct location |
| 7 | Click first posting > Apply | Lever application form page loads |

## Project Structure

```
ui-tests/
├── src/
│   ├── main/java/framework/ui/
│   │   ├── base/              # DriverFactory (Chrome & Firefox, ThreadLocal)
│   │   ├── config/            # ConfigReader, SiteUrls
│   │   ├── managers/          # PageObjectManager (lazy init)
│   │   ├── pages/
│   │   │   ├── base/          # BasePage (waits, clicks, scrolls, assertions)
│   │   │   ├── home/          # HomeLocator, HomeValidation
│   │   │   ├── careers/       # CareersLocator, CareersAction, CareersValidation
│   │   │   ├── lever/         # LeverLocator, LeverAction, LeverValidation
│   │   │   └── common/        # CommonLocator, CommonAction (cookie banner)
│   │   └── utils/             # ScreenshotUtil
│   └── test/java/framework/ui/
│       ├── tests/             # AssignmentTest
│       └── testcomponents/    # BaseTest, TestListener, ExtentReportManager
├── testng.xml
└── pom.xml
```

## Framework Design

- **Page Object Model** with Action / Validation / Locator separation per page
- **PageObjectManager** provides lazy-initialized access to all page objects
- **BaseTest** handles driver lifecycle, cookie banner dismissal, and page manager setup
- **TestListener** captures screenshots on failure (file + ExtentReport Base64 embed)
- **DriverFactory** supports Chrome and Firefox via `ThreadLocal` for parallel safety

## Prerequisites

- **Java 17**
- **Maven 3.6+**
- Chrome or Firefox browser installed

## Running Tests

From the project root:

```bash
# Run with Chrome (default)
mvn test -pl ui-tests

# Run with Firefox
mvn test -pl ui-tests -Dbrowser=firefox
```

## Reports

- **ExtentReport:** `ui-tests/target/extent-report.html`
- **Screenshots on failure:** `ui-tests/target/screenshots/`
