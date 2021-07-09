#!/usr/bin/env bash

# shellcheck disable=SC2164
cd wccs
./gradlew clean
./gradlew build


echo 'Copy files...'


scp -i ~/id_rsa_axe ~/wccs/build/libs/wccs-0.0.2-SNAPSHOT.jar axe@192.168.101.9:/home/axe/wccs
scp -r -i ~/id_rsa_axe ~/wccs/build/libs/wccs/ axe@192.168.101.9:/var/www/html/



echo 'Restart server...'

ssh -i ~/id_rsa_axe axe@192.168.101.9 << EOF
pgrep java | xargs kill -9
nohup java -jar wccs/wccs-0.0.2-SNAPSHOT.jar > wccs/log.txt &
EOF

echo -e 'Bye \n'