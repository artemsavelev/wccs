#!/usr/bin/env bash

#./gradlew clean

#./gradlew build

echo 'Copy files...'

scp  build/libs/wccs-0.0.1-SNAPSHOT.jar  axe@192.168.88.2:/home/axe/wccs

echo 'Restart server...'

ssh  axe@192.168.88.2 << EOF
pgrep java | xargs kill -9
nohup java -jar wccs-0.0.1-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye'