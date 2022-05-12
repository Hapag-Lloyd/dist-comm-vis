#!/bin/bash

# Builds the classpath with all libraries and runs the scanner to produce the model.
#
# $1 - path to project to scan. Should contain the jars and classes needed (all dependencies)
# $2 - project id of your choice
# $3 - project name of your choice
#
# Note: I had troubles to get this running on Windows machines: classpath entries have to be separated by ';' instead of ':'
#       and the JarLauncher wasn't found.
# Note: Add the maven-dependency plugin to your project to put all dependencies into a separate folder. See
#       /distributed-projects/microservice-a/pom.xml

set -euo pipefail

# download latest release
analyzer_jar_file_name="/tmp/analyzer.jar"
release_version=$(curl -s https://api.github.com/repos/Hapag-Lloyd/dist-comm-vis/releases/latest | jq .tag_name -r)

curl https://github.com/Hapag-Lloyd/dist-comm-vis/releases/download/"${release_version}"/distcommvis-"${release_version}".jar -o "${analyzer_jar_file_name}" -L

# find all libraries to include
jars=$(find "$1" -name "*.jar")

classpath=""

for f in $jars; do
  classpath="$classpath:$f"
done

# add the scanner downloaded above
classpath="${classpath:1}:${analyzer_jar_file_name}"

java -cp "${classpath}" org.springframework.boot.loader.JarLauncher scan com.hlag.tools.commvis.example "$2" --name="$3"
