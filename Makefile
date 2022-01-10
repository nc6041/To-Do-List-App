run: compile
	java ToDoApp

compile:
	javac Day.java
	javac ToDoDataLoader.java
	javac ToDoBackEnd.java
	javac ToDoFrontEnd.java
	javac ToDoApp.java

test: compileTest
	java TextUITester
	java -jar junit5.jar -cp . -c ToDoAppTests


compileTest: compile
	javac TextUITester.java
	javac -cp .:junit5.jar ToDoAppTests.java
clean:
	rm *.class
