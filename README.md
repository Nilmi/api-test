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

- Automated API test designed to enable code reuse, maintainability and expandability.
- This project can be expanded to a test suite which contain large number of test cases belong to different test suites (for example: regression test, smoke test, etc) covering different features. 
- Code organized in to,
    - Gherkin test cases: 
    
        - Test cases are organized in feature files as scenarios (package: src/test/resources/features/)
        - Test cases can be tagged according to the test suite they belong / functionality they cover. 
        - Once test cases are tagged like this, the tag can be used to run test cases as test suite.
        - Currently there is one test case in feature directory to cover the given acceptance criteria.
    
    - Step definition:
    
        - Maps Gherkin scenario steps with Java code (package: src/test/java/nz/assurity/qa/apitest/stepdefinitions).
        - Step definition are implemented using RESTAssured library.
        
    - Utilities:
        - Contains ConfigurationManager (helps to initialize test configurations) and FileHelper (helps to generate file name and create report file)
        (package: src/test/java/nz/assurity/qa/apitest/stepdefinitions).
        
    - Runner:
        - Contains feature file, step definition binding.
        
    - TestNG xml and pom.xml:
        - Starts the test execution. testng.xml included in pom.xml file, this enables execution of tests once `mvn test` command executed.      
           

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
- Clone project from github using `git clone https://github.com/Nilmi/api-test.git

Steps:
- Open terminal and navigate to project root folder and run command `mvn test`

**5. Test Report**

After execution of this API test case it generates a report in html format. Reports are generated inside reports folder inside the project folder.
This report has three different views:

	- Dashboard view
		- Provides an overview of test results using graphical representation
		- Summarizes test results according to features, scenarios, test steps
		- Provides test start time, end time and duration details
![Dashboard view](https://user-images.githubusercontent.com/25843579/66724292-ce76c880-ee56-11e9-8f06-5b01ad8d6d01.png)
		

	- Test category view
		- This view shows test case list belongs to the test suite executed along with the test execution status.
![Test category view](https://user-images.githubusercontent.com/25843579/66724295-d898c700-ee56-11e9-974e-e10fa75476a2.png)


	- Detailed results view
		- Provides detailed test results representation of each test case
		- Test steps of each test case are highlighted according to color code based on execution status
![Detailed results view](https://user-images.githubusercontent.com/25843579/66724298-e1899880-ee56-11e9-85c0-89dae3cf4d2e.png)		
		
|Colour code |Representation  |
|--|--|
|Green   |Passed  |
|Red   |Failed  |
|Blue   |Skipped  |
