# Maven Selenium + TestNG CI/CD Setup Guide

This project is configured as a Maven-based Selenium + TestNG framework, ready to integrate with any CI/CD pipeline (Jenkins, GitHub Actions, GitLab CI, Azure DevOps).

## Project Structure

```
.
├── src
│   ├── main
│   │   └── java
│   │       ├── base
│   │       │   └── BaseTest.java
│   │       └── pages
│   │           └── LoginPage.java
│   └── test
│       ├── java
│       │   └── tests
│       │       └── LoginTest.java
│       └── resources
│           ├── testng-selenium.xml
│           └── testng.xml
├── pom.xml
└── CI_CD_SETUP.md
```

## Key Components

### 1. **pom.xml** - Maven Configuration
Configured with:
- Selenium WebDriver 4.25.0
- TestNG 7.10.1
- Maven Surefire Plugin 3.5.4 (for running tests)
- Java 17 compilation target

### 2. **BaseTest.java** - Test Foundation
- Provides `setUp()` method: Initializes ChromeDriver before each test
- Provides `tearDown()` method: Quits driver after each test
- All test classes extend BaseTest

### 3. **LoginPage.java** - Page Object Model
- Encapsulates login page locators and methods
- Methods: `open(url)`, `login(username, password)`

### 4. **LoginTest.java** - Test Class
- Extends BaseTest
- Contains `@Test` annotated test methods
- Uses LoginPage for interaction
- Assertions to validate outcomes

### 5. **testng-selenium.xml** - TestNG Suite Configuration
- Defines test suite for UI tests
- Specifies classes and methods to run
- Non-parallel execution by default

## Running Tests Locally

### Prerequisites
- JDK 17+
- Maven 3.6+
- Chrome/Chromium browser
- ChromeDriver (version matching Chrome)

### Commands

```bash
# Run all tests
mvn clean test

# Run with specific TestNG suite
mvn clean test -Dtest=tests.LoginTest

# Run in headless mode (CI environment)
mvn clean test -Dheadless=true
```

## CI/CD Pipeline Integration

### General Pipeline Structure

```
1. Checkout repository
2. Setup JDK + Maven
3. Run: mvn clean test
4. Generate reports
5. Archive test results
```

### Jenkins

**Pipeline Script Example:**

```groovy
pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/somnathsarak/RestAssuredFramework.git'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn clean test -Dheadless=true'
            }
        }
        
        stage('Report') {
            steps {
                junit 'target/surefire-reports/*.xml'
                publishHTML([
                    reportDir: 'target/surefire-reports',
                    reportFiles: 'index.html',
                    reportName: 'TestNG Report'
                ])
            }
        }
    }
}
```

### GitHub Actions

**.github/workflows/selenium-tests.yml:**

```yaml
name: Selenium Tests

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    
    - name: Run tests
      run: mvn clean test -Dheadless=true
    
    - name: Upload test results
      if: always()
      uses: actions/upload-artifact@v3
      with:
        name: test-results
        path: target/surefire-reports/
```

### GitLab CI

**.gitlab-ci.yml:**

```yaml
image: maven:3.8.1-openjdk-17

stages:
  - test

selenium_tests:
  stage: test
  script:
    - mvn clean test -Dheadless=true
  artifacts:
    reports:
      junit: target/surefire-reports/*.xml
    paths:
      - target/surefire-reports/
    expire_in: 1 week
```

### Azure DevOps

**azure-pipelines.yml:**

```yaml
trigger:
  - main

pool:
  vmImage: 'ubuntu-latest'

steps:
- task: UseJavaVersion@0
  inputs:
    version: '17'
    jdkArchitectureOption: 'x64'
    jdkSourceOption: 'PreInstalled'

- task: Maven@3
  inputs:
    mavenPomFile: 'pom.xml'
    mavenOptions: '-Xmx3072m'
    javaHomeOption: 'JDKVersion'
    jdkVersionOption: '1.17'
    publishJUnitResults: true
    testResultsFiles: 'target/surefire-reports/*.xml'
    goals: 'clean test -Dheadless=true'
```

## Headless Mode for CI

For CI environments without display servers, run tests in headless mode:

```bash
mvn clean test -Dheadless=true
```

Update **BaseTest.java** to support headless:

```java
ChromeOptions options = new ChromeOptions();
if (System.getProperty("headless") != null) {
    options.addArguments("--headless");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
}
driver = new ChromeDriver(options);
```

## Test Reports

After test execution, reports are available at:
- **Surefire Reports:** `target/surefire-reports/`
- **TestNG Reports:** `test-output/`

## Maven Profiles for Different Environments

Add to **pom.xml** for environment-specific configs:

```xml
<profiles>
    <profile>
        <id>qa</id>
        <properties>
            <testng.suite>src/test/resources/testng-selenium.xml</testng.suite>
        </properties>
    </profile>
    <profile>
        <id>prod</id>
        <properties>
            <testng.suite>src/test/resources/testng-prod.xml</testng.suite>
        </properties>
    </profile>
</profiles>
```

Run with profile:

```bash
mvn clean test -Pqa
```

## Troubleshooting

### "No tests found"
- Ensure test class extends BaseTest
- Test methods annotated with `@Test`
- Class name ends with `Test`

### ChromeDriver issues in CI
- Use headless mode
- Ensure Chrome/Chromium installed
- Use WebDriverManager for auto driver management

### Dependencies not resolving
```bash
mvn clean install -U
```

## Next Steps

1. **Add WebDriverManager** for automatic driver management
2. **Parallel execution** by modifying testng.xml
3. **Screenshots/logs** on failure
4. **HTML reports** with Extent Reports
5. **Database integration** for test data
6. **Docker containerization** for consistent CI environments

## References

- [Maven Official Docs](https://maven.apache.org/)
- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [TestNG Documentation](https://testng.org/)
- [Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)
