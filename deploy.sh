#!/bin/sh 

if [[ $1 =~ [a-zA-Z0-9_]+@[a-zA-Z0-9_]+.com ]]
then
	scp ./target/db-design-paintball-1.0-SNAPSHOT.jar $1:/home/minecraft/hyperion-network/hub/plugins/db-design-paintball-1.0-SNAPSHOT.jar
	ssh -t $1 "chmod +x /home/minecraft/hyperion-network/hub/plugins/db-design-paintball-1.0-SNAPSHOT.jar" 
else
	echo "An invalid username & server were provided!\nUsage: ./deploy.sh user@server.com"
fi


