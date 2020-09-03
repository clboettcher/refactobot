# LEAP Spoon Refactoring

## About

Applies code refactorings powered by spoon.

## Usage

###  Running [JsfRefactoringProcessor.java](bot/src/main/java/de/allianz/leap/spoon/JsfRefactoringProcessor.java)

Create IntelliJ Java application run config:

- classpath of module `bot` []()
- main class `spoon.Launcher`
- arguments

Given the code you with to transform is stored in env variable `TARGET` and
this repo is checked out under env variable `SPOON`. (Output is written to `${TARGET}/spooned`.

```
--input $TARGET/src/main/java 
--output $TARGET/spooned 
--template $SPOON/bot/src/main/java/de/allianz/leap/spoon/template
-p "de.allianz.leap.spoon.JsfRefactoringProcessor" 
--enable-comments 
--with-imports 
--level WARN 
```

### Show Spoon Meta Model in UI

```
java -cp "/path/to/spoon-core-8.2.0-jar-with-dependencies.jar" spoon.Launcher 
    --input "/path/to/my/JavaClass.java" 
    --gui 
```
