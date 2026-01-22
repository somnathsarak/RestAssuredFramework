# GitHub Actions Setup Guide

This document provides a complete guide for setting up and using GitHub Actions with the Selenium + TestNG Maven project.

## Workflow File Location

`.github/workflows/selenium-tests.yml`

The workflow file is located in the repository root under `.github/workflows/` directory. GitHub automatically discovers and runs workflows from this location.

## Workflow Overview

**Name:** Selenium + TestNG Tests

**Triggers:**
- Push to `main` or `develop` branches
- Pull requests to `main` or `develop` branches
- Daily scheduled run at 2 AM UTC (cron: '0 2 * * *')

**Runner:** ubuntu-latest

## Workflow Steps Explained

### 1. Checkout Code
```yaml
- uses: actions/checkout@v4
```
Clones the repository into the runner environment.

### 2. Setup JDK 17
```yaml
- uses: actions/setup-java@v4
  with:
    java-version: '17'
    distribution: 'temurin'
    cache: maven
```
- Installs OpenJDK 17 (Temurin distribution)
- Enables Maven dependency caching for faster builds

### 3. Display Versions
```yaml
- run: |
    mvn -version
    java -version
```
Logs Maven and Java versions for debugging.

### 4. Run Selenium + TestNG Tests
```yaml
- run: |
    mvn clean test \
      -DheadlessMode=true \
      -Dorg.slf4j.simpleLogger.defaultLogLevel=info \
      -Dorg.slf4j.simpleLogger.showDateTime=true
```
**Parameters:**
- `clean` - Cleans previous builds
- `test` - Runs Maven test lifecycle
- `-DheadlessMode=true` - Runs Chrome in headless mode (no GUI)
- `-Dorg.slf4j.simpleLogger.defaultLogLevel=info` - Sets logging level
- `-Dorg.slf4j.simpleLogger.showDateTime=true` - Adds timestamps to logs

### 5. Publish Test Results
```yaml
- uses: actions/upload-artifact@v4
  if: always()
  with:
    name: surefire-reports
    path: target/surefire-reports/
    retention-days: 30
```
- Uploads Surefire test reports as artifacts
- Runs regardless of test success/failure (`if: always()`)
- Retained for 30 days

### 6. Upload Screenshots on Failure
```yaml
- uses: actions/upload-artifact@v4
  if: failure()
  with:
    name: failure-screenshots
    path: target/screenshots/
    retention-days: 7
```
- Uploads screenshots only on test failure
- Retained for 7 days

### 7. Publish Test Summary
```yaml
- uses: EnricoMi/publish-unit-test-result-action@v2
  if: always()
  with:
    files: target/surefire-reports/TEST-*.xml
    check_name: Test Results
    comment_mode: always
```
- Publishes test results as GitHub Check annotation
- Comments on pull requests with test summary
- Shows pass/fail counts

### 8. Notify on Failure
```yaml
- run: echo "::error::Selenium tests failed! Check the logs for details."
  if: failure()
```
Displays error notification in GitHub Actions logs.

## Running Workflows

### View Workflow Runs
1. Navigate to **Actions** tab in the repository
2. Click on **Selenium + TestNG Tests** workflow
3. View all runs, including:
   - Timestamp
   - Branch (main/develop/PR)
   - Status (passing/failing/in progress)
   - Duration

### Trigger Workflow Manually
1. Go to **Actions** tab
2. Select **Selenium + TestNG Tests** workflow
3. Click **Run workflow** button
4. Select branch (main/develop)
5. Click **Run workflow**

### View Artifacts
1. Navigate to workflow run details
2. Scroll to **Artifacts** section
3. Download:
   - `surefire-reports` - Test reports (all runs)
   - `failure-screenshots` - Failure screenshots (only on failure)

## Pull Request Integration

When you create a pull request to `main` or `develop`:

1. **Workflow automatically triggers**
2. **Check status appears** below PR description
3. **Test results show** as:
   - ✅ All passed
   - ❌ Some failed
   - ⚠️ Workflow error
4. **PR comments** with test summary (if using publish action)
5. **Blocking checks** can be enforced (requires branch protection rules)

### Example PR Check
```
Test Results
✅ 5 passed, 0 failed, 0 skipped in 2m 15s
```

## Scheduled Runs

Workflow runs automatically **every day at 2 AM UTC**:

- No manual trigger needed
- Useful for:
  - Regression testing
  - Catching flaky tests
  - Performance tracking
  - Smoke testing

### Modifying Schedule

