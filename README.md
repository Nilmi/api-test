**Automated API Test**

**1. Technical details**

This automated API test implemented in Java using following  frameworks and libraries:

|Framework/ Library  |Usage  |
|--|--|
|Gherkin   |Write the test cases in BDD pattern  |
|Cucumber   |Implement Gherkin test cases in Java programming language  |
|Apache commons   |Read configurations from properties file  |
|TestNG   |Create test runner to start the test  |
|RESTAssured    |Send GET request to API, receive the response and perform assertions  |
|Extent reports   |Generate automated test report  |

**2. Code structure**
- This project can be expanded to a test suite which contain large number of test cases belong to different test suites (for example: regression test, smoke test, etc) covering different features. 

**3. About the test** 

Test were written using Gherkin language. This test sends a get request to the API endpoint with "category ID" and "catalogue". Then executes following verification steps against the response:

	- Verify the response status code is 200  
	- Verify "Name" is "Carbon credits"  
	- Verify "CanRelist" parameter value is "true"  
	- Verify there is a "Promotions" element with Name "Gallery"  
	- Verify the "Description" of "Gallery" promotions element contains the text "2x larger image"

**4. How to run the test?**

Preconditions:
- Apache Maven installed and configured according to [installation guide](https://maven.apache.org/install.html)
- Clone project from github using `git clone [url]

Open terminal and navigate to project root folder and run command `mvn test`

**5. Test Report**

After execution of this API test case it generates a report in html format. Reports are generated inside reports folder inside the project folder.
This report has three different views:

	- Dashboard view
		- Provides an overview of test results using graphical representation
		- Summarizes test results according to features, scenarios, test steps
		- Provides test start time, end time and duration details


	- Test category view
		- This view shows test case list belongs to the test suite executed along with the test execution status.

	- Detailed results view
		- Provides detailed test results representation of each test case
		- Test steps of each test case are highlighted according to color code based on execution status
		
|Colour code |Representation  |
|--|--|
|Green   |Passed  |
|Red   |Failed  |
|Blue   |Skipped  |
