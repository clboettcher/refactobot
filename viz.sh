#!/usr/bin/env bash

set -e

SRC_DIR="$(dirname "$0")"

"$SRC_DIR/dl-bin.sh"


java -cp "$SRC_DIR/bin/spoon-core-5.4.0-jar-with-dependencies.jar" spoon.Launcher -i "$SRC_DIR/sample/src/main/java/com/zacharyfox/rmonitor/leaderboard/frames/MainFrame.java" --gui --noclasspath
