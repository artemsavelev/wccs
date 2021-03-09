#!/usr/bin/env bash

#./gradlew clean

#./gradlew build

echo 'Copy files...'

scp -i ~/.ssh/id_rsa_axebd build/libs/wccs-0.0.1-SNAPSHOT.jar -i ~/.ssh/id_rsa_axebd axe@192.168.101.9:/home/axe/wccs

echo 'Restart server...'

ssh -i ~/.ssh/id_rsa_axebd axe@192.168.101.9 << EOF
pgrep java | xargs kill -9
nohup java -jar wccs-0.0.1-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye'