# AgarIO
Java based implementation of Agar.io

GitHub link: https://github.com/vigneetsompura/AgarIO

## Group information

| Name   |      UFID      |  GitHub ID |
|----------|:-------------:|------:|
| Vigneet Sompura |  8121-1616 | vigneetsompura |
| Varun Patni |    8969-9355   |    varun4609 |
|  Ankit Soni | 8761-9158 |    ankitprahladsoni |

Note: Varun initially faced some issues with synching his github username, and so some of the commits he made are under the name 'Unknown, vsp46, varun4609'.

# Project Contributions
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The project was implemented in a way that required all the participants to get involved in most of the modules that made up this project. While everyone was involved in designing/developing functionalities related to multithreading and concurrency, we have made our best attempts to seperate contributions based on project functionalities. Individual contributions are as follows:
## Vigneet
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Vigneet was incharge of implementing the core game mechanincs on the server side. In collaboration with Varun, he also helped in designing the GUI and client side input handling logic.
## Varun
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Varun was incharge of establishing a framework for efficient client-server communication. After that he helped out with documentation and some of the client core logic and GUI functionalities.
## Ankit
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ankit was incharge of wirting the client core functionalities and also refactoring and sturcturing of the entire project source code. Majority of the work done in creating the project poster was done by Ankit.
# Project Description
## How it works!
### Game arena
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;When you start the game as a player, you will be asked the IP address of a server that you want to connect to.
### Player start conditions:
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Once you enter the IP address of the server (game session) that you want to connect to, you will be placed in an arena which will be full of these yellow stationary points called ‘Food’.
### Game Progression:
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The player will use their mouse cursor a to point to the direction where they want their object to move. In order the progress through the game, the player will consume Food (which increases a player’s objects radius) or other players (eliminating them from the game).
### Player end condition:
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The game ends for a player if they are consumed by another opponent, or if they are the last player standing.
### Game objective:
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Consuming Food causes the radius of a player’s object to increase. The main objective of this game is to be the last player standing. This can be acheived by wither consuming all the opponents or increasing your objects radius large enough to occupy the whole arena.
# How to execute:
```java
javac src/agario/*.java src/client/*.java src/server/*.java
cd src
java server/Server
java client/Client
```
