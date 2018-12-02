# MetalCrawler
## Introduction
This is dungeon crawling Android Game. Levels are procedurally generated, meaning that they are algorithmically created
as opposed to hand done. The game was developed with Android Studio in Java. The application was tested on an emulator of
the Nexus 5.

## Prerequisites
- Minimum Android SDK version: 26.
- Targeted SDK version: 28.

## Game Mechanics
The goal of the game is to get the best highscore by collecting in-game items called "Cogs". 
The player will need to fight or avoid enemies to ensure they don't meet an untimely demise. Several different enemies
will be encountered as the player delves deeper into the dungeon, all with their own attack patterns. The player may also
choose their class before the start of any game, effecting the characteristics of the character. The game employs mechanics
such as "momentum", which accumulates damage output as the player moves continuously in one direction. "Momentum" is lost
when the player stops or changes direction. There is also the "Riposte" mechanic where the player deals more damage the 
instant they move in the opposite direction they were originally moving. 

## Additional Features
The application requests permission to write to the sd card in order to save the player highscore. Regardless of whether the
user grants permission however, the application will continue to save the highscore in preferences. This feature was included
to try and mitigate accidental deletions of one's save by uninstalling the app, or deleting it from the sd card. 
