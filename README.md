# jfit2
![JFit Demo](https://github.com/user-attachments/assets/23b4800c-b457-46a1-bdce-e7b3040914e8)

jFit2 is a fully tested Java utility for emprical complexity analysis. It's envisioned as an improvement over [it's predecessor](https://github.com/fringewidth/jfit).

## Necessity:

The original jFit was my first software development project, and as a result has a lot of shortcomings.

- The Python code generation that runs the complexity analysis was hardcoded and consequently, was not very flexible and extremely difficult to debug (albeit it was a nice programming exercise to generate the code of one language in another).
- jFit showed you pretty graphs, but you had to infer the complexity on your own from looking at the graph.
- A lot of bugs :/

## Features and Improvements

jFit2 is designed to be much simpler, yet with a more advanced functionality. Here are some improvements:

- Inferring of most likely complexity based on $$r^2$$ scores of each fit.
- Runtime generation of python code has been scrapped and replaced with a much simpler file based communication between the two processes.
- The data is now cleaned and smoothened before fitting for a much more accurate curve.
- The java side of things is fully tested with every edge case I could think of.
- No bugs or unexpected behaviour.

## Java Requirements

jFit2 uses Maven for dependency management and requiers Java 17 to run.

### Dependencies

- **JUnit 4.13.2**: This project uses JUnit for running tests.

### Build Tool

- **Maven:** This project uses Maven for managing project's build, reporting and documentation from a central piece of information.

### JDK

- **Java 17:** This project is compiled with Java 17.

## Python Requirements

- `analyse.py` has imports to NumPy, SciPy, Matplotlib, Pandas and SciKit learn.

## Usage

To use jFit2, follow the steps below:

1. **Clone the Repository:** Clone this repository to your local machine using `git clone https://github.com/fringewidth/jFit2.git.`

2. **Compile and run the Java Project.**

The `Main` class generates a CSV file with running times of the `__method` method for different input sizes. It then calls a Python script `analyse.py` to analyze these running times and infer the runtime complexity.

You can modify the `__method` method in the `Main` class to include the code of the algorithm you want to analyze. The `__method` method should take an array of longs as input and modify this array in-place.

You can also modify the `start`, `end`, and `increment` variables in the `Main` class to control the range of input sizes for which the running times are generated.

The `sorted` variable in the `Main` class determines whether the input array is sorted or not. If `sorted` is `true`, the input array will be sorted in ascending order. If `sorted` is `false`, the input array will be unsorted.

The `csvPath` variable in the `Main` class specifies the path of the CSV file to which the running times are written. You can modify this variable to change the output location of the CSV file.

The `callPy` method in the `Main` class calls the Python script `analyse.py` with the path of the CSV file as an argument. You can modify the `callPy` method to call a different Python script or to pass different arguments to the Python script.
