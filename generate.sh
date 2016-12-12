#!/usr/bin/env bash

# Generated transformed source output and format code
echo "Generate output:"

# Delete output directory
echo " > clean output directory"
rm -r ./spooned 2> /dev/null

# Transform source code with given processors
echo " > generate sources"
java -classpath ./bot/target/bot-1.0-SNAPSHOT.jar:./bin/spoon-core-5.4.0-jar-with-dependencies.jar \
     spoon.Launcher -i ./sample/src/main/java -o ./spooned \
     -p de.qaware.refactobot.NotNullParameterProcessor:de.qaware.refactobot.CatchProcessor:de.qaware.refactobot.NotNullCheckAdderProcessor \
     --enable-comments --noclasspath --with-imports --level WARN

# Format output code with Google code formatter
echo " > format sources"
find ./spooned -name '*.java' | xargs java -jar ./bin/google-java-format-1.1-all-deps.jar --replace

echo "Finished!"