**Automated API Test**

**1. Technical details**

This automated API test is implemented in Java using following frameworks and libraries:

|Framework/ Library  |Usage  |
|--|--|
|Gherkin   |Write the test cases in BDD pattern  |
|Cucumber   |Implement Gherkin test cases in Java programming language  |
|Apache commons   |Read configurations from properties file  |
|TestNG   |Create test runner to bind feature files with step definition files |
|RESTAssured    |Send GET request to API, receive the response and perform assertions  |
|Extent reports   |Generate automated test report  |
|Maven  |Manage dependencies and initialize test    | 

**2. Code structure**

- Automated API test designed to enable code reuse, maintainability and expandability.
- Diagram below shows the main classes/ components and the test execution flow.
![classes/ components](https://user-images.githubusercontent.com/25843579/66760714-39ff7b00-eed5-11e9-9dc6-df24af13b05f.png)
- This project can be expanded to a test suite which contain large number of test cases belong to different test suites (for example: regression test, smoke test, etc) covering different features. 

- Test case '_Send GET request to API with parameters "category ID" and "catelogue"_' designed using 'Scenario Outline:' to verify any category object by only adding test data (category ID, catelogue, category name, CanRelist, promotions element name, promotions description text) to 'Examples:' section, but without doing any changes to the code.
- Endpoint URL is parameterized to take parametrized values (v1/Categories/{category-id}/Details.json?catalogue={catalogue})
- Code organized in to,
    - Gherkin test cases: 
    
        - Test cases are organized in feature files as scenarios (package: src/test/resources/features/)
        - Test cases can be tagged according to the test suite they belong / functionality they cover. 
        - Once test cases are tagged like this, the tag can be used to run test cases as test suite.
        - Currently there is one test case in feature directory to cover the given acceptance criteria.
![Gherkin test case](https://user-images.githubusercontent.com/25843579/66760392-98782980-eed4-11e9-84f4-a44e3c573fff.png)
    
    - Step definition:
    
        - Maps Gherkin scenario steps with Java code (package: src/test/java/nz/assurity/qa/apitest/stepdefinitions).
        - Step definitions are implemented using RESTAssured library.
        
    - Util:
        - Contains ConfigurationManager (helps to initialize test configurations) and FileHelper (helps to generate file name and create report file)
        (package: src/test/java/nz/assurity/qa/apitest/stepdefinitions).
        
    - Runner:
        - Contains feature file, step definition binding.
        
    - TestNG xml and pom.xml:
        - Starts the test execution.
        - testng.xml mentioned in pom.xml file, this enables execution of tests once `mvn test` command executed.      
           

**3. About the test** 

Tests are written using Gherkin language. This test sends a GET request to the API endpoint with "category ID" and "catalogue" parameter values. Then executes following verification steps against the response:

	- Verify the response status code is 200  
	- Verify "Name" is "Carbon credits"  
	- Verify "CanRelist" parameter value is "true"  
	- Verify there is a "Promotions" element with Name "Gallery"  
	- Verify the "Description" of "Gallery" promotions element contains the text "2x larger image"

**4. How to run the test?**

Preconditions:
- Apache Maven installed and configured according to [installation guide](https://maven.apache.org/install.html)
- Clone project from github using `'git@github.com:Nilmi/api-test.git'`

Steps:
- Open terminal and navigate to project root folder 
- Run command `mvn test`

**5. Test Report**

After execution of this API test case it generates a report in html format. Reports are generated inside project folder reports directory (following directory path: api-test/reports/)
This report has three different views:

	- Dashboard view
		- Provides an overview of test results using graphical representation
		- Summarizes test results according to features, scenarios, test steps
		- Provides test start time, end time and duration details
![Dashboard view](https://user-images.githubusercontent.com/25843579/66754089-c7d46980-eec7-11e9-8aa1-1f049fffb63e.png)
		

	- Test category view
		- This view shows executed test case list along with the test execution status.
![Test category view](https://user-images.githubusercontent.com/25843579/66754115-d4f15880-eec7-11e9-877d-54716226305b.png)


	- Detailed results view
		- Provides detailed test results representation of each test case
		- Test steps of each test case are highlighted according to color code based on execution status
![Detailed results view](https://user-images.githubusercontent.com/25843579/66754128-de7ac080-eec7-11e9-8639-b2301a826cb3.png)		
		
|Colour code |Representation  |
|--|--|
|Green   |Passed  |
|Red   |Failed  |
|Blue   |Skipped  |
