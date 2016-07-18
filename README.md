# Cutting optimizer

## Description

It is a tool to optimize the distribution of pieces on one or more 2D fixed size sheets. This optimizer takes as input

* The size of each sheet (width, height)
* A list of peaces to arrange, each one with width and height

and produces as output

* A list of sheets, each one widh one or more panels (used or free)
* A list of pieces that the optimizer could not arrange because it is bigger than the sheet size


## TODO:
* Write more and deeper test cases (with more peaces, improve assertions)
* Algorithm improvement: evaluate all paths (http://www.intechopen.com/books/greedy_algorithms/a_greedy_algorithm_with_forward-looking_strategy)
* Build a CLI (or REST API?) so others can build graphic and/or web tools to load and visualize results
* Optional add
* Tag pieces at input
* Add cutting thickness to settings


