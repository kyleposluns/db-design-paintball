#!/bin/sh 

LOCAL_PATH=./target
REMOTE_PATH=/home/minecraft/hyperion-network/hub/plugins/
FILE=db-design-paintball-1.0-SNAPSHOT.jar
FULL_LOCAL_PATH=$LOCAL_PATH/$FILE
FULL_REMOTE_PATH=$REMOTE_PATH/$FILE

if [ ! -f $FULL_LOCAL_PATH ]; then
    echo "Could not find the file: $FULL_LOCAL_PATH"
	exit 1
fi


if [[ $1 =~ [a-zA-Z0-9_]+@[a-zA-Z0-9_]+.com ]]
then
	scp $FULL_LOCAL_PATH $1:$FULL_REMOTE_PATH
	ssh -t $1 "chmod +x $FULL_REMOTE_PATH" 
else
	echo "An invalid username & server were provided!\nUsage: ./deploy.sh user@server.com"
	exit 1
fi


