Shuffle
=======

An application that produces a shuffled set of values.

Status
------

Travis CI Build Status: [![Build Status](https://travis-ci.org/oneam/Shuffle.svg?branch=master)](https://travis-ci.org/oneam/Shuffle)

Codecov.io Code Coverage Status: [![codecov.io](https://codecov.io/github/oneam/Shuffle/coverage.svg?branch=master)](https://codecov.io/github/oneam/Shuffle?branch=master)

Introduction
------------

Shuffle takes a single positive integer as input (N) and produces a list of integers in the range 1..N shuffled in a random order.

It uses the [Fisher-Yates Shuffle](https://en.wikipedia.org/wiki/Fisher-Yates_shuffle) to produce the list as fast a reasonably possible regardless of size.

For speed and memory reasons, it has a limitation of only producing lists with up to 10 million items.

Building Shuffle
----------------

In order to build Shuffle, you need to have [Java 8 SE Developer Kit](http://www.oracle.com/technetwork/java/javase/overview/index.html) installed on your system.

After pulling the Git repo, you can build the application by calling:

```
./gradlew installDist
```

Shuffle is built with Gradle wrapper, which will:

* download an embedded version of the Gradle build tools
* download all required dependencies
* build the application jar
* download wrapper scripts that make executing Shuffle easier

Running Shuffle
---------------

The Shuffle application binaries are installed in the `build/install/Shuffle` directory. In order to run the application, navigate to the directory and run:

```
bin/Shuffle -n <numValues>
```

where numValues is the number of values that will be shuffled.

If you run into problems, you can run:

```
bin/Shuffle -h
```

To get a full list of options.

Testing Shuffle
---------------

Shuffle has a full suite of unit tests. The tests are designed to ensure that the application will perform as expected given valid input and also ensures that the lists produced are a sufficiently randomized set of values.

In order to run the unit tests included in Shuffle, use Gradle's check target:

```
./gradlew check
```

The test results can be viewed by opening `build/reports/tests/index.html`

Under the shuffle package, there are 2 groups of tests:

* ShuffleApplicationTest tests ensure that Shuffle accepts and processes valid input.
* ShufflerTest ensures that the output produced by Shuffle is correct and suitably shuffled.

Code coverage results can be viewed by opening `build/reports/jacoco/test/html/index.html`. This data is a reflection of the data reported by [Codecov.io](https://codecov.io/github/oneam/Shuffle/coverage.svg?branch=master).
