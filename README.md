# Packaging Challenge

## Approach

### Strategy
The strategy in this project is finding a solution for the classic knapsack problem.
There is a classic solution for solving the problem which used in the code: recursive approach.

### Algorithm
In the solution there are two main phases: firstly read from input and secondly process the input and finding the answers.
Based on the two phases there are two main services and implementations. In the read service the format of the file will be processed,
and it converts to our desired model. Each line of input file will be converted to the Line class which has a list of Items.
after reading the lines the Find service will be called, and the process of finding answers will be started.
In this step the recursive method with the complexity of O(nw) finds the answers based on maximum between *process(n-1, w)* and *process(n-1, w - w(n))*.


### Data Structure
There are three Models: Line,Item, and Result.
For making and converting the input to these models, it has been used some useful structure like ***java.util.List*** and ***java.nio.file.Files***.


### Concerns
The main concerns are : writing clean code based on SOLID principles with the function testable.
For achieving that the services are defined in different interfaces, and interface and implementation have been separated.
With the help of Reflection the private methods have been tested, and with the help of JUnit 5, unit tests and integration tests have been done.

### Design Pattern
For making services Singleton, with the help of Enum instance the services are Singleton.

### Constraints
Max weight that a package can take is ≤ 100
There might be up to 15 items you need to choose from
Max weight and cost of an item is ≤ 100

## How to build

    mvn clean install
    
## How to test

    mvn test
