#!/bin/sh

set -e




OUTPUT_DIR=./spooned
[[ "$OS" =~ "Windows" ]] && CP_SEP=";" || CP_SEP=":"
SRC_DIR="$(dirname "$0")"

"$SRC_DIR/dl-bin.sh"

# Generated transformed source output and format code
echo "Generate output:"

# Delete output directory
echo " > clean output directory"
rm -rf "$OUTPUT_DIR"
mkdir -p "$OUTPUT_DIR"

# Transform source code with given processors
echo " > generate sources"

java -classpath "$SRC_DIR/bot/target/bot-1.0-SNAPSHOT.jar${CP_SEP}$SRC_DIR/bin/spoon-core-5.4.0-jar-with-dependencies.jar" \
    spoon.Launcher -i "$SRC_DIR/sample/src/main/java" -o "$OUTPUT_DIR" \
    -p "de.qaware.refactobot.NotNullParameterProcessor${CP_SEP}de.qaware.refactobot.CatchProcessor${CP_SEP}de.qaware.refactobot.NotNullCheckAdderProcessor${CP_SEP}de.qaware.refactobot.PackageReferenceProcessor${CP_SEP}de.qaware.refactobot.RenameTypeProcessor${CP_SEP}de.qaware.refactobot.RenameTypeRefProcessor" \
    --enable-comments --noclasspath --with-imports --level WARN

# Format output code with Google code formatter
echo " > format sources"
find "$OUTPUT_DIR" -name '*.java' | xargs java -jar "$SRC_DIR/bin/google-java-format-1.1-all-deps.jar" --replace
echo "Finished!"
