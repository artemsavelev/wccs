#!/usr/bin/env bash

# shellcheck disable=SC2164
cd ..
#./gradle clean
#./gradlew build


echo 'Copy files...'


scp -i ~/id_rsa_axe build/libs/wccs-0.1.3.jar axe@192.168.101.9:/home/axe/wccs
scp -r -i ~/id_rsa_axe build/libs/wccs/ axe@192.168.101.9:/var/www/html/

echo 'Restart server...'

ssh -i ~/id_rsa_axe axe@192.168.101.9 << EOF
pgrep java | xargs kill -9
java -jar wccs/wccs-0.1.3.jar > wccs/log.txt &
EOF

echo -e 'Bye \n'