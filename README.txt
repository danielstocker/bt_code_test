bt_code_test
============

Code Repository for my BT Coding Test

The task was completed using Java JRE 1.7 on Windows 8.1. 

COMPILING AND RUNNING THE APPLICATION:
In this code repository you will find two Java packages:

- OrganisationSearch (containing the actual program)
and
- OrganisationSearchTest (containing a testing solution for the program)
-> This test suite uses the files in the "testFiles" directory for some of its tests.

WINDOWS:
1) Create a directory for the built files (e.g. bin\)
2) Open a new command line window 
3) You will be using javac (the java compiler) to compile the packages
3a) On Windows you might have to run this from where you installed the JRE
    the default location for this is C:\Program Files\Java\jdk1.7.0_71\bin for JRE 1.7
	or use the absolute path to javac. 
	(default: C:\Program Files\Java\jdk1.7.0_71\bin\javac.exe)
4) On the command line execute "javac -sourcepath src\OrganisationSearch\*.java -d bin\ -cp .\src\OrganisationSearch\*.java"
4a) To build both the test solution and the actual program you require "javac -sourcepath src\OrganisationSearchTest\*.java -d bin\ -cp .\src\OrganisationSearch\*.java -cp .\src\OrganisationSearchTest\*.java"
5) To run the program enter "java OrganisationSearch.OrganisationSearch YourInputFileName Employee1 Employee2" on the command line
5a) You may have to add a folder class path reference to make this work. To do so use the following command line:
	java -cp .\bin\; OrganisationSearch.OrganisationSearch YourInputFileName Employee1 Employee2
	Replace the path specified as .\bin\ with the path of your bin folder relative to where you are running the application from.
6) To run the test solution use the following command "java -cp .\bin\; OrganisationSearchTest.TestRunner"
6a) In order to run all the tests successfully you need to be in the directory that contains the "testFiles" directory which is used by the unit test. 
	You will be prompted by the test application to switch into this directory if it cannot find its test files.
	
UNIX based operating systems:
1) Create a directory for the built files (e.g. bin/)
2) Open a terminal window
3) You will be using javac (the java compiler) to compile the packages
4) On the command line execute "javac -sourcepath src/OrganisationSearch/*.java -d bin/ -cp ./src/OrganisationSearch/*.java"
4a) To build both the test solution and the actual program you require "javac -sourcepath src/OrganisationSearchTest/*.java -d bin/ -cp ./src/OrganisationSearch/*.java -cp ./src/OrganisationSearchTest/*.java"
5) To run the program enter "java OrganisationSearch.OrganisationSearch YourInputFileName Employee1 Employee2" on the command line
5a) You may have to add a folder class path reference to make this work. To do so use the following command line:
	java -cp ./bin/; OrganisationSearch.OrganisationSearch YourInputFileName Employee1 Employee2
	Replace the path specified as ./bin/ with the path of your bin folder relative to where you are running the application from.
5b) On Linux the class path separator is not ';' but ':'.  (-cp ./bin/:)
6) To run the test solution use the following command "java -cp ./bin/; OrganisationSearchTest.TestRunner"
6a) In order to run all the tests successfully you need to be in the directory that contains the "testFiles" directory which is used by the unit test. 
	You will be prompted by the test application to switch into this directory if it cannot find its test files.
	