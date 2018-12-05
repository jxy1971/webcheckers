all:
	mvn compile exec:java
gen:
	mvn exec:exec@tests-and-coverage
appl:
	mvn clean test-compile surefire:test@appl jacoco:report@appl
model:
	mvn clean test-compile surefire:test@model jacoco:report@model
ui:
	mvn clean test-compile surefire:test@ui jacoco:report@ui
package:
	mvn package -DskipTests
test:
	mvn clean test jacoco:report
