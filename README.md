# Paintball: 


A minecraft minigame that allows players to spawn into a server, choose a team, and survive for as long as they can against monsters to beat other teams and earn high scores.


## What is going on: 

In order to include our dependencies into our file properly, we are using maven. Maven is a technology that allows easy packaging of dependencies and a bunch of other things to play with when building executable files. Really what is necessary to know is that there is a cental maven repository, where all code that uses maven can be stored. Each piece of code has a unique address. This address is really a combination of the groupid and the artifactid. All of the maven settings can be found in the pom.xml file. There are a bunch of maven commands that can be run to do different things to your environment. The first command that should be ran when you load the code into your editor is maven install. This will make sure all of your dependencies are installed and will install your code into your local maven repository. That's right maven has a local repository on your machine that keeps all of your dependencies and code that you write using maven! Another useful command is clean, which deletes all previously compiled code. Package is the last command that we will need. This will turn all of our code into a jar file that we can drop in the plugins folder of our server. 


## Testing Locally: 

I recommend using a symlink to test your plugin on your local server. A symlink is a pointer. Imagine your desktop at the moment. There might be some file icons on your desktop but the program doesn't actually live on your desktop. It is just a shortcut you can use in order to access that program. Think of a symlink like a shortcut. Essentially wheat we're doing is we're going to create a symlink in our plugins folder that points to the jar in our target folder (where the code is getting compiled) so that we don't have to copy and paste the code into the plugins folder every time we want to test the code. 


To create a symlink open up a terminal. 

ln -s path/to/sourcefile path/to/target

To give you an example lets say the jar I want to put in the plugins folder lives in
~/Developer/db-design-paintball/target/db-design-paintball-1.0-SNAPSHOT.jar  and we want to move it to 
~/Developer/servers/server1/plugins/

The symlink we'll type is
ln -s ~/Developer/db-design-paintball/target/db-design-paintball-1.0-SNAPSHOT.jar ~/Developer/servers/server1/plugins/db-design-paintball-1.0-SNAPSHOT.jar

You can also use relative paths. 
