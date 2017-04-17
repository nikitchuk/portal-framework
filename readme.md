## For launch  
 
**Command** ```mvn clean test -DsuiteXmlFile=${user.dir}src/test/java/com/portals/portal/portal.xml``` ;

**For launch on Mac OS** - *com/portals/portal/portal.xml:14 value should be equal value="mac" *
**For launch on Win OS** - *com/portals/portal/portal.xml:14 value should be equal any value exept value="mac" *

## For report 
Also U can use  mvn allure:report for build allure report. Please ude Firefox or Safary for open allure-maven-plugin.html file. 
Path to file   ${user.dir}/target/allure-report/allure-maven-plugin.html
