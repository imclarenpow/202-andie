# Builds the program as a Java executable for Windows 
# (may need to change the JAVA_HOME) depending on jdk's directory on your machine
# command to run in bash is: sh buildwin.sh

#!/bin/bash

# Set the JDK installation path (only for windows)
export JAVA_HOME="C:\Program Files\Java\jdk-17"

# Update the PATH to include JDK's bin directory (only for windows)
export PATH="$JAVA_HOME/bin:$PATH"

# Compile the Java source files
echo "Compiling Java source files..."
javac -d bin src/cosc202/andie/*.java src/cosc202/andie/lang/*.java src/cosc202/andie/colour/*.java src/cosc202/andie/edit/*.java src/cosc202/andie/file/*.java src/cosc202/andie/filter/*.java src/cosc202/andie/help/*.java src/cosc202/andie/image/*.java src/cosc202/andie/view/*.java

# Create the directory for MessageBundles
mkdir -p bin/cosc202/andie/lang/MessageBundles

# Copy the MessageBundles properties files
cp -r src/cosc202/andie/lang/MessageBundles/*.properties bin/cosc202/andie/lang/MessageBundles

# Create the executable JAR file
echo "Creating executable JAR..."
jar cvfe andie.jar cosc202.andie.Andie -C bin .

# Make the JAR file executable
chmod +x andie.jar

echo "Build complete."
