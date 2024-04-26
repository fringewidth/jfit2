# jfit2

jFit2 is an envisioned as an improvement over [it's predecessor](https://github.com/fringewidth/jfit), which is a Java tool for empirical complexity analysis.

## Necessity:

The original jFit was my first software development project, and as a result has a lot of shortcomings.

- The Python code generation that runs the complexity analysis was hardcoded and consequently, was not very flexible and extremely difficult to debug (albeit it was a nice programming exercise to generate the code of one language in another).
- jFit showed you pretty graphs, but you had to infer the complexity on your own from looking at the graph.
- A lot of bugs :/

## Goals

I have the following requirements for jFit2:

- Support for inference of $O(n^k)$, $O(log(n))$, $O(nlog(n))$ and $O(k^n)$ runtime complexities for arrays.
- Type flexibility on the arrays themselves.
- Correctness through testing.
- Improved error detection and error handling
- Better user experience.

## Plan of action

- [x] Basic PoC.
- [ ] Unit tests for correctness.
- [ ] Refactoring
- [ ] Improve UX.
- [ ] Improve type flexibility
