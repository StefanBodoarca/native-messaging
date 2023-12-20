#!/bin/bash

RELEASE=1.0.0
TARGET_DEPLOY=/home/stefan/workspace/ps/internship/deploy_native_app/listener-to-es-$RELEASE.jar

mvn clean install -DskipTests
mv target/listener-to-es-1.0-SNAPSHOT-jar-with-dependencies.jar $TARGET_DEPLOY

