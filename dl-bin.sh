#!/bin/sh

if [[ ! -e "bin/google-java-format-1.1-all-deps.jar" ]]
then
    echo "Downloading Google format..."
    mkdir -p bin
    curl -Lo bin/google-java-format-1.1-all-deps.jar https://github.com/google/google-java-format/releases/download/google-java-format-1.1/google-java-format-1.1-all-deps.jar
fi

if [[ ! -e "bin/spoon-core-5.4.0-jar-with-dependencies.jar" ]]
then
    echo "Downloading Spoon core..."
    mkdir -p bin
    curl -Lo bin/spoon-core-5.4.0-jar-with-dependencies.jar https://gforge.inria.fr/frs/download.php/file/36283/spoon-core-5.4.0-jar-with-dependencies.jar
fi
