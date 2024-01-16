#!/bin/sh
set -e

exec java -Dserver.port=$PORT $JAVA_OPTS -jar ./app.jar
