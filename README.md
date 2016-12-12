![MIT License](https://img.shields.io/badge/license-MIT%20License-blue.svg)

# RefactoBot: Saving legacy system's lives

First sketch of a refactoring robot based on the fabulous [Spoon](http://spoon.gforge.inria.fr) code transformation library. 

## Sample application

Kudos to: https://github.com/zacharyfox/RMonitorLeaderboard (GPL license)

## Sample processors

 * Find empty catch blocks: _CatchProcessor_
 * Add _@NotNull_ annotation to every Object parameter: _NotNullParameterProcessor_
 * Add not null pre-condition checks to every Object parameter: _NotNullCheckAdderProcessor__
 * Find more examples here: https://github.com/SpoonLabs/spoon-examples
 
## Usage

Use the following bash commands to access the samples:

 * `./viz.sh` to visualize the model for a sample Java file according the Spoon meta model
 * `./generate.sh` to transform the code with all given processors and re-format code according google's java coding style 

## License

This software is provided under the MIT open source license, read the `LICENSE` file for details.