#!/usr/bin/env bash

#./gradlew clean

#./gradlew build

echo 'Copy files...'

scp -i ~/id_rsa_axe ~/IdeaProjects/wccs/build/libs/wccs-0.0.1-SNAPSHOT.jar axe@192.168.100.71:/home/axe/wccs

echo 'Restart server...'

ssh -i ~/id_rsa_axe axe@192.168.100.71 << EOF
pgrep java | xargs kill -9
nohup java -jar wccs/wccs-0.0.1-SNAPSHOT.jar > wccs/log.txt &
EOF

echo 'Bye'