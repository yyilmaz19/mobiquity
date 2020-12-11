
**About Project**
This project is a API server testing framework that is used to test by DDT and BDD Cucumber Framework. 

** Framework Structure **

This BDD framework is designed as a POM file structured. In this design pattern, all related pages are stored together and represented as classes. POM is best applicable for the applications which contain multiple pages.

To execute tests, this framework runners/CukesRunner file has to run

Each of which have fields which can be uniquely referenced with respect to the page. 
Page structure is designed as 

Runners  
Runner file contain cukesrunnes.java file that is executed as a framework setting file. All options about framework running is implemented by cukesrunner.

Step_definitions
Step definition files involve automation java codes files. All codes that execute to test are implemented in step definition.

Utilities
Utilities files provides to reduce redundancy for common methods and functions of test environments. 

Features 
All test scenarios and test cases are stored in features files. 
Features files are written by Gherkin language and all person, even nontechnical can understand these test cases written in Gherkin language as well.

Configuration.properties 
Manipulating external information is very easy from configuration properties files. If BaseURI changes, it doesn't matter by changing only a place.

** Test Reports **
Cucumber framework produces a fancy report by maven. This HTML report shows details of test results and their all steps.

Once, to see test report, 

Make sure run the test framework with Maven 
( send the command "mvn verify" through terminal or click the maven verify button)

Open the / target/default-cucumber-reports/overview-features.html report file with any browser 