Edit `.github/workflows/selenium-tests.yml`:

```yaml
schedule:
  - cron: '0 2 * * *'  # 2 AM UTC daily
```

**Cron format:** `minute hour day month day-of-week`

**Examples:**
- `0 2 * * *` - Daily at 2 AM UTC
- `0 */6 * * *` - Every 6 hours
- `0 10 * * 1-5` - Weekdays at 10 AM UTC
- `0 9 * * 6,0` - Weekends at 9 AM UTC

## Environment Configuration

### Headless Mode

Tests run in **headless mode** (no browser UI):

```yaml
-DheadlessMode=true
```

Update `BaseTest.java` to support headless:

```java
ChromeOptions options = new ChromeOptions();
if (System.getProperty("headlessMode") != null) {
    options.addArguments("--headless");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
}
driver = new ChromeDriver(options);
```

### Matrix Strategy

Current matrix configuration:

```yaml
strategy:
  matrix:
    java-version: ['17']
    maven-version: ['3.8.1']
```

**Expand to test multiple versions:**

```yaml
strategy:
  matrix:
    java-version: ['11', '17', '21']
    os: [ubuntu-latest, windows-latest, macos-latest]
```

This runs tests on all combinations (3 × 3 = 9 jobs).

## Secrets and Variables

### Repository Secrets

For sensitive data (usernames, passwords, API keys):

1. Go to **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Add secret (e.g., `TEST_USERNAME`, `TEST_PASSWORD`)
4. Use in workflow:

```yaml
env:
  TEST_USERNAME: ${{ secrets.TEST_USERNAME }}
  TEST_PASSWORD: ${{ secrets.TEST_PASSWORD }}
```

### Environment Variables

Add to workflow for test configuration:

```yaml
jobs:
  test:
    env:
      TEST_ENV: production
      BROWSER: chrome
      TIMEOUT: 30
```

## Troubleshooting

### Workflow Not Running

**Check:**
1. File path: `.github/workflows/selenium-tests.yml`
2. Branch: Triggers set to `main` and `develop`
3. Syntax: Valid YAML format
4. Permissions: Repository has GitHub Actions enabled

**Fix:**
```bash
# Local YAML validation
cd .github/workflows/
cat selenium-tests.yml
```

### Tests Failing in CI but Passing Locally

**Common Causes:**
- Missing Chrome/Chromium in runner
- Headless mode compatibility issues
- Timing/flakiness in tests
- Environment-specific configurations

**Debug:**
1. View workflow logs
2. Check artifact reports
3. Add verbose logging
4. Capture screenshots on failure

### Slow Workflow Execution

**Optimization:**
1. Enable Maven caching (already done)
2. Run tests in parallel
3. Use job matrix to distribute tests
4. Cache dependencies

```yaml
cache:
  maven
```

## Best Practices

1. **Run workflow on pull requests** - Catch issues early
2. **Require passing checks** - Enforce test quality (branch protection)
3. **Monitor scheduled runs** - Detect flaky tests
4. **Archive artifacts** - Keep test history
5. **Use secrets** - Never hardcode credentials
6. **Document changes** - Update workflow comments
7. **Test locally first** - Run `mvn clean test` before pushing
8. **Review logs** - Check GitHub Actions logs for errors

## Useful Links

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Java Setup Action](https://github.com/actions/setup-java)
- [Upload Artifact Action](https://github.com/actions/upload-artifact)
- [Cron Schedule Syntax](https://crontab.guru/)
- [GitHub Checks API](https://docs.github.com/en/rest/checks)

## Advanced: Notifications

### Slack Notifications

Add to workflow steps:

```yaml
- name: Notify Slack on failure
  if: failure()
  uses: slackapi/slack-github-action@v1
  with:
    webhook-url: ${{ secrets.SLACK_WEBHOOK }}
```

### Email Notifications

Configure in **Settings** → **Notifications**.

### Custom Webhooks

Use workflow to trigger external services:

```yaml
- name: Trigger external webhook
  if: failure()
  run: |
    curl -X POST https://your-webhook-url \
      -H 'Content-Type: application/json' \
      -d '{"status": "test_failed"}'
```

## Summary

This GitHub Actions workflow provides:
- ✅ Automated testing on push and pull request
- ✅ Scheduled daily regression testing
- ✅ Test reports and artifacts
- ✅ Screenshot capture on failure
- ✅ Pull request integration
- ✅ Headless browser support
- ✅ Java 17 and Maven environment
- ✅ Failure notifications
